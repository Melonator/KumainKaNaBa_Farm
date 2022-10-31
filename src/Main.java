import models.Game;
public class Main{
    public static void main(String[] args) {
        Game game = new Game();
        while(game.run())
        {
            System.out.println("[ Would you like to start a new game? ]");
            //Break if the player wants to exit the game
        }
    }
}