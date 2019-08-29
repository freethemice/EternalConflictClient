package net.eternalconflict.www.listeners.launcher;

import net.eternalconflict.www.ConfigFile;
import net.eternalconflict.www.Launcher;
import net.eternalconflict.www.handlers.SocketHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        Object button = e.getSource();
        Launcher launcher = Launcher.instance;
        if (button == launcher.getLogin())
        {
            String password = new String(launcher.getPasswordText().getPassword());
            ConfigFile data = new ConfigFile();
            data.set("username", launcher.getUsernameText().getText());
            data.set("password", password);
            data.set("command", "login");
            SocketHandler.instance.sendRawData(data);
        }
        if (button == Launcher.instance.getRegister())
        {

        }
        if (button == Launcher.instance.getUpdate())
        {

        }
    }
}
