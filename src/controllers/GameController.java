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
    private int day;

    public GameController(FarmModel farmModel, FarmView farmView, PlayerModel playerModel) {
        this.farmModel = farmModel;
        this.farmView = farmView;
        this.playerModel =  playerModel;
        this.toolValidity = new ToolValidity();
        this.toolCommands = new Hashtable();
        this.gameCommands = new Hashtable();
        day = 0;
        initTileImages();
        initCommands();

        this.farmView.setTextFieldActionListener(e -> {
            JTextField textField = (JTextField)e.getSource();
            String text = textField.getText();

            compileCommand(text);
            textField.setText("Enter command");
            textField.setForeground(Color.GRAY);
        });
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

        else {
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
        else if(commands[0] == "clear")
            farmView.clearLogsBox();
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
        ArrayList<Tile> adjacentTiles = farmModel.getAdjacentTiles(coordinate);
        Plant plant = farmModel.getPlantFromList(commands[1]);
        int errorCode = toolValidity.validatePlant(adjacentTiles.toArray(new Tile[adjacentTiles.size()]),
                farmModel.getTileState(coordinate), plant, playerModel.getPlayerCoins());

        if(errorCode != 1) {
            return;
        }

        farmModel.setPlant(plant, coordinate);
        farmModel.setState(State.PLANT, coordinate);
        playerModel.decreaseMoney(plant.getStorePrice());

        farmView.setTileImage(coordinate.x, coordinate.y, plant.getName());
        farmView.appendLogsBoxText("You've planted a " + plant.getName() + "for " + plant.getStorePrice() + "...\n");
    }

    private void harvest(String[] commands) {
        Coordinate coordinate = new Coordinate(commands[1]);
        Plant plant = farmModel.getTilePlant(coordinate);
        int errorCode = toolValidity.validateHarvest(farmModel.getTileState(coordinate), farmModel.getTileHarvestDays(coordinate));

        if(errorCode != 1)
            return;

        farmModel.removePlant(coordinate);
        farmModel.setState(State.DEFAULT, coordinate);

        FarmerType farmerType = playerModel.getPlayerFarmerType();
        int produce = new Random().nextInt(plant.getMaxProduce() - plant.getMinProduce() + 1) + plant.getMinProduce();
        int harvestTotal = produce * (plant.getRetail() + farmerType.getBonusProduce());
        float waterBonus = harvestTotal * 0.2f * (farmModel.getTileWaterCount(coordinate) - 1);
        float fertBonus = harvestTotal * 0.5f * farmModel.getTileFertCount(coordinate);
        float finalPrice = harvestTotal + waterBonus + fertBonus;
        if(plant.getType() == "Flower")
            finalPrice = finalPrice * 1.1f;

        boolean leveledUp = playerModel.addExp(plant.getExpYield());
        playerModel.increaseMoney(finalPrice);

        farmView.appendLogsBoxText("You've harvested " + harvestTotal + " " + plant.getName() + "...\n");
        farmView.appendLogsBoxText("Total Earnings: " + finalPrice + " coins...\n");
    }

    private void plow(String[] commands) {
        Coordinate coordinate = new Coordinate(commands[1]);
        int errorCode = toolValidity.validatePlow(farmModel.getTileState(coordinate));

        if(errorCode != 1)
            return;

        farmModel.setState(State.PLOWED, coordinate);
        boolean leveledUp = playerModel.addExp(0.5f);

        farmView.setTileImage(coordinate.x, coordinate.y, "Plowed");
        farmView.appendLogsBoxText("You plowed the tile...\n");
    }

    private void water(String[] commands) {
        Coordinate coordinate = new Coordinate(commands[1]);
        FarmerType farmerType = playerModel.getPlayerFarmerType();
        int errorCode = toolValidity.validateWater(farmModel.getTileState(coordinate));

        if(errorCode != 1)
            return;

        farmModel.addWaterCount(farmerType.getWaterBonus(), coordinate);
        farmView.appendLogsBoxText("You've watered your " + farmModel.getTilePlant(coordinate).getName() + "...\n");
    }

    private void fertilize(String[] commands) {
        Coordinate coordinate = new Coordinate(commands[1]);
        FarmerType farmerType = playerModel.getPlayerFarmerType();
        int errorCode = toolValidity.validateFert(farmModel.getTileState(coordinate), playerModel.getPlayerCoins());

        if(errorCode != 1)
            return;

        playerModel.decreaseMoney(10);
        boolean leveledUp = playerModel.addExp(4);
        farmModel.addFertCount(farmerType.getWaterBonus(), coordinate);
        farmView.appendLogsBoxText("You've fertilized your " + farmModel.getTilePlant(coordinate).getName() + " for 10 coins...\n");
        farmView.appendLogsBoxText("+ 4 exp\n");
    }

    private void shovel(String[] commands) {
        Coordinate coordinate = new Coordinate(commands[1]);
        int errorCode = toolValidity.validateShovel(farmModel.getTileState(coordinate), playerModel.getPlayerCoins());

        if(errorCode != 1)
            return;

        playerModel.decreaseMoney(7);
        boolean leveledUp = playerModel.addExp(2);
        farmModel.removePlant(coordinate);
        farmModel.setState(State.DEFAULT, coordinate);

        farmView.setTileImage(coordinate.x, coordinate.y, "Grass");
        farmView.appendLogsBoxText("You used your shovel for 7 coins...\n");
        farmView.appendLogsBoxText("+ 2 exp\n");
    }

    private void pickaxe(String[] commands) {
        Coordinate coordinate = new Coordinate(commands[1]);
        int errorCode = toolValidity.validatePick(farmModel.getTileState(coordinate), playerModel.getPlayerCoins());

        if(errorCode != 1)
            return;

        playerModel.decreaseMoney(10);
        boolean leveledUp = playerModel.addExp(15);
        farmModel.setState(State.DEFAULT, coordinate);

        farmView.setTileImage(coordinate.x, coordinate.y, "Grass");
        farmView.appendLogsBoxText("You used your pickaxe for 10 coins...\n");
        farmView.appendLogsBoxText("+ 15 exp\n");
    }

    private void advanceDay(String[] commands) {
        ArrayList<Tile> activeCrops = farmModel.getActiveGrowingCrops();
        this.day++;

        for(Tile t : activeCrops) {
            farmModel.decHarvestDays(t);
            if(t.getHarvestDays() == -1) {
                t.setState(State.WITHERED);
                t.setFertCount(0);
                t.setWaterCount(0);
                t.setPlant(new Plant("Withered", "None", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
            }
        }

        activeCrops = farmModel.getActiveGrowingCrops();
        if(activeCrops.size() == 0) {
            if(playerModel.getPlayerCoins() < 5) {
                // Game over
            }
        }
    }

    private void inquireTile(String[] commands) {
        Coordinate coordinate = new Coordinate(commands[1]);
    }

    private void inquirePlant(String[] commands) {

    }
}
