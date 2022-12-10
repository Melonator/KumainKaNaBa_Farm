package models;

import gameClasses.Coordinate;
import gameClasses.Plant;
import gameClasses.State;
import gameClasses.Tile;

import java.io.File;
import java.util.*;

/**
 * This class is the model for the farm. It contains helper methods to retrieve and set data as well as housing the masterlist of plants.
 */
public class FarmModel {
    private Tile[][] tiles; // 2D array of tiles the player can plant on
    private Dictionary<String, Plant> plantMasterList; // list of plants the player can choose from

    /**
     * Constructor for FarmModel. Initializes the tiles and plantMasterList.
     */
    public FarmModel() {
        this.tiles = new Tile[5][10];
        this.plantMasterList = new Hashtable<>();
        initTiles();
        initPlants();
        initRocks();
    }

    /**
     * Initializes the dictionary of plants from a text file.
     */
    private void initPlants() {
        String filePath = "readTexts/plants.txt";
        if(System.getProperty("os.name").equals("Windows 11") || System.getProperty("os.name").equals("Windows 10"))
            filePath = "src/readTexts/plants.txt";

        File file = new File(filePath);
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
            this.plantMasterList.put(p.getName().toLowerCase(), p);
        }
    }

    /**
     * Creates a plant object from a string array of values.
     * @param values the string array containing the data of the plant
     * @return the plant object
     */
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
    /**
     * Initializes the tiles.
     */
    private void initTiles() {
        for(int i = 0; i < 5; i ++) {
            for(int j = 0; j < 10; j++) {
                this.tiles[i][j] = new Tile();
            }
        }
    }

    /**
     * Initializes the rocks on the tiles.
     */
    private void initRocks() {
        String filePath = "readTexts/rocksMap.txt";
        if(System.getProperty("os.name").equals("Windows 11") || System.getProperty("os.name").equals("Windows 10"))
            filePath = "src/readTexts/rocksMap.txt";

        File file = new File(filePath);
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

        // Reading the text file and setting the state of the tiles to rock.
        // Character 1 represents a rock.
        int i = 0;
        for(String s : list) {
            for(int j = 0; j < s.length(); j++) {
                if(s.charAt(j) == '1') {
                    this.tiles[i][j].setState(State.ROCK);
                }
            }
            i++;
        }
    }

    /**
     * Gets the state of all adjacent tiles to the given coordinate.
     * @param coord the coordinate of the tile
     * @return an arraylist of states of the adjacent tiles if the tile is not on the edge, else it returns an empty arraylist.
     */
    public ArrayList<State> getAdjacentTilesStates(Coordinate coord) {
        ArrayList<State> states = new ArrayList<>();

        if(coord.x == 0 || coord.x == 4 || coord.y == 0 || coord.y == 9) {
            return states;
        }

        states.add(this.tiles[coord.x - 1][coord.y + 1].getState());
        states.add(this.tiles[coord.x][coord.y + 1].getState());
        states.add(this.tiles[coord.x + 1][coord.y + 1].getState());

        states.add(this.tiles[coord.x - 1][coord.y].getState());
        states.add(this.tiles[coord.x + 1][coord.y].getState());

        states.add(this.tiles[coord.x - 1][coord.y - 1].getState());
        states.add(this.tiles[coord.x][coord.y - 1].getState());
        states.add(this.tiles[coord.x + 1][coord.y - 1].getState());
        return states;
    }

    /**
     * Sets the plant of a tile given a coordinate and a plant.
     * @param plant the plant to be set
     * @param coord the coordinate of the tile
     */
    public void setPlant(Plant plant, Coordinate coord) {
       tiles[coord.x][coord.y].setPlant(plant);
    }

    /**
     * Decreases the harvest days of a tile given a coordinate.
     * @param coord the coordinate of the tile
     */
    public void decHarvestDays(Coordinate coord) {
        int currHarvestDays = tiles[coord.x][coord.y].getHarvestDays();
        tiles[coord.x][coord.y].setHarvestDays(currHarvestDays - 1);
    }

    /**
     * Adds a water count to a tile given a coordinate.
     * @param coord the coordinate of the tile
     */
    public void addWaterCount(Coordinate coord) {
        int currWaterCount = tiles[coord.x][coord.y].getWaterCount();
        tiles[coord.x][coord.y].setWaterCount(currWaterCount + 1);
    }

    /**
     * Adds a fertilizer count to a tile given a coordinate.
     * @param coord the coordinate of the tile
     */
    public void addFertCount(Coordinate coord) {
        int currFertCount = tiles[coord.x][coord.y].getFertCount();
        tiles[coord.x][coord.y].setFertCount(currFertCount + 1);
    }

    /**
     * Sets the state of a tile given a coordinate and a state.
     * @param state the state to be set
     * @param coord the coordinate of the tile
     */
    public void setState(State state, Coordinate coord) {
        tiles[coord.x][coord.y].setState(state);
    }

    /**
     * Removes the plant and resets its values from a tile given a coordinate.
     * @param coord the coordinate of the tile
     */
    public void removePlant(Coordinate coord) {
        tiles[coord.x][coord.y].setPlant(new Plant("Empty", "No Type", 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0));
        tiles[coord.x][coord.y].setFertCount(0);
        tiles[coord.x][coord.y].setWaterCount(0);
        tiles[coord.x][coord.y].setHarvestDays(0);
    }

    /**
     * Gets the plant from the masterlist given a plant name.
     * @param plantName the name of the plant
     * @return the plant
     */
    public Plant getPlantFromList(String plantName) {
        return this.plantMasterList.get(plantName);
    }

    /**
     * Gets the plant from a tile given a coordinate.
     * @param coord the coordinate of the tile
     * @return the plant
     */
    public Plant getTilePlant(Coordinate coord) {
        return tiles[coord.x][coord.y].getPlant();
    }

    /**
     * Gets the water count from a tile given a coordinate.
     * @param coord the coordinate of the tile
     * @return the water count
     */
    public int getTileWaterCount(Coordinate coord) {
        return tiles[coord.x][coord.y].getWaterCount();
    }

    /**
     * Gets the fertilizer count from a tile given a coordinate.
     * @param coord the coordinate of the tile
     * @return the fertilizer count
     */
    public int getTileFertCount(Coordinate coord) {
        return tiles[coord.x][coord.y].getFertCount();
    }

    /**
     * Gets the harvest days from a tile given a coordinate.
     * @param coord the coordinate of the tile
     * @return the harvest days
     */
    public int getTileHarvestDays(Coordinate coord) {
        return tiles[coord.x][coord.y].getHarvestDays();
    }

    /**
     * Gets the state from a tile given a coordinate.
     * @param coord the coordinate of the tile
     * @return the state
     */
    public State getTileState(Coordinate coord) {
        return tiles[coord.x][coord.y].getState();
    }

    /**
     * Gets the coordinate of all active growing crops.
     * @return an arraylist of coordinates of all active growing crops
     */
    public ArrayList<Coordinate> getActiveGrowingCrops() {
        ArrayList<Coordinate> activeCrops = new ArrayList();
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 10; j++) {
                if(tiles[i][j].getState() == State.PLANT)
                    activeCrops.add(new Coordinate(i, j));
            }
        }
        return activeCrops;
    }
}
