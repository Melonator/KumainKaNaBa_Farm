package gameClasses;

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

    public float getCoins() {
        return coins;
    }

    public void setCoins(float coins) {
        this.coins = coins;
    }

    public float getExp() {
        return exp;
    }

    public void setExp(float exp) {
        this.exp = exp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public FarmerType getType() {
        return type;
    }

    public void setType(FarmerType type) {
        this.type = type;
    }
}
