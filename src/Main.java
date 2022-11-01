import models.Game;

import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);
        int choice = 1;
        while(choice == 1)
        {
            game.run();
            System.out.println("[ Would you like to start a new game? ]\n>>");
            choice = scanner.nextInt();
        }
    }
}