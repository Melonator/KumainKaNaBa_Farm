package models;

import gameClasses.Coordinate;
import gameClasses.Plant;
import gameClasses.State;
import gameClasses.Tile;

import java.util.Dictionary;
import java.util.Hashtable;

public class FarmModel {
    private Tile[][] tiles;
    private Dictionary<String, Plant> plantMasterList;

    public FarmModel() {
        this.tiles = new Tile[5][10];
        this.plantMasterList = new Hashtable<>();
    }

    public Tile[] getAdjacentTiles(Coordinate coord) {
        return null;
    }

    public void setPlant(Plant plant, Coordinate coord) {
       tiles[coord.x][coord.y].setPlant(plant);
    }

    public void decHarvestDays(Coordinate coord) {
        int currHarvestDays = tiles[coord.x][coord.y].getHarvestDays();
        tiles[coord.x][coord.y].setHarvestDays(currHarvestDays - 1);
    }

    public void addWaterCount(int bonus, Coordinate coord) {
        int currWaterCount = tiles[coord.x][coord.y].getWaterCount();
        tiles[coord.x][coord.y].setHarvestDays(currWaterCount + 1);
    }

    public void addFertCount(int bonus, Coordinate coord) {
        int currFertCount = tiles[coord.x][coord.y].getFertCount();
        tiles[coord.x][coord.y].setHarvestDays(currFertCount + 1);
    }

    public void setState(State state, Coordinate coord) {
        tiles[coord.x][coord.y].setState(state);
    }

    public void removePlant(Coordinate coord) {
        tiles[coord.x][coord.y].setPlant(new Plant("Empty", "No Type", 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0));
        tiles[coord.x][coord.y].setFertCount(0);
        tiles[coord.x][coord.y].setWaterCount(0);
        tiles[coord.x][coord.y].setHarvestDays(0);
    }

    public Plant getTilePlant(Coordinate coord) {
        return tiles[coord.x][coord.y].getPlant();
    }

    public int getTileWaterCount(Coordinate coord) {
        return tiles[coord.x][coord.y].getWaterCount();
    }

    public int getTileFertCount(Coordinate coord) {
        return tiles[coord.x][coord.y].getFertCount();
    }

    public int getTileHarvestDays(Coordinate coord) {
        return tiles[coord.x][coord.y].getHarvestDays();
    }

    public State getTileState(Coordinate coord) {
        return tiles[coord.x][coord.y].getState();
    }
}
