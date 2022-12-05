package models;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.io.InputStream;

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
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;

import javax.swing.ImageIcon;
import java.awt.Image.*;

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

        // Import font
        try {
            InputStream is = getClass().getResourceAsStream("/assets/Minecraft.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        initializeStatusBar();
        initializeLeftPanel();
        initializeLogsElements();
        this.mainFrame.setVisible(true);
    }

    private void initializeStatusBar() {
        JPanel statusBar = new JPanel();
        statusBar.add(new JLabel("  "));
        //statusBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0),BorderFactory.createLineBorder(Color.BLACK)));
        //statusBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
        statusBar.setBackground(Color.BLACK);
        //statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));
        statusBar.setPreferredSize(new Dimension(750, 60));
        statusBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Import Sun Image Icon
        ImageIcon sunIcon = new ImageIcon(getClass().getResource("/assets/coin-icon.png"));
        Image sunImage = sunIcon.getImage();
        Image newSunImage = sunImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        sunIcon = new ImageIcon(newSunImage);
    
        // Add Day Count
        JLabel dayStatus = new JLabel("DAY: " + dayCount, sunIcon, SwingConstants.CENTER);
        //dayStatus.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        dayStatus.setIconTextGap(4);
        dayStatus.setForeground(Color.WHITE);
        dayStatus.setFont(new Font("Minecraft", Font.PLAIN, 20));
        statusBar.add(dayStatus);
        statusBar.add(new JLabel("   "));

        // Add Coin Status
        JLabel coinsStatus = new JLabel("COINS: " + "0", sunIcon, SwingConstants.CENTER);
        coinsStatus.setForeground(Color.WHITE);
        coinsStatus.setFont(new Font("Minecraft", Font.PLAIN, 20));  
        statusBar.add(coinsStatus);
        statusBar.add(new JLabel("   "));

        // Add Exp Status
        JLabel expStatus = new JLabel("EXP: " + "0", sunIcon, SwingConstants.CENTER);
        expStatus.setForeground(Color.WHITE);
        expStatus.setFont(new Font("Minecraft", Font.PLAIN, 20));
        statusBar.add(expStatus);
        statusBar.add(new JLabel("   "));

        // Add Level Status
        JLabel levelStatus = new JLabel("LVL: " + "0", sunIcon, SwingConstants.CENTER);
        levelStatus.setForeground(Color.WHITE);
        levelStatus.setFont(new Font("Minecraft", Font.PLAIN, 20));
        statusBar.add(levelStatus);
        statusBar.add(new JLabel("   "));

        // Add Type Status
        JLabel typeStatus = new JLabel("TYPE: " + "Farmer", sunIcon, SwingConstants.CENTER);
        typeStatus.setForeground(Color.WHITE);
        typeStatus.setFont(new Font("Minecraft", Font.PLAIN, 20));
        statusBar.add(typeStatus);

        // add status bar to main frame
        this.mainFrame.add(statusBar, BorderLayout.NORTH);

    }

    private void initializeLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBackground(Color.BLACK);

        // Y-axis label
        JPanel leftLabel = new JPanel();
        leftLabel.setBackground(Color.BLACK);
        leftLabel.setLayout(new BoxLayout(leftLabel, BoxLayout.Y_AXIS));
        int yCounter = 1;

        JPanel smallMargin = new JPanel();
        smallMargin.setPreferredSize(new Dimension(40, 20));
        smallMargin.setBackground(Color.BLACK);
        leftLabel.add(smallMargin);

        for (int i = 0; i < 5; i++) {
            JPanel yLabel = new JPanel();
            yLabel.setBackground(Color.BLACK);
            JLabel yLabelLabel = new JLabel(String.valueOf(yCounter));
            yLabelLabel.setFont(new Font("Minecraft", Font.PLAIN, 20));
            yLabel.add(yLabelLabel);
            yLabelLabel.setForeground(Color.WHITE);
            yLabel.setPreferredSize(new Dimension(40, 70));
            yLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            leftLabel.add(yLabel);
            yCounter++;
        }
        leftPanel.add(leftLabel, BorderLayout.WEST);

        // X-axis label
        JPanel topLabel = new JPanel();
        topLabel.setBackground(Color.BLACK);
        char xCounter = 'a';

        // Add empty label
        JPanel cornerLabel = new JPanel();
        cornerLabel.setPreferredSize(new Dimension(30, 30));
        cornerLabel.setBackground(Color.BLACK);
        topLabel.add(cornerLabel);

        for (int i = 0; i < 10; i++) {
            JPanel xLabel = new JPanel();
            xLabel.setBackground(Color.BLACK);
            JLabel xLabelLabel = new JLabel(String.valueOf(xCounter));
            xLabelLabel.setFont(new Font("Minecraft", Font.PLAIN, 20));
            xLabelLabel.setForeground(Color.WHITE);
            xLabel.add(xLabelLabel);
            xLabel.setPreferredSize(new Dimension(70, 30));
            xLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            topLabel.add(xLabel);
            xCounter++;
        }

        // Right Corner Empty Label
        JPanel rightCornerLabel = new JPanel();
        rightCornerLabel.setPreferredSize(new Dimension(10, 30));
        rightCornerLabel.setBackground(Color.BLACK);
        topLabel.add(rightCornerLabel);

        leftPanel.add(topLabel, BorderLayout.NORTH);



        // Add Tiles
        JPanel plantBody = new JPanel();
        plantBody.setBackground(Color.BLACK);
        plantBody.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel[][] plantTiles = new JPanel[5][10];

        // Initialize Tiles
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                plantTiles[i][j] = new JPanel();
                plantTiles[i][j].setBackground(Color.decode("#000000"));
                plantTiles[i][j].setPreferredSize(new Dimension(70, 70));
                plantTiles[i][j].setBorder(BorderFactory.createLineBorder(Color.decode("#FFFFFF")));
                plantBody.add(plantTiles[i][j], BorderLayout.CENTER);
            }
        }
        leftPanel.add(plantBody, BorderLayout.CENTER);

        // Add Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setPreferredSize(new Dimension(0, 210));
        leftPanel.add(bottomPanel, BorderLayout.SOUTH);

        

        this.mainFrame.add(leftPanel);
    }


    private void initializeLogsElements() {    
        JPanel logsPanel = new JPanel();
        logsPanel.setBackground(Color.BLACK);
        logsPanel.setPreferredSize(new Dimension(440, 670));
        logsPanel.setLayout(new BorderLayout());

        // Add Logs Title
        JLabel logsTitle = new JLabel("FARM LOGS");
        logsTitle.setForeground(Color.WHITE);

        logsTitle.setFont(new Font("Minecraft", Font.PLAIN, 20));
        logsPanel.add(logsTitle, BorderLayout.NORTH);

        // Add Logs Panel
        JPanel logs = new JPanel();
        logs.setPreferredSize(new Dimension(50, 50));
        logs.setBorder(BorderFactory.createLineBorder(Color.decode("#FFFFFF")));
        logsPanel.add(logs, BorderLayout.CENTER);

        // Add Right Margin
        JPanel rightMargin = new JPanel();
        rightMargin.setBackground(Color.BLACK);
        rightMargin.setPreferredSize(new Dimension(20, 670));
        logsPanel.add(rightMargin, BorderLayout.EAST);

        // Add Bottom Margin
        JPanel bottomMargin = new JPanel();
        bottomMargin.setBackground(Color.BLACK);
        bottomMargin.setPreferredSize(new Dimension(440, 20));
        logsPanel.add(bottomMargin, BorderLayout.SOUTH);


        // Add Chatbox Panel
        JPanel chatboxPanel = new JPanel();
        chatboxPanel.setLayout(new BorderLayout());
        logsPanel.add(chatboxPanel, BorderLayout.CENTER);
        
        // Add Text Area with Scroll
        JTextArea logsbox = new JTextArea();
        logsbox.setEditable(false);
        logsbox.setBackground(Color.BLACK);
        logsbox.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(logsbox);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setForeground(Color.WHITE);
        scroll.setPreferredSize(new Dimension(50, 670));
        chatboxPanel.add(scroll, BorderLayout.CENTER);


        // Add Chatbox 
        JPanel chatboxBorder = new JPanel();
        chatboxBorder.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        chatboxBorder.setLayout(new BorderLayout());
        JTextField chatbox = new JTextField("Enter command");
        chatbox.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        chatbox.setBackground(Color.BLACK);
        chatbox.setForeground(Color.GRAY);
        chatbox.setFont(new Font("Minecraft", Font.PLAIN, 25));
        chatboxBorder.add(chatbox, BorderLayout.CENTER);
        chatboxPanel.add(chatboxBorder, BorderLayout.SOUTH);

        // Add Placeholder text
        chatbox.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (chatbox.getText().equals("Enter command")) {
                    chatbox.setText("");
                    chatbox.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (chatbox.getText().isEmpty()) {
                    chatbox.setText("Enter command");
                    chatbox.setForeground(Color.GRAY);
                }
            }
        });

        // Add Logs Panel to MainFrame
        this.mainFrame.add(logsPanel, BorderLayout.EAST);
    }
}