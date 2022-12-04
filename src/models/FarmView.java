package models;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.text.html.ListView;
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
        this.mainFrame.setSize(1280, 720);
        this.mainFrame.setResizable(false);
        this.mainFrame.setLayout(new BorderLayout());
        this.mainFrame.getContentPane().setBackground(Color.BLACK);


        initializeStatusBar();
        initializeLeftPanel();
        initializeLogsElements();
        this.mainFrame.setVisible(true);
    }

    private void initializeStatusBar() {
        JPanel statusBar = new JPanel();
        statusBar.add(new JLabel("  "));
        // statusBar.setBackground(Color.BLACK);
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

    private void initializeLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        // Y-axis label
        JPanel leftLabel = new JPanel();
        leftLabel.setLayout(new BoxLayout(leftLabel, BoxLayout.Y_AXIS));
        int yCounter = 1;

        JPanel smallMargin = new JPanel();
        smallMargin.setPreferredSize(new Dimension(40, 20));
        leftLabel.add(smallMargin);

        for (int i = 0; i < 5; i++) {
            JPanel yLabel = new JPanel();
            yLabel.add(new JLabel(String.valueOf(yCounter)));
            yLabel.setPreferredSize(new Dimension(40, 70));
            yLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            leftLabel.add(yLabel);
            yCounter++;
        }
        leftPanel.add(leftLabel, BorderLayout.WEST);

        // X-axis label
        JPanel topLabel = new JPanel();
        char xCounter = 'a';

        // Add empty label
        JPanel cornerLabel = new JPanel();
        cornerLabel.setPreferredSize(new Dimension(30, 30));
        topLabel.add(cornerLabel);

        for (int i = 0; i < 10; i++) {
            JPanel xLabel = new JPanel();
            xLabel.add(new JLabel(String.valueOf(xCounter)));
            xLabel.setPreferredSize(new Dimension(70, 30));
            xLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            topLabel.add(xLabel);
            xCounter++;
        }

        // Right Corner Empty Label
        JPanel rightCornerLabel = new JPanel();
        rightCornerLabel.setPreferredSize(new Dimension(10, 30));
        topLabel.add(rightCornerLabel);

        leftPanel.add(topLabel, BorderLayout.NORTH);



        // Add Tiles
        JPanel plantBody = new JPanel();
        plantBody.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel[][] plantTiles = new JPanel[5][10];

        // Initialize Tiles
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                plantTiles[i][j] = new JPanel();
                plantTiles[i][j].setBackground(Color.decode("#FFFFFF"));
                plantTiles[i][j].setPreferredSize(new Dimension(70, 70));
                plantTiles[i][j].setBorder(BorderFactory.createLineBorder(Color.decode("#000000")));
                plantBody.add(plantTiles[i][j], BorderLayout.CENTER);
            }
        }
        leftPanel.add(plantBody, BorderLayout.CENTER);

        // Add Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setPreferredSize(new Dimension(0, 210));
        leftPanel.add(bottomPanel, BorderLayout.SOUTH);

        this.mainFrame.add(leftPanel);
    }


    private void initializeLogsElements() {    
        JPanel logsPanel = new JPanel();
        logsPanel.setPreferredSize(new Dimension(440, 670));
        logsPanel.setLayout(new BorderLayout());

        // Add Logs Title
        JLabel logsTitle = new JLabel("LOGS");

        logsTitle.setFont(logsTitle.getFont().deriveFont(20f));
        logsPanel.add(logsTitle, BorderLayout.NORTH);

        // Add Logs Panel
        JPanel logs = new JPanel();
        logs.setPreferredSize(new Dimension(50, 50));
        logs.setBorder(BorderFactory.createLineBorder(Color.decode("#000000")));
        logsPanel.add(logs, BorderLayout.CENTER);

        // Add Right Margin
        JPanel rightMargin = new JPanel();
        rightMargin.setPreferredSize(new Dimension(10, 670));
        logsPanel.add(rightMargin, BorderLayout.EAST);

        // Add Bottom Margin
        JPanel bottomMargin = new JPanel();
        bottomMargin.setPreferredSize(new Dimension(440, 10));
        logsPanel.add(bottomMargin, BorderLayout.SOUTH);


        // Add Chatbox Panel
        JPanel chatboxPanel = new JPanel();
        chatboxPanel.setLayout(new BorderLayout());
        chatboxPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#000000")));
        logsPanel.add(chatboxPanel, BorderLayout.CENTER);
        
        // Add Text Area with Scroll
        JTextArea logsbox = new JTextArea();
        logsbox.setEditable(false);

        JScrollPane scroll = new JScrollPane(logsbox);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(440, 670));
        chatboxPanel.add(scroll, BorderLayout.CENTER);


        // Add Chatbox 
        JTextField chatbox = new JTextField();
        chatbox.setFont(chatbox.getFont().deriveFont(20f));
        chatboxPanel.add(chatbox, BorderLayout.SOUTH);

        // Add Logs Panel to MainFrame
        this.mainFrame.add(logsPanel, BorderLayout.EAST);
    }
}