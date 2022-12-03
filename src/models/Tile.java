package models;

/**
 * The Tile class is reponsible for storing the plant and tile's information.
 */
public class Tile {
    private Plant plant;
    private int harvestDays;
    private int waterCount;
    private int fertCount;
    private State state;

    /**
     * Constructor for Tile. Initializes the tile.
     */
    public Tile() {
        this.harvestDays = 0;
        this.waterCount = 0;
        this.fertCount = 0;
        this.state = State.DEFAULT;
        this.plant = new Plant("Empty", "No Type", 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0);
    }

    /**
     * Returns the plant object assigned to the tile.
     * 
     * @return The plant object.
     */
    public Plant getPlant() {
        return this.plant;
    }

    /**
     * Returns the number of days it takes to harvest a crop
     * 
     * @return The number of days it takes to harvest the crop.
     */
    public int getHarvestDays() {
        return this.harvestDays;
    }

    /**
     * Returns the number of times the tile has been watered.
     * 
     * @return The number of times the tile has been watered.
     */
    public int getWaterCount() {
        return this.waterCount;
    }

    /**
     * Returns the number of times the tile has been fertilized.
     * 
     * @return The number of times the tile has been fertilized.
     */
    public int getFertCount() {
        return this.fertCount;
    }

    /**
     * Returns the state of the tile.
     * 
     * @return The state of the tile.
     */
    public State getState() {
        return this.state;
    }

    /**
     * Assigns a plant to the tile.
     * 
     * @param plant The plant to assign to the tile.
     * @return true if the plant was assigned to the tile, false otherwise
     */
    public boolean setPlant(Plant plant) {
        //Do not assign if the tile is not plowed
        //A special withered plant can be assigned if the plant withers
        if(this.state != state.PLOWED && plant.getName() != "Withered")
            return false;

        this.plant = plant;
        this.harvestDays = plant.getHarvestTime();
        //Do not set the state to plant if the plant withered
        if(plant.getName() != "Withered")
            this.state = State.PLANT;
        return true;
    }


    /**
     * Decrement the number of days until harvest
     */
    public void decHarvestDays() {
        this.harvestDays--;
    }

    /**
     * Increment the number of times the tile has been watered.
     * 
     * @param bonus The bonus water count to add to the plant's maximum.
     * @return true if the tile was watered, false otherwise.
     */
    public boolean addWaterCount(int bonus)
    {
        //Do not add to the watercount if the max needs is met
        if(this.waterCount == this.plant.getWaterMax() + bonus)
            return false;

        this.waterCount++;
        return true;
    }

    /**
     * Increment the number of times the tile has been fertilized.
     * 
     * @param bonus The bonus fertilizer count to add to the plant's maximum.
     * @return true if the tile was fertilized, false otherwise.
     */
    public boolean addFertCount(int bonus ) {
        //Do not add to the fertcount if the max needs is met
        if(this.fertCount == this.plant.getFertMax() + bonus)
            return false;

        this.fertCount++;
        return true;
    }

    /**
     * Sets the state of the tile.
     * 
     * @param state The state to set the tile to.
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Removes the plant from the tile and resets the tile's attributes.
     */
    public void removePlant() {
        this.plant = new Plant("Empty", "No Type", 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0);
        this.fertCount = 0;
        this.waterCount = 0;
        this.harvestDays = 0;
    }
}
