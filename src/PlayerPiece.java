public class PlayerPiece {

    private String playerColour;
    private int row;
    private int col;

    public PlayerPiece(String playerColour) {
        this.playerColour = playerColour;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
