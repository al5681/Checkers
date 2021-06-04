import java.util.ArrayList;

/**
 * Represents a player who has a collection of pieces they move
 * around the board
 */
public class Player {

    private ArrayList<PlayerPiece> playerPieces = new ArrayList<>();

    public Player(String playerColour)
    {
        for(int i = 0; i <= 11; i++)
        {
            playerPieces.add(new PlayerPiece(playerColour));
        }
    }

    public ArrayList<PlayerPiece> getPlayerPieces() {
        return playerPieces;
    }

    public void setPlayerPieces(ArrayList<PlayerPiece> playerPieces) {
        this.playerPieces = playerPieces;
    }
}
