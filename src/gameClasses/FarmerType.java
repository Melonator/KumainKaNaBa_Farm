package gameClasses;

/**
 * The FarmerType class is reponsible to store the farmer type and bonuses of the player.
 */
public class FarmerType {
    private final String name; // name of the farmer type
    private final int waterBonus; // water bonus of the farmer type
    private final int fertBonus; // fertilizer bonus of the farmer type
    private final int seedDiscount; // seed discount of the farmer type
    private final int bonusProduce; // bonus produce of the farmer type
    private final int price; // price of the farmer type

    /**
     * Constructor for FarmerType. Initializes the farmer type.
     * 
     * @param name              Name of the farmer type
     * @param waterBonus        Water bonus of the farmer type
     * @param fertBonus         Fertilizer bonus of the farmer type
     * @param seedDiscount      Seed discount of the farmer type
     * @param bonusProduce      Bonus produce of the farmer type
     * @param price             Price of the farmer type
     */
    public FarmerType(String name, int waterBonus, int fertBonus, int seedDiscount, int bonusProduce, int price) {
        this.name = name;
        this.waterBonus = waterBonus;
        this.fertBonus = fertBonus;
        this.seedDiscount = seedDiscount;
        this.bonusProduce = bonusProduce;
        this.price = price;
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

    /**
     * Returns the price of the farmer type
     * 
     * @return price of the farmer type
     */
    public int getPrice() {
        return this.price;
    }
}
