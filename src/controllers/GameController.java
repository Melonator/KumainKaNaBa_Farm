package controllers;

import gameClasses.*;
import models.FarmModel;
import models.FarmView;
import models.PlayerModel;
import models.ToolValidity;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class GameController {
    private FarmModel farmModel;
    private FarmView farmView;
    private PlayerModel playerModel;
    private ToolValidity toolValidity;
    private Dictionary<String, Command> toolCommands;
    private Dictionary<String, Command> gameCommands;
    private boolean canRegister;
    private int day;

    public GameController(FarmModel farmModel, FarmView farmView, PlayerModel playerModel) {
        this.farmModel = farmModel;
        this.farmView = farmView;
        this.playerModel =  playerModel;
        this.canRegister = false;
        this.toolValidity = new ToolValidity();
        this.toolCommands = new Hashtable();
        this.gameCommands = new Hashtable();
        day = 1;
        initTileImages();
        initCommands();

        this.farmView.setTextFieldActionListener(e -> {
            JTextField textField = (JTextField)e.getSource();
            String text = textField.getText();

            compileCommand(text);

            textField.setText("");
        });

        farmView.setCoinsStatus(String.format("%.2f", playerModel.getPlayerCoins()));
        farmView.setExpStatus(String.format("%.2f", playerModel.getPlayerExp()));
        farmView.setLevelStatus(String.format("%d", playerModel.getPlayerLevel()));
        setFarmerTypeBonusLabels();
    }

    private void initTileImages() {
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 10; j++) {
                if(this.farmModel.getTileState(new Coordinate(i, j)) == State.ROCK)
                    this.farmView.setTileImage(i, j, "Rock");
                else
                    this.farmView.setTileImage(i,j, "Grass");
            }
        }
    }

    private void initCommands() {
        final Command plant = this::plant;
        final Command harvest = this::harvest;
        final Command plow = this::plow;
        final Command water = this::water;
        final Command fertilize = this::fertilize;
        final Command shovel = this::shovel;
        final Command pickaxe = this::pickaxe;

        final Command inquireTile = this::inquireTile;
        final Command inquirePlant = this::inquirePlant;
        final Command advanceDay = this::advanceDay;

        toolCommands.put("plant", plant);
        toolCommands.put("harvest", harvest);
        toolCommands.put("plow", plow);
        toolCommands.put("water", water);
        toolCommands.put("fertilize", fertilize);
        toolCommands.put("shovel", shovel);
        toolCommands.put("pickaxe", pickaxe);

        gameCommands.put("inquire plant", inquirePlant);
        gameCommands.put("inquire tile", inquireTile);
        gameCommands.put("sleep", advanceDay);
    }

    public String parseCommand(String[] commands) {
        if(toolCommands.get(commands[0]) != null) {
            return "tool";
        }

        else if(gameCommands.get(commands[0]) != null || commands[0].equals("inquire")) {
            if(commands[0].equals("inquire")) {
                if(isPlant(commands[1])) {
                    commands[0] = "inquire plant";
                }
                else {
                    commands[0] = "inquire tile";
                }
            }

            return "game";
        }
        else
            return "no type";
    }
    public void compileCommand(String command) {
        command.toLowerCase();
        String[] commands = command.split(" ");
        String commandType = "";
        if(!checkCommandValidity(commands))
            return;
        commandType = parseCommand(commands);

        if(commandType == "tool")
            toolCommands.get(commands[0]).execCommand(commands);
        else if(commandType == "game")
            gameCommands.get(commands[0]).execCommand(commands);
        else if(commands[0].equals("clear"))
            farmView.clearLogsBox();
        else if(commands[0].equals("register"))
            register();
        else if(commands[0].equals("help"))
            displayHelp();
    }

    private boolean checkCommandValidity(String[] commands) {
       if (toolCommands.get(commands[0]) != null) {
           if(commands[0].equals("plant")) {
               if (!isPlant(commands[1]))
                   return false;

               if(!isCoordinate(commands[2]))
                   return false;

               return true;
           }

           if(!isCoordinate(commands[1]))
               return false;

           return true;
       }

       if(gameCommands.get(commands[0]) != null || commands[0].equals("inquire")) {
          if(commands[0].equals("inquire")) {
              if(isPlant(commands[1]))
                  return true;
              else if(isCoordinate(commands[1]))
                  return true;

              return false;
          }

          return true;
       }

       if(commands[0].equals("clear") || commands[0].equals("register") || commands[0].equals("help"))
           return true;

       return false;
    }

    private boolean isPlant(String input) {
        if(farmModel.getPlantFromList(input) == null)
            return false;

        return true;
    }

    private boolean isCoordinate(String coordinate) {
        if(coordinate.length() != 2)
            return false;

        if(coordinate.charAt(0) < '1' || coordinate.charAt(0) > '5')
            return false;

        if(coordinate.charAt(1) < 'a' || coordinate.charAt(1) > 'j')
            return false;

        return true;
    }

    private void plant(String[] commands) {
        Coordinate coordinate = new Coordinate(commands[2]);
        ArrayList<State> adjacentTilesStates = farmModel.getAdjacentTilesStates(coordinate);
        Plant plant = farmModel.getPlantFromList(commands[1]);
        int errorCode = toolValidity.validatePlant(adjacentTilesStates.toArray(new State[adjacentTilesStates.size()]),
                farmModel.getTileState(coordinate), plant, playerModel.getPlayerCoins());

        if(errorCode != 1) {
            switch(errorCode){
                case 0:
                    farmView.appendLogsBoxText("Medyo gipit tayo ngayon...\n");
                    break;
                case 2:
                    farmView.appendLogsBoxText("Plow it first...\n");
                    break;
                case 3:
                    farmView.appendLogsBoxText("No space...\n");
                    break;
            }
            return;
        }
        farmModel.setPlant(plant, coordinate);
        farmModel.setState(State.PLANT, coordinate);
        playerModel.decreaseMoney(plant.getStorePrice() - playerModel.getPlayerFarmerType().getSeedDiscount());

        farmView.setTileImage(coordinate.x, coordinate.y, commands[1]);
        farmView.appendLogsBoxText("You've planted a " + plant.getName() + " for " + plant.getStorePrice() + " coins...\n");

        farmView.setCoinsStatus(String.format("%.2f", playerModel.getPlayerCoins()));
    }

    private void harvest(String[] commands) {
        Coordinate coordinate = new Coordinate(commands[1]);
        Plant plant = farmModel.getTilePlant(coordinate);
        int errorCode = toolValidity.validateHarvest(farmModel.getTileState(coordinate), farmModel.getTileHarvestDays(coordinate));

        if(errorCode != 1) {
            switch(errorCode){
                case 2:
                    farmView.appendLogsBoxText("What are you trying to harvest???\n");
                    break;
                case 4:
                    farmView.appendLogsBoxText("Not ready for harvest yet...\n");
                    break;
            }
            return;
        }


        FarmerType farmerType = playerModel.getPlayerFarmerType();
        int produce = new Random().nextInt(plant.getMaxProduce() - plant.getMinProduce() + 1) + plant.getMinProduce();
        int harvestTotal = produce * (plant.getRetail() + farmerType.getBonusProduce());
        float waterBonus = harvestTotal * 0.2f * (farmModel.getTileWaterCount(coordinate) - 1);
        float fertBonus = harvestTotal * 0.5f * farmModel.getTileFertCount(coordinate);
        float finalPrice = harvestTotal + waterBonus + fertBonus;
        if(plant.getType().equals("Flower"))
            finalPrice = finalPrice * 1.1f;

        boolean leveledUp = playerModel.addExp(plant.getExpYield());
        promptRegister(leveledUp);

        if(leveledUp)
            farmView.setLevelStatus(String.format("%d", playerModel.getPlayerLevel()));

        playerModel.increaseMoney(finalPrice);

        farmView.setTileImage(coordinate.x, coordinate.y, "Grass");
        farmView.appendLogsBoxText("You've harvested " + plant.getName() + "...\n");
        farmView.appendLogsBoxText("Total Earnings: " + finalPrice + " coins...\n");

        farmView.setCoinsStatus(String.format("%.2f", playerModel.getPlayerCoins()));
        farmView.setExpStatus(String.format("%.2f", playerModel.getPlayerExp()));

        farmModel.removePlant(coordinate);
        farmModel.setState(State.DEFAULT, coordinate);
    }

    private void plow(String[] commands) {
        Coordinate coordinate = new Coordinate(commands[1]);
        int errorCode = toolValidity.validatePlow(farmModel.getTileState(coordinate));

        if(errorCode != 1) {
            switch(errorCode) {
                case 2:
                    farmView.appendLogsBoxText("You can't plow on that...\n");
                    break;
            }

            return;
        }

        farmModel.setState(State.PLOWED, coordinate);
        boolean leveledUp = playerModel.addExp(0.5f);
        promptRegister(leveledUp);

        if(leveledUp)
            farmView.setLevelStatus(String.format("%d", playerModel.getPlayerLevel()));

        farmView.setTileImage(coordinate.x, coordinate.y, "Plowed");
        farmView.appendLogsBoxText("You plowed the tile...\n");
        farmView.appendLogsBoxText("+ 0.5 exp\n");

        farmView.setExpStatus(String.format("%.2f", playerModel.getPlayerExp()));
    }

    private void water(String[] commands) {
        Coordinate coordinate = new Coordinate(commands[1]);
        FarmerType farmerType = playerModel.getPlayerFarmerType();
        int errorCode = toolValidity.validateWater(farmModel.getTileState(coordinate), farmModel.getTileWaterCount(coordinate),
                farmModel.getTilePlant(coordinate).getWaterMax(), farmerType.getWaterBonus());

        if(errorCode != 1) {
            switch(errorCode) {
                case 2:
                    farmView.appendLogsBoxText("Nothing to water here...\n");
                    break;
                case 4:
                    farmView.appendLogsBoxText("Water max reached...\n");
                    break;
            }
            return;
        }

        boolean leveledUp = playerModel.addExp(0.5f);
        promptRegister(leveledUp);

        if(leveledUp)
            farmView.setLevelStatus(String.format("%d", playerModel.getPlayerLevel()));

        farmModel.addWaterCount(coordinate);
        farmView.appendLogsBoxText("You've watered your " + farmModel.getTilePlant(coordinate).getName() + "...\n");
        farmView.appendLogsBoxText("+ 0.5 exp\n");

        farmView.setExpStatus(String.format("%.2f", playerModel.getPlayerExp()));
    }

    private void fertilize(String[] commands) {
        Coordinate coordinate = new Coordinate(commands[1]);
        FarmerType farmerType = playerModel.getPlayerFarmerType();
        int errorCode = toolValidity.validateFert(farmModel.getTileState(coordinate), playerModel.getPlayerCoins(), farmModel.getTileFertCount(coordinate),
                farmModel.getTilePlant(coordinate).getFertMax(), farmerType.getFertBonus());

        if(errorCode != 1) {
            switch(errorCode) {
                case 0:
                    farmView.appendLogsBoxText("Medyo gipit tayo ngayon...\n");
                case 2:
                    farmView.appendLogsBoxText("Nothing to fertilize here...\n");
                    break;
                case 4:
                    farmView.appendLogsBoxText("Fertilizer max reached...\n");
                    break;
            }
            return;
        }

        playerModel.decreaseMoney(10);
        boolean leveledUp = playerModel.addExp(4);
        promptRegister(leveledUp);

        if(leveledUp)
            farmView.setLevelStatus(String.format("%d", playerModel.getPlayerLevel()));

        farmModel.addFertCount(coordinate);
        farmView.appendLogsBoxText("You've fertilized your " + farmModel.getTilePlant(coordinate).getName() + " for 10 coins...\n");
        farmView.appendLogsBoxText("+ 4 exp\n");

        farmView.setCoinsStatus(String.format("%.2f", playerModel.getPlayerCoins()));
        farmView.setExpStatus(String.format("%.2f", playerModel.getPlayerExp()));
    }

    private void shovel(String[] commands) {
        Coordinate coordinate = new Coordinate(commands[1]);
        int errorCode = toolValidity.validateShovel(farmModel.getTileState(coordinate), playerModel.getPlayerCoins());

        if(errorCode != 1) {
            switch(errorCode){
                case 0:
                    farmView.appendLogsBoxText("Medyo gipit tayo ngayon...\n");
                    break;
                case 2:
                    farmView.appendLogsBoxText("You can't shovel a rock...\n");
                    break;
            }
            return;
        }

        playerModel.decreaseMoney(7);
        boolean leveledUp = playerModel.addExp(2);
        promptRegister(leveledUp);

        if(leveledUp)
            farmView.setLevelStatus(String.format("%d", playerModel.getPlayerLevel()));

        farmModel.removePlant(coordinate);
        farmModel.setState(State.DEFAULT, coordinate);

        farmView.setTileImage(coordinate.x, coordinate.y, "Grass");
        farmView.appendLogsBoxText("You used your shovel for 7 coins...\n");
        farmView.appendLogsBoxText("+ 2 exp\n");

        farmView.setCoinsStatus(String.format("%.2f", playerModel.getPlayerCoins()));
        farmView.setExpStatus(String.format("%.2f", playerModel.getPlayerExp()));
    }

    private void pickaxe(String[] commands) {
        Coordinate coordinate = new Coordinate(commands[1]);
        int errorCode = toolValidity.validatePick(farmModel.getTileState(coordinate), playerModel.getPlayerCoins());

        if(errorCode != 1) {
            switch(errorCode) {
                case 0:
                    farmView.appendLogsBoxText("Medyo gipit tayo ngayon...\n");
                    break;
                case 2:
                    farmView.appendLogsBoxText("What are you even trying to do...\n");
                    break;
            }
            return;
        }

        playerModel.decreaseMoney(50);
        boolean leveledUp = playerModel.addExp(15);
        promptRegister(leveledUp);

        if(leveledUp)
            farmView.setLevelStatus(String.format("%d", playerModel.getPlayerLevel()));

        farmModel.setState(State.DEFAULT, coordinate);

        farmView.setTileImage(coordinate.x, coordinate.y, "Grass");
        farmView.appendLogsBoxText("You used your pickaxe for 50 coins...\n");
        farmView.appendLogsBoxText("+ 15 exp\n");

        farmView.setCoinsStatus(String.format("%.2f", playerModel.getPlayerCoins()));
        farmView.setExpStatus(String.format("%.2f", playerModel.getPlayerExp()));
    }

    private void advanceDay(String[] commands) {
        ArrayList<Coordinate> activeCrops = farmModel.getActiveGrowingCrops();
        this.day++;

        farmView.appendLogsBoxText("Sleeping...\n");
        farmView.setDayStatus(Integer.toString(this.day));
        for(Coordinate coord : activeCrops) {
            Plant p = farmModel.getTilePlant(coord);
            int waterCount = farmModel.getTileWaterCount(coord);
            int fertCount = farmModel.getTileFertCount(coord);

            farmModel.decHarvestDays(coord);
            if(farmModel.getTileHarvestDays(coord) == -1 ) {
                farmModel.removePlant(coord);
                farmModel.setState(State.WITHERED, coord);
                farmModel.setPlant(farmModel.getPlantFromList("withered"), coord);
                farmView.setTileImage(coord.x, coord.y, "withered");
                farmView.appendLogsBoxText("Your " + p.getName() + " withered...\n");
            }

            else if(farmModel.getTileHarvestDays(coord) == 0) {
                if(waterCount < p.getWaterMin() || fertCount < p.getFertMin()) {
                    farmModel.removePlant(coord);
                    farmModel.setState(State.WITHERED, coord);
                    farmModel.setPlant(farmModel.getPlantFromList("withered"), coord);
                    farmView.setTileImage(coord.x, coord.y, "withered");
                    farmView.appendLogsBoxText("Your " + p.getName() + " withered...\n");
                }
                else {
                    char c = (char)(coord.y + 97);
                    farmView.appendLogsBoxText(farmModel.getTilePlant(coord).getName() + " on " + (coord.x + 1) + c + " ready to harvest!\n");
                }
            }
        }

        activeCrops = farmModel.getActiveGrowingCrops();
        if(activeCrops.size() == 0) {
            if(playerModel.getPlayerCoins() < 5) {
                farmView.disableChatBox();
                farmView.appendLogsBoxText("Bakit ka kasi nagpakagutom?!\n");
                farmView.appendLogsBoxText("*Sinapak ang pader*\n");
                farmView.appendLogsBoxText("(Game over na btw)\n");
            }
        }
    }

    private void inquireTile(String[] commands) {
        Coordinate coordinate = new Coordinate(commands[1]);
        int waterCount = farmModel.getTileWaterCount(coordinate);
        int fertCount = farmModel.getTileFertCount(coordinate);
        int harvestDays = farmModel.getTileHarvestDays(coordinate);
        Plant p = farmModel.getTilePlant(coordinate);

        farmView.appendLogsBoxText("Inquiring tile...\n");
        farmView.appendLogsBoxText("------------------\n");

        if(farmModel.getTileState(coordinate) == State.PLANT) {
            farmView.appendLogsBoxText("Plant: " + p.getName() + "\n");
            farmView.appendLogsBoxText("Water-Count: " + waterCount + "/" + p.getWaterMin() + "(" + p.getWaterMax() + ")\n");
            farmView.appendLogsBoxText("Fert-Count: " + fertCount + "/" + p.getFertMin() + "(" + p.getFertMax() + ")\n");
            farmView.appendLogsBoxText("Harvest-Days: " + harvestDays + "\n");
        }
        else if (farmModel.getTileState(coordinate) == State.ROCK)
            farmView.appendLogsBoxText("There's a rock...\n");
        else if (farmModel.getTileState(coordinate) == State.WITHERED)
            farmView.appendLogsBoxText("It's withered...\n");
        else if (farmModel.getTileState(coordinate) == State.PLOWED)
            farmView.appendLogsBoxText("It's plowed...\n");
        else
            farmView.appendLogsBoxText("The grass looks like grass...\n");

        farmView.appendLogsBoxText("------------------\n");
    }

    private void inquirePlant(String[] commands) {
        Plant p = farmModel.getPlantFromList(commands[1]);
        farmView.appendLogsBoxText("+++" + p.getName() + "+++\n");
        farmView.appendLogsBoxText("Type: " + p.getType() + "\n");
        farmView.appendLogsBoxText("Price: " + p.getStorePrice() + "\n");
        farmView.appendLogsBoxText("Water-Stats: " + p.getWaterMin() + "(" +p.getWaterMax() + ")\n");
        farmView.appendLogsBoxText("Fert-Stats: " + p.getFertMin() + "(" +p.getFertMax() + ")\n");
        farmView.appendLogsBoxText("Produce: " + p.getMinProduce() + "-" + p.getMaxProduce() + "\n");
        farmView.appendLogsBoxText("Exp-Yield: " + p.getExpYield() + "\n");
        farmView.appendLogsBoxText("Sell-Price: " + p.getRetail() + "\n");
        farmView.appendLogsBoxText("++++++++++++++++++++++++++\n");
    }

    private void setFarmerTypeBonusLabels() {
        FarmerType type = playerModel.getPlayerFarmerType();
        farmView.setWaterStatus(Integer.toString(type.getWaterBonus()));
        farmView.setFertilizerStatus(Integer.toString(type.getFertBonus()));
        farmView.setDiscountStatus(Integer.toString(type.getSeedDiscount()));
        farmView.setBonusProduceStatus(Integer.toString(type.getBonusProduce()));
        farmView.setTypeStatus(playerModel.getPlayerFarmerType().getName());
    }

    private boolean canRegisterNext() {
        switch(playerModel.getPlayerLevel()) {
            case 5:
            case 10:
            case 15:
                playerModel.pushRegisterQueue();
                return true;
        }
        return false;
    }

    private void promptRegister(boolean leveledUp) {
        if(leveledUp) {
            if(canRegisterNext()) {
                farmView.setNextRegisterStatus(playerModel.getRegisterable().getName() + " | " + playerModel.getRegisterCost() + " coins");
                farmView.appendLogsBoxText("You can now register for the next farmer type!\n");
                this.canRegister = true;
            }
        }
    }

    private void register() {
        if(!canRegister) {
            farmView.appendLogsBoxText("There is nothing to register...\n");
            return;
        }

        if(playerModel.getPlayerCoins() < playerModel.getRegisterCost()) {
            farmView.appendLogsBoxText("You can't afford to register...\n");
            return;
        }

        playerModel.decreaseMoney(playerModel.getRegisterCost());
        playerModel.registerNextFarmerType();

        farmView.appendLogsBoxText("You are now a " + playerModel.getPlayerFarmerType().getName() + "!\n");
        farmView.setNextRegisterStatus("None");
        farmView.setCoinsStatus(String.format("%.2f", playerModel.getPlayerCoins()));
        setFarmerTypeBonusLabels();
        this.canRegister = false;
    }

    private void displayHelp() {
        farmView.appendLogsBoxText("Tool: Plow, Shovel, Pickaxe, Water, Fertilize\n");
        farmView.appendLogsBoxText("Command: Sleep, Register, Clear\n");
        farmView.appendLogsBoxText("Others: Inquire\n");
        farmView.appendLogsBoxText("\n++++++++++SEEDS+++++++++++\n");
        farmView.appendLogsBoxText("Turnip, Carrot, Potato, Rose\n");
        farmView.appendLogsBoxText("Tulips, Sunflower, Mango-Tree, Apple-Tree\n");
        farmView.appendLogsBoxText("++++++++++++++++++++++++++\n\n");
        farmView.appendLogsBoxText("++++++++++FORMAT++++++++++\n");
        farmView.appendLogsBoxText("Plant <Seed> <Tile>\n");
        farmView.appendLogsBoxText("Harvest <Tile>\n");
        farmView.appendLogsBoxText("<Tool> <Tile>\n");
        farmView.appendLogsBoxText("Inquire <Tile>\n");
        farmView.appendLogsBoxText("Inquire <Seed>\n");
        farmView.appendLogsBoxText("<Command>\n");
        farmView.appendLogsBoxText("++++++++++++++++++++++++++\n");
    }
}
