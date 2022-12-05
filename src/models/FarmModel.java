package models;

import gameClasses.Coordinate;
import gameClasses.Plant;
import gameClasses.State;
import gameClasses.Tile;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class FarmModel {
    private Tile[][] tiles;
    private Dictionary<String, Plant> plantMasterList;

    public FarmModel() {
        this.tiles = new Tile[5][10];
        initTiles();
        this.plantMasterList = new Hashtable<>();
        plantMasterList.put("Turnip", new Plant("Turnip", "Root", 0, 1, 2, 0, 1, 1, 2, 5, 6, 5f));
        plantMasterList.put("Carrot", new Plant("Carrot", "Root", 3, 1, 2, 0, 1, 1, 2, 10, 9, 7.5f));
    }

    private void initTiles() {
        for(int i = 0; i < 5; i ++) {
            for(int j = 0; j < 10; j++) {
                this.tiles[i][j] = new Tile();
            }
        }
    }

    public ArrayList<Tile> getAdjacentTiles(Coordinate coord) {
        ArrayList<Tile> tiles = new ArrayList<>();

        if(coord.x == 0 || coord.x == 9 || coord.y == 0 || coord.y == 4) {
            return tiles;
        }

        tiles.add(this.tiles[coord.x - 1][coord.y + 1]);
        tiles.add(this.tiles[coord.x][coord.y + 1]);
        tiles.add(this.tiles[coord.x + 1][coord.y + 1]);

        tiles.add(this.tiles[coord.x - 1][coord.y]);
        tiles.add(this.tiles[coord.x + 1][coord.y]);

        tiles.add(this.tiles[coord.x - 1][coord.y - 1]);
        tiles.add(this.tiles[coord.x][coord.y - 1]);
        tiles.add(this.tiles[coord.x + 1][coord.y - 1]);
        return tiles;
    }

    public void setPlant(Plant plant, Coordinate coord) {
       tiles[coord.x][coord.y].setPlant(plant);
    }

    public void decHarvestDays(Tile tile) {
        int currHarvestDays = tile.getHarvestDays();
        tile.setHarvestDays(currHarvestDays - 1);
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

    public Plant getPlantFromList(String plantName) {
        plantName = plantName.substring(0,1).toUpperCase() + plantName.substring(1);
        return this.plantMasterList.get(plantName);
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

    public ArrayList<Tile> getActiveGrowingCrops() {
        ArrayList<Tile> activeCrops = new ArrayList();
        for(Tile[] tiles : this.tiles) {
            for(Tile t : tiles) {
                if(t.getState() == State.PLANT)
                    activeCrops.add(t);
            }
        }

        return activeCrops;
    }
}
