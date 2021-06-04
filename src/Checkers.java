public class Checkers {

    // create instance of the board load the player pieces onto the board
    private CheckersBoard board;
    private final Player playerBlack= new Player("black");
    private final Player playerwhite= new Player("white");

    public Checkers()
    {
        board = new CheckersBoard();
        int white = 0;
        // spawn white pieces
        for(int row = 0; row < 8; row++)
        {
            for(int col = 0; col < 3; col++)
            {
                if(board.getBoard()[col][row].isUsable() == true)
                {
                    board.getBoard()[col][row].setPiece(playerwhite.getPlayerPieces().get(white));
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
