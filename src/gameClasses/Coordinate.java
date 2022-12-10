package gameClasses;

/**
 * Holds a 2d coordinate.
 */
public class Coordinate {
    public int x; // row index
    public int y; // column index

    /**
     * Sets the coordinates.
     * @param x the row index
     * @param y the column index
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Converts a string coordinate into an integer.
     * @param coordinate the coordinate in the form of a string
     */
    public Coordinate(String coordinate) {
        this.x = (int)coordinate.charAt(0) - 49; // ranges numbers from 0-9
        this.y = (int)coordinate.charAt(1) - 97; // ranges letters from a-z
    }
}
