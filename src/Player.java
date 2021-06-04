import java.util.ArrayList;

public class Player {

    private ArrayList<PlayerPiece> playerPieces = new ArrayList<>();

    public Player(String playerColour)
    {
        for(int i = 0; i <= 11; i++)
        {
            playerPieces.add(new PlayerPiece(playerColour));
        }
    }
}
