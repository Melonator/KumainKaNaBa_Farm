package models;

import gameClasses.FarmerType;
import gameClasses.Player;

import java.io.File;
import java.util.*;

public class PlayerModel {
    private Player player;
    private Deque<FarmerType> registerQueue;
    private Queue<FarmerType> farmerTypes;

    public PlayerModel() {
        this.registerQueue = new ArrayDeque();
        this.player = new Player();
        this.farmerTypes = new LinkedList();
        initFarmerTypes();
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

        float currentExp = player.getExp();
        player.setExp(currentExp + amount);

        // Checks if player has leveled up or not
        int oldLevel = player.getLevel();
        int newLevel = (int)(player.getExp() / 100) ;
        if(oldLevel != newLevel) {
            player.setLevel(newLevel);
            leveledUp = true;
        }

        return leveledUp;
    }

    public void registerNextFarmerType() {
        player.setFarmerType(this.registerQueue.pop());
        this.registerQueue.clear();
    }

    public int getRegisterCost() {
        int price = 0;
        Iterator<FarmerType> iterator = this.registerQueue.stream().iterator();

        while(iterator.hasNext()) {
            FarmerType ft = iterator.next();
            price += ft.getPrice();
        }

        return price;
    }

    public FarmerType getRegisterable() {
        return this.registerQueue.peek();
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

    public void pushRegisterQueue() {
       this.registerQueue.push(this.farmerTypes.poll());
    }

    public FarmerType getPlayerFarmerType() {
        return player.getType();
    }
    private void initFarmerTypes() {
        String filePath = "readTexts/farmerTypes.txt";
        if(System.getProperty("os.name").equals("Windows 11") || System.getProperty("os.name").equals("Windows 10"))
            filePath = "src/readTexts/farmerTypes.txt";

        File file = new File(filePath);
        Scanner input = null;
        ArrayList<String> list = new ArrayList();
        try {
            input = new Scanner(file);
        }
        catch(Exception e) {
            System.out.println("File not found!");
        }

        while (input.hasNextLine()) {
            list.add(input.nextLine());
        }

        for(String s : list) {
            String[] values = s.split(" ");
            String name = values[0];
            int bonusProduce = Integer.parseInt(values[1]);
            int seedDiscount = Integer.parseInt(values[2]);
            int waterBonus = Integer.parseInt(values[3]);
            int fertBonus = Integer.parseInt(values[4]);
            int regFee = Integer.parseInt(values[5]);

            FarmerType farmerType = new FarmerType(name, waterBonus, fertBonus,
                    seedDiscount, bonusProduce, regFee);

            this.farmerTypes.add(farmerType);
        }
    }
}
