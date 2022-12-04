package models;

import javax.swing.JFrame;
import javax.swing.BorderFactory;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;


public class FarmView {
    private JFrame mainFrame;
    private int dayCount = 1;

    public FarmView() {
        this.mainFrame = new JFrame("Kumain ka ba ba Farm");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setLayout(new BorderLayout());
        this.mainFrame.setSize(1280, 720);
        this.mainFrame.setResizable(false);
        this.mainFrame.setVisible(true);
        this.mainFrame.getContentPane().setBackground(Color.decode("#F0F0F0"));

        initializeStatusBar();
        initializePlantTiles();
        initializeLogsElements();
        this.mainFrame.setVisible(true);
    }

    private void initializeStatusBar() {
        JPanel statusBar = new JPanel();
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        statusBar.setPreferredSize(new Dimension(750, 50));
        
        // Add Day Count
        JLabel dayStatus = new JLabel("DAY: " + dayCount);
        dayStatus.setFont(dayStatus.getFont().deriveFont(20f));
        statusBar.add(dayStatus);
        statusBar.add(new JLabel("   "));

        // Add Coin Status
        JLabel coinsStatus = new JLabel("COINS: " + "0");
        coinsStatus.setFont(coinsStatus.getFont().deriveFont(20f));      
        statusBar.add(coinsStatus);
        statusBar.add(new JLabel("   "));

        // Add Exp Status
        JLabel expStatus = new JLabel("EXP: " + "0");
        expStatus.setFont(expStatus.getFont().deriveFont(20f));
        statusBar.add(expStatus);
        statusBar.add(new JLabel("   "));

        // Add Level Status
        JLabel levelStatus = new JLabel("LVL: " + "0");
        levelStatus.setFont(levelStatus.getFont().deriveFont(20f));
        statusBar.add(levelStatus);
        statusBar.add(new JLabel("   "));

        // Add Type Status
        JLabel typeStatus = new JLabel("TYPE: " + "Farmer");
        typeStatus.setFont(typeStatus.getFont().deriveFont(20f));
        statusBar.add(typeStatus);

        // add status bar to main frame
        this.mainFrame.add(statusBar, BorderLayout.NORTH);

    }

    private void initializePlantTiles() {
        JPanel plantBody = new JPanel();
        plantBody.setLayout(new FlowLayout(FlowLayout.LEFT));
        plantBody.setPreferredSize(new Dimension(830, 670));
        JPanel[][] plantTiles = new JPanel[6][11];
        int yCounter = 1;
        char xCounter = 'a';

        //initialize each tile
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 11; j++) {
                plantTiles[i][j] = new JPanel();
                if (i == 0 && j == 0) {
                    plantTiles[i][j].setPreferredSize(new Dimension(40, 30));                  
                }
                else if (j == 0) {
                    plantTiles[i][j].setPreferredSize(new Dimension(40, 70));
                    plantTiles[i][j].add(new JLabel("" + yCounter));
                    yCounter++;
                }
                else if (i == 0) {
                    plantTiles[i][j].setPreferredSize(new Dimension(70, 30));
                    plantTiles[i][j].add(new JLabel("" + xCounter));
                    xCounter+=1;
                }
                else {
                    plantTiles[i][j].setBackground(Color.decode("#FFFFFF"));
                    plantTiles[i][j].setPreferredSize(new Dimension(70, 70));
                    plantTiles[i][j].setBorder(BorderFactory.createLineBorder(Color.decode("#000000")));
                }
                plantBody.add(plantTiles[i][j]);
            }

        }
        this.mainFrame.add(plantBody, BorderLayout.CENTER);
    }


    private void initializeLogsElements() {    
        JPanel logsPanel = new JPanel();
        logsPanel.setPreferredSize(new Dimension(450, 670));
        logsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        logsPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#000000")));


        this.mainFrame.add(logsPanel, BorderLayout.EAST);


    }

    
}