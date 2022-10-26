package models;

public class Player {
    private int coins;
    private float exp;
    private int level;
    private FarmerType type;

    public Player() {
        this.coins = 0;
        this.exp = 0;
        this.level = 0;
        //this.type = 
    }

    public int getCoins() {
        return this.coins;
    }

    public float getExp() {
        return this.exp;
    }

    public int getLevel() {
        return this.level;
    }

    public FarmerType getType() {
        return this.type;
    }

    /**
     * Increases exp by num
     *
     * @returns whether the player leveled up or not.
     */
    public boolean addExp(float num) {
        boolean leveledUp = false;

        if (this.exp + num > 1000)
            this.exp = 1000;
        else
            this.exp += num;

        // Checks if player has leveled up or not
        if (this.exp >= 100 && this.level != 1)
            leveledUp = true;
        else if (this.exp >= 201 && this.level != 2)
            leveledUp = true;
        else if (this.exp >= 500 && this.level != 5)
            leveledUp = true;
        else if (this.exp == 1000 && this.level != 10)
            leveledUp = true;
        
        return leveledUp;
    }

    /**
     * @returns whether the player's coins was successfully decreased or not
     */
    public boolean decCoins(int num) {
        if (this.coins - num < 0)
            return false;

        this.coins -= num;
        return true;
    }

    public void incCoins(int num) {
        this.coins += num;
    }

    public void setType(FarmerType type) {
        this.type = type;
    }

}
