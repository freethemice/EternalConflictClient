package net.eternalconflict.www;

import net.eternalconflict.www.enums.ServerInfoEnum;
import net.eternalconflict.www.handlers.ConsoleHandler;
import net.eternalconflict.www.holders.DownloadHolder;
import net.eternalconflict.www.listeners.launcher.ButtonListener;

import javax.swing.*;
import javax.swing.JTabbedPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


import static net.eternalconflict.www.EternalConflict.connectToServer;
import static net.eternalconflict.www.EternalConflict.gameVersion;

public class Launcher extends JFrame {
    private ButtonListener buttonListener;
    private Dimension dim;
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JButton login;
    private JButton update;
    private JButton register;
    private JLabel Info;
    private JProgressBar progress;
    private JPasswordField passwordText;
    private JTextField usernameText;
    private JLabel serverstate;
    private JLabel loginInfo;
    private JCheckBox saveInfo;
    private JMenuItem option;
    private ConfigFile options;
    private JTabbedPane launchertab;
    public static Launcher instance;

    private List<DownloadHolder> filesNeeded;

    Color dark_blue = new Color(20, 28, 99);
    Color dark_green = new Color(32, 105, 27);
    Color dark_red = new Color(175, 23, 25);

    public Launcher() {
        options = new ConfigFile("", "options.info");
        instance = this;

        filesNeeded = new ArrayList<DownloadHolder>();
        buttonListener = new ButtonListener();

        dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame = new JFrame("Eternal Conflict Launcher: " + gameVersion);
        mainFrame.setVisible(true);
        mainFrame.setSize(500, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        mainFrame.setResizable(false);

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);

        Icon loginimg = new ImageIcon("resources/launcher/Login.png");
        Icon pressedlogin = new ImageIcon("resources/launcher/Login_pressed.png");
        Icon updateimg = new ImageIcon("resources/launcher/Update.png");
        Icon regesterimg = new ImageIcon("resources/launcher/Regester.png");

        JMenuBar menu = new JMenuBar();
        JMenu settings = new JMenu("Settings");
        settings.setToolTipText("Launcher settings.");

        JMenuItem about = new JMenuItem("About");
        about.setToolTipText("About the Game.");
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame about = new JFrame("About");
                JOptionPane.showMessageDialog(about, "About Eternal conflict: Code by Daniel Appleby and Aaron Appleby. " , "About", JOptionPane.INFORMATION_MESSAGE);
            }

        });
        about.setMnemonic(KeyEvent.VK_A);
        KeyStroke cntrlAKey = KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK);
        about.setAccelerator(cntrlAKey);

        // Options window

        option = new JMenuItem("Options");
        option.setToolTipText("Launcher options.");
        option.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new OptionsWindow();
            }
        });
        option.setMnemonic(KeyEvent.VK_O);
        KeyStroke cntrlOKey = KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK);
        option.setAccelerator(cntrlOKey);

        JMenuItem issues = new JMenuItem("Report a bug");
        issues.setToolTipText("Report a bug.");
        issues.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = "Leaveing The Launcher";
                String message = "You are about to leave the launcher and go to a website. Do you wish to continue?";

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
        });
        issues.setMnemonic(KeyEvent.VK_R);
        KeyStroke cntrlRKey = KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK);
        issues.setAccelerator(cntrlRKey);

        JMenuItem exit = new JMenuItem("Exit");
        exit.setToolTipText("Exit the Launcher.");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String message = "You are about to exit the launcher do you wish to continue?";
                String title = "Do you really want to exit the launcher?";

                int reply = JOptionPane.showConfirmDialog(null,message, title,JOptionPane.YES_NO_OPTION);
                if(reply == JOptionPane.YES_OPTION)
                {
                    System.exit(0);
                }
            }
        });
        exit.setMnemonic(KeyEvent.VK_E);
        KeyStroke cntrlEKey = KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK);
        exit.setAccelerator(cntrlEKey);

        menu.add(settings);
        settings.add(about);
        settings.add(option);
        settings.add(issues);
        settings.addSeparator();
        settings.add(exit);

        mainFrame.setJMenuBar(menu);

        login = new JButton(loginimg);
        login.setPressedIcon(pressedlogin);
        login.setPreferredSize(new Dimension(81,23));
        login.addActionListener(buttonListener);
        login.setEnabled(false);

        update = new JButton(updateimg);
        update.setPreferredSize(new Dimension(81,23));
        update.addActionListener(buttonListener);
        update.setToolTipText("An update is available.");
        update.setVisible(false);

        register = new JButton(regesterimg);
        register.setPreferredSize(new Dimension(81,23));
        register.setToolTipText("If you do not have an account click this button.");
        register.addActionListener(buttonListener);

        progress = new JProgressBar();

        saveInfo = new JCheckBox();
        saveInfo.setToolTipText("Save your login information.");
        saveInfo.setBackground(Color.LIGHT_GRAY);
        if(!EternalConflict.serverUp) saveInfo.setEnabled(false);
        if(EternalConflict.serverUp) saveInfo.setEnabled(true);
        saveInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                options.set("saveinfo", false);
                options.set("username", "");
                options.set("password", "");
                options.save();

            }
        });
        saveInfo.setSelected(false);


        loginInfo = new JLabel("Remember my login");
        loginInfo.setForeground(Color.white);
        JLabel passlabel = new JLabel("Password");
        passlabel.setForeground(Color.white);

        JLabel usernamelabel = new JLabel("Username");
        usernamelabel.setForeground(Color.white);

        JLabel connection = new JLabel("Server connection:");
        connection.setForeground(Color.white);

        Info = new JLabel(" ");
        Info.setForeground(Color.white);

        serverstate = new JLabel("offline");
        serverstate.setForeground(dark_red);

        passwordText = new JPasswordField(16);
        passwordText.setToolTipText("Enter password");
        passwordText.setForeground(Color.BLACK);

        usernameText = new JTextField(16);
        usernameText.setToolTipText("Enter Username");
        passwordText.setForeground(Color.BLACK);

        JTextArea textArea = new JTextArea (25, 80);
        textArea.setEditable (false);

        ConsoleHandler out = new ConsoleHandler (textArea);
        System.setOut (new PrintStream(out));

        GridBagConstraints constraints = new GridBagConstraints();


        constraints.insets = new Insets(3, 3, 3, 3);
        constraints.gridx = 0;
        constraints.gridy = 1;
        mainPanel.add(login,constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        mainPanel.add(register,constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        mainPanel.add(update,constraints);

        constraints.gridx = 2;
        constraints.gridy = 2;
        mainPanel.add(passwordText,constraints);

        constraints.gridx = 1;
        constraints.gridy= 1;
        mainPanel.add(usernamelabel,constraints);

        constraints.gridx = 2;
        constraints.gridy = 1;
        mainPanel.add(usernameText,constraints);

        constraints.gridx = 3;
        constraints.gridy = 1;
        mainPanel.add(connection, constraints);

        constraints.gridx = 3;
        constraints.gridy = 2;
        mainPanel.add(serverstate,constraints);

        constraints.gridx = 1;
        constraints.gridy= 2;
        mainPanel.add(passlabel,constraints);

        constraints.gridx = 2;
        constraints.gridy = 3;
        mainPanel.add(progress,constraints);

        constraints.gridx = 1;
        constraints.gridy = 6;
        mainPanel.add(Info,constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        mainPanel.add(saveInfo, constraints);

        constraints.gridx = 2;
        constraints.gridy = 4;
        mainPanel.add(loginInfo, constraints);

        //constraints.gridx = 1;
        //constraints.gridy = 3;
        //mainFrame.setLayout (new BorderLayout ());

        mainFrame.add(mainPanel,BorderLayout.CENTER);

        mainFrame.add (
                new JScrollPane (
                        textArea,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.SOUTH);
        textArea.setBackground(Color.white);
        textArea.setForeground(Color.BLACK);
        textArea.setLineWrap(true);
        textArea.setFont(textArea.getFont().deriveFont(Font.ITALIC, textArea.getFont().getSize()));
        mainFrame.pack();
        mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, dim.height/2-mainFrame.getSize().height/2);

        try {
            if (options.containsKey("saveinfo")) {
                if (options.getBoolean("saveinfo")) {
                    if (options.containsKey("username") && options.containsKey("password")) {
                        getUsernameText().setText(options.getString("username"));
                        String password = options.getString("password");
                        password = ConfigFile.decode(password);
                        getPasswordText().setText(password);
                    }
                    saveInfo.setSelected(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //updateStatus();


    }

    public JCheckBox getSaveInfo() {
        return saveInfo;
    }

    public ConfigFile getOptions() {
        return options;
    }

    public void updateStatus()
    {
        mainPanel.setBackground(Color.LIGHT_GRAY);
        saveInfo.setBackground(Color.LIGHT_GRAY);
        if (EternalConflict.serverUp) {
            mainPanel.setBackground(dark_blue);
            saveInfo.setBackground(dark_blue);
        }

        if(EternalConflict.serverUp)serverstate.setForeground(dark_green);
        if(!EternalConflict.serverUp)serverstate.setForeground(dark_red);

        if (EternalConflict.serverUp) {
            login.setToolTipText("Login and play the game.");
            login.setEnabled(true);
        }
        if (!EternalConflict.serverUp) {
            login.setToolTipText("Server connection problem.");
            this.login.setEnabled(false);
        }

        String status = "offline.";
        if (EternalConflict.serverUp) status = "online.";
        serverstate.setText(status);
        System.out.println("Server Status: " + status);
        mainFrame.pack();
    }
    public JLabel getInfo() {
        return Info;
    }

    public List<DownloadHolder> getFilesNeeded() {
        return filesNeeded;
    }

    public void setUpdate()
    {
        ConfigFile latest = EternalConflict.getLatestInfo(ServerInfoEnum.VERSIONS.getAddress());

        progress.setVisible(false);
        filesNeeded.clear();
        boolean updatebln = false;
        if (!EternalConflict.versionInfo.getString("resources").equals(latest.getString("resources")))
        {
            String downloadFile = latest.getString("resources_download");
            DownloadHolder downloadHolder = new DownloadHolder(downloadFile, latest.getString("resources"), "resources");
            filesNeeded.add(downloadHolder);

            System.out.println("Resources update needed: ");
            System.out.println(downloadFile);
            updatebln = true;
        }
        if (!EternalConflict.versionInfo.getString("libraries").equals(latest.getString("libraries")))
        {
            String downloadFile = latest.getString("libraries_download");
            DownloadHolder downloadHolder = new DownloadHolder(downloadFile, latest.getString("libraries"), "libraries");
            filesNeeded.add(downloadHolder);

            System.out.println("Libraries update needed: ");
            System.out.println(downloadFile);
            updatebln = true;
        }
        if (!EternalConflict.versionInfo.getString("game").equals(latest.getString("game")))
        {
            String downloadFile = latest.getString("game_download");
            DownloadHolder downloadHolder = new DownloadHolder(downloadFile, latest.getString("game"), "game");
            filesNeeded.add(downloadHolder);

            System.out.println("Game update needed: " );
            System.out.println(downloadFile);
            updatebln = true;
        }
        this.update.setVisible(updatebln);
        mainFrame.pack();

        if (!updatebln)
        {
            connectToServer();
        }

        //this.login.setEnabled(!updatebln);

        //updateStatus();
    }
    public ButtonListener getButtonListener() {
        return buttonListener;
    }

    public Dimension getDim() {
        return dim;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getLogin() {
        return login;
    }

    public JButton getUpdate() {
        return update;
    }

    public JButton getRegister() {
        return register;
    }

    public JProgressBar getProgress() {
        return progress;
    }

    public JPasswordField getPasswordText() {
        return passwordText;
    }

    public JTextField getUsernameText() {
        return usernameText;
    }

    public JMenuItem getOption()
    {
        return option;
    }
}
