/**
 * Represents a tile on the board,
 * darkBrown indicates it is one of the usable tiles on the board
 * piece indicates if there is currently one of the players pieces
 * on the tile
 */
public class Tile {

    private boolean darkBrown;
    private PlayerPiece piece;

    public Tile(boolean usable) {
        this.darkBrown = usable;
    }

    public boolean isDarkBrown() {
        return darkBrown;
    }

    public void setDarkBrown(boolean darkBrown) {
        this.darkBrown = darkBrown;
    }

    public PlayerPiece getPiece() {
        return piece;
    }

    public void setPiece(PlayerPiece piece) {
        this.piece = piece;
    }

    @Override
    public String toString() {
        if (this.piece != null) {
            return " | " + darkBrown + " , " + piece + " | ";
        }
        return " | " + darkBrown + " | ";
    }
}
