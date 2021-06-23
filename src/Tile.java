/**
 * Represents a tile on the board,
 * darkBrown indicates it is one of the usable tiles on the board
 * piece indicates if there is currently one of the players pieces
 * on the tile
 */
public class Tile {

    private boolean darkBrown;
    private PlayerPiece piece;
    private final int row;
    private final int col;

    /**
     * Creates a tile that is either dark brown (usable)
     * or light brown (unusable)
     *
     * @param darkBrown
     * @param row
     * @param col
     */
    public Tile(boolean darkBrown, int row, int col) {
        this.darkBrown = darkBrown;
        this.row = row;
        this.col = col;
    }

    public boolean isDarkBrown() {
        return darkBrown;
    }

    public PlayerPiece getPiece() {
        return piece;
    }

    public void setPiece(PlayerPiece piece) {
        this.piece = piece;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        if (this.piece != null) {
            return " | " + darkBrown + " , " + piece + " | ";
        }
        return " | " + darkBrown + " | ";
    }
}
