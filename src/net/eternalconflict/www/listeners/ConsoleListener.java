package net.eternalconflict.www.listeners;

import net.eternalconflict.www.ConfigFile;
import net.eternalconflict.www.EternalConflict;
import net.eternalconflict.www.handlers.SocketHandler;
import net.eternalconflict.www.interfaces.ConsoleInterface;

public class ConsoleListener implements ConsoleInterface {
    private String username = null;
    @Override
    public void inputFromConsole(String msg) {
        if (msg.equalsIgnoreCase("quit")) {
            if (msg.equalsIgnoreCase("quit"))
            {
                ConfigFile data = new ConfigFile();
                data.set("command", "quit");
                SocketHandler.instance.sendRawData(data);
            }
            return;
        }

        if (username == null)
        {
            username = msg;
            System.out.println("Please enter your password!");
        }
        else if (EternalConflict.playerHolder == null)
        {
            ConfigFile data = new ConfigFile();
            data.set("username", username);
            data.set("password", msg);
            data.set("command", "login");
            SocketHandler.instance.sendRawData(data);
        }
        else
        {
            ConfigFile out = new ConfigFile();
            out.set("say", msg);
            //You have logged in lets play!!!!
            SocketHandler.instance.sendRawData(out);
        }

    }
}
