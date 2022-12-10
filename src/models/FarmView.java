package models;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * FarmView is the class that handles the GUI of the game.
 */
public class FarmView {
    private JFrame mainFrame;
    private JPanel leftPanel = new JPanel();
    private JTextField chatbox;
    private JTextArea logsbox;
    private JLabel dayStatus;
    private JLabel coinsStatus;
    private JLabel expStatus;
    private JLabel levelStatus;
    private JLabel typeStatus;
    private JLabel waterStatus;
    private JLabel fertilizerStatus;
    private JLabel discountStatus;
    private JLabel bonusProduceStatus;

    private JLabel nextRegisterStatus;
    private int dayCount = 1;
    private JLabel[][] plantTiles = new JLabel[5][10];
    private Hashtable<String, ImageIcon> tileImages;
    /**
     * FarmView is the constructor of the class. It initializes the main frame of the game.
     */
    public FarmView() {
        this.mainFrame = new JFrame("Wag magpakagutom ha");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setSize(1280, 720);
        this.mainFrame.setResizable(false);
        this.mainFrame.setLayout(new BorderLayout());
        this.mainFrame.getContentPane().setBackground(Color.BLACK);
        this.tileImages = new Hashtable();

        // Left Panel
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBackground(Color.BLACK);

        // Import font
        try {
            InputStream is = getClass().getResourceAsStream("/assets/fonts/Minecraft.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        // Initialize Elements
        initializeStatusBar();
        initializeLabels();
        initializeEmptyTiles();
        initializeBottomPanel();
        initializeLogsElements();

        initializePlantImages();


        // Add Panels to the main frame
        this.mainFrame.add(leftPanel);
        this.mainFrame.setVisible(true);
    }

    private ArrayList<String> getPlantNames() {
        String filePath = "readTexts/plants.txt";
        if(System.getProperty("os.name").equals("Windows 11") || System.getProperty("os.name").equals("Windows 10"))
            filePath = "src/readTexts/plants.txt";

        File file = new File(filePath);
        Scanner input = null;
        ArrayList<String> list = new ArrayList();
        ArrayList<String> plantNames = new ArrayList();
        try {
            input = new Scanner(file);
        }
        catch(Exception e) {
            System.out.println("File not found!");
        }

        while (input.hasNextLine()) {
            list.add(input.nextLine());
        }

        for(String s : list) {
            String[] values = s.split(" ");
            plantNames.add(values[0].toLowerCase());
        }

        return plantNames;
    }
    private void initializePlantImages() {
        for(String s : getPlantNames()) {
            ImageIcon plantImport = new ImageIcon(getClass().getResource("/assets/icons/" +s+".png"));
            Image plantImage = plantImport.getImage();
            Image newPlantImage = plantImage.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            plantImport = new ImageIcon(newPlantImage);

            tileImages.put(s, plantImport);
        }

        ImageIcon grassImport = new ImageIcon(getClass().getResource("/assets/icons/grass.png"));
        Image grassImage = grassImport.getImage();
        Image newGrassImage = grassImage.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        grassImport = new ImageIcon(newGrassImage);
        tileImages.put("Grass", grassImport);


        ImageIcon plowImport = new ImageIcon(getClass().getResource("/assets/icons/plowed.png"));
        Image plowImage = plowImport.getImage();
        Image newPlowImage = plowImage.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        plowImport = new ImageIcon(newPlowImage);
        tileImages.put("Plowed", plowImport);


        ImageIcon rockImport = new ImageIcon(getClass().getResource("/assets/icons/rock.png"));
        Image rockImage = rockImport.getImage();
        Image newRockImage = rockImage.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        rockImport = new ImageIcon(newRockImage);
        tileImages.put("Rock", rockImport);
    }
    /**
     * initializeStatusBar initializes the status bar of the game.
     */
    private void initializeStatusBar() {
        JPanel statusBar = new JPanel();
        statusBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        statusBar.setBackground(Color.BLACK);
        statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));
        statusBar.setPreferredSize(new Dimension(750, 60));
        statusBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Import Sun Image Icon
        ImageIcon sunImport = new ImageIcon(getClass().getResource("/assets/icons/sun.png"));
        Image sunImage = sunImport.getImage();
        Image newSunImage = sunImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        sunImport = new ImageIcon(newSunImage);

        // Import Coin Image Icon
        ImageIcon coinImport = new ImageIcon(getClass().getResource("/assets/icons/coin.png"));
        Image coinImage = coinImport.getImage();
        Image newCoinImage = coinImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        coinImport = new ImageIcon(newCoinImage);

        // Import EXP Image Icon
        ImageIcon expImport = new ImageIcon(getClass().getResource("/assets/icons/exp.png"));
        Image expImage = expImport.getImage();
        Image newExpImage = expImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        expImport = new ImageIcon(newExpImage);

        // Import LVL Image Icon
        ImageIcon lvlImport = new ImageIcon(getClass().getResource("/assets/icons/level.png"));
        Image lvlImage = lvlImport.getImage();
        Image newLvlImage = lvlImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        lvlImport = new ImageIcon(newLvlImage);

        // Import Farmer Type Icon
        ImageIcon farmerTypeImport = new ImageIcon(getClass().getResource("/assets/icons/type.png"));
        Image farmerTypeImage = farmerTypeImport.getImage();
        Image newFarmerTypeImage = farmerTypeImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        farmerTypeImport = new ImageIcon(newFarmerTypeImage);
    
        // Add Day Count
        JLabel dayIcon = new JLabel(sunImport);
        statusBar.add(dayIcon);
        dayStatus = new JLabel("DAY: " + dayCount);
        dayStatus.setBorder(BorderFactory.createEmptyBorder(3, 15, 0, 15));
        dayStatus.setForeground(Color.WHITE);
        dayStatus.setFont(new Font("Minecraft", Font.PLAIN, 20));
        statusBar.add(dayStatus);

        // Add Coin Status
        JLabel coinIcon = new JLabel(coinImport);
        statusBar.add(coinIcon);
        coinsStatus = new JLabel("COINS: " + "0");
        coinsStatus.setBorder(BorderFactory.createEmptyBorder(3, 10, 0, 15));
        coinsStatus.setForeground(Color.WHITE);
        coinsStatus.setFont(new Font("Minecraft", Font.PLAIN, 20));  
        statusBar.add(coinsStatus);

        // Add Exp Status
        JLabel expIcon = new JLabel(expImport);
        statusBar.add(expIcon);
        expStatus = new JLabel("EXP: " + "1000");
        expStatus.setBorder(BorderFactory.createEmptyBorder(3, 10, 0, 15));
        expStatus.setForeground(Color.WHITE);
        expStatus.setFont(new Font("Minecraft", Font.PLAIN, 20));
        statusBar.add(expStatus);

        // Add Level Status
        JLabel lvlIcon = new JLabel(lvlImport);
        statusBar.add(lvlIcon);
        levelStatus = new JLabel("LVL: " + "0");
        levelStatus.setBorder(BorderFactory.createEmptyBorder(3, 10, 0, 15));
        levelStatus.setForeground(Color.WHITE);
        levelStatus.setFont(new Font("Minecraft", Font.PLAIN, 20));
        statusBar.add(levelStatus);

        // Add Type Status
        JLabel farmerTypeIcon = new JLabel(farmerTypeImport);
        statusBar.add(farmerTypeIcon);
        typeStatus = new JLabel("TYPE: " + "Farmer");
        typeStatus.setBorder(BorderFactory.createEmptyBorder(3, 10, 0, 15));
        typeStatus.setForeground(Color.WHITE);
        typeStatus.setFont(new Font("Minecraft", Font.PLAIN, 20));
        statusBar.add(typeStatus);

        // Add status bar to main frame
        this.mainFrame.add(statusBar, BorderLayout.NORTH);

    }

    /**
     *  initializeLabels initializes the x and y-axis labels of the game.
     */
    private void initializeLabels() {
        // Y-axis label panel (1-5)
        JPanel leftLabel = new JPanel();
        leftLabel.setBackground(Color.BLACK);
        leftLabel.setLayout(new BoxLayout(leftLabel, BoxLayout.Y_AXIS));
        int yCounter = 1;

        // Add margin to top of y-axis panel
        JPanel smallMargin = new JPanel();
        smallMargin.setPreferredSize(new Dimension(40, 20));
        smallMargin.setBackground(Color.BLACK);
        leftLabel.add(smallMargin);

        // Add the number labels
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

        // X-axis label panel (a-j)
        JPanel topLabel = new JPanel();
        topLabel.setBackground(Color.BLACK);
        char xCounter = 'a';

        // Add empty label under x-axis
        JPanel cornerLabel = new JPanel();
        cornerLabel.setPreferredSize(new Dimension(30, 30));
        cornerLabel.setBackground(Color.BLACK);
        topLabel.add(cornerLabel);

        // Add the x-axis letter labels
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
    }

    /**
     *  initializeBottomPanel initializes the bottom panel of the game.
     */
    private void initializeBottomPanel() {
        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setPreferredSize(new Dimension(0, 200));

        // Add Left Margin
        JPanel leftMargin = new JPanel();
        leftMargin.setPreferredSize(new Dimension(40, 0));
        leftMargin.setBackground(Color.BLACK);
        bottomPanel.add(leftMargin, BorderLayout.WEST);


        // Add Bottom Stats bar
        JPanel bottomStats = new JPanel();

        bottomStats.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        bottomStats.setBackground(Color.BLACK);
        bottomStats.setLayout(new GridLayout(2,3, 0, 5));
        bottomStats.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(bottomStats, BorderLayout.NORTH);

        // Import Water Image
        ImageIcon waterImport = new ImageIcon(getClass().getResource("/assets/icons/water.png"));
        Image waterImage = waterImport.getImage();
        Image waterImageScaled = waterImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        waterImport = new ImageIcon(waterImageScaled);

        // Import Fertilizer Image
        ImageIcon fertilizerImport = new ImageIcon(getClass().getResource("/assets/icons/fertilizer.png"));
        Image fertilizerImage = fertilizerImport.getImage();
        Image fertilizerImageScaled = fertilizerImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        fertilizerImport = new ImageIcon(fertilizerImageScaled);

        // Import Discount Image
        ImageIcon discountImport = new ImageIcon(getClass().getResource("/assets/icons/discount.png"));
        Image discountImage = discountImport.getImage();
        Image discountImageScaled = discountImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        discountImport = new ImageIcon(discountImageScaled);

        // Import Bonus Produce Image
        ImageIcon bonusProduceImport = new ImageIcon(getClass().getResource("/assets/icons/produce.png"));
        Image bonusImage = bonusProduceImport.getImage();
        Image bonusImageScaled = bonusImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        bonusProduceImport = new ImageIcon(bonusImageScaled);

        // Import Register Image
        ImageIcon nextRegisterImport = new ImageIcon(getClass().getResource("/assets/icons/registration.png"));
        Image registerImage = nextRegisterImport.getImage();
        Image registerImageScaled = registerImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        nextRegisterImport = new ImageIcon(registerImageScaled);

        // Add Water Panel
        JPanel waterPanel = new JPanel();
        waterPanel.setBackground(Color.BLACK);
        waterPanel.setLayout(new BoxLayout(waterPanel, BoxLayout.X_AXIS));
        
        // Add Water Bonus
        JLabel waterIcon = new JLabel(waterImport);
        waterPanel.add(waterIcon);
        waterStatus = new JLabel("WATER BONUS: " + "0");
        waterStatus.setBorder(BorderFactory.createEmptyBorder(3, 10, 0, 15));
        waterStatus.setForeground(Color.WHITE);
        waterStatus.setFont(new Font("Minecraft", Font.PLAIN, 18));
        waterPanel.add(waterStatus);

        bottomStats.add(waterPanel);

        // Add Fertilizer Panel
        JPanel fertilizerPanel = new JPanel();
        fertilizerPanel.setBackground(Color.BLACK);
        fertilizerPanel.setLayout(new BoxLayout(fertilizerPanel, BoxLayout.X_AXIS));

        // Add Fertilizer Bonus
        JLabel fertilizerIcon = new JLabel(fertilizerImport);
        fertilizerPanel.add(fertilizerIcon);
        fertilizerStatus = new JLabel("FERT. BONUS: " + "0");
        fertilizerStatus.setBorder(BorderFactory.createEmptyBorder(3, 10, 0, 15));
        fertilizerStatus.setForeground(Color.WHITE);
        fertilizerStatus.setFont(new Font("Minecraft", Font.PLAIN, 18));
        fertilizerPanel.add(fertilizerStatus);

        bottomStats.add(fertilizerPanel);

        // Add Discount Panel
        JPanel discountPanel = new JPanel();
        discountPanel.setBackground(Color.BLACK);
        discountPanel.setLayout(new BoxLayout(discountPanel, BoxLayout.X_AXIS));

        // Add Discount
        JLabel discountIcon = new JLabel(discountImport);
        discountPanel.add(discountIcon);
        discountStatus = new JLabel("DISCOUNT BONUS: " + "0");
        discountStatus.setBorder(BorderFactory.createEmptyBorder(3, 10, 0, 15));
        discountStatus.setForeground(Color.WHITE);
        discountStatus.setFont(new Font("Minecraft", Font.PLAIN, 18));
        discountPanel.add(discountStatus);

        bottomStats.add(discountPanel);

        // Add Bonus Produce Panel
        JPanel bonusProducePanel = new JPanel();
        bonusProducePanel.setBackground(Color.BLACK);
        bonusProducePanel.setLayout(new BoxLayout(bonusProducePanel, BoxLayout.X_AXIS));

        // Add Bonus Produce
        JLabel bonusProduceIcon = new JLabel(bonusProduceImport);
        bonusProducePanel.add(bonusProduceIcon);
        bonusProduceStatus = new JLabel("BONUS PRODUCE: " + "0");
        bonusProduceStatus.setBorder(BorderFactory.createEmptyBorder(3, 10, 0, 15));
        bonusProduceStatus.setForeground(Color.WHITE);
        bonusProduceStatus.setFont(new Font("Minecraft", Font.PLAIN, 18));
        bonusProducePanel.add(bonusProduceStatus);

        bottomStats.add(bonusProducePanel);

        // Add Next Register Panel
        JPanel nextRegisterPanel = new JPanel();
        nextRegisterPanel.setBackground(Color.BLACK);
        nextRegisterPanel.setLayout(new BoxLayout(nextRegisterPanel, BoxLayout.X_AXIS));

        // Add Next Register
        JLabel nextRegisterIcon = new JLabel(nextRegisterImport);
        nextRegisterPanel.add(nextRegisterIcon);
        nextRegisterStatus = new JLabel("NEXT REGISTER: " + "NONE");
        nextRegisterStatus.setBorder(BorderFactory.createEmptyBorder(3, 10, 0, 15));
        nextRegisterStatus.setForeground(Color.WHITE);
        nextRegisterStatus.setFont(new Font("Minecraft", Font.PLAIN, 18));
        nextRegisterPanel.add(nextRegisterStatus);

        bottomStats.add(nextRegisterPanel);
        
        // Add Bottom Panel
        leftPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * initializeTiles initializes the 5x10 tiles
     */
    private void initializeEmptyTiles() {
        JPanel plantBody = new JPanel();
        plantBody.setBackground(Color.BLACK);
        plantBody.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Initialize Tiles
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                this.plantTiles[i][j] = new JLabel();
                this.plantTiles[i][j].setBackground(Color.decode("#000000"));
                this.plantTiles[i][j].setPreferredSize(new Dimension(70, 70));
                plantBody.add(plantTiles[i][j]);
            }
        }
        leftPanel.add(plantBody, BorderLayout.CENTER);
    }

    /**
     * initializeLogsElements initializes the logs and command elements
     */
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
        
        // Add Text Field Area
        logsbox = new JTextArea();
        logsbox.setFont(new Font("Minecraft", Font.PLAIN, 18));
        logsbox.setLineWrap(true);
        logsbox.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        logsbox.setEditable(false);
        logsbox.setBackground(Color.BLACK);
        logsbox.setForeground(Color.WHITE);

        // Add Welcome Message
        logsbox.append("Hello Farmer! Kumain ka na ba?\n");
        logsbox.append("Need help? Type 'help' below.\n");
        logsbox.append("----------------------------------\n");

        // Add Scroll Bar
        JScrollPane scroll = new JScrollPane(logsbox);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setForeground(Color.WHITE);
        scroll.setPreferredSize(new Dimension(50, 670));
        chatboxPanel.add(scroll, BorderLayout.CENTER);


        // Add TextField chatbox
        JPanel chatboxBorder = new JPanel();
        chatboxBorder.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        chatboxBorder.setLayout(new BorderLayout());
        chatbox = new JTextField("Enter command");
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

    /**
     * setTileImage sets the image of a tile
     * @param x
     * @param y
     * @param image
     */
    private void setTileImage(int x, int y, ImageIcon image) {
        this.plantTiles[x][y].removeAll();
        JLabel addImage = new JLabel(image);
        addImage.setSize(70,70);
        this.plantTiles[x][y].add(addImage);
        this.plantTiles[x][y].repaint();
    }

    /**
     * setGrassImage sets all tiles to grass image
     */
    public void setTileImage(int x, int y, String name) {
        setTileImage(x, y, this.tileImages.get(name));
    }

    public void setDayStatus(String value) {
        this.dayStatus.setText("DAY: "+value);
    }

    public void setCoinsStatus(String value) {
        this.coinsStatus.setText("COINS: "+value);
    }

    public void setExpStatus(String value) {
        this.expStatus.setText("EXP: "+value);
    }

    public void setLevelStatus(String value) {
        this.levelStatus.setText("LVL: "+value);
    }

    public void setTypeStatus(String value) {
        this.typeStatus.setText("TYPE: "+value);
    }

    public void setWaterStatus(String value) {
        this.waterStatus.setText("WATER BONUS: "+value);
    }

    public void setFertilizerStatus(String value) {
        this.fertilizerStatus.setText("FERT. BONUS: " +value);
    }

    public void setDiscountStatus(String value) {
        this.discountStatus.setText("DISCOUNT BONUS: "+value);
    }

    public void setBonusProduceStatus(String value) {
        this.bonusProduceStatus.setText("BONUS PRODUCE: "+value);
    }

    public void setNextRegisterStatus(String value) {
        this.nextRegisterStatus.setText("NEXT REGISTER: "+value);
    }

    public void setTextFieldActionListener(ActionListener actionListener) {
        this.chatbox.addActionListener(actionListener);
    }

    public void appendLogsBoxText(String text) {
        logsbox.append(text);
    }

    public void clearLogsBox() {
        logsbox.setText("");
        logsbox.append("Hello Farmer! Kumain ka na ba?\n");
        logsbox.append("Need help? Type 'help' below.\n");
        logsbox.append("----------------------------------\n");
    }

    public void disableChatBox() {
        chatbox.setEditable(false);
    }

}