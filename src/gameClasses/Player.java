package gameClasses;

/**
 * The Player class is reponsible for storing the player's information.
 */
public class Player {
    private float coins; // coins of the player
    private float exp; // exp of the player
    private int level; // level of the player
    private FarmerType type; // type of the player with its respective bonuses

    /**
     * Constructor for Player. Initializes the player.
     */
    public Player() {
        this.coins = 100;
        this.exp = 0;
        this.level = 0;
        this.type = new FarmerType("Farmer", 0, 0, 0, 0, 0);
    }

    /**
     * Returns the coins of the player.
     * @return coins of the player
     */
    public float getCoins() {
        return coins;
    }

    /**
     * Sets the coins of the player.
     * @param coins the coins of the player
     */
    public void setCoins(float coins) {
        this.coins = coins;
    }

    /**
     * Returns the exp of the player.
     * @return exp of the player
     */
    public float getExp() {
        return exp;
    }

    /**
     * Sets the exp of the player.
     * @param exp the exp of the player
     */
    public void setExp(float exp) {
        this.exp = exp;
    }

    /**
     * Returns the level of the player.
     * @return level of the player
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the level of the player.
     * @param level the level of the player
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Returns the farmer type of the player.
     * @return farmer type of the player
     */
    public FarmerType getType() {
        return type;
    }

    /**
     * Sets the farmer type of the player.
     * @param type the farmer type of the player
     */
    public void setFarmerType(FarmerType type) {
        this.type = type;
    }
}
