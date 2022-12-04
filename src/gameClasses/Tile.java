package gameClasses;

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
     */
    public void setPlant(Plant plant) {
        this.plant = plant;
        this.harvestDays = plant.getHarvestTime();
    }

    public void setHarvestDays(int amount) {
        this.harvestDays = amount;
    }

    public void setWaterCount(int amount) {
        this.waterCount = amount;
    }

    public void setFertCount(int amount) {
        this.fertCount = amount;
    }

    /**
     * Sets the state of the tile.
     * 
     * @param state The state to set the tile to.
     */
    public void setState(State state) {
        this.state = state;
    }
}
