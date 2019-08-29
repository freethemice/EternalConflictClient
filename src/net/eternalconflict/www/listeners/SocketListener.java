package net.eternalconflict.www.listeners;

import net.eternalconflict.www.ConfigFile;
import net.eternalconflict.www.EternalConflict;
import net.eternalconflict.www.Launcher;
import net.eternalconflict.www.handlers.SocketHandler;
import net.eternalconflict.www.holders.PlayerHolder;
import net.eternalconflict.www.interfaces.SocketInterface;
import net.eternalconflict.www.maps.SolarSystemMap;

import java.io.IOException;

public class SocketListener implements SocketInterface {
    @Override
    public void inputFromSocket(SocketHandler socketHandler, ConfigFile data) {
        if (data.containsKey("command"))
        {
            String command = data.getString("command");
            switch (command)
            {
                case "quit":
                    EternalConflict.quit = true;
                    try {
                        System.out.println("Connection Closed.");
                        System.out.println("Press any-key to close.");
                        EternalConflict.bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                case "login":
                    if (EternalConflict.playerHolder == null)
                    {
                        EternalConflict.playerHolder = new PlayerHolder(data);
                        System.out.println("You have logged in as: " + EternalConflict.playerHolder.getName());
                    }
                    return;
                case "viewing":
                    data.set("command", null);
                    SolarSystemMap solarSystemMap = new SolarSystemMap(data);
                    EternalConflict.playerHolder.setViewing(solarSystemMap);
                    System.out.println("Map set at " + solarSystemMap.getKey());
                    if (EternalConflict.gameEng == null)
                    {
                        EternalConflict.openGame();
                        Launcher.instance.getMainFrame().setVisible(false);
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
