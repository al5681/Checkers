/**
 * Represents the model for the game
 */
public class Checkers {


    private CheckersBoard checkersBoard;
    private Player playerBlack = new Player("black");
    private Player playerWhite = new Player("white");
    private String currentTurn = playerBlack.getPlayerPieces().get(0).getPlayerColour(); // black goes first

    /**
     * Creates an instance of the board and loads the pieces in their initial spawns for the players
     */
    public Checkers() {
        // create board
        checkersBoard = new CheckersBoard();
        // spawn white pieces
        int white = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 8; col++) {
                if (checkersBoard.getBoard()[row][col].isDarkBrown()) {
                    // set the initial co-ordinates
                    playerWhite.getPlayerPieces().get(white).setRowPos(row);
                    playerWhite.getPlayerPieces().get(white).setColPos(col);
                    // place on board
                    checkersBoard.getBoard()[row][col].setPiece(playerWhite.getPlayerPieces().get(white));
                    white++;
                }
            }
        }
        // spawn black pieces
        int black = 0;
        for (int row = 5; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (checkersBoard.getBoard()[row][col].isDarkBrown()) {
                    // set the initial co-ordinates
                    playerBlack.getPlayerPieces().get(black).setRowPos(row);
                    playerBlack.getPlayerPieces().get(black).setColPos(col);
                    // place on board
                    checkersBoard.getBoard()[row][col].setPiece(playerBlack.getPlayerPieces().get(black));
                    black++;

                }
            }
        }
    }

    public void changeCurrentPlayersTurn()
    {
        if(currentTurn.equals("black")) {
            currentTurn = playerWhite.getPlayerPieces().get(0).getPlayerColour();
        }
        else {
            currentTurn = playerBlack.getPlayerPieces().get(0).getPlayerColour();
        }
        System.out.println(currentTurn); // DELETE LATER
    }

    public CheckersBoard getCheckersBoard() {
        return checkersBoard;
    }

    public void setCheckersBoard(CheckersBoard checkersBoard) {
        this.checkersBoard = checkersBoard;
    }

    public Player getPlayerBlack() {
        return playerBlack;
    }

    public Player getPlayerWhite() {
        return playerWhite;
    }

    public String getCurrentTurn()
    {
        return currentTurn;
    }
}
