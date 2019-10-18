package net.eternalconflict.www.listeners;

import net.eternalconflict.www.ConfigFile;
import net.eternalconflict.www.EternalConflict;
import net.eternalconflict.www.Launcher;
import net.eternalconflict.www.gamewindow.GameWindow;
import net.eternalconflict.www.handlers.SocketHandler;
import net.eternalconflict.www.holders.PlayerHolder;
import net.eternalconflict.www.interfaces.SocketInterface;
import net.eternalconflict.www.maps.SolarSystemMap;

public class SocketListener implements SocketInterface {
    private long startTime = 0;
    @Override
    public void inputFromSocket(SocketHandler socketHandler, ConfigFile data) {
        if (data.containsKey("command"))
        {
            String command = data.getString("command");
            switch (command)
            {
                case "info":
                    System.out.println("Server Status: " + data.getString("status"));
                    System.out.println("Server Version: " + data.getString("version"));
                    System.out.println("Waiting Room: " + data.getInteger("player"));
                    System.out.println("Online: " + data.getInteger("online"));
                    break;
                case "quit":
                    EternalConflict.quit = true;

                    System.out.println("Connection Closed.");
                    System.out.println("Press any-key to close.");

                    return;
                case "login":
                    if (EternalConflict.playerHolder == null)
                    {
                        EternalConflict.playerHolder = new PlayerHolder(data);
                        System.out.println("You have logged in as: " + EternalConflict.playerHolder.getName());
                    }
                    return;
                case "update":
                    if (GameWindow.instance.isLoaded()) {
                        data.set("command", null);
                        SolarSystemMap ssMapUpdate = new SolarSystemMap(data);
                        EternalConflict.playerHolder.getViewing().updateFromConfig(ssMapUpdate);
                        GameWindow.instance.setMeshChanged(true);
                        //GameWindow.instance.refreshView();
                    }

                    return;
                case "viewing":
                    data.set("command", null);
                    SolarSystemMap solarSystemMap = new SolarSystemMap(data);
                    EternalConflict.playerHolder.setViewing(solarSystemMap);
                    System.out.println("Map set at " + solarSystemMap.getKey());
                    if (EternalConflict.gameEng == null)
                    {
                        Launcher launcher = Launcher.instance;
                        ConfigFile options = launcher.getOptions();
                        options.set("saveinfo", launcher.getSaveInfo().isSelected());
                        if(launcher.getSaveInfo().isSelected())
                        {
                            String password = new String(launcher.getPasswordText().getPassword());
                            password = ConfigFile.encode(password);
                            options.set("username", launcher.getUsernameText().getText());
                            options.set("password", password);
                        }
                        else
                        {
                            options.set("username", "");
                            options.set("password", "");
                        }
                        options.save();
                        EternalConflict.openGame();
                        //Launcher.instance.getMainFrame().setVisible(false);
                        Launcher.instance.getMainPanel().setVisible(false);
                        Launcher.instance.getMainFrame().pack();
                        startTime = System.currentTimeMillis();
                    }
                    return;
            }
        }
        if (data.containsKey("say"))
        {
            String msg = data.getString("say");
            System.out.println(msg);
        }


    }
}
