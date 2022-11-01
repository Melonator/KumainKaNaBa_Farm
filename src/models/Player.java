package models;

public class Player {
    private float coins;
    private float exp;
    private int level;
    private FarmerType type;

    public Player() {
        this.coins = 100;
        this.exp = 0;
        this.level = 0;
        this.type = new FarmerType("Farmer", 0, 0, 0, 0);
    }

    public float getCoins() {
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
        Notification.push("[ You have gained " + num + " exp! ]");
        boolean leveledUp = false;

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
     * @returns whether the player's coins was successfully decreased or not
     */
    public boolean decCoins(int num) {
        if (this.coins - num < 0)
            return false;

        Notification.push("[ You've spent " + num + " Object Coins! ]");
        this.coins -= num;
        return true;
    }

    public void incCoins(float num) {
        Notification.push("[ You've gained " + num + " Object Coins! ]");
        this.coins += num;
    }

    public void setType(FarmerType type) {
        this.type = type;
    }

}
