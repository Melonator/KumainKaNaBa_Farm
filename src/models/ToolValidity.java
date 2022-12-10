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
    
    public int validatePlant(State[] adjacentTiles, State state, Plant plant, float playerCoins) {
        if(playerCoins < plant.getStorePrice())
            return -1;

        if(state != State.PLOWED)
            return -1;

        if(plant.getType().equals("Fruit-Tree") && adjacentTiles.length != 8) {
            return -1;
        }

        if(plant.getType().equals("Fruit-Tree")) {
            for(State s: adjacentTiles) {
                if(s == State.ROCK || s == State.PLANT || s == State.WITHERED) {
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

    public int validateWater(State state, int waterCount, int waterMax, int bonus) {
        if(state != State.PLANT)
            return -1;

        if(waterCount >= waterMax + bonus)
            return -1;

        return 1;
    }

    public int validateFert(State state, float playerCoins, int fertCount, int fertMax, int bonus) {
        if(state != State.PLANT)
            return -1;

        if(playerCoins < 10)
            return -1;

        if(fertCount >= fertMax + bonus)
            return -1;

        return 1;
    }

    public int validatePick(State state, float playerCoins) {
        if(state != State.ROCK)
            return -1;

        if(playerCoins < 50)
            return -1;

        return 1;
    }

    public int validateShovel(State state, float playerCoins) {
        if(state == State.ROCK)
            return -1;

        if(playerCoins < 7)
            return -1;

        return 1;
    }
}
