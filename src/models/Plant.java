package models;

/**
 * The Plant class is responsible for holding the plant's data. All of its attributes are final.
 */
public class Plant {
    private final String name;
    private final String type;
    private final int harvestTime;
    private final int waterMin;
    private final int waterMax;
    private final int fertMin;
    private final int fertMax;
    private final int minProduce;
    private final int maxProduce;
    private final int storePrice;
    private final int retail;
    private final float expYield;

    public Plant(String name, String type, int harvestTime,
            int waterMin, int waterMax, int fertMin, int fertMax, int minProduce,
            int maxProduce, int storePrice, int retail, float expYield)
    {
        this.name = name;
        this.type = type;
        this.harvestTime = harvestTime;
        this.waterMin = waterMin;
        this.waterMax = waterMax;
        this.fertMin = fertMin;
        this.fertMax = fertMax;
        this.minProduce = minProduce;
        this.maxProduce = maxProduce;
        this.storePrice = storePrice;
        this.retail = retail;
        this.expYield = expYield;
    }

    /**
     * Returns the name of the plant.
     * @return the name of the plant.
     */
    public String getName()
    {
        return this.name;
    }


    /**
     * Returns the type of the plant.
     * @return the type of the plant.
     */
    public String getType()
    {
        return this.type;
    }


    /**
     * Returns the harvest time of the plant.
     * @return the harvest time of the plant.
     */
    public int getHarvestTime() {
        return this.harvestTime;
    }

    /**
     * Returns the minimum water requirement of the plant.
     * @return the minimum water requirement of the plant.
     */
    public int getWaterMin() {
        return this.waterMin;
    }

    /**
     * Returns the maximum water requirement of the plant.
     * @return the maximum water requirement of the plant.
     */
    public int getWaterMax() {
        return this.waterMax;
    }

    /**
     * Returns the minimum fertilizer requirement of the plant.
     * @return the minimum fertilizer requirement of the plant.
     */
    public int getFertMin() {
        return this.fertMin;
    }

    /**
     * Returns the maximum fertilizer requirement of the plant.
     * @return the maximum fertilizer requirement of the plant.
     */
    public int getFertMax() {
        return this.fertMax;
    }

    /**
     * Returns the minimum produce of the plant.
     * @return the minimum produce of the plant.
     */
    public int getMinProduce(){
        return this.minProduce;
    }

    /**
     * Returns the maximum produce of the plant.
     * @return the maximum produce of the plant.
     */
    public int getMaxProduce() {
        return this.maxProduce;
    }

    /**
     * Returns the store price of the seed.
     * @return the store price of the seed.
     */
    public int getStorePrice() {
        return this.storePrice;
    }

    /**
     * Returns the retail price of the produce.
     * @return the retail price of the produce.
     */
    public int getRetail() {
        return this.retail;
    }

    /**
     * Returns the expected yield of the plant.
     * @return the expected yield of the plant.
     */
    public float getExpYield() {
        return this.expYield;
    }
}
