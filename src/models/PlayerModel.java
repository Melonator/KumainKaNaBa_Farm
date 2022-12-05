package models;

import gameClasses.FarmerType;
import gameClasses.Player;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class PlayerModel {
    private Player player;
    private Queue<FarmerType> registerQueue;
    private Dictionary<String, FarmerType> farmerTypes;

    public PlayerModel() {
        this.registerQueue = new LinkedList();
        this.player = new Player();
        this.farmerTypes = new Hashtable();
    }

    public void increaseMoney(float amount) {
        float currentMoney = player.getCoins();
        player.setCoins(currentMoney + amount);
    }

    public void decreaseMoney(int amount) {
        float currentMoney = player.getCoins();
        player.setCoins(currentMoney - amount);
    }

    public boolean addExp(float amount) {
        boolean leveledUp = false;

        //Avoids exp overflow for max level
        if (player.getExp() + amount > 1000)
            player.setExp(1000);
        else {
            float currentExp = player.getExp();
            player.setExp(currentExp + amount);
        }

        // Checks if player has leveled up or not
        if (player.getExp() >= 100 && player.getLevel() != 1)
            leveledUp = true;
        else if (player.getExp() >= 200 && player.getLevel() != 2)
            leveledUp = true;
        else if (player.getExp() >= 300 && player.getLevel() != 3)
            leveledUp = true;
        else if (player.getExp() >= 400 && player.getLevel() != 4)
            leveledUp = true;
        else if (player.getExp() >= 500 && player.getLevel() != 5)
            leveledUp = true;
        else if (player.getExp() >= 600 && player.getLevel() != 6)
            leveledUp = true;
        else if (player.getExp() >= 700 && player.getLevel() != 7)
            leveledUp = true;
        else if (player.getExp() >= 800 && player.getLevel() != 8)
            leveledUp = true;
        else if (player.getExp() >= 900 && player.getLevel() != 9)
            leveledUp = true;
        else if (player.getExp() == 1000 && player.getLevel() != 10)
            leveledUp = true;

        return leveledUp;
    }

    public void register(String type) {
        FarmerType ft = farmerTypes.get(type);
        decreaseMoney(ft.getSeedDiscount());
        player.setType(ft);
    }

    public int getRegisterCost() {
        int price = 0;
        for(FarmerType ft : this.registerQueue) {
            price += ft.getPrice();
        }

        return price;
    }

    public FarmerType getRegisterable() {
        return this.registerQueue.element();
    }

    public float getPlayerCoins() {
        return player.getCoins();
    }

    public float getPlayerExp() {
        return player.getExp();
    }

    public int getPlayerLevel() {
        return player.getLevel();
    }

    public FarmerType getPlayerFarmerType() {
        return player.getType();
    }
}
