package models;

import gameClasses.FarmerType;
import gameClasses.Player;

import java.io.File;
import java.util.*;

/**
 * This class is the model for the player. It contains helper methods to retrieve and set data as well as housing the masterlist of farmer types.
 */
public class PlayerModel {
    private Player player; // the player object
    private Deque<FarmerType> registerQueue; // the queue of farmer types the player can register
    private Queue<FarmerType> farmerTypes; // the list of farmer types the player can choose from

    /**
     * Constructor for PlayerModel. Initializes the player, registerQueue, and farmerTypes.
     */
    public PlayerModel() {
        this.registerQueue = new ArrayDeque();
        this.player = new Player();
        this.farmerTypes = new LinkedList();
        initFarmerTypes();
    }

    /**
     * Increments the player's money by a certain amount.
     * @param amount the amount to increment the player's money by
     */
    public void increaseMoney(float amount) {
        float currentMoney = player.getCoins();
        player.setCoins(currentMoney + amount);
    }

    /**
     * Decrements the player's money by a certain amount.
     * @param amount the amount to decrement the player's money by
     */
    public void decreaseMoney(int amount) {
        float currentMoney = player.getCoins();
        player.setCoins(currentMoney - amount);
    }

    /**
     * Increments the player's experience by a certain amount.
     * @param amount the amount to increment the player's experience by
     * @return true if the player leveled up, false otherwise
     */
    public boolean addExp(float amount) {
        boolean leveledUp = false;

        float currentExp = player.getExp();
        player.setExp(currentExp + amount);

        // Checks if player has leveled up or not
        int oldLevel = player.getLevel();
        int newLevel = (int)(player.getExp() / 100) ;
        if(oldLevel != newLevel) {
            player.setLevel(newLevel);
            leveledUp = true;
        }

        return leveledUp;
    }

    /**
     * Registers the next farmer type in the queue.
     */
    public void registerNextFarmerType() {
        player.setFarmerType(this.registerQueue.pop());
        this.registerQueue.clear();
    }

    /**
     * Gets the total cost of the farmer types in the register queue.
     * @return the total cost of the farmer types in the register queue
     */
    public int getRegisterCost() {
        int price = 0;
        Iterator<FarmerType> iterator = this.registerQueue.stream().iterator();

        while(iterator.hasNext()) {
            FarmerType ft = iterator.next();
            price += ft.getPrice();
        }

        return price;
    }

    /**
     * Gets the next farmer type the player can register.
     * @return the next farmer type the player can register
     */
    public FarmerType getRegisterable() {
        return this.registerQueue.peek();
    }

    /**
     * Gets the player's coins.
     * @return the player's coins
     */
    public float getPlayerCoins() {
        return player.getCoins();
    }

    /**
     * Gets the player's experience.
     * @return the player's experience
     */
    public float getPlayerExp() {
        return player.getExp();
    }

    /**
     * Gets the player's level.
     * @return the player's level
     */
    public int getPlayerLevel() {
        return player.getLevel();
    }

    /**
     * Pushes the next farmer type in the farmerTypes queue to the register queue.
     */
    public void pushRegisterQueue() {
       this.registerQueue.push(this.farmerTypes.poll());
    }

    /**
     * Gets the player's farmer type.
     * @return the player's farmer type
     */
    public FarmerType getPlayerFarmerType() {
        return player.getType();
    }

    /**
     * Initializes the farmer types from a text file.
     */
    private void initFarmerTypes() {
        String filePath = "readTexts/farmerTypes.txt";
        if(System.getProperty("os.name").equals("Windows 11") || System.getProperty("os.name").equals("Windows 10"))
            filePath = "src/readTexts/farmerTypes.txt";

        File file = new File(filePath);
        Scanner input = null;
        ArrayList<String> list = new ArrayList();
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
            String name = values[0];
            int bonusProduce = Integer.parseInt(values[1]);
            int seedDiscount = Integer.parseInt(values[2]);
            int waterBonus = Integer.parseInt(values[3]);
            int fertBonus = Integer.parseInt(values[4]);
            int regFee = Integer.parseInt(values[5]);

            FarmerType farmerType = new FarmerType(name, waterBonus, fertBonus,
                    seedDiscount, bonusProduce, regFee);

            this.farmerTypes.add(farmerType);
        }
    }
}
