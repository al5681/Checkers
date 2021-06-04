/**
 * Represents the model for the game
 */
public class Checkers {


    private CheckersBoard board;
    private final Player playerBlack= new Player("black");
    private final Player playerwhite= new Player("white");

    /**
     * Creates an instance of the boards and loads the pieces for the players,
     * for now only loads the white pieces
     */
    public Checkers()
    {
        board = new CheckersBoard();
        int white = 0;
        // spawn white pieces
        for(int row = 0; row < 3; row++)
        {
            for(int col = 0; col < 8; col++)
            {
                if(board.getBoard()[col][row].isDarkBrown() == true)
                {
                    board.getBoard()[row][col].setPiece(playerwhite.getPlayerPieces().get(white));
                    white++;

                }
            }
        }
    }

    public CheckersBoard getBoard() {
        return board;
    }

    public void setBoard(CheckersBoard board) {
        this.board = board;
    }

    public Player getPlayerBlack() {
        return playerBlack;
    }

    public Player getPlayerwhite() {
        return playerwhite;
    }

}
