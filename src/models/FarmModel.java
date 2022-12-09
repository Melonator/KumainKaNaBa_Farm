package models;

import gameClasses.Coordinate;
import gameClasses.Plant;
import gameClasses.State;
import gameClasses.Tile;

import java.io.File;
import java.util.*;

public class FarmModel {
    private Tile[][] tiles;
    private Dictionary<String, Plant> plantMasterList;

    public FarmModel() {
        this.tiles = new Tile[5][10];
        this.plantMasterList = new Hashtable<>();
        initTiles();
        initPlants();
        initRocks();
    }

    private void initPlants() {
        File file = new File("src/plants.txt");
        Scanner input = null;
        ArrayList<String> list = new ArrayList();
        try {
            input = new Scanner(file);
        }
        catch(Exception e) {
            System.out.println("File not found!");
        }

        while (input.hasNextLine()) {
            list.add(input.nextLine());
        }

        for(String s : list) {
            String[] values = s.split(" ");
            Plant p = createPlantFromText(values);
            this.plantMasterList.put(p.getName(), p);
        }
    }

    private Plant createPlantFromText(String[] values) {
        String name = values[0];
        String type = values[1];
        int harvestTime = Integer.parseInt(values[2]);
        int waterMin = Integer.parseInt(values[3]);
        int waterMax = Integer.parseInt(values[4]);
        int fertMin = Integer.parseInt(values[5]);
        int fertMax = Integer.parseInt(values[6]);
        int minProduce = Integer.parseInt(values[7]);
        int maxProduce = Integer.parseInt(values[8]);
        int storePrice = Integer.parseInt(values[9]);
        int retail = Integer.parseInt(values[10]);
        float exp = Float.parseFloat(values[11]);

        return new Plant(name, type, harvestTime, waterMin, waterMax,
                fertMin, fertMax, minProduce, maxProduce, storePrice,
                retail, exp);
    }
    private void initTiles() {
        for(int i = 0; i < 5; i ++) {
            for(int j = 0; j < 10; j++) {
                this.tiles[i][j] = new Tile();
            }
        }
    }

    private void initRocks() {
        File file = new File("src/rocksMap.txt");
        Scanner input = null;
        List<String> list = new ArrayList();
        try {
            input = new Scanner(file);
        }
        catch(Exception e) {
            System.out.println("File not found!");
        }

        while (input.hasNextLine()) {
            list.add(input.nextLine());
        }

        int i = 0;
        for(String s : list) {
            for(int j = 0; i < s.length(); i++) {
                if(s.charAt(i) == '1') {
                    this.tiles[i][j].setState(State.ROCK);
                }
            }
            i++;
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
