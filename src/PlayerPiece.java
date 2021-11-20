import java.io.Serializable;

/**
 * Represents a Piece a player has
 */
public class PlayerPiece implements Serializable {

    private String playerColour;
    private int rowPos;
    private int colPos;
    private boolean canMakeLegalMove;
    private boolean canMakeLegalJump;
    private boolean selected;

    /**
     * Creates a piece for the player to use of the right colour
     *
     * @param playerColour
     */
    public PlayerPiece(String playerColour) {
        this.playerColour = playerColour;
    }

    public String getPlayerColour() {
        return playerColour;
    }

    public int getRowPos() {
        return rowPos;
    }

    public void setRowPos(int rowPos) {
        this.rowPos = rowPos;
    }

    public int getColPos() {
        return colPos;
    }

    public void setColPos(int colPos) {
        this.colPos = colPos;
    }

    public boolean getCanMakeLegalMove() {
        return canMakeLegalMove;
    }

    public void setCanMakeLegalMove(boolean canMakeLegalMove) {
        this.canMakeLegalMove = canMakeLegalMove;
    }

    public boolean getCanMakeLegalJump() {
        return canMakeLegalJump;
    }

    public void setCanMakeLegalJump(boolean canMakeLegalJump) {
        this.canMakeLegalJump = canMakeLegalJump;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return playerColour;
    }
}
