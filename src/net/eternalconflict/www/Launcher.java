package net.eternalconflict.www;

import net.eternalconflict.www.listeners.launcher.ButtonListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Launcher {
    private ButtonListener buttonListener;
    private Dimension dim;
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JButton login;
    private JButton update;
    private JButton register;
    private JProgressBar progress;
    private JPasswordField passwordText;
    private JTextField usernameText;
    public static Launcher instance;
    public Launcher(boolean serverUp) {
        instance = this;
        buttonListener = new ButtonListener();

        dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame = new JFrame("Eternal Conflict Launcher");
        mainFrame.setVisible(true);
        mainFrame.setSize(500, 200);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, dim.height/2-mainFrame.getSize().height/2);
        mainFrame.setResizable(false);

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.GRAY);
        if (serverUp) mainPanel.setBackground(Color.BLUE);



        Icon loginimg = new ImageIcon("resources/launcher/Login.png");
        Icon pressedlogin = new ImageIcon("resources/launcher/Login_pressed.png");
        Icon updateimg = new ImageIcon("resources/launcher/Update.png");
        Icon regesterimg = new ImageIcon("resources/launcher/Regester.png");

        JMenuBar menu = new JMenuBar();
        mainFrame.setJMenuBar(menu);

        JMenu settings = new JMenu("Settings");
        menu.add(settings);

        JMenuItem about = new JMenuItem("About");
        JMenuItem options = new JMenuItem("Options");
        JMenuItem exit = new JMenuItem("Exit");

        login = new JButton(loginimg);
        login.setPressedIcon(pressedlogin);
        login.setPreferredSize(new Dimension(81,23));
        login.addActionListener(buttonListener);
        if (!serverUp) login.setEnabled(false);

        update = new JButton(updateimg);
        update.setPreferredSize(new Dimension(81,23));
        update.addActionListener(buttonListener);

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

        JLabel serverstate = new JLabel(String.valueOf(serverUp));
        if(serverUp)serverstate.setForeground(Color.GREEN);
        if(!serverUp)serverstate.setForeground(Color.RED);

        passwordText = new JPasswordField(16);

        usernameText = new JTextField(16);

        GridBagConstraints constraints = new GridBagConstraints();

        settings.add(about);
        settings.add(options);
        settings.add(exit);


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

        constraints.gridx = 0;
        constraints.gridy = 10;

        mainPanel.add(progress,constraints);
        mainFrame.add(mainPanel,BorderLayout.BEFORE_FIRST_LINE);
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
