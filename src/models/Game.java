package models;

import java.util.Queue;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner6;

public class Game {
    private Queue<FarmerType> registerQueue;
    private Tile tile;
    private Plant[] plantMasterList;
    private Tool tool;
    private Player player;
    private int dayCounter;

    public Game()
    {
        //TODO: Initialize all properties
        
        //registerQueue = 
        this.player = new Player();
        this.dayCounter = 1;
        this.tool = new Tool();
        this.tile = new Tile();
        //TODO: Initialize all 8 Plants for the masterlist
        plantMasterList = new Plant[8];
        
        plantMasterList[0] = new Plant("Turnip", "Root", 2, 1, 2, 0, 1, 1, 2, 5, 6, 5f);
        plantMasterList[1] = new Plant("Carrot", "Root", 3, 1, 2, 0, 1, 1, 2, 10, 9, 7.5f);
        plantMasterList[2] = new Plant("Potato", "Root", 5, 3, 4, 1, 2, 1, 10, 20, 3, 12.5f);
        plantMasterList[3] = new Plant("Rose", "Flower", 1, 1, 2, 0, 1, 1, 0, 5, 5, 2.5f);
        plantMasterList[4] = new Plant("Tulips", "Flower", 2, 2, 3, 0, 1, 1, 0, 10, 9, 5f);
        plantMasterList[5] = new Plant("Sunflower", "Flower", 3, 2, 3, 1, 2, 1, 0, 20, 19, 7.5f);
        plantMasterList[6] = new Plant("Mango", "Fruit Tree", 10, 7, 7, 4, 4, 5, 15, 100, 8, 25f);
        plantMasterList[7] = new Plant("Apple", "Fruit Tree", 10, 7, 7, 5, 5, 10, 15, 200, 5, 25f);
    
    }

    public void advanceDay()
    {
        dayCounter++;

    }

    /**
     *
     * @returns whether the register as successful or not
     */
    public boolean register()
    {
        boolean isRegistered = false;

        return isRegistered;
    }

    /**
     *
     * @return whether the player stopped the game (e.g. have a choice to stop the current game)
     */
    public boolean run()
    {
        Scanner scanner = new Scanner(System.in);
        boolean isGameOver = false;
        
        while (!isGameOver) {
            System.out.println("Day Counter: " + dayCounter);
            System.out.println("\nWhat would you like to do?");
            System.out.println("<1> Plow\n<2> Plant\n<3> Water\n<4> Fertilize\n<5> Shovel\n<6> Harvest\n<7> Advance Day");
            System.out.print("Offer Input >> ");
            int input = scanner.nextInt();

            switch (input) {
                case 1:tool.plow(player, tile); break;
                case 2:
                    System.out.println("What would you like to plant?");
                    for (int i = 0; i < plantMasterList.length; i++) {
                        System.out.println("<" + (i+1) + "> " + plantMasterList[i].getName());
                    }
                    System.out.print("Offer Input >> ");
                    int choice = scanner.nextInt();

                    tool.plantSeed(player, tile, plantMasterList[choice-1]);
                    break;
                case 3: tool.water(player, tile); break;
                case 4: tool.fertilize(player, tile); break;
                case 5: tool.shovel(player, tile); break;
                case 6: tool.harvest(player, tile); break;
                case 7: advanceDay(); break;
            }
            // condition to end game : if no more money and growing crops(?)
            // isGameOver = true;
        }
        scanner.close();
        return isGameOver;
    }

}