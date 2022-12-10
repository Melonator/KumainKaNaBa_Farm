package gameClasses;

public class Coordinate {
    public int x;
    public int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Coordinate(String coordinate) {
        this.x = (int)coordinate.charAt(0) - 49;
        this.y = (int)coordinate.charAt(1) - 97;
    }
}
