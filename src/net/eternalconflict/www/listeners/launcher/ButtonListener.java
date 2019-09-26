package net.eternalconflict.www.listeners.launcher;

import net.eternalconflict.www.ConfigFile;
import net.eternalconflict.www.EternalConflict;
import net.eternalconflict.www.Launcher;
import net.eternalconflict.www.OptionsWindow;
import net.eternalconflict.www.enums.ServerInfoEnum;
import net.eternalconflict.www.handlers.SocketHandler;
import net.eternalconflict.www.holders.DownloadHolder;

import javax.swing.*;
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

        if (button == launcher.getSuggestions()){
            String title = "Information Notice!!!";
            String message = "Sugesting your ideas will apply to our turms and conditions. We are not obligated to add your idea. Do you wish to continue?";
            int reply = JOptionPane.showConfirmDialog(null,message, title,JOptionPane.YES_NO_OPTION);
            if(reply == JOptionPane.YES_OPTION)
            {

                String title1 = "Leaveing The Launcher";
                String message1 = "You are about to open your defualt browser and open the suggestions page. Do you wish to continue?";

                int reply1 = JOptionPane.showConfirmDialog(null,message1, title1,JOptionPane.YES_NO_OPTION);
                if(reply1 == JOptionPane.YES_OPTION)
                {
                    try {
                        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                            Desktop.getDesktop().browse(new URI(ServerInfoEnum.WEB.getAddress()));
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                }
            }

        }
        if (button == launcher.getUpdates())
        {
            String title = "Leaveing The Launcher";
            String message = "This will open your default browser and go to our news and updates page. Do you wish to continue?";

            int reply = JOptionPane.showConfirmDialog(null,message, title,JOptionPane.YES_NO_OPTION);
            if(reply == JOptionPane.YES_OPTION)
            {
                try {
                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        Desktop.getDesktop().browse(new URI(ServerInfoEnum.WEB.getAddress()));
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if (button == launcher.getOption())
        {
            new OptionsWindow();
        }

        if (button == launcher.getAbout())
        {
            JFrame about = new JFrame("About");
            JOptionPane.showMessageDialog(about, "About Eternal conflict: Code by Daniel Appleby and Aaron Appleby. All glory goes to Jesus alone." , "About", JOptionPane.INFORMATION_MESSAGE);
        }

        if(button == launcher.getIssues())
        {

            String title = "Leaveing The Launcher";
            String message = "This will open your default browser and go to the bug tracker. Do you wish to continue?";

            int reply = JOptionPane.showConfirmDialog(null,message, title,JOptionPane.YES_NO_OPTION);
            if(reply == JOptionPane.YES_OPTION)
            {
                try {
                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        Desktop.getDesktop().browse(new URI(ServerInfoEnum.ISSUES.getAddress()));
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        }

        if(button == launcher.getExit())
        {
            String message = "You are about to exit the launcher do you wish to continue?";
            String title = "Do you really want to exit the launcher?";

            int reply = JOptionPane.showConfirmDialog(null,message, title,JOptionPane.YES_NO_OPTION);
            if(reply == JOptionPane.YES_OPTION)
            {

                System.exit(0);
            }
        }

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

            String title = "Leaveing the Launcher";
            String message = "This will open your default browser and go to our regestration page. Do you wish to continue?";

            int reply = JOptionPane.showConfirmDialog(null,message, title,JOptionPane.YES_NO_OPTION);
            if(reply == JOptionPane.YES_OPTION)
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
                            EternalConflict.reloadVersionInfo();
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
