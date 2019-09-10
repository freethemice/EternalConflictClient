package net.eternalconflict.www;

import net.eternalconflict.www.gamewindow.GameWindow;
import net.eternalconflict.www.handlers.ListenerHandler;
import net.eternalconflict.www.handlers.SocketHandler;
import net.eternalconflict.www.holders.DownloadHolder;
import net.eternalconflict.www.holders.PlayerHolder;
import net.eternalconflict.www.listeners.ConsoleListener;
import net.eternalconflict.www.listeners.SocketListener;
import org.lwjglb.engine.GameEngine;
import org.lwjglb.engine.IGameLogic;
import org.lwjglb.engine.Window;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.TimerTask;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class EternalConflict {
    public static boolean  quit = false;
    public static File mainPath;
    public static PlayerHolder playerHolder = null;
    public static BufferedReader bufferedReader = null;
    public static IGameLogic gameLogic;
    public static Window.WindowOptions opts;
    public static GameEngine gameEng;
    public static ConfigFile versionInfo;
    public static boolean serverUp;
    public static java.util.Timer mnTimer;
    public static String gameVersion = "0.0.090219";
    public static String recVersion = "0.0.090219_Rec";
    public static String libsVersion = "0.0.0902919_Libs";
    public static void main(String args[]) throws IOException {

        mainPath = new File(".");
        mnTimer = new java.util.Timer();

        bufferedReader= new BufferedReader(new InputStreamReader(System.in));
        new ListenerHandler();
        ListenerHandler.instance.addListener(new ConsoleListener());
        serverUp = false;

        versionInfo = new ConfigFile("", "launcher.info");
        versionInfo.loadFromFile();

        if (!versionInfo.containsKey("resources"))
        {
            versionInfo.set("resources", recVersion);
        }
        if (!versionInfo.containsKey("libraries"))
        {
            versionInfo.set("libraries", libsVersion);
        }
        if (!versionInfo.containsKey("game"))
        {
            versionInfo.set("game", gameVersion);
        }
        versionInfo.save();




        Launcher launcher = new Launcher();
        launcher.setUpdate();




/*
        String line=null;
        System.out.println("Client Address : "+ SocketHandler.instance.getAddress());
        System.out.println("Enter user name:");

        try{
            line=bufferedReader.readLine();
            while(!quit){
                ListenerHandler.instance.fireEvent(ListenerEnum.CONSOLE, line);
                if (!quit) {
                    line = bufferedReader.readLine();
                }
            }
            ConfigFile data = new ConfigFile();
            data.set("command", "quit");
            SocketHandler.instance.sendRawData(data);
        }
        catch(IOException e){
            e.printStackTrace();
            //System.out.println("Socket read Error");
        }
        finally{
            SocketHandler.instance.close();
            bufferedReader.close();
            System.out.println("Connection Closed");

        }*/

    }

    public static void connectToServer() {

        try {
            System.out.println("Eternal Conflict Console:");
            System.out.println("Current Game Version: " + gameVersion);
            System.out.println("Current Game Resources Version: " + recVersion);
            System.out.println("Current Game Libraries Version: " + libsVersion);
            System.out.println("Connecting to server...");
            new SocketHandler();
            ListenerHandler.instance.addListener(new SocketListener());
            new Thread(SocketHandler.instance).start();
            serverUp =  true;
        } catch (Exception e) {
            System.out.println("Can't Connect To Server.");
            System.out.println("Retrying in 5 minutes...");
            
            mnTimer.schedule(new TimerTask() {
                int i = 0;
                @Override
                public void run() {
                    i++;
                    if (i > 4)
                    {
                        this.cancel();
                        connectToServer();
                    }
                    else
                    {
                        int count = 5 - i;
                        System.out.println("Retrying in " + count + " minutes...");
                    }
                }
            },1000 * 60, 1000 *  60);
        }
        finally {
            Launcher.instance.updateStatus();

        }
    }



    public static void extractZip(String fileName, DownloadHolder downloadHolder, JProgressBar jProgressBar)
    {
        jProgressBar.setMaximum(100000);
        jProgressBar.setValue(0);
        jProgressBar.setVisible(true);

        Launcher launcher = Launcher.instance;
        launcher.getMainFrame().setTitle("Installing update, please wait...");
        Runnable updatethread = new Runnable() {
            public void run() {
                try (ZipFile file = new ZipFile(fileName)) {
                    int numberOfEntries = file.size();
                    FileSystem fileSystem = FileSystems.getDefault();
                    //Get file entries
                    Enumeration<? extends ZipEntry> entries = file.entries();


                    //We will unzip files in this folder
                    String uncompressedDirectory = ""; //
                    //Files.createDirectory(fileSystem.getPath(uncompressedDirectory));
                    int files = 0;
                    //Iterate over entries
                    while (entries.hasMoreElements()) {
                        files++;
                        final int currentProgress = (int) ((((double) files) / ((double) numberOfEntries)) * 100000d);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                jProgressBar.setValue(currentProgress);
                            }
                        });
                        ZipEntry entry = entries.nextElement();
                        //If directory then create a new directory in uncompressed folder
                        if (entry.isDirectory()) {
                            System.out.println("Creating Directory:" + uncompressedDirectory + entry.getName());
                            Files.createDirectories(fileSystem.getPath(uncompressedDirectory + entry.getName()));
                        }
                        //Else create the file
                        else {
                            InputStream is = file.getInputStream(entry);
                            BufferedInputStream bis = new BufferedInputStream(is);
                            String uncompressedFileName = uncompressedDirectory + entry.getName();
                            Path uncompressedFilePath = fileSystem.getPath(uncompressedFileName);
                            if (Files.exists(uncompressedFilePath)) Files.delete(uncompressedFilePath);
                            Files.createFile(uncompressedFilePath);
                            FileOutputStream fileOutput = new FileOutputStream(uncompressedFileName);
                            while (bis.available() > 0) {
                                fileOutput.write(bis.read());
                            }
                            fileOutput.close();
                            System.out.println("Written :" + entry.getName());
                        }
                    }
                    versionInfo.set(downloadHolder.getKey(), downloadHolder.getVersion());
                    versionInfo.save();
                    jProgressBar.setValue(0);
                    jProgressBar.setVisible(false);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }};
        new Thread(updatethread).start();
    }
    public static void downloadZip(JProgressBar jProgressBar, DownloadHolder downloadHolder)
    {
        jProgressBar.setMaximum(100000);
        jProgressBar.setValue(0);
        jProgressBar.setVisible(true);

        Runnable updatethread = new Runnable() {
            public void run() {
                try {

                    URL url = new URL(downloadHolder.getUrl());
                    HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
                    long completeFileSize = httpConnection.getContentLength();

                    BufferedInputStream in = new java.io.BufferedInputStream(httpConnection.getInputStream());
                    FileOutputStream fos = new java.io.FileOutputStream(
                            "package.zip");
                    BufferedOutputStream bout = new BufferedOutputStream(
                            fos, 1024);
                    byte[] data = new byte[1024];
                    long downloadedFileSize = 0;
                    int x = 0;
                    while ((x = in.read(data, 0, 1024)) >= 0) {
                        downloadedFileSize += x;

                        // calculate progress
                        final int currentProgress = (int) ((((double)downloadedFileSize) / ((double)completeFileSize)) * 100000d);

                        // update progress bar
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                jProgressBar.setValue(currentProgress);
                            }
                        });

                        bout.write(data, 0, x);
                    }
                    bout.close();
                    in.close();
                    extractZip("package.zip", downloadHolder, jProgressBar);
                } catch (FileNotFoundException e) {
                } catch (IOException e) {
                }
            }
        };
        new Thread(updatethread).start();
    }
    public static ConfigFile getLatestInfo(String urlString)
    {
        try {

            URL url = new URL(urlString);

            // read text returned by server
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;
            String config = "";
            while ((line = in.readLine()) != null) {
                config = config + line + "\n";
            }
            in.close();
            ConfigFile tmp = new ConfigFile();
            tmp.loadFromString(config);
            return tmp;

        }
        catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }
        return null;
    }
    public static void openGame()
    {
        try {
            boolean vSync = true;
            gameLogic = new GameWindow();
            opts = new Window.WindowOptions();
            opts.cullFace = false;
            opts.showFps = true;
            opts.compatibleProfile = true;
            opts.antialiasing = true;
            opts.frustumCulling = false;
            gameEng = new GameEngine("Eternal Conflict", vSync, opts, gameLogic);
            gameEng.start();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }

}
