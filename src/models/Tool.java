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
    public boolean plantSeed(Player player, Tile tile, Plant plant)
    {
        if(!tile.setPlant(plant)) {
            String reason = "";
            if(tile.getState() == State.PLANT || tile.getState() == State.ROCK)
                reason = "is occupied!";
            else if(tile.getState() == State.DEFAULT)
                reason = "is not plowed!";
            else if(tile.getState() == State.WITHERED)
                reason = "is withered!";

            Notification.push("[ You can't plant on this because the tile " + reason + " ]");
            return false;
        }
        else if(!player.decCoins(plant.getStorePrice() - player.getType().getSeedDiscount())) {
            Notification.push("[ You can't afford this seed! ]");
            return false;
        }

        //Check if the plant is a tree, then check adjacent tiles if occupied
        //Return false if at least one is occupied
        //Return false if the tree is planted on the edge of the farm

        Notification.push("[ You have successfully planted a " + plant.getName() + "! ]");
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
        boolean canHarvest = true;
        if(tile.getState() != State.PLANT) {
            Notification.push("[ There is no plant on this tile! ]");
            return false;
        }

        if(tile.getHarvestDays() != 0){
            Notification.push("[ It is not harvest day for this plant! ]");
            canHarvest = false;
        }

        if(tile.getWaterCount() < tile.getPlant().getWaterMin()){
            Notification.push("[ Warning: The water requirement hasn't been met! ]");
            canHarvest = false;
        }
        if(tile.getFertCount() < tile.getPlant().getFertMin()) {
            Notification.push("[ Warning: The fertilizer requirement hasn't been met! ]");
            canHarvest = false;
        }


        if(!canHarvest)
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

        Notification.push("[ You have successfully harvested  " + produce + " " + tile.getPlant().getName() + "! ]");
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
        if(tile.getState() == State.PLOWED) {
            Notification.push("[ The tile is already plowed! ]");
            return false;
        }

        else if(tile.getState() != State.DEFAULT) {
            Notification.push("[ The tile is occupied! ]");
            return false;
        }

        Notification.push("[ You have successfully plowed the tile! ]");
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
        if(tile.getState() != State.PLANT){
            Notification.push("[ There is no plant to water! ]");
            return false;
        }
        if(!tile.addWaterCount(player.getType().getWaterBonus())){
            Notification.push("[ The needs of this plant in terms of water is met! You continue to water it though... ]");
            return false;
        }

        Notification.push("[ You have successfully watered the " + tile.getPlant().getName() + "! ]");
        return player.addExp(0.5f);
    }

    /**
     * Adds fertilizerCount to a tile
     *
     * @returns whether the player leveled up or not
     */
    public boolean fertilize(Player player, Tile tile)
    {
        if(tile.getState() != State.PLANT){
            Notification.push("[ There is no plant to fertilize! ]");
            return false;
        }

        if(!tile.addFertCount(player.getType().getFertBonus())){
            Notification.push("[ The needs of this plant in terms of fertilizing is met! You continue to fertilize it though... ]");
            Notification.push("[ No Object Coins has been decreased! ]");
            return false;
        }

        if(!player.decCoins(10)){
            Notification.push("[ You cannot afford to fertilize this plant! ]");
            return false;
        }

        Notification.push("[ You have successfully fertilized the " + tile.getPlant().getName() + "! ]");
        return player.addExp(4.0f);
    }

    /**
     * Removes the plant in a certain tile (Withered or existing)
     *
     * @returns whether the player leveled up or not
     */
    public boolean shovel(Player player, Tile tile)
    {
        if(!player.decCoins(7)){
            Notification.push("[ You cannot afford to use the shovel! ]");
            return false;
        }

        if(tile.getState() != State.ROCK)
            tile.setState(State.DEFAULT);
        else
            Notification.push("[ You can't use a shovel on a rock! ]");

        Notification.push("[ You have successfully used the shovel! ]");
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
        if(tile.getState() != State.ROCK){
            Notification.push("[ There is no rock to use the pickaxe on! ]");
            return false;
        }
        if(!player.decCoins(10)){
            Notification.push("[ You cannot afford to use the pickaxe! ]");
            return false;
        }

        Notification.push("[ You have successfully removed the rock! ]");
        tile.setState(State.DEFAULT);
        return player.addExp(15);
    }
}
