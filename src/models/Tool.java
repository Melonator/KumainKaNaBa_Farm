package models;

import java.util.Random;
public class Tool {

    public Tool()
    {

    }

    /**
     * Aggregates a plant object to a specific tile
     *
     * @returns whether the planting was successful or not. Player's money is a factor in this case
     */
    public boolean plantSeed(Player player, Tile tile, Tile[][] tiles, Plant plant)
    {
        if(!tile.setPlant(plant))
            return false;
        if(!player.decCoins(plant.getStorePrice() - player.getType().getSeedDiscount()))
            return false;

        //Check if the plant is a tree, then check adjacent tiles if occupied
        //Return false if at least one is occupied
        //Return false if the tree is planted on the edge of the farm

        tile.setState(State.PLANT);
        return true;
    }

    /**
     * Harvests the plant in a specific tile
     *
     * @returns whether the player leveled up or not
     */
    public boolean harvest(Player player, Tile tile)
    {
        if(tile.getState() != State.PLANT && (tile.getWaterCount() < tile.getPlant().getWaterMin()
                || tile.getFertCount() < tile.getPlant().getFertMin()) && tile.getHarvestDays() != 0)
            return false;

        tile.setState(State.DEFAULT);
        Plant p = tile.getPlant();
        FarmerType f = player.getType();
        int produce = new Random().nextInt(p.getMaxProduce() - p.getMinProduce() + 1) + p.getMinProduce();
        int harvestTotal = produce * (p.getRetail() + f.getBonusProduce());
        float waterBonus = harvestTotal * 0.2f * (tile.getWaterCount() - 1);
        float fertBonus = harvestTotal * 0.5f * tile.getFertCount();
        float finalPrice = harvestTotal + waterBonus + fertBonus;
        if(p.getType() == "Flower")
            finalPrice = finalPrice * 1.1f;

        player.incCoins(finalPrice);

        return player.addExp(p.getExpYield() * produce);
    }

    /**
     * Plow's a specific tile, making it available to plant
     *
     * @returns whether the player leveled up or not
     */
    public boolean plow(Player player, Tile tile)
    {
        if(tile.getState() != State.DEFAULT)
            return false;

        tile.setState(State.PLOWED);
        return player.addExp(0.5f);
    }

    /**
     * Adds waterCount to a tile
     *
     * @returns whether the player leveld up or not
     */
    public boolean water(Player player, Tile tile)
    {
        if(tile.getState() != State.PLANT)
            return false;
        if(!tile.addWaterCount(player.getType().getWaterBonus()))
            return false;

        return player.addExp(0.5f);
    }

    /**
     * Adds fertilizerCount to a tile
     *
     * @returns whether the player leveled up or not
     */
    public boolean fertilize(Player player, Tile tile)
    {
        if(tile.getState() != State.PLANT)
            return false;
        if(!player.decCoins(10))
            return false;
        if(!tile.addFertCount(player.getType().getFertBonus()))
            return false;

        return player.addExp(4.0f);
    }

    /**
     * Removes the plant in a certain tile (Withered or existing)
     *
     * @returns whether the player leveled up or not
     */
    public boolean shovel(Player player, Tile tile)
    {
        if(!player.decCoins(7))
            return false;

        if(tile.getState() != State.ROCK)
            tile.setState(State.DEFAULT);

        tile.resetFertCount();
        tile.resetWaterCount();
        return player.addExp(2);
    }

    /**
     * Removes the rock in a certain tile
     *
     * @returns whether the player leveled up or not
     */
    public boolean pickaxe(Player player, Tile tile)
    {
        if(tile.getState() != State.ROCK)
            return false;
        if(!player.decCoins(10))
            return false;

        tile.setState(State.DEFAULT);
        return player.addExp(15);
    }
}
