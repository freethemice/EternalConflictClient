package net.eternalconflict.www;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class OptionsWindow extends JFrame{

    public OptionsWindow()
    {

        JCheckBox serverCheck1;
        JCheckBox serverCheck2;
        JCheckBox serverCheck3;
        JCheckBox serverCheck5;
        JCheckBox serverCheck10;
        JCheckBox checkNum1;
        JCheckBox checkNum2;
        JCheckBox checkNum3;
        JCheckBox checkNum5;
        JCheckBox checkNum10;

        JFrame options = new JFrame("Options");
        //options.setVisible(true);
        options.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        options.setSize(900,700);
        options.setResizable(false);
        options.setBackground(Color.lightGray);

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
            public void actionPerformed(ActionEvent e)
            {

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

        options.setVisible(true);
    }

}
