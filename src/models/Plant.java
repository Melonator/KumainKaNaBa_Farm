package models;

public class Plant {
    private final String name;
    private final String type;
    private final int harvestTime;
    private final int waterMin;
    private final int waterMax;
    private final int fertMin;
    private final int fertMax;
    private final int maxProduce;
    private final int storePrice;
    private final int retail;
    private final float expYield;

    public Plant(String name, String type, int harvestTime,
            int waterMin, int waterMax, int fertMin, int fertMax,
            int maxProduce, int storePrice, int retail, int expYield)
    {
        this.name = name;
        this.type = type;
        this.harvestTime = harvestTime;
        this.waterMin = waterMin;
        this.waterMax = waterMax;
        this.fertMin = fertMin;
        this.fertMax = fertMax;
        this.maxProduce = maxProduce;
        this.storePrice = storePrice;
        this.retail = retail;
        this.expYield = expYield;
    }

    public String getName()
    {
        return this.name;
    }

    public String getType()
    {
        return this.type;
    }

    public int getHarvestTime() {
        return this.harvestTime;
    }

    public int getWaterMin() {
        return this.waterMin;
    }

    public int getWaterMax() {
        return this.waterMax;
    }

    public int getFertMin() {
        return this.fertMin;
    }

    public int getFertMax() {
        return this.fertMax;
    }

    public int getMaxProduce() {
        return this.maxProduce;
    }

    public int getStorePrice() {
        return this.storePrice;
    }

    public int getRetail() {
        return this.retail;
    }

    public float getExpYield() {
        return this.expYield;
    }
}
