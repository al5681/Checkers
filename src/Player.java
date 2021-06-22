import java.util.ArrayList;

/**
 * Represents a player who has a collection of pieces they move
 * around the board
 */
public class Player {

    private ArrayList<PlayerPiece> playerPieces = new ArrayList<>();

    /**
     * Adds 12 pieces for the player to start with, of the right colour
     *
     * @param playerColour
     */
    public Player(String playerColour) {
        for (int i = 0; i <= 11; i++) {
            playerPieces.add(new PlayerPiece(playerColour));
        }
    }

    public ArrayList<PlayerPiece> getPlayerPieces() {
        return playerPieces;
    }

}
