package models;

/**
 * The FarmerType class is reponsible to store the farmer type and bonuses of the player.
 */
public class FarmerType {
    private final String name;
    private final int waterBonus;
    private final int fertBonus;
    private final int seedDiscount;
    private final int bonusProduce;

    /**
     * Constructor for FarmerType. Initializes the farmer type.
     * 
     * @param name              Name of the farmer type
     * @param waterBonus        Water bonus of the farmer type
     * @param fertBonus         Fertilizer bonus of the farmer type
     * @param seedDiscount      Seed discount of the farmer type
     * @param bonusProduce      Bonus produce of the farmer type
     */
    public FarmerType(String name, int waterBonus, int fertBonus, int seedDiscount, int bonusProduce) {
        this.name = name;
        this.waterBonus = waterBonus;
        this.fertBonus = fertBonus;
        this.seedDiscount = seedDiscount;
        this.bonusProduce = bonusProduce;
    }

    /**
     * Returns the name of the farmer type
     * 
     * @return name of the farmer type
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the water bonus of the farmer type
     * 
     * @return water bonus of the farmer type
     */
    public int getWaterBonus() {
        return this.waterBonus;
    }

    /**
     * Returns the fertilizer bonus of the farmer type
     * 
     * @return fertilizer bonus of the farmer type
     */
    public int getFertBonus() {
        return this.fertBonus;
    }

    /**
     * Returns the seed discount of the farmer type
     * 
     * @return seed discount of the farmer type
     */
    public int getSeedDiscount() {
        return this.seedDiscount;
    }

    /**
     * Returns the bonus produce of the farmer type
     * 
     * @return bonus produce of the farmer type
     */
    public int getBonusProduce() {
        return this.bonusProduce;
    }
}
