/**
 * Represents a Piece a player has
 */
public class PlayerPiece {

    private String playerColour;

    public PlayerPiece(String playerColour) {
        this.playerColour = playerColour;
    }

    @Override
    public String toString() {
        return
                "" + playerColour;
    }
}
