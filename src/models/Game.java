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
        plantMasterList[6] = new Plant("Mango Tree", "Fruit Tree", 10, 7, 7, 4, 4, 5, 15, 100, 8, 25f);
        plantMasterList[7] = new Plant("Apple Tree", "Fruit Tree", 10, 7, 7, 5, 5, 10, 15, 200, 5, 25f);
    
    }

    public boolean advanceDay()
    {
        this.dayCounter++;

        return false;
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

    public void plant() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What would you like to plant?");
        for (int i = 0; i < plantMasterList.length; i++) {
            System.out.println("<" + (i+1) + "> " + plantMasterList[i].getName());
        }
        System.out.print("Offer Input >> ");
        int choice = scanner.nextInt();

        tool.plantSeed(player, tile, plantMasterList[choice-1]);
    }

    /**
     *
     * @return whether the player stopped the game (e.g. have a choice to stop the current game)
     */
    public void run()
    {
        Scanner scanner = new Scanner(System.in);
        boolean isGameOver = false;
        boolean leveledUp = false;
        
        while (!isGameOver) {
            System.out.println("Day Counter: " + this.dayCounter);
            System.out.println("Exp: " + this.player.getExp() + " | Lvl: " + this.player.getLevel());
            System.out.println("Object Coins: " + this.player.getCoins());
            System.out.println("\nWhat would you like to do?");
            System.out.println("<1> Plow <2> Plant <3> Water\n<4> Fertilize <5> Shovel <6> Harvest\n<7> Advance Day <8> Stop Game");
            System.out.print("Offer Input >> ");
            int input = scanner.nextInt();

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
            Notification.display();
            // condition to end game : if no more money and growing crops(?)
            // isGameOver = true;
        }
        scanner.close();
    }

}