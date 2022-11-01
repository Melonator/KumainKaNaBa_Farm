package models;

import java.util.Random;

/**
 * The Tool class is reponsible for simulating the players actions.
 */
public class Tool {

    public Tool()
    {

    }


    /**
     * Plants the seed to the tile and notifies the user if successful or not (along with the reason).
     * 
     * @param player The player who is planting the seed.
     * @param tile The tile that the player is trying to plant on.
     * @param plant The plant that the player is trying to plant.
     */
    public void plantSeed(Player player, Tile tile, Plant plant)
    {
        if(player.getCoins() >= plant.getStorePrice() - player.getType().getSeedDiscount()){
            if(!tile.setPlant(plant)) {
                String reason = "";
                if(tile.getState() == State.PLANT || tile.getState() == State.ROCK)
                    reason = "is occupied!";
                else if(tile.getState() == State.DEFAULT)
                    reason = "is not plowed!";
                else if(tile.getState() == State.WITHERED)
                    reason = "is withered!";

                Notification.push("[ You can't plant on this because the tile " + reason + " ]");
                return;
            }
        }

        if(!player.decCoins(plant.getStorePrice() - player.getType().getSeedDiscount())) {
            Notification.push("[ You can't afford this seed! ]");
            return;
        }

        Notification.push("[ You have successfully planted a " + plant.getName() + "! ]");
        tile.setState(State.PLANT);
    }


    /**
     * Harvests the plant from the tile and notifies the user if successful or not (along with the reason).
     * 
     * @param player The player who is harvesting the tile.
     * @param tile   The tile that the player is trying to harvest on.
     * @return true if the player leveled up, false otherwise.
     */
    public boolean harvest(Player player, Tile tile)
    {
        boolean canHarvest = true;
        if(tile.getState() != State.PLANT) {
            Notification.push("[ There is no plant to harvest on this tile! ]");
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
        tile.removePlant();
        player.incCoins(finalPrice);

        return player.addExp(p.getExpYield() * produce);
    }


    /**
     * Plows the tile and notifies the user if successful or not (along with the reason).
     * @param player The player who is plowing the tile.
     * @param tile   The tile that the player is trying to plow on.
     * @return true if the player leveled up, false otherwise.
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
     * Waters the tile and notifies the user if successful or not (along with the reason).
     * @param player The player who is watering the tile.
     * @param tile   The tile that the player is trying to water on.
     * @return true if the player leveled up, false otherwise.
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
     * Fertilizes the tile and notifies the user if successful or not (along with the reason).
     * @param player The player who is fertilizing the tile.
     * @param tile   The tile that the player is trying to fertilize on.
     * @return true if the player leveled up, false otherwise.
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
     * Shovels the tile and notifies the user if successful or not (along with the reason).
     * @param player The player who is shoveling the tile.
     * @param tile   The tile that the player is trying to shovel on.
     * @return true if the player leveled up, false otherwise.
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
        tile.removePlant();
        return player.addExp(2);
    }

    /**
     * Removes a rock from the tile and notifies the user if successful or not (along with the reason).
     * @param player The player who is removing the rock.
     * @param tile   The tile that the player is trying to remove the rock on.
     * @return true if the player leveled up, false otherwise.
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
