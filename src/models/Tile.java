package models;

public class Tile {
    private Plant plant;
    private int harvestDays;
    private int waterCount;
    private int fertCount;
    private State state;

    public Tile() {
        //this.plant = ;
        this.harvestDays = 0;
        this.waterCount = 0;
        this.fertCount = 0;
        this.state = State.DEFAULT;
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

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public void decHarvestDays() {
        this.harvestDays--;
    }

    public void incWaterCount()
    {
        this.waterCount++;
    }

    public void incFertCount() {
        this.fertCount++;
    }

    public void setState(State state) {
        this.state = state;
    }
}
