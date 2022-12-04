package controllers;

import gameClasses.Command;
import gameClasses.FarmerType;
import models.FarmModel;
import models.PlayerModel;
import models.ToolValidity;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

public class GameController {
    private FarmModel farmModel;
    private PlayerModel playerModel;
    private ToolValidity toolValidity;
    private Dictionary<String, Command> toolCommands = new Hashtable();
    private Dictionary<String, Command> gameCommands = new Hashtable();

    public GameController() {
        this.farmModel = new FarmModel();
        this.playerModel = new PlayerModel();
        this.toolValidity = new ToolValidity();
        initRocks();
        initCommands();
    }

    private void initRocks() {

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
        else
            gameCommands.get(commands[0]).execCommand(commands);
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
        for (Enumeration<String> keyEnumeration = farmModel.getPlantMasterList().keys(); keyEnumeration.hasMoreElements();)  {
            if(keyEnumeration.nextElement().toLowerCase().equals(input))
                return true;
        }

            return false;
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
        System.out.println("planted");
    }

    private void harvest(String[] commands) {
        System.out.println("harvest");
    }

    private void plow(String[] commands) {
        System.out.println("plow");
    }

    private void water(String[] commands) {
        System.out.println("water");
    }

    private void fertilize(String[] commands) {
        System.out.println("fertilize");
    }

    private void shovel(String[] commands) {
        System.out.println("shovel");
    }

    private void pickaxe(String[] commands) {
        System.out.println("pickaxe");
    }

    private void advanceDay(String[] commands) {
        System.out.println("advance");
    }

    private void inquireTile(String[] commands) {
        System.out.println("inquire tile");
    }

    private void inquirePlant(String[] commands) {
        System.out.println("inquire plant");
    }
}
