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

    public void increaseMoney(int amount) {
        float currentMoney = player.getCoins();
        player.setCoins(currentMoney + amount);
    }

    public void decreaseMoney(int amount) {
        float currentMoney = player.getCoins();
        player.setCoins(currentMoney - amount);
    }

    public boolean addExp(float amount) {
        return true;
    }

    public void register(String type) {

    }

    public int getRegisterCost() {
        return 0;
    }

    public FarmerType getRegisterable() {
        return registerQueue.peek();
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

    public FarmerType getPlayerType() {
        return player.getType();
    }
}
