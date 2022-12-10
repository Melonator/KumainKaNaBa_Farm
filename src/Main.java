/**
 * Kumain ka na ba Farm is a simple farming game that runs in Java. 
 * It is a CCPROG3 machine project for the 1st semester of AY 2022-2023.
 * 
 * @author Johndayll Lewis D. Arizala
 * @author John Kovie L. Niño
 * @version 1.0-beta
 * 
 */

import controllers.GameController;
import models.*;

public class Main{
    public static void main(String[] args) {
        FarmModel farmModel = new FarmModel();
        FarmView farmView = new FarmView();
        PlayerModel playerModel = new PlayerModel();

        GameController gameController = new GameController(farmModel, farmView, playerModel);
    }
}