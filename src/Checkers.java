/**
 * Represents the model for the game
 */
public class Checkers {


    private CheckersBoard board;
    private final Player playerBlack = new Player("black");
    private final Player playerWhite = new Player("white");

    /**
     * Creates an instance of the board and loads the pieces in their initial spawns for the players
     */
    public Checkers() {
        // create board
        board = new CheckersBoard();
        // spawn white pieces
        int white = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 8; col++) {
                if (board.getBoard()[row][col].isDarkBrown()) {
                    playerWhite.getPlayerPieces().get(white).setRowPos(row);
                    playerWhite.getPlayerPieces().get(white).setColPos(col);
                    board.getBoard()[row][col].setPiece(playerWhite.getPlayerPieces().get(white));
                    white++;
                }
            }
        }
        // spawn black pieces
        int black = 0;
        for (int row = 5; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board.getBoard()[row][col].isDarkBrown()) {
                    playerBlack.getPlayerPieces().get(black).setRowPos(row);
                    playerBlack.getPlayerPieces().get(black).setColPos(col);
                    board.getBoard()[row][col].setPiece(playerBlack.getPlayerPieces().get(black));
                    black++;

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

    public Player getPlayerWhite() {
        return playerWhite;
    }

}
