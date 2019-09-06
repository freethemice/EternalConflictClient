package net.eternalconflict.www;

import net.eternalconflict.www.enums.ServerInfoEnum;
import net.eternalconflict.www.handlers.ConsoleHandler;
import net.eternalconflict.www.handlers.SocketHandler;
import net.eternalconflict.www.holders.DownloadHolder;
import net.eternalconflict.www.listeners.launcher.ButtonListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class Launcher {
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
    public JCheckBox serverCheck1;
    public JCheckBox serverCheck2;
    public JCheckBox serverCheck3;
    public JCheckBox serverCheck5;
    public JCheckBox serverCheck10;
    public JCheckBox checkNum1;
    public JCheckBox checkNum2;
    public JCheckBox checkNum3;
    public JCheckBox checkNum5;
    public JCheckBox checkNum10;
    public static boolean serverUp;
    public static java.util.Timer mnTimer;
    public static Launcher instance;
    private List<DownloadHolder> filesNeeded;
    public Launcher() {
        instance = this;

        filesNeeded = new ArrayList<DownloadHolder>();
        buttonListener = new ButtonListener();

        dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame = new JFrame("Eternal Conflict Launcher");
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


        JMenuItem options = new JMenuItem("Options");
        options.setToolTipText("Launcher options.");
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame options = new JFrame("Options and Settings");
                options.setSize(900,700);
                options.setResizable(false);
                options.setBackground(Color.lightGray);
                options.setVisible(true);
                options.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel optionsPanel = new JPanel();

                JLabel checkStat = new JLabel("Server checks status: ");

                serverCheck1 = new JCheckBox("Every 1 minutes");
                serverCheck1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                    }
                });
                serverCheck2 = new JCheckBox("Every 2 minutes");
                serverCheck2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });
                serverCheck3 = new JCheckBox("Every 3 minutes");
                serverCheck3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });
                serverCheck5 = new JCheckBox("Every 5 minutes");
                serverCheck5.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });
                serverCheck10 = new JCheckBox("Every 10 mimutes");
                serverCheck10.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });

                JLabel checktime = new JLabel("Number of server checks per minute");

                checkNum1 = new JCheckBox("1 Times");
                checkNum1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });
                checkNum2 = new JCheckBox("2 time");
                checkNum2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });
                checkNum3 = new JCheckBox("3 times");
                checkNum3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });
                checkNum5 = new JCheckBox("5 times");
                checkNum5.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });
                checkNum10 = new JCheckBox("10 times");
                checkNum10.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });

                GridBagConstraints optConstraints = new GridBagConstraints();

                optConstraints.insets = new Insets(3, 3, 3, 3);

                optConstraints.gridx = 1;
                optConstraints.gridy = 1;
                optionsPanel.add(checkStat,optConstraints);

                optConstraints.gridx = 1;
                optConstraints.gridy = 2;
                optionsPanel.add(serverCheck1,optConstraints);
                optConstraints.gridx = 2;
                optConstraints.gridy = 2;
                optionsPanel.add(serverCheck2,optConstraints);
                optConstraints.gridx = 3;
                optConstraints.gridy = 2;
                optionsPanel.add(serverCheck3,optConstraints);
                optConstraints.gridx = 4;
                optConstraints.gridy = 2;
                optionsPanel.add(serverCheck5,optConstraints);
                optConstraints.gridx = 5;
                optConstraints.gridy = 2;
                optionsPanel.add(serverCheck10,optConstraints);

                optConstraints.gridx = 2;
                optConstraints.gridy = 2;
                optionsPanel.add(checktime,optConstraints);

                optConstraints.gridx = 2;
                optConstraints.gridy = 3;
                optionsPanel.add(checkNum1,optConstraints);

                optConstraints.gridx = 3;
                optConstraints.gridy = 3;
                optionsPanel.add(checkNum2,optConstraints);

                optConstraints.gridx = 4;
                optConstraints.gridy = 3;
                optionsPanel.add(checkNum3,optConstraints);

                optConstraints.gridx = 5;
                optConstraints.gridy = 3;
                optionsPanel.add(checkNum5,optConstraints);

                optConstraints.gridx = 6;
                optConstraints.gridy = 3;
                optionsPanel.add(checkNum10,optConstraints);
                options.add(optionsPanel, BorderLayout.CENTER );

            }
        });
        options.setMnemonic(KeyEvent.VK_O);
        KeyStroke cntrlOKey = KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK);
        options.setAccelerator(cntrlOKey);




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
        settings.add(options);
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

        JLabel passlabel = new JLabel("Password");
        passlabel.setForeground(Color.BLACK);

        JLabel usernamelabel = new JLabel("Username");
        usernamelabel.setForeground(Color.BLACK);

        JLabel connection = new JLabel("Server connection:");
        connection.setForeground(Color.BLACK);

        Info = new JLabel(" ");
        Info.setForeground(Color.BLACK);

        serverstate = new JLabel("offline");
        serverstate.setForeground(Color.RED);

        passwordText = new JPasswordField(16);
        passwordText.setToolTipText("Enter password");

        usernameText = new JTextField(16);
        usernameText.setToolTipText("Enter Username");


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
        constraints.gridy = 4;
        mainPanel.add(Info,constraints);

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

        mainFrame.pack();
        mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, dim.height/2-mainFrame.getSize().height/2);

        //updateStatus();


    }
    public void updateStatus()
    {
        mainPanel.setBackground(Color.LIGHT_GRAY);
        if (EternalConflict.serverUp) mainPanel.setBackground(Color.BLUE);

        if(EternalConflict.serverUp)serverstate.setForeground(Color.GREEN);
        if(!EternalConflict.serverUp)serverstate.setForeground(Color.RED);

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
        if (!updatebln)
        {
            EternalConflict.connectToServer();
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
}
