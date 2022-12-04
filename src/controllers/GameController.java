package controllers;

import models.FarmModel;
import models.PlayerModel;
import models.ToolValidity;

import java.util.Random;

public class GameController {
    private FarmModel farmModel;
    private PlayerModel playerModel;
    private ToolValidity toolValidity;

    public GameController() {
        this.farmModel = new FarmModel();
        this.playerModel = new PlayerModel();
        this.toolValidity = new ToolValidity();
        initRocks();
    }

    private void initRocks() {

    }
}
