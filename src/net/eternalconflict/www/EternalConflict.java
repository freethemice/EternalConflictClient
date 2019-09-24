package net.eternalconflict.www;

import net.eternalconflict.www.gamewindow.GameWindow;
import net.eternalconflict.www.gamewindow.menus.MainMenu;
import net.eternalconflict.www.handlers.ListenerHandler;
import net.eternalconflict.www.handlers.SocketHandler;
import net.eternalconflict.www.holders.DownloadHolder;
import net.eternalconflict.www.holders.PlayerHolder;
import net.eternalconflict.www.listeners.SocketListener;
import org.lwjglb.engine.GameEngine;
import org.lwjglb.engine.IGameLogic;
import org.lwjglb.engine.Window;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
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
    public static IGameLogic gameLogic;
    public static Window.WindowOptions opts;
    public static GameEngine gameEng;
    public static boolean serverUp;
    public static java.util.Timer mnTimer;
    public static String gameVersion = "0.0.082919";
    public static String recVersion = "0.0.082919";
    public static String libsVersion = "0.0.082919";
    public static void main(String args[]) throws IOException {

        mainPath = new File(".");

        mnTimer = new java.util.Timer();

        new ListenerHandler();
        serverUp = false;

        Launcher launcher = new Launcher();

        System.out.println("Eternal Conflict Console:");
        System.out.println("Checking for updates...");
        launcher.setUpdate();



    }

    public static ConfigFile reloadVersionInfo() {
        ConfigFile versionInfo = new ConfigFile("", "launcher.info");
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
        recVersion = versionInfo.getString("resources");
        libsVersion = versionInfo.getString("libraries");
        gameVersion = versionInfo.getString("game");
        return versionInfo;
    }

    public static void connectToServer() {

        try {
            System.out.println("Connecting to server...");

            new SocketHandler();
            ListenerHandler.instance.addListener(new SocketListener());

            new Thread(SocketHandler.instance).start();
            serverUp =  true;
        } catch (Exception e) {
            int time = 5;
            if (Launcher.instance.getOptions().containsKey("options.retry"))
            {

                int index = Launcher.instance.getOptions().getInteger("options.retry");
                time = index+1;
                if (index == 4) time = 5;
                if (index == 5) time = 10;
                if (index == 6) time = 587;

            }
            if(Launcher.instance.getOptions().containsKey("num.checks")){
                System.out.println("Option unavailable ");
                System.out.println("Retrying in " + time + " minutes...");
            }
            System.out.println("Can't Connect To Server.");
            System.out.println("Retrying in " + time + " minutes...");

            int finalTime = time;
            mnTimer.schedule(new TimerTask() {
                int i = 0;
                @Override
                public void run() {
                    i++;
                    if (i > finalTime - 1)
                    {
                        this.cancel();
                        connectToServer();
                    }
                    else
                    {
                        int count = finalTime - i;
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
                    ConfigFile versionInfo = new ConfigFile("", "launcher.info");
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
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("Connection", "close");
            connection.setRequestProperty("Content-Type", "text/html");
            connection.setRequestProperty("Accept", "text/html");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            connection.connect();
            if (connection.getResponseCode() == 200) {
                try(InputStream inputStream = connection.getInputStream())
                {
                    try(InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8")) {
                        try(BufferedReader in = new BufferedReader(isr))
                        {
                            String line;
                            String config = "";
                            while ((line = in.readLine()) != null) {
                                config = config + line + "\n";
                            }
                            ConfigFile tmp = new ConfigFile();
                            tmp.loadFromString(config);
                            return tmp;
                        }
                    }
                }
            }
        }
        catch (SocketTimeoutException e)
        {
            System.out.println("Timeout: " + e.getMessage());
        }
        catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
            try {
                System.out.println(connection.getResponseCode());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            if (connection != null)
            {
                connection.disconnect();
            }
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
