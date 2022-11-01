/**
 * Kumain ka na ba Farm is a simple farming game that runs in Java. 
 * It is a CCPROG3 machine project for the 1st semester of AY 2022-2023.
 * 
 * @author Johndayll Lewis D. Arizala
 * @author John Kovie L. Niño
 * @version 1.0-beta
 * 
 */

import models.Game;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        Game game;
        Scanner scanner = new Scanner(System.in);
        int choice = 1;

        // This is a while loop that will run the game until the user not inputs .
        while(choice == 1) 
        {
            game = new Game(); // Creating a new instance of the Game class.
            game.run(); // Running the game.
            System.out.println("[ Would you like to start a new game? ]\n>>");
            choice = scanner.nextInt();
        }

        scanner.close();
    }
}