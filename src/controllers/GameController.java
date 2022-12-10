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

/**
 * This class is the controller for the game. It contains the logic for the game, housing all the models and views.
 */
public class GameController {
    private FarmModel farmModel; // the model for the farm
    private FarmView farmView; // the view for the farm
    private PlayerModel playerModel; // the model for the player
    private ToolValidity toolValidity; // the model for the tool validity
    private Dictionary<String, Command> toolCommands; // the dictionary of tool commands
    private Dictionary<String, Command> gameCommands; // the dictionary of game commands
    private boolean canRegister; // whether the player can register a farmer or not
    private int day; // the current day

    /**
     * Constructor for GameController. Initializes the models, tiles, and views, as well as the tool and game commands.
     * @param farmModel the model for the farm
     * @param farmView the view for the farm
     * @param playerModel the model for the player
     */
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

        // Setting the action listener for the text field.
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

    /**
     * Initializes the tile images, whether they are grass or rock.
     */
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

    /**
     * Initializes the tool and game commands.
     */
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

    /**
     * Parses the command and returns the type of command.
     * @param commands the commands and its arguments
     * @return the type of command
     */
    public String parseCommand(String[] commands) {
        if(toolCommands.get(commands[0]) != null) {
            return "tool";
        }

        else if(gameCommands.get(commands[0]) != null || commands[0].equals("inquire")) {
            if(commands[0].equals("inquire")) {
                if(isPlant(commands[1])) {
                    commands[0] = "inquire plant"; // changes the command to inquire plant if the second argument is a plant
                }
                else {
                    commands[0] = "inquire tile"; // changes the command to inquire tile if the second argument is a tile
                }
            }

            return "game"; 
        }
        else
            return "no type";
    }

    /**
     * Checks the validity of the command and its arguments, parses the command, and executes the command.
     * @param command the command and its arguments
     */
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

    /**
     * Checks the validity of the command and its arguments.
     * @param commands the command and its arguments
     * @return true if the command and its arguments are valid, false otherwise
     */
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

    /**
     * Checks if the argument is a plant.
     * @param input the input to be checked
     * @return true if the input is a plant, false otherwise
     */
    private boolean isPlant(String input) {
        if(farmModel.getPlantFromList(input) == null)
            return false;

        return true;
    }

    /**
     * Checks if the argument is a coordinate.
     * @param coordinate the coordinate to be checked
     * @return true if the coordinate is valid, false otherwise
     */
    private boolean isCoordinate(String coordinate) {
        if(coordinate.length() != 2)
            return false;

        if(coordinate.charAt(0) < '1' || coordinate.charAt(0) > '5')
            return false;

        if(coordinate.charAt(1) < 'a' || coordinate.charAt(1) > 'j')
            return false;

        return true;
    }

    /**
     * Executes the plant command.
     * @param commands the command and its arguments
     */
    private void plant(String[] commands) {
        Coordinate coordinate = new Coordinate(commands[2]);
        ArrayList<State> adjacentTilesStates = farmModel.getAdjacentTilesStates(coordinate);
        Plant plant = farmModel.getPlantFromList(commands[1]);
        int errorCode = toolValidity.validatePlant(adjacentTilesStates.toArray(new State[adjacentTilesStates.size()]),
                farmModel.getTileState(coordinate), plant, playerModel.getPlayerCoins());

        // Feedback to view for invalid commands
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

    /**
     * Executes the harvest command.
     * @param commands the command and its arguments
     */
    private void harvest(String[] commands) {
        Coordinate coordinate = new Coordinate(commands[1]);
        Plant plant = farmModel.getTilePlant(coordinate);
        int errorCode = toolValidity.validateHarvest(farmModel.getTileState(coordinate), farmModel.getTileHarvestDays(coordinate));

        // Feedback to view for invalid commands
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

        // Calculate the final price of the harvest
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
            farmView.setLevelStatus(String.valueOf(playerModel.getPlayerLevel()));

        playerModel.increaseMoney(finalPrice);

        farmView.setTileImage(coordinate.x, coordinate.y, "Grass");
        farmView.appendLogsBoxText("You've harvested " + plant.getName() + "...\n");
        farmView.appendLogsBoxText("+ " + finalPrice + " coins...\n");
        farmView.appendLogsBoxText("+ " + plant.getExpYield() + " exp...\n");

        farmView.setCoinsStatus(String.format("%.2f", playerModel.getPlayerCoins()));
        farmView.setExpStatus(String.format("%.2f", playerModel.getPlayerExp()));

        farmModel.removePlant(coordinate);
        farmModel.setState(State.DEFAULT, coordinate);
    }

    /**
     * Executes the plow command.
     * @param commands the command and its arguments
     */
    private void plow(String[] commands) {
        Coordinate coordinate = new Coordinate(commands[1]);
        int errorCode = toolValidity.validatePlow(farmModel.getTileState(coordinate));

        // Feedback to view for invalid commands
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
            farmView.setLevelStatus(String.valueOf(playerModel.getPlayerLevel()));

        farmView.setTileImage(coordinate.x, coordinate.y, "Plowed");
        farmView.appendLogsBoxText("You plowed the tile...\n");
        farmView.appendLogsBoxText("+ 0.5 exp\n");

        farmView.setExpStatus(String.format("%.2f", playerModel.getPlayerExp()));
    }

    /**
     * Executes the water command.
     * @param commands the command and its arguments
     */
    private void water(String[] commands) {
        Coordinate coordinate = new Coordinate(commands[1]);
        FarmerType farmerType = playerModel.getPlayerFarmerType();
        int errorCode = toolValidity.validateWater(farmModel.getTileState(coordinate), farmModel.getTileWaterCount(coordinate),
                farmModel.getTilePlant(coordinate).getWaterMax(), farmerType.getWaterBonus());

        // Feedback to view for invalid commands
        if(errorCode != 1) {
            switch(errorCode) {
                case 2:
                    farmView.appendLogsBoxText("Nothing to water here...\n");
                    break;
                case 4:
                    farmView.appendLogsBoxText("Water max reached...\n");
                    break;
            }

            if(errorCode != 4)
                return;
        }

        boolean leveledUp = playerModel.addExp(0.5f);
        promptRegister(leveledUp);

        if(leveledUp)
            farmView.setLevelStatus(String.valueOf(playerModel.getPlayerLevel()));

        farmModel.addWaterCount(coordinate);
        farmView.appendLogsBoxText("You've watered your " + farmModel.getTilePlant(coordinate).getName() + "...\n");
        farmView.appendLogsBoxText("+ 0.5 exp\n");

        farmView.setExpStatus(String.format("%.2f", playerModel.getPlayerExp()));
    }

    /**
     * Executes the fertilize command.
     * @param commands the command and its arguments
     */
    private void fertilize(String[] commands) {
        Coordinate coordinate = new Coordinate(commands[1]);
        FarmerType farmerType = playerModel.getPlayerFarmerType();
        int errorCode = toolValidity.validateFert(farmModel.getTileState(coordinate), playerModel.getPlayerCoins(), farmModel.getTileFertCount(coordinate),
                farmModel.getTilePlant(coordinate).getFertMax(), farmerType.getFertBonus());

        // Feedback to view for invalid commands
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
            
            if(errorCode != 4)
                return;
        }

        playerModel.decreaseMoney(10);
        boolean leveledUp = playerModel.addExp(4);
        promptRegister(leveledUp);

        if(leveledUp)
            farmView.setLevelStatus(String.valueOf(playerModel.getPlayerLevel()));

        farmModel.addFertCount(coordinate);
        farmView.appendLogsBoxText("You've fertilized your " + farmModel.getTilePlant(coordinate).getName() + " for 10 coins...\n");
        farmView.appendLogsBoxText("+ 4 exp\n");

        farmView.setCoinsStatus(String.format("%.2f", playerModel.getPlayerCoins()));
        farmView.setExpStatus(String.format("%.2f", playerModel.getPlayerExp()));
    }

    /**
     * Executes the shovel command.
     * @param commands the command and its arguments
     */
    private void shovel(String[] commands) {
        Coordinate coordinate = new Coordinate(commands[1]);
        int errorCode = toolValidity.validateShovel(farmModel.getTileState(coordinate), playerModel.getPlayerCoins());

        // Feedback to view for invalid commands
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

        playerModel.decreaseMoney(0);
        boolean leveledUp = playerModel.addExp(51);
        promptRegister(leveledUp);

        if(leveledUp)
            farmView.setLevelStatus(String.valueOf(playerModel.getPlayerLevel()));

        farmModel.removePlant(coordinate);
        farmModel.setState(State.DEFAULT, coordinate);

        farmView.setTileImage(coordinate.x, coordinate.y, "Grass");
        farmView.appendLogsBoxText("You used your shovel for 7 coins...\n");
        farmView.appendLogsBoxText("+ 2 exp\n");

        farmView.setCoinsStatus(String.format("%.2f", playerModel.getPlayerCoins()));
        farmView.setExpStatus(String.format("%.2f", playerModel.getPlayerExp()));
    }

    /**
     * Executes the pickaxe command.
     * @param commands the command and its arguments
     */
    private void pickaxe(String[] commands) {
        Coordinate coordinate = new Coordinate(commands[1]);
        int errorCode = toolValidity.validatePick(farmModel.getTileState(coordinate), playerModel.getPlayerCoins());

        // Feedback to view for invalid commands
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
            farmView.setLevelStatus(String.valueOf(playerModel.getPlayerLevel()));

        farmModel.setState(State.DEFAULT, coordinate);

        farmView.setTileImage(coordinate.x, coordinate.y, "Grass");
        farmView.appendLogsBoxText("You used your pickaxe for 50 coins...\n");
        farmView.appendLogsBoxText("+ 15 exp\n");

        farmView.setCoinsStatus(String.format("%.2f", playerModel.getPlayerCoins()));
        farmView.setExpStatus(String.format("%.2f", playerModel.getPlayerExp()));
    }

    /**
     * Executes the 'sleep' command, advances the day.
     * @param commands the command and its arguments
     */
    private void advanceDay(String[] commands) {
        ArrayList<Coordinate> activeCrops = farmModel.getActiveGrowingCrops();
        this.day++;

        farmView.appendLogsBoxText("Sleeping...\n");
        farmView.setDayStatus(Integer.toString(this.day));

        // Logic for withering plants, loops through all active crops
        for(Coordinate coord : activeCrops) {
            Plant p = farmModel.getTilePlant(coord);
            int waterCount = farmModel.getTileWaterCount(coord);
            int fertCount = farmModel.getTileFertCount(coord);

            farmModel.decHarvestDays(coord);
            // Negative harvest days means the plant is withered
            if(farmModel.getTileHarvestDays(coord) == -1 ) {
                farmModel.removePlant(coord);
                farmModel.setState(State.WITHERED, coord);
                farmModel.setPlant(farmModel.getPlantFromList("withered"), coord);
                farmView.setTileImage(coord.x, coord.y, "withered");
                farmView.appendLogsBoxText("Your " + p.getName() + " withered...\n");
            }
            // Zero harvest days means the plant is ready to harvest
            else if(farmModel.getTileHarvestDays(coord) == 0) {
                // Checks if the plant's needs are met
                if(waterCount < p.getWaterMin() || fertCount < p.getFertMin()) {
                    farmModel.removePlant(coord);
                    farmModel.setState(State.WITHERED, coord);
                    farmModel.setPlant(farmModel.getPlantFromList("withered"), coord);
                    farmView.setTileImage(coord.x, coord.y, "withered");
                    farmView.appendLogsBoxText("Your " + p.getName() + " withered...\n");
                }
                // If the plant's needs are met, it is ready to harvest
                else {
                    char c = (char)(coord.y + 97);
                    farmView.appendLogsBoxText(farmModel.getTilePlant(coord).getName() + " on " + (coord.x + 1) + c + " ready to harvest!\n");
                }
            }
        }

        // Logic for gameover, checks if there are no active crops
        activeCrops = farmModel.getActiveGrowingCrops(); // Updates activeCrops
        if(activeCrops.size() == 0) {
            if(playerModel.getPlayerCoins() < 5) {
                farmView.disableChatBox();
                farmView.appendLogsBoxText("Bakit ka kasi nagpakagutom?!\n");
                farmView.appendLogsBoxText("*Sinapak ang pader*\n");
                farmView.appendLogsBoxText("(Game over na btw)\n");
            }
        }
    }

    /**
     * Executes the 'inquire' command, provides information of a certain tile.
     * @param commands the command and its arguments
     */
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

    /**
     * Executes the 'inquire' command, provides information of a certain plant.
     * @param commands the command and its arguments
     */
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

    /**
     * Sets the bonuses in the view based on the farmer type.
     */
    private void setFarmerTypeBonusLabels() {
        FarmerType type = playerModel.getPlayerFarmerType();
        farmView.setWaterStatus(Integer.toString(type.getWaterBonus()));
        farmView.setFertilizerStatus(Integer.toString(type.getFertBonus()));
        farmView.setDiscountStatus(Integer.toString(type.getSeedDiscount()));
        farmView.setBonusProduceStatus(Integer.toString(type.getBonusProduce()));
        farmView.setTypeStatus(playerModel.getPlayerFarmerType().getName());
    }

    /**
     * Checks if the player can register for the next farmer type.
     * @return true if the player can register, false otherwise
     */
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

    /**
     * Prompts the player to register for the next farmer type.
     * @param leveledUp true if the player leveled up, false otherwise
     */
    private void promptRegister(boolean leveledUp) {
        if(leveledUp) {
            if(canRegisterNext()) {
                farmView.setNextRegisterStatus(playerModel.getRegisterable().getName() + " | " + playerModel.getRegisterCost() + " coins");
                farmView.appendLogsBoxText("You can now register for the next farmer type!\n");
                this.canRegister = true;
            }
        }
    }

    /**
     * Executes the 'register' command, registers the player for the next farmer type.
     */
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

    /**
     * Executes the 'help' command, displays the help menu.
     */
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
