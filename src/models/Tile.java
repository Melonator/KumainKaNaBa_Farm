package models;

public class Tile {
    private Plant plant;
    private int harvestDays;
    private int waterCount;
    private int fertCount;
    private State state;

    public Tile()
    {

    }

    public Plant getPlant()
    {
        return this.plant;
    }

    public int getHarvestDays() {
        return this.harvestDays;
    }

    public int getWaterCount()
    {
        return this.waterCount;
    }

    public int getFertCount() {
        return this.fertCount;
    }

    public State getState()
    {
        return this.state;
    }

    public void setPlant(Plant plant)
    {

    }

    public void decHarvestDays()
    {
    }

    public void incWaterCount()
    {

    }

    public void incFertCount()
    {

    }

    public void setState(State state)
    {


    }
}
