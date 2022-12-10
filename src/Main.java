/**
 * Kumain ka na ba Farm is a simple farming game that runs in Java. 
 * It is a CCPROG3 machine project for the 1st semester of AY 2022-2023.
 * 
 * @author Johndayll Lewis D. Arizala
 * @author John Kovie L. Niño
 * @version 1.0
 * 
 */

import controllers.GameController;
import models.*;

/**
 * This is the main class of the game. It is the entry point of the program.
 */
public class Main{
    public static void main(String[] args) {
        FarmModel farmModel = new FarmModel(); // FarmModel is the model of the game
        FarmView farmView = new FarmView(); // FarmView is the view of the game
        PlayerModel playerModel = new PlayerModel(); // PlayerModel is the model of the player

        GameController gameController = new GameController(farmModel, farmView, playerModel); // GameController is the controller of the game
    }
}