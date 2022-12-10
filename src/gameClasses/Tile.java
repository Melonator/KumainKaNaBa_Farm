package gameClasses;

/**
 * The Tile class is reponsible for storing the plant and tile's information.
 */
public class Tile {
    private Plant plant; // plant object assigned to the tile
    private int harvestDays; // number of days it takes to harvest a crop
    private int waterCount; // number of times the tile has been watered
    private int fertCount; // number of times the tile has been fertilized
    private State state; // state of the tile

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

    /**
     * Sets the number of days it takes to harvest a crop.
     * 
     * @param amount The number of days it takes to harvest a crop.
     */
    public void setHarvestDays(int amount) {
        this.harvestDays = amount;
    }

    /**
     * Sets the number of times the plant has been watered.
     * 
     * @param amount The number of times the plant has been watered.
     */
    public void setWaterCount(int amount) {
        this.waterCount = amount;
    }

    /**
     * Sets the number of times the plant has been fertilized.
     * 
     * @param amount The number of times the plant has been fertilized.
     */
    public void setFertCount(int amount) {
        this.fertCount = amount;
    }

    /**
     * Sets the state of the tile.
     * 
     * @param state The state of the tile.
     */
    public void setState(State state) {
        this.state = state;
    }
}
