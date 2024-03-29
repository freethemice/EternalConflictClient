package net.eternalconflict.www;

import net.eternalconflict.www.enums.ServerInfoEnum;
import net.eternalconflict.www.handlers.ConsoleHandler;
import net.eternalconflict.www.holders.DownloadHolder;
import net.eternalconflict.www.listeners.launcher.ButtonListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import static net.eternalconflict.www.EternalConflict.*;

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
    private JMenuItem about;
    private JMenuItem issues;
    private JMenuItem exit;
    private JMenuItem newsupdates;
    private JMenuItem suggestions;
    private JMenuItem alphaKeys;
    private JMenuItem resetPass;
    private JMenuItem resetEmail;
    private JMenu settings;
    private JMenu news;
    private ConfigFile options;
    public static Launcher instance;
    private PrintStream printStream;
    private List<DownloadHolder> filesNeeded;

    public Color dark_blue = new Color(20, 28, 99);
    public Color dark_green = new Color(32, 105, 27);
    public Color dark_red = new Color(175, 23, 25);

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

         Image icon = Toolkit.getDefaultToolkit().getImage("resources/launcher/Eternal_Conflict_Icon.png");
         mainFrame.setIconImage(icon);

        JMenuBar menu = new JMenuBar();
        settings = new JMenu("Settings");
        settings.setToolTipText("Launcher settings.");

        news = new JMenu("Eternal Conflict");
        news.setToolTipText("News");

        newsupdates = new JMenuItem("News and Updates");
        newsupdates.setToolTipText("News and Updates for Eternal Conflict.");
        newsupdates.addActionListener(buttonListener);
        newsupdates.setMnemonic(KeyEvent.VK_N);
        KeyStroke cntrlNKey = KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK);
        newsupdates.setAccelerator(cntrlNKey);

        about = new JMenuItem("About");
        about.setToolTipText("About the Game.");
        about.addActionListener(buttonListener);
        about.setMnemonic(KeyEvent.VK_A);
        KeyStroke cntrlAKey = KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK);
        about.setAccelerator(cntrlAKey);

        option = new JMenuItem("Options");
        option.setToolTipText("Launcher options.");
        option.addActionListener(buttonListener);
        option.setMnemonic(KeyEvent.VK_O);
        KeyStroke cntrlOKey = KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK);
        option.setAccelerator(cntrlOKey);

        issues = new JMenuItem("Report a bug");
        issues.setToolTipText("Report a bug.");
        issues.addActionListener(buttonListener);
        issues.setMnemonic(KeyEvent.VK_R);
        KeyStroke cntrlRKey = KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK);
        issues.setAccelerator(cntrlRKey);

        suggestions = new JMenuItem("Sugest a feture or idea");
        suggestions.setToolTipText("Sugestions and ideas");
        suggestions.addActionListener(buttonListener);
        suggestions.setMnemonic(KeyEvent.VK_S);
        KeyStroke cntrlSKey = KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK);
        suggestions.setAccelerator(cntrlSKey);

        alphaKeys = new JMenuItem("About Alpha Keys");
        alphaKeys.setToolTipText("Alpha key Info");
        alphaKeys.addActionListener(buttonListener);
        alphaKeys.setMnemonic(KeyEvent.VK_K);
        KeyStroke cntrlKKey = KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK);
        alphaKeys.setAccelerator(cntrlKKey);

        resetPass = new JMenuItem("Reset Password");
        resetPass.setToolTipText("Reset your password");
        resetPass.addActionListener(buttonListener);
        resetPass.setMnemonic(KeyEvent.VK_P);
        KeyStroke cntrlPKey = KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK);
        resetPass.setAccelerator(cntrlPKey);

        resetEmail = new JMenuItem("Change Email Address");
        resetEmail.setToolTipText("Change your Email Address");
        resetEmail.addActionListener(buttonListener);
        resetEmail.setMnemonic(KeyEvent.VK_C);
        KeyStroke cntrlCKey = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK);
        resetEmail.setAccelerator(cntrlCKey);

        exit = new JMenuItem("Exit");
        exit.setToolTipText("Exit the Launcher.");
        exit.addActionListener(buttonListener);
        exit.setMnemonic(KeyEvent.VK_E);
        KeyStroke cntrlEKey = KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK);
        exit.setAccelerator(cntrlEKey);


        menu.add(news);
        menu.add(settings);
        news.add(newsupdates);
        news.addSeparator();
        news.add(issues);
        news.add(suggestions);
        settings.add(about);
        settings.add(option);
        settings.add(resetPass);
        settings.add(resetEmail);
        settings.addSeparator();
        settings.add(alphaKeys);
        settings.add(exit);

        mainFrame.setJMenuBar(menu);

        login = new JButton(loginimg);
        login.setPressedIcon(pressedlogin);
        login.setPreferredSize(new Dimension(81,23));
        login.addActionListener(buttonListener);
        login.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,dark_blue, dark_blue));
        login.setEnabled(false);

        update = new JButton(updateimg);
        update.setPreferredSize(new Dimension(81,23));
        update.addActionListener(buttonListener);
        update.setToolTipText("Update - " + gameVersion + " is avalable for download.");
        update.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,dark_blue, dark_blue));
        update.setVisible(false);

        register = new JButton(regesterimg);
        register.setPreferredSize(new Dimension(81,23));
        register.setToolTipText("Click here if you need an account");
        register.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,dark_blue, dark_blue));
        register.addActionListener(buttonListener);

        progress = new JProgressBar();
        progress.setVisible(false);
        progress.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,dark_blue, dark_blue));
        saveInfo = new JCheckBox();
        saveInfo.setToolTipText("Save your login information.");

        saveInfo.setBackground(Color.LIGHT_GRAY);
        saveInfo.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,dark_blue, dark_blue));
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
        saveInfo.setEnabled(true);

        loginInfo = new JLabel("Remember my login");
        loginInfo.setForeground(Color.white);
        JLabel passlabel = new JLabel("Password");
        passlabel.setForeground(Color.white);

        JLabel usernamelabel = new JLabel("Username");
        usernamelabel.setForeground(Color.white);

        JLabel connection = new JLabel("Server connection:");
        connection.setForeground(Color.white);

      //  Info = new JLabel(" ");
      //  Info.setForeground(Color.white);

        serverstate = new JLabel("offline");
        serverstate.setForeground(dark_red);

        passwordText = new JPasswordField(16);
        passwordText.setToolTipText("Enter password");
        passwordText.setForeground(Color.BLACK);
        passwordText.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,dark_blue, dark_blue));

        usernameText = new JTextField(16);
        usernameText.setToolTipText("Enter Username");
        usernameText.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,dark_blue, dark_blue));
        passwordText.setForeground(Color.BLACK);


        JTextArea textArea = new JTextArea (25, 80);
        textArea.setEditable (false);

        ConsoleHandler out = new ConsoleHandler (textArea);
        printStream = new PrintStream(out);
        System.setOut (printStream);


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

       // constraints.gridx = 1;
       // constraints.gridy = 6;
       // mainPanel.add(Info,constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        mainPanel.add(saveInfo, constraints);

        constraints.gridx = 2;
        constraints.gridy = 4;
        mainPanel.add(loginInfo, constraints);

        mainFrame.add(mainPanel,BorderLayout.CENTER);

        mainFrame.add (
                new JScrollPane (
                        textArea,
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.SOUTH);
        textArea.setBackground(Color.white);
        textArea.setForeground(Color.BLACK);
        textArea.setLineWrap(true);
        textArea.setFont(textArea.getFont().deriveFont(Font.ITALIC, textArea.getFont().getSize()));
        textArea.setToolTipText("Console window.");
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
    }
    public void close()
    {
        printStream.close();
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
        if (latest == null)
        {
            System.out.println("Can't Connect To Server!");
            reTryUpdate();
            return;
        }
        if (!latest.containsKey("resources") || !latest.containsKey("libraries") || !latest.containsKey("game"))
        {
            System.out.println("Server is down for mantiance!");
            reTryUpdate();
            return;
        }
        EternalConflict.reloadVersionInfo();

        progress.setVisible(false);
        filesNeeded.clear();
        boolean updatebln = false;
        if (!EternalConflict.recVersion.equals(latest.getString("resources")))
        {
            String downloadFile = latest.getString("resources_download");
            DownloadHolder downloadHolder = new DownloadHolder(downloadFile, latest.getString("resources"), "resources");
            filesNeeded.add(downloadHolder);

            System.out.println("New resources are out, " + recVersion + ", you need to update");
            System.out.println(downloadFile);
            updatebln = true;
        }
        if (!EternalConflict.libsVersion.equals(latest.getString("libraries")))
        {
            String downloadFile = latest.getString("libraries_download");
            DownloadHolder downloadHolder = new DownloadHolder(downloadFile, latest.getString("libraries"), "libraries");
            filesNeeded.add(downloadHolder);

            System.out.println("New libraries have beeen released," + libsVersion + ", you need to update.");
            System.out.println(downloadFile);
            updatebln = true;
        }
        if (!EternalConflict.gameVersion.equals(latest.getString("game")))
        {
            String downloadFile = latest.getString("game_download");
            DownloadHolder downloadHolder = new DownloadHolder(downloadFile, latest.getString("game"), "game");
            filesNeeded.add(downloadHolder);
            System.out.println("A new game update hase been released," + gameVersion + ", you need to update." );
            System.out.println(downloadFile);
            updatebln = true;
        }
        else
        {
            System.out.println("All Files are up to date");
            System.out.println("Game Version: " + EternalConflict.gameVersion + " (" + latest.getString("game") + ")");
            System.out.println("Resources Version: " +  EternalConflict.recVersion + " (" + latest.getString("resources") + ")");
            System.out.println("Libraries Version: " + EternalConflict.libsVersion + " (" + latest.getString("libraries") + ")");
        }
        this.update.setVisible(updatebln);
        mainFrame.pack();
        if (!updatebln)
        {
            connectToServer();
        }
    }

    private void reTryUpdate() {
        int time = 5;
        if (Launcher.instance.getOptions().containsKey("options.retry"))
        {
            int index = Launcher.instance.getOptions().getInteger("options.retry");
            time = index+1;
            if (index == 4) time = 5;
            if (index == 5) time = 10;
            if (index == 6) time = 587;

        }
        System.out.println("Retrying in " + time + " minutes...");

        int finalTime = time;
        mnTimer.schedule(new TimerTask() {
            int i = 0;
            @Override
            public void run() {
                i++;
                if (i > finalTime - 1)
                {
                    this.cancel();
                    setUpdate();
                }
                else
                {
                    int count = finalTime - i;
                    System.out.println("Retrying in " + count + " minutes...");
                }
            }
        },1000 * 60, 1000 *  60);
    }


    public ButtonListener getButtonListener() { return buttonListener; }

    public Dimension getDim() { return dim; }

    public JFrame getMainFrame() { return mainFrame; }

    public JPanel getMainPanel() { return mainPanel; }

    public JButton getLogin() { return login; }

    public JButton getUpdate() { return update; }

    public JButton getRegister() { return register; }

    public JProgressBar getProgress() { return progress; }

    public JPasswordField getPasswordText() { return passwordText; }

    public JTextField getUsernameText() { return usernameText; }

    public JMenuItem getOption() { return option; }

    public JMenuItem getAbout() { return about; }

    public  JMenuItem getIssues(){ return issues; }

    public JMenuItem getExit(){return exit;}

    public JMenu getSettings(){return settings;}

    public JMenu getNews(){return news;}

    public JMenuItem getNewsupdates(){return newsupdates;}

    public JMenuItem getSuggestions(){return suggestions;}

    public JMenuItem getAlphaKeys(){return alphaKeys;}

    public JMenuItem getResetPass(){return resetPass;}

    public JMenuItem getResetEmail(){return resetEmail;}
}
