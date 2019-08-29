package net.eternalconflict.www;

import net.eternalconflict.www.gamewindow.GameWindow;
import net.eternalconflict.www.handlers.ListenerHandler;
import net.eternalconflict.www.handlers.SocketHandler;
import net.eternalconflict.www.holders.PlayerHolder;
import net.eternalconflict.www.listeners.ConsoleListener;
import net.eternalconflict.www.listeners.SocketListener;
import org.lwjglb.engine.GameEngine;
import org.lwjglb.engine.IGameLogic;
import org.lwjglb.engine.Window;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class EternalConflict {
    public static boolean  quit = false;
    public static File mainPath;
    public static PlayerHolder playerHolder = null;
    public static BufferedReader bufferedReader = null;
    public static IGameLogic gameLogic;
    public static Window.WindowOptions opts;
    public static GameEngine gameEng;

    public static void main(String args[]) throws IOException {

        mainPath = new File(".");


        bufferedReader= new BufferedReader(new InputStreamReader(System.in));
        new ListenerHandler();
        ListenerHandler.instance.addListener(new SocketListener());
        ListenerHandler.instance.addListener(new ConsoleListener());

        boolean serverUp = false;
        try {
            new SocketHandler();
            SocketHandler.instance.start();
            serverUp =  true;
        } catch (Exception e) {
            System.out.println("Can't Connect To Server.");
        }


        new Launcher(serverUp);

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
