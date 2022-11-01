package models;

/**
 * The Player class is reponsible for storing the player's information.
 */
public class Player {
    private float coins;
    private float exp;
    private int level;
    private FarmerType type;

    /**
     * Constructor for Player. Initializes the player.
     */
    public Player() {
        this.coins = 100;
        this.exp = 0;
        this.level = 0;
        this.type = new FarmerType("Farmer", 0, 0, 0, 0);
    }

    /**
     * Returns the object coins of the player
     * 
     * @return object coins of the player
     */
    public float getCoins() {
        return this.coins;
    }

    /**
     * Returns the exp of the player
     * 
     * @return exp of the player
     */
    public float getExp() {
        return this.exp;
    }

    /**
     * Returns the level of the player
     * 
     * @return level of the player
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Returns the farmer type of the player
     * 
     * @return farmer type of the player
     */
    public FarmerType getType() {
        return this.type;
    }


    /**
     * Adds exp to the player and checks if the player has enough exp to level up.
     * 
     * @param num The amount of exp to add to the player.
     * @return true if the player levels up, false otherwise.
     */
    public boolean addExp(float num) {
        Notification.push("[ You have gained " + num + " exp! ]");
        boolean leveledUp = false;

        //Avoids exp overflow for max level
        if (this.exp + num > 1000)
            this.exp = 1000;
        else
            this.exp += num;

        // Checks if player has leveled up or not
        if (this.exp >= 100 && this.level != 1)
            leveledUp = true;
        else if (this.exp >= 200 && this.level != 2)
            leveledUp = true;
        else if (this.exp >= 300 && this.level != 3)
            leveledUp = true;
        else if (this.exp >= 400 && this.level != 4)
            leveledUp = true;
        else if (this.exp >= 500 && this.level != 5)
            leveledUp = true;
        else if (this.exp >= 600 && this.level != 6)
            leveledUp = true;
        else if (this.exp >= 700 && this.level != 7)
            leveledUp = true;
        else if (this.exp >= 800 && this.level != 8)
            leveledUp = true;
        else if (this.exp >= 900 && this.level != 9)
            leveledUp = true;
        else if (this.exp == 1000 && this.level != 10)
            leveledUp = true;
        
        return leveledUp;
    }


    /**
     * Decreases the object coins of the player.
     * 
     * @param num The number of coins to be spent.
     * @return true if the object coins has been deducted, false otherwise.
     */
    public boolean decCoins(int num) {
        //Do not decrease the coins if the player cannot afford the spending
        if (this.coins - num < 0)
            return false;

        Notification.push("[ You've spent " + num + " Object Coins! ]");
        this.coins -= num;
        return true;
    }

    /**
     * Increases the object coins of the player.
     * 
     * @param num The number of coins to be gained.
     */
    public void incCoins(float num) {
        Notification.push("[ You've gained " + num + " Object Coins! ]");
        this.coins += num;
    }

    /**
     * Sets the farmer type of the player.
     * 
     * @param type The farmer type to be set.
     */
    public void setType(FarmerType type) {
        this.type = type;
    }

}
