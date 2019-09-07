package net.eternalconflict.www;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class OptionsWindow extends JFrame{

    public OptionsWindow()
    {

        JComboBox serverCheck;
        JComboBox checkNum;


        JFrame options = new JFrame("Launcher Options");
        options.setDefaultCloseOperation(HIDE_ON_CLOSE);
        options.setSize(900,700);
        options.setResizable(false);
        if(EternalConflict.serverUp)options.setBackground(Color.BLUE);
        if(!EternalConflict.serverUp)options.setBackground(Color.LIGHT_GRAY);

        JPanel optionsPanel = new JPanel();

        JLabel checkStat = new JLabel("Server checks status: ");

        serverCheck = new JComboBox();
        serverCheck.addItem("Check every 1 minute");
        serverCheck.addItem("Check every 2 minutes");
        serverCheck.addItem("Check every 3 minutes");
        serverCheck.addItem("Check every 5 minutes");
        serverCheck.addItem("Check every 10 minutes");
        serverCheck.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

            }
        });

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

            }
        });

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
        options.add(optionsPanel, BorderLayout.WEST );

        options.setVisible(true);
    }

}
