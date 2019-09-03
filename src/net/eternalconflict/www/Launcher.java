package net.eternalconflict.www;

import net.eternalconflict.www.handlers.ConsoleHandler;
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
        if (EternalConflict.serverUp) mainPanel.setBackground(Color.BLUE);

        Icon loginimg = new ImageIcon("resources/launcher/Login.png");
        Icon pressedlogin = new ImageIcon("resources/launcher/Login_pressed.png");
        Icon updateimg = new ImageIcon("resources/launcher/Update.png");
        Icon regesterimg = new ImageIcon("resources/launcher/Regester.png");

        JMenuBar menu = new JMenuBar();
        JMenu settings = new JMenu("Settings");

        JMenuItem about = new JMenuItem("About");
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame about = new JFrame("About");
                JOptionPane.showMessageDialog(about, "This is the launcher for Eteran conflict", "About", JOptionPane.INFORMATION_MESSAGE);
            }

        });
        about.setMnemonic(KeyEvent.VK_A);
        KeyStroke cntrlAKey = KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK);
        about.setAccelerator(cntrlAKey);

        JMenuItem options = new JMenuItem("Options");
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame options = new JFrame("Options and Settings");
                JOptionPane.showMessageDialog(options, "This is a place holder for the opthions button.", "Options and settings ", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        options.setMnemonic(KeyEvent.VK_O);
        KeyStroke cntrlOKey = KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK);
        options.setAccelerator(cntrlOKey);

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
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
        if (!EternalConflict.serverUp) login.setEnabled(false);

        update = new JButton(updateimg);
        update.setPreferredSize(new Dimension(81,23));
        update.addActionListener(buttonListener);
        update.setVisible(false);

        register = new JButton(regesterimg);
        register.setPreferredSize(new Dimension(81,23));
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

        JLabel serverstate = new JLabel(String.valueOf(EternalConflict.serverUp));
        if(EternalConflict.serverUp)serverstate.setForeground(Color.GREEN);
        if(!EternalConflict.serverUp)serverstate.setForeground(Color.RED);

        passwordText = new JPasswordField(16);

        usernameText = new JTextField(16);


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

        String status = "offline.";
        if (EternalConflict.serverUp) status = "online.";
        System.out.println("Server Status: " + status);

    }

    public JLabel getInfo() {
        return Info;
    }

    public List<DownloadHolder> getFilesNeeded() {
        return filesNeeded;
    }

    public void setUpdate()
    {
        ConfigFile latest = EternalConflict.getLatestInfo("http://www.firesoftitan.com/ec/info.data");

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
        this.login.setEnabled(!updatebln);
        if (!EternalConflict.serverUp) this.login.setEnabled(false);
        
        mainFrame.pack();

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
