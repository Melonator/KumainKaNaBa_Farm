package models;

public class FarmerType {
    private String name;
    private int waterBonus;
    private int fertBonus;
    private int seedDiscount;
    private int bonusProduce;

    public FarmerType(String name, int waterBonus, int fertBonus, int seedDiscount, int bonusProduce) {
        this.name = name;
        this.waterBonus = waterBonus;
        this.fertBonus = fertBonus;
        this.seedDiscount = seedDiscount;
        this.bonusProduce = bonusProduce;
    }

    public String getName() {
        return this.name;
    }

    public int getWaterBonus() {
        return this.waterBonus;
    }

    public int getFertBonus() {
        return this.fertBonus;
    }

    public int getSeedDiscount() {
        return this.seedDiscount;
    }

    public int getBonusProduce() {
        return this.bonusProduce;
    }
}
