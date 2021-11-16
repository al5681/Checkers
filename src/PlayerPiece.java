/**
 * Represents a Piece a player has
 */
public class PlayerPiece {

    private String playerColour;
    private int rowPos;
    private int colPos;
    private boolean canMakeLegalMove;
    private boolean selected;
    private boolean canMakeJump;

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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isCanMakeLegalMove() {
        return canMakeLegalMove;
    }

    public boolean getCanMakeJump() {
        return canMakeJump;
    }

    public void setCanMakeJump(boolean canMakeJump) {
        this.canMakeJump = canMakeJump;
    }

    @Override
    public String toString() {
        return playerColour;
    }
}
