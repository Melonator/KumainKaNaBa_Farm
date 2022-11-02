package models;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner6;

/**
 * The Game class is responsible for handling the core game mechanics and the game flow.
 */
public class Game {
    private Queue<FarmerType> registerQueue;
    private Tile tile;
    private Plant[] plantMasterList;
    private Tool tool;
    private Player player;
    private int dayCounter;

    /**
     * Constructor for the Game class. Initializes the game.
     */
    public Game()
    {
        //Initialize all the classes' members
        //registerQueue is a LinkedList for FIFO
        registerQueue = new LinkedList();
        this.player = new Player();
        this.dayCounter = 1;
        this.tool = new Tool();
        this.tile = new Tile();
        plantMasterList = new Plant[9];

        //Create all 8 plants (The Withered being the 9th)
        plantMasterList[0] = new Plant("Turnip", "Root", 2, 1, 2, 0, 1, 1, 2, 5, 6, 5f);
        plantMasterList[1] = new Plant("Carrot", "Root", 3, 1, 2, 0, 1, 1, 2, 10, 9, 7.5f);
        plantMasterList[2] = new Plant("Potato", "Root", 5, 3, 4, 1, 2, 1, 10, 20, 3, 12.5f);
        plantMasterList[3] = new Plant("Rose", "Flower", 1, 1, 2, 0, 1, 1, 1, 5, 5, 2.5f);
        plantMasterList[4] = new Plant("Tulips", "Flower", 2, 2, 3, 0, 1, 1, 1, 10, 9, 5f);
        plantMasterList[5] = new Plant("Sunflower", "Flower", 3, 2, 3, 1, 2, 1, 1, 20, 19, 7.5f);
        plantMasterList[6] = new Plant("Mango Tree", "Fruit Tree", 10, 7, 7, 4, 4, 5, 15, 100, 8, 25f);
        plantMasterList[7] = new Plant("Apple Tree", "Fruit Tree", 10, 7, 7, 5, 5, 10, 15, 200, 5, 25f);
        plantMasterList[8] = new Plant("Withered", "No Type", -1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    /**
     * Checks is the if the game is over or not.
     * 
     * @return if the game is over or not.
     */
    private boolean advanceDay()
    {
        this.dayCounter++;

        //Decrease the harvest days if a plant is present
        if (tile.getState() == State.PLANT) {
            //Decrease the harvest days if it's not withered
            if (tile.getState() != State.WITHERED) {
                tile.decHarvestDays();

                //Wither the plant if wasn't harvested on harvest day
                if (tile.getHarvestDays() == -1) {
                    Notification.push("[ Your " + tile.getPlant().getName() + " has withered! ]");
                    tile.removePlant();
                    tile.setState(State.WITHERED);
                    tile.setPlant(plantMasterList[8]);
                }
            }
            //Logic for harvest day
            if (tile.getHarvestDays() == 0) {
                if (tile.getWaterCount() >= tile.getPlant().getWaterMin() && tile.getFertCount() >= tile.getPlant().getFertMin()) {
                    Notification.push("[ Your " + tile.getPlant().getName() + " is ready for harvest! ]");
                }
                //Plant will wither if needs are not met on harvest day
                else {
                    Notification.push("[ Your " + tile.getPlant().getName() + " has withered! ]");
                    tile.removePlant();
                    tile.setState(State.WITHERED);
                    tile.setPlant(plantMasterList[8]);
                }
            }
        }

        //Logic for game over
        //12 coins is the minimum to continue with a withered plant (Shovel and turnip)
        if(tile.getState() == State.WITHERED && player.getCoins() < 12)
        {
            Notification.push("[ You no longer have any money to continue the game... ]");
            return true;
        }
        //5 coins is the minimum with no growing plants
        else if(tile.getState() == State.DEFAULT && player.getCoins() < 5)
        {
            Notification.push("[ You no longer have any money to continue the game... ]");
            return true;
        }

        return false;
    }

    /**
     * Prompt and logic for the registration process.
     * 
     * @return if the player successfully registered or not.
     */
    private boolean register()
    {
        //Skeleton code for registering the player in future implementations
        boolean isRegistered = false;

        return isRegistered;
    }

    /**
     * Prompts the user to plant.
     */
    private void plant() {
        Scanner scanner = new Scanner(System.in);

        //Display plants
        System.out.println("What would you like to plant?");
        for (int i = 0; i < plantMasterList.length - 1; i++) {
            System.out.println("<" + (i+1) + "> " + plantMasterList[i].getName() + ": " + plantMasterList[i].getStorePrice() + " oc");
        }
        System.out.print("Offer Input >> ");
        int choice = scanner.nextInt();

        //Attempt in planting the seed
        tool.plantSeed(player, tile, plantMasterList[choice-1]);
    }

    /**
     * Runs the game.
     */
    public void run()
    {
        Scanner scanner = new Scanner(System.in);
        boolean isGameOver = false;
        //variable to check whether the player leveled up or not
        //Is used for registering in future implementations
        boolean leveledUp = false;

        //Main game loop
        while (!isGameOver) {
            //Print necessary information
            System.out.println("Day Counter: " + this.dayCounter);
            System.out.println("Exp: " + this.player.getExp() + " | Lvl: " + this.player.getLevel());
            System.out.println("Object Coins: " + this.player.getCoins());
            System.out.println("Plant on tile: " + tile.getPlant().getName());
            if(tile.getState() == State.WITHERED)
                System.out.println("Times Watered: Cannot Water | Times Fertilized: Cannot Fertilize | Days until harvest: Cannot Harvest");
            else
                System.out.println("Times Watered: " + tile.getWaterCount() + "/" + tile.getPlant().getWaterMin() + "(" + tile.getPlant().getWaterMax() + ")"
                        + " | Times Fertilized: " + tile.getFertCount() + "/" + tile.getPlant().getFertMin() + "(" + tile.getPlant().getFertMax() + ")" + " | Days until harvest: " + tile.getHarvestDays());
            System.out.println("\nWhat would you like to do?");
            System.out.println("<1> Plow   <4> Fertilize  <7> Advance Day\n<2> Plant  <5> Shovel     <8> Stop Game\n<3> Water  <6> Harvest");
            System.out.print("Offer Input >> ");
            int input = scanner.nextInt();

            //Execute corresponding action
            switch (input) {
                case 1: leveledUp = tool.plow(this.player, this.tile); break;
                case 2: plant(); break;
                case 3: leveledUp = tool.water(this.player, this.tile); break;
                case 4: leveledUp = tool.fertilize(this.player, this.tile); break;
                case 5: leveledUp = tool.shovel(this.player, this.tile); break;
                case 6: leveledUp = tool.harvest(this.player, this.tile); break;
                case 7: isGameOver = advanceDay(); break;
                case 8: isGameOver = true;
            }
            //Display all notifications in the queue
            Notification.display();

            System.out.println("");
        }
    }

}