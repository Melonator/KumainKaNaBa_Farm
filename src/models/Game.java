package models;

import java.util.Queue;

public class Game {
    private Queue<FarmerType> registerQueue;
    private Tile tile;
    private Plant[] plantMasterList;
    private Player player;

    public Game()
    {
        //TODO: Initialize all properties
        //TODO: Initialize all 8 Plants for the masterlist
    }

    public void advanceDay()
    {

    }

    /**
     *
     * @returns whether the register as successful or not
     */
    public boolean register()
    {
        return true;
    }

    /**
     *
     * @return whether the player stopped the game (e.g. have a choice to stop the current game)
     */
    public boolean run()
    {
        return true;
    }

}