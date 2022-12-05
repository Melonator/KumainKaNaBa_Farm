package gameClasses;

public class Coordinate {
    public int x;
    public int y;

    public Coordinate() {
        this.x = 0;
        this.y = 0;
    }
    public Coordinate(String coordinate) {
        this.x = coordinate.charAt(0) - 49;
        this.y = coordinate.charAt(1) - 105;
    }
}
