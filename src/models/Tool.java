package models;

public class Tool {

    public Tool()
    {

    }

    /**
     * Aggregates a plant object to a specific tile
     *
     * @returns whether the planting was successful or not. Player's money is a factor in this case
     */
    public boolean plantSeed(Player player, Tile tile, Tile[] tiles, Plant plant)
    {
        return true;
    }

    /**
     * Harvests the plant in a specific tile
     *
     * @returns the amount of exp gained. 0 signifies an invalid harvest (e.g. empty tile, withered, rock, etc...)
     */
    public float harvest(Player player, Tile tile)
    {
        return 1.0f;
    }

    /**
     * Plow's a specific tile, making it available to plant
     *
         * @returns the amount of exp gained. 0 signifies
     */
    public float plow(Player player, Tile tile)
    {
        return 1.0f;
    }

    /**
     * Adds waterCount to a tile
     *
     * @returns the amount of exp gained. 0 signifies lack of money
     */
    public float water(Player player, Tile tile)
    {
        return 1.0f;
    }

    /**
     * Adds fertilizerCount to a tile
     *
     * @returns the amount of exp gained. 0 signifies lack of money
     */
    public float fertilize(Player player, Tile tile)
    {
        return 1.0f;
    }

    /**
     * Removes the plant in a certain tile (Withered or existing)
     *
     * @returns the amount of exp gained. 0 signifies lack of money or unsuccesful(e.g. empty tile or rock)
     */
    public float shovel(Player player, Tile tile)
    {
        return 1.0f;
    }

    /**
     * Removes the rock in a certain tile
     *
     * @returns the amount of exp gained. 0 signifies lack of money or unsuccesful(e.g. no rock)
     */
    public float pickaxe(Player player, Tile tile)
    {
        return 1.0f;
    }
}
