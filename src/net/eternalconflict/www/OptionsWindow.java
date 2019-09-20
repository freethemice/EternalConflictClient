package net.eternalconflict.www;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class OptionsWindow extends JFrame{

    public OptionsWindow()
    {

        JComboBox serverCheck;
        JComboBox checkNum;
        JComboBox launcherStat;
        JCheckBox vsyncopt;
        ConfigFile mainOptionsFile = Launcher.instance.getOptions();

        JFrame options = new JFrame("Launcher Options");
        Image icon = Toolkit.getDefaultToolkit().getImage("resources/textures/icons/Eternal_Conflict_Icon.png");
        options.setIconImage(icon);
        options.setDefaultCloseOperation(HIDE_ON_CLOSE);
        options.setSize(900,700);
        options.setResizable(false);
        if(EternalConflict.serverUp)options.setBackground(Color.BLUE);
        if(!EternalConflict.serverUp)options.setBackground(Color.LIGHT_GRAY);

        JPanel optionsPanel = new JPanel();

        JLabel checkStat = new JLabel("On failure to connect retry? ");

        serverCheck = new JComboBox();
        serverCheck.addItem("every 1 minute");
        serverCheck.addItem("every 2 minutes");
        serverCheck.addItem("every 3 minutes");
        serverCheck.addItem("every 5 minutes");
        serverCheck.addItem("every 10 minutes");
        serverCheck.addItem("every 587 minutes");
        serverCheck.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                mainOptionsFile.set("options.retry", serverCheck.getSelectedIndex());
                mainOptionsFile.save();
            }
        });
        if (mainOptionsFile.containsKey("options.retry"))
        {
            int index = mainOptionsFile.getInteger("options.retry");
            serverCheck.setSelectedIndex(index);
        }
        else
        {
            serverCheck.setSelectedIndex(4);
        }

        JLabel checktime = new JLabel("Number of server checks per minute");

        checkNum = new JComboBox();
        checkNum.addItem("1");
        checkNum.addItem("2");
        checkNum.addItem("3");
        checkNum.addItem("5");
        checkNum.addItem("10");
        checkNum.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                mainOptionsFile.set("num.check", checkNum.getSelectedIndex());
                mainOptionsFile.save();
            }
        });
        if (mainOptionsFile.containsKey("num.checks"))
        {
            int index = mainOptionsFile.getInteger("num.checks");
            checkNum.setSelectedIndex(index);
        }
        else
        {
            checkNum.setSelectedIndex(4);
        }

        JLabel vsyncLabel = new JLabel ("Vsync on/off.");

        vsyncopt = new JCheckBox();
        vsyncopt.setToolTipText("Turns Vsync on and off.");
        if(vsyncopt.isSelected()){
            boolean vSync = true;
        }
        if(!vsyncopt.isSelected()){
            boolean vSync = false;
        }

        JLabel launcherState = new JLabel ("Launcher state after game is closed.");

        launcherStat = new JComboBox();
        launcherStat.addItem("Close launcher.");
        launcherStat.addItem("Keep launcher open");
        launcherStat.addItem("Open Launcher only if there is an error.");
        launcherStat.addItem("Let the little green men in your computer choose.");

        launcherStat.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                mainOptionsFile.set("launcherStat", launcherStat.getSelectedIndex());
                mainOptionsFile.save();
            }
        });
        if (mainOptionsFile.containsKey("launcherStat"))
        {
            int index = mainOptionsFile.getInteger("launcherStat");
            launcherStat.setSelectedIndex(index);
        }
        else
        {
            launcherStat.setSelectedIndex(0);
        }

        GridBagConstraints optConstraints = new GridBagConstraints();

        optConstraints.insets = new Insets(5, 3, 3, 3);

        optConstraints.gridx = 0;
        optConstraints.gridy = 0;
        optionsPanel.add(checkStat,optConstraints);

        optConstraints.gridx = 0;
        optConstraints.gridy = 1;
        optionsPanel.add(serverCheck,optConstraints);

        optConstraints.gridx = 0;
        optConstraints.gridy = 3;
        optionsPanel.add(checktime,optConstraints);

        optConstraints.gridx = 0;
        optConstraints.gridy = 4;
        optionsPanel.add(checkNum,optConstraints);

        optConstraints.gridx = 1;
        optConstraints.gridy = 1;
        optionsPanel.add(vsyncLabel,optConstraints);

        optConstraints.gridx = 1;
        optConstraints.gridy = 2;
        optionsPanel.add(vsyncopt,optConstraints);

        optConstraints.gridx = 2;
        optConstraints.gridy = 1;
        optionsPanel.add(launcherState, optConstraints);

        optConstraints.gridx = 2;
        optConstraints.gridy = 2;
        optionsPanel.add(launcherStat, optConstraints);

        options.add(optionsPanel, BorderLayout.CENTER );

        options.setVisible(true);
        Dimension dim = Launcher.instance.getDim();
        options.setLocation(dim.width/2-options.getSize().width/2, dim.height/2-options.getSize().height/2);
    }


}
