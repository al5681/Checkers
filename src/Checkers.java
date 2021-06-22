import java.awt.*;
import java.util.ArrayList;

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

    /**
     * Changes the turn to the other player
     */
    public void changeCurrentPlayersTurn() {
        if (currentTurn.equals("black")) {
            currentTurn = playerWhite.getPlayerPieces().get(0).getPlayerColour();
        } else {
            currentTurn = playerBlack.getPlayerPieces().get(0).getPlayerColour();
        }
    }

    public boolean canMakeLegalMoves(PlayerPiece playerPiece) {
        ArrayList<Tile> tilesThatCanBeMovedTo = findTilesThatCanBeMovedTo(playerPiece);
        if(tilesThatCanBeMovedTo.size() != 0) {
            playerPiece.setCanMakeLegalMove(true);
            return true;
        }
        return false;
    }


    public ArrayList<Tile> findTilesThatCanBeMovedTo(PlayerPiece playerPiece) {
        ArrayList<Tile> tilesThatCanBeMovedTo = new ArrayList<>();
        ArrayList<Point> neighbourCoOrdinates = getNeighbours(playerPiece);
        for (int row = 0; row < neighbourCoOrdinates.size(); row++) {
            for (int col = 0; col < neighbourCoOrdinates.size(); col++) {
                if (checkersBoard.getBoard()[neighbourCoOrdinates.get(row).x][neighbourCoOrdinates.get(row).y].isDarkBrown()
                        && checkersBoard.getBoard()[neighbourCoOrdinates.get(row).x][neighbourCoOrdinates.get(row).y].getPiece() == null
                        && !tilesThatCanBeMovedTo.contains(checkersBoard.getBoard()[neighbourCoOrdinates.get(row).x][neighbourCoOrdinates.get(row).y]))
                    if (playerPiece.getPlayerColour().equals("black") && neighbourCoOrdinates.get(row).x == playerPiece.getRowPos() - 1) {
                        tilesThatCanBeMovedTo.add(checkersBoard.getBoard()[neighbourCoOrdinates.get(row).x][neighbourCoOrdinates.get(row).y]);
                    } else if (playerPiece.getPlayerColour().equals("white") && neighbourCoOrdinates.get(row).x == playerPiece.getRowPos() + 1) {
                        tilesThatCanBeMovedTo.add(checkersBoard.getBoard()[neighbourCoOrdinates.get(row).x][neighbourCoOrdinates.get(row).y]);
                    }
            }
        }
        return tilesThatCanBeMovedTo;
    }


    /**
     * Takes a playerpiece and finds the co-ordinates of its neighbouring tiles
     *
     * @param playerPiece
     * @return the co-ordinates of each neighbour tile
     */
    public ArrayList<Point> getNeighbours(PlayerPiece playerPiece) {
        ArrayList<Point> neighbourCoOrdinates = new ArrayList<>();
        int rowNbr[] = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
        int colNbr[] = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};
        for (int k = 0; k < 8; ++k) {
            if (inBounds(playerPiece.getRowPos() + rowNbr[k], playerPiece.getColPos() + colNbr[k])) {
                neighbourCoOrdinates.add(new Point(playerPiece.getRowPos() + rowNbr[k], playerPiece.getColPos() + colNbr[k]));
            }
        }
        return neighbourCoOrdinates;
    }

    // helper method to check if a tile index is in bounds
    private boolean inBounds(int row, int col) {
        if (row >= 0 && row < checkersBoard.getRows() &&
                col >= 0 && col < checkersBoard.getCols()) {
            return true;
        }
        return false;
    }

    public boolean legalMovePossible(PlayerPiece playerPiece) {
        return false;
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

    public String getCurrentTurn() {
        return currentTurn;
    }
}
