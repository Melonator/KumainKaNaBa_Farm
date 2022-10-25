package models;

public class Player {
    private int coins;
    private float exp;
    private int level;
    private FarmerType type;

    public Player()
    {

    }

    public int getCoins()
    {
        return this.coins;
    }

    public float getExp()
    {
        return this.exp;
    }

    public int getLevel()
    {
        return this.level;
    }

    public FarmerType getType()
    {
        return this.type;
    }

    /**
     * Increases exp by num
     *
     * @returns whether the player leveled up or not.
     */
    public boolean addExp(float num)
    {
        return true;
    }

    /**
     * @returns whether the player's coins was successfully decreased or not
     */
    public boolean decCoins(int num)
    {
        return true;
    }

    public void incCoins(int num)
    {

    }

    public void setType(FarmerType type)
    {

    }

}
