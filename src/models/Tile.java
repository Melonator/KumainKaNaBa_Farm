package models;

public class Tile {
    private Plant plant;
    private int harvestDays;
    private int waterCount;
    private int fertCount;
    private State state;

    public Tile() {
        this.harvestDays = 0;
        this.waterCount = 0;
        this.fertCount = 0;
        this.state = State.DEFAULT;
        this.plant = new Plant("Empty", "No Type", 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0);
    }

    public Plant getPlant() {
        return this.plant;
    }

    public int getHarvestDays() {
        return this.harvestDays;
    }

    public int getWaterCount() {
        return this.waterCount;
    }

    public int getFertCount() {
        return this.fertCount;
    }

    public State getState() {
        return this.state;
    }

    public boolean setPlant(Plant plant) {
        if(this.state != state.PLOWED)
            return false;

        this.plant = plant;
        this.harvestDays = plant.getHarvestTime();
        this.state = State.PLANT;
        return true;
    }

    public void decHarvestDays() {
        this.harvestDays--;
    }

    public boolean addWaterCount(int bonus)
    {
        if(this.waterCount == this.plant.getWaterMax() + bonus)
            return false;

        this.waterCount++;
        return true;
    }

    public boolean addFertCount(int bonus ) {
        if(this.fertCount == this.plant.getFertMax() + bonus)
            return false;

        this.fertCount++;
        return true;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void resetWaterCount() {
        this.waterCount = 0;
    }

    public void resetFertCount() {
        this.fertCount = 0;
    }

}
