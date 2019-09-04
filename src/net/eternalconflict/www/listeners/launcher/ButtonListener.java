package net.eternalconflict.www.listeners.launcher;

import net.eternalconflict.www.ConfigFile;
import net.eternalconflict.www.EternalConflict;
import net.eternalconflict.www.Launcher;
import net.eternalconflict.www.enums.ServerInfoEnum;
import net.eternalconflict.www.handlers.SocketHandler;
import net.eternalconflict.www.holders.DownloadHolder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.TimerTask;

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
        if (button == launcher.getRegister())
        {
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI(ServerInfoEnum.REGISTER.getAddress()));
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
        if (button == launcher.getUpdate())
        {
            launcher.getUpdate().setEnabled(false);
            launcher.getProgress().setVisible(false);

            final int max = launcher.getFilesNeeded().size();
            java.util.Timer timer = new java.util.Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (!launcher.getProgress().isVisible()) {
                        if (launcher.getFilesNeeded().size() > 0) {
                            DownloadHolder downloadHolder = launcher.getFilesNeeded().get(0);
                            launcher.getFilesNeeded().remove(0);
                            int fileS = max - launcher.getFilesNeeded().size();
                            launcher.getMainFrame().setTitle("Downloading: " + fileS + " of " + max);
                            EternalConflict.downloadZip(launcher.getProgress(), downloadHolder);
                        }
                        else
                        {
                            launcher.getUpdate().setEnabled(true);
                            launcher.getUpdate().setVisible(false);
                            launcher.getLogin().setEnabled(true);
                            if (!EternalConflict.serverUp) launcher.getLogin().setEnabled(false);
                            launcher.getMainFrame().setTitle("Eternal Conflict Launcher");
                            launcher.setUpdate();
                            this.cancel();
                        }

                    }
                }
            };
            timer.schedule(timerTask, 10, 10);


        }
    }
}
