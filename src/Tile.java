public class Tile {

    private boolean usable;
    private PlayerPiece piece;

    public Tile(boolean usable)
    {
        this.usable = usable;
    }

    public boolean isUsable() {
        return usable;
    }

    public void setUsable(boolean usable) {
        this.usable = usable;
    }

    public PlayerPiece getPiece() {
        return piece;
    }

    public void setPiece(PlayerPiece piece) {
        this.piece = piece;
    }

    @Override
    public String toString() {
        if(this.piece != null)
        {
            return " | " + usable + " , " + piece  + " | ";
        }
        return " | " + usable  + " | ";
    }
}
