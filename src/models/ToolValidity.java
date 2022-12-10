package models;

import gameClasses.Plant;
import gameClasses.State;
import gameClasses.Tile;

/**
 * This class helps verify the validity of certain actions the player can perform. Each method returns an integer value that corresponds to a specific error message.
 */
public class ToolValidity {
    /**
     * Validates the plow action.
     * @param state the state of the tile the player is trying to plow
     * @return an integer value that corresponds to a specific error message
     */
    public int validatePlow(State state) {
        if (state != State.DEFAULT)
            return 2; // Error: Cannot plow due to a different state

        return 1; // Valid
    }
    
    /**
     * Validates the plant action.
     * @param adjacentTiles the states of the adjacent tiles to the tile the player is trying to plant
     * @param state the state of the tile the player is trying to plant
     * @param plant the plant the player is trying to plant
     * @param playerCoins the coins of the player
     * @return an integer value that corresponds to a specific error message
     */
    public int validatePlant(State[] adjacentTiles, State state, Plant plant, float playerCoins) {
        if(playerCoins < plant.getStorePrice())
            return 0; // Error: Not enough coins

        if(state != State.PLOWED)
            return 2; // Error: Cannot plant due to a different state

        if(plant.getType().equals("Fruit-Tree") && adjacentTiles.length != 8) {
            return 3; // Error: Cannot plant due to no space
        }

        if(plant.getType().equals("Fruit-Tree")) {
            for(State s: adjacentTiles) {
                if(s == State.ROCK || s == State.PLANT || s == State.WITHERED) {
                    return 3; // Error: Cannot plant due to no space
                }
            }
        }

        return 1; // Valid
    }

    /**
     * Validates the harvest action.
     * @param state the state of the tile the player is trying to harvest
     * @param harvestDays the harvest days of the plant the player is trying to harvest
     * @return an integer value that corresponds to a specific error message
     */
    public int validateHarvest(State state, int harvestDays) {
        if(state != State.PLANT)
            return 2; // Error: Cannot harvest due to a different state

        if(harvestDays != 0)
            return 4; // Error: Cannot harvest due to not being ready

        return 1; // Valid
    }

    /**
     * Validates the water action.
     * @param state the state of the tile the player is trying to water
     * @param waterCount the water count of the plant the player is trying to water
     * @param waterMax the water max of the plant the player is trying to water
     * @param bonus the water bonus of the player's farmer type
     * @return an integer value that corresponds to a specific error message
     */
    public int validateWater(State state, int waterCount, int waterMax, int bonus) {
        if(state != State.PLANT)
            return 2; // Error: Cannot water due to a different state

        if(waterCount >= waterMax + bonus)
            return 4; // Error: Cannot water due to max water count

        return 1; // Valid
    }

    /**
     * Validates the fertilize action.
     * @param state the state of the tile the player is trying to fertilize
     * @param playerCoins the coins of the player
     * @param fertCount the fertilize count of the plant the player is trying to fertilize
     * @param fertMax the fertilize max of the plant the player is trying to fertilize
     * @param bonus the fertilize bonus of the player's farmer type
     * @return an integer value that corresponds to a specific error message
     */
    public int validateFert(State state, float playerCoins, int fertCount, int fertMax, int bonus) {
        if(state != State.PLANT)
            return 2; // Error: Cannot fertilize due to a different state

        if(playerCoins < 10)
            return 0; // Error: Not enough coins

        if(fertCount >= fertMax + bonus)
            return 4; // Error: Cannot fertilize due to max fertilize count

        return 1; // Valid
    }

    /**
     * Validates the pickaxe action.
     * @param state the state of the tile the player is trying to pickaxe
     * @param playerCoins the coins of the player
     * @return an integer value that corresponds to a specific error message
     */
    public int validatePick(State state, float playerCoins) {
        if(state != State.ROCK)
            return 2; // Error: Cannot pickaxe due to a different state

        if(playerCoins < 50)
            return 0; // Error: Not enough coins

        return 1; // Valid
    }

    /**
     * Validates the shovel action.
     * @param state the state of the tile the player is trying to shovel
     * @param playerCoins the coins of the player
     * @return an integer value that corresponds to a specific error message
     */
    public int validateShovel(State state, float playerCoins) {
        if(state == State.ROCK)
            return 2; // Error: Cannot shovel due to a different state

        if(playerCoins < 7)
            return 0; // Error: Not enough coins

        return 1; // Valid
    }
}
