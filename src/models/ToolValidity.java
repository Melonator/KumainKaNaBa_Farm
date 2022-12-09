package models;

import gameClasses.Plant;
import gameClasses.State;
import gameClasses.Tile;

public class ToolValidity {
    public int validatePlow(State state) {
        if (state != State.DEFAULT)
            return -1;

        return 1;
    }
    
    public int validatePlant(Tile[] adjacentTiles, State state, Plant plant, float playerCoins) {
        if(playerCoins < plant.getStorePrice())
            return -1;

        if(state != State.PLOWED)
            return -1;

        if(plant.getType() == "Tree" && adjacentTiles.length != 8) {
            return -1;
        }

        if(plant.getType() == "Tree") {
            for(Tile t : adjacentTiles) {
                if(t.getState() != State.DEFAULT) {
                    return -1;
                }
            }
        }

        return 1;
    }

    public int validateHarvest(State state, int harvestDays) {
        if(state != State.PLANT)
            return -1;

        if(harvestDays != 0)
            return -1;

        return 1;
    }

    public int validateWater(State state) {
        if(state != State.PLANT)
            return -1;

        return 1;
    }

    public int validateFert(State state, float playerCoins) {
        if(state != State.PLANT)
            return -1;

        if(playerCoins < 10)
            return -1;

        return 1;
    }

    public int validatePick(State state, float playerCoins) {
        if(state != State.ROCK)
            return -1;

        if(playerCoins < 100)
            return -1;

        return 1;
    }

    public int validateShovel(State state, float playerCoins) {
        if(state == State.ROCK)
            return -1;

        if(playerCoins < 100)
            return -1;

        return 1;
    }
}
