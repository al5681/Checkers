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
    private GameState gameState;
    private Tile tileToDeleteForJump;

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
        // check the initial black players that can make a move
        for (int i = 0; i < playerBlack.getPlayerPieces().size(); i++) {
            canMakeLegalMoves(playerBlack.getPlayerPieces().get(i));
        }
        gameState = GameState.SelectingPiece; // initialise game state
    }

    /**
     * Takes a tile, and if it contains a piece, sets that piece to selected and highlights the tiles it can move to
     *
     * @param tileOfPiece
     * @return tileToDelete for jump
     */
    public void selectPiece(Tile tileOfPiece) {
        if (tileOfPiece.getPiece() != null) {
            PlayerPiece playerPiece = tileOfPiece.getPiece();
            ArrayList<Tile> tiles = validMoves(playerPiece);
            if(gameState == GameState.MakingJump && playerPiece.getCanMakeLegalMove() && playerPiece.getPlayerColour().equals(getCurrentTurn()) && currentTurn.equals("white")){
                tileToDeleteForJump = tiles.get(0);
                // the row and column to move to depends on the row col of Tile tileOfPiece so change it accordingly!!!
                Tile tiletoJumpTo;
                int tileToJumpToCol;
                int tileToJumpToRow = tiles.get(0).getRow()+1;
                if(tileOfPiece.getCol() < tileToDeleteForJump.getCol()) {
                    tileToJumpToCol = tiles.get(0).getCol()+1;
                    tiletoJumpTo = checkersBoard.getBoard()[tileToJumpToRow][tileToJumpToCol];
                }
                else {
                    tileToJumpToCol = tiles.get(0).getCol() - 1;
                    tiletoJumpTo = checkersBoard.getBoard()[tileToJumpToRow][tileToJumpToCol];
                }
                if(tiletoJumpTo.getPiece() != null || !inBounds(tileToJumpToRow,tileToJumpToCol)) {
                    playerPiece.setCanMakeLegalMove(false);
                    gameState = GameState.SelectingPiece;
                    tileToDeleteForJump = null;
                    currentTurn = playerWhite.getPlayerPieces().get(0).getPlayerColour();
                    for (int i = 0; i < playerWhite.getPlayerPieces().size(); i++) {
                        if(playerWhite.getPlayerPieces().get(i).equals(playerPiece)){

                        }
                        else {
                            canMakeLegalMovesNoJumps(playerWhite.getPlayerPieces().get(i));
                        }
                    }
                    return;
                }
                else {
                    tiles.remove(0);
                    tiles.add(tiletoJumpTo);
                }
            }
            else if(gameState == GameState.MakingJump && playerPiece.getCanMakeLegalMove() && playerPiece.getPlayerColour().equals(getCurrentTurn()) && currentTurn.equals("black")) {
                tileToDeleteForJump = tiles.get(0);
                Tile tileToJumpTo;
                int tileToJumpToCol;
                int tileToJumpToRow = tiles.get(0).getRow() - 1;
                if (tileOfPiece.getCol() < tileToDeleteForJump.getCol()) {
                    tileToJumpToCol = tiles.get(0).getCol() + 1;
                    if (tileToJumpToCol > 9 || tileToJumpToCol < 0 || tileToJumpToRow > 9 || tileToJumpToRow < 0) {
                        {
                            tileToJumpTo = null;
                        }
                    }
                    tileToJumpTo = checkersBoard.getBoard()[tileToJumpToRow][tileToJumpToCol];
                } else {
                    tileToJumpToCol = tiles.get(0).getCol() - 1;
                    if (tileToJumpToCol > 9 || tileToJumpToCol < 0 || tileToJumpToRow > 9 || tileToJumpToRow < 0) {
                        {
                            tileToJumpTo = null;
                        }
                    } else {
                        tileToJumpTo = checkersBoard.getBoard()[tileToJumpToRow][tileToJumpToCol];
                    }
                }
                if (tileToJumpTo != null) {
                    if (tileToJumpTo.getPiece() != null) {
                        playerPiece.setCanMakeLegalMove(false);
                        gameState = GameState.SelectingPiece;
                        tileToDeleteForJump = null;
                        currentTurn = playerBlack.getPlayerPieces().get(0).getPlayerColour();
                        for (int i = 0; i < playerBlack.getPlayerPieces().size(); i++) {
                            if (playerBlack.getPlayerPieces().get(i).equals(playerPiece)) {

                            } else {
                                canMakeLegalMovesNoJumps(playerBlack.getPlayerPieces().get(i));
                            }
                        }
                        return;
                    }
                    else {
                        tiles.remove(0);
                        tiles.add(tileToJumpTo);
                    }
                }
                else if(tileToJumpTo == null)
                {
                    playerPiece.setCanMakeLegalMove(false);
                    gameState = GameState.SelectingPiece;
                    tileToDeleteForJump = null;
                    currentTurn = playerBlack.getPlayerPieces().get(0).getPlayerColour();
                    for (int i = 0; i < playerBlack.getPlayerPieces().size(); i++) {
                        if (playerBlack.getPlayerPieces().get(i).equals(playerPiece)) {

                        } else {
                            canMakeLegalMovesNoJumps(playerBlack.getPlayerPieces().get(i));
                        }
                    }
                }
            }
            if (playerPiece.getCanMakeLegalMove() && playerPiece.getPlayerColour().equals(getCurrentTurn()) ) {
                playerPiece.setSelected(true);
                gameState = GameState.SelectingTileToMoveTo;
                // highlight all the tiles the piece can move to
                for (Tile tile : tiles) {
                    tile.setHighlighted(true);
                }
            }
        }
    }

    /**
     * Takes a tile for a player piece to move to, and if that tile is available for that player piece they are
     * moved there
     *
     * @param tileToMoveTo
     */
    public void movePiece(Tile tileToMoveTo) {
        if (getHighlightedTiles().contains(tileToMoveTo)) {
            if (currentTurn.equals("black")) {
                if(tileToDeleteForJump != null)
                {
                    playerWhite.getPlayerPieces().remove(tileToDeleteForJump.getPiece());
                    checkersBoard.getBoard()[tileToDeleteForJump.getPiece().getRowPos()][tileToDeleteForJump.getPiece().getColPos()].setPiece(null);
                }
                for (int i = 0; i < playerBlack.getPlayerPieces().size(); i++) {
                    if (playerBlack.getPlayerPieces().get(i).isSelected()) {
                        // remove from current tile
                        checkersBoard.getBoard()[playerBlack.getPlayerPieces().get(i).getRowPos()][playerBlack.getPlayerPieces().get(i).getColPos()].setPiece(null);
                        // get and set new positions for piece
                        playerBlack.getPlayerPieces().get(i).setRowPos(tileToMoveTo.getRow());
                        playerBlack.getPlayerPieces().get(i).setColPos(tileToMoveTo.getCol());
                        // update the state of the tile
                        playerWhite.getPlayerPieces().remove(tileToMoveTo.getPiece());
                        checkersBoard.getBoard()[tileToMoveTo.getRow()][tileToMoveTo.getCol()].setPiece(playerBlack.getPlayerPieces().get(i));
                        // the player piece is no longer selected
                        playerBlack.getPlayerPieces().get(i).setSelected(false);

                    }
                }
            }
            if (currentTurn.equals("white")) {
                if(tileToDeleteForJump != null)
                {
                    playerBlack.getPlayerPieces().remove(tileToDeleteForJump.getPiece());
                    checkersBoard.getBoard()[tileToDeleteForJump.getPiece().getRowPos()][tileToDeleteForJump.getPiece().getColPos()].setPiece(null);
                }
                for (int i = 0; i < playerWhite.getPlayerPieces().size(); i++) {
                    if (playerWhite.getPlayerPieces().get(i).isSelected()) {
                        checkersBoard.getBoard()[playerWhite.getPlayerPieces().get(i).getRowPos()][playerWhite.getPlayerPieces().get(i).getColPos()].setPiece(null);
                        playerWhite.getPlayerPieces().get(i).setRowPos(tileToMoveTo.getRow());
                        playerWhite.getPlayerPieces().get(i).setColPos(tileToMoveTo.getCol());
                        playerBlack.getPlayerPieces().remove(checkersBoard.getBoard()[tileToMoveTo.getRow()][tileToMoveTo.getCol()]);
                        playerBlack.getPlayerPieces().remove(tileToMoveTo.getPiece());
                        checkersBoard.getBoard()[tileToMoveTo.getRow()][tileToMoveTo.getCol()].setPiece(playerWhite.getPlayerPieces().get(i));
                        playerWhite.getPlayerPieces().get(i).setSelected(false);

                    }
                }
            }
            System.out.println(playerBlack.getPlayerPieces().size());
            System.out.println(playerWhite.getPlayerPieces().size());
            // unhighlight tiles
            ArrayList<Tile> currentlyHighlighted = getHighlightedTiles();
            for (Tile tile : currentlyHighlighted) {
                tile.setHighlighted(false);
            }
            for (int i = 0; i < playerBlack.getPlayerPieces().size(); i++) {
                playerBlack.getPlayerPieces().get(i).setCanMakeJump(false);
            }
            for (int i = 0; i < playerWhite.getPlayerPieces().size(); i++) {
                playerWhite.getPlayerPieces().get(i).setCanMakeJump(false);
            }
            tileToDeleteForJump = null;
            gameState = GameState.SelectingPiece; // reset the game state
            changeCurrentPlayersTurn(); // end the turn
        }
    }

    /**
     * Changes the turn to the other player
     */
    public void changeCurrentPlayersTurn() {
        if (currentTurn.equals("black")) {
            currentTurn = playerWhite.getPlayerPieces().get(0).getPlayerColour();
            for (int i = 0; i < playerWhite.getPlayerPieces().size(); i++) {
                canMakeLegalMoves(playerWhite.getPlayerPieces().get(i));
            }
        } else {
            currentTurn = playerBlack.getPlayerPieces().get(0).getPlayerColour();
            for (int i = 0; i < playerBlack.getPlayerPieces().size(); i++) {
                canMakeLegalMoves(playerBlack.getPlayerPieces().get(i));
            }
        }
    }

    /**
     * Takes a player piece and sets its 'canMakeLegalMove' attribute to the right value
     * for the current state of the game
     *
     * @param playerPiece
     */
    public void canMakeLegalMoves(PlayerPiece playerPiece) {
        ArrayList<Tile> tilesThatCanBeMovedTo = validMoves(playerPiece);
        playerPiece.setCanMakeLegalMove(tilesThatCanBeMovedTo.size() != 0);

    }

    public void canMakeLegalMovesNoJumps(PlayerPiece playerPiece) {
        ArrayList<Tile> tilesThatCanBeMovedTo = findTilesThatCanBeMovedToNoJumps(playerPiece);
        playerPiece.setCanMakeLegalMove(tilesThatCanBeMovedTo.size() != 0);

    }

    /**
     * Checks the states of the neighbour tiles of a player piece, if the tile
     * is dark brown, has no current player piece on it (is not occupied),
     * and is not behind the player piece, it is added to a list of tiles that piece can move to
     *
     * @param playerPiece
     * @return an array list of the tiles that the player piece can move to
     */
    public ArrayList<Tile> validMoves(PlayerPiece playerPiece) {
        ArrayList<Tile> tilesThatCanBeMovedTo = new ArrayList<>();
        ArrayList<Point> neighbourCoOrdinates = getNeighbours(playerPiece);
        for (int row = 0; row < neighbourCoOrdinates.size(); row++) {
            for (int col = 0; col < neighbourCoOrdinates.size(); col++) {
                // check jumps
                if (currentTurn == "black" && checkersBoard.getBoard()[neighbourCoOrdinates.get(row).x][neighbourCoOrdinates.get(row).y].getPiece() != null &&
                        checkersBoard.getBoard()[neighbourCoOrdinates.get(row).x][neighbourCoOrdinates.get(row).y].getPiece().getPlayerColour() == "white"
                        && playerPiece.getPlayerColour().equals("black") && neighbourCoOrdinates.get(row).x == playerPiece.getRowPos() - 1) {
                    playerPiece.setCanMakeJump(true);
                    playerPiece.setCanMakeLegalMove(true);
                    ArrayList<Tile> tileOfPeiceToRemove = new ArrayList<>();
                    tileOfPeiceToRemove.add(checkersBoard.getBoard()[neighbourCoOrdinates.get(row).x][neighbourCoOrdinates.get(row).y]);
                    gameState = GameState.MakingJump;
                    return tileOfPeiceToRemove;
                } else if (currentTurn == "white" && checkersBoard.getBoard()[neighbourCoOrdinates.get(row).x][neighbourCoOrdinates.get(row).y].getPiece() != null &&
                        checkersBoard.getBoard()[neighbourCoOrdinates.get(row).x][neighbourCoOrdinates.get(row).y].getPiece().getPlayerColour() == "black"
                        && playerPiece.getPlayerColour().equals("white") && neighbourCoOrdinates.get(row).x == playerPiece.getRowPos() + 1) {
                    playerPiece.setCanMakeJump(true);
                    playerPiece.setCanMakeLegalMove(true);
                    ArrayList<Tile> tileOfPieceToRemove = new ArrayList<>();
                    tileOfPieceToRemove.add(checkersBoard.getBoard()[neighbourCoOrdinates.get(row).x][neighbourCoOrdinates.get(row).y]);
                    gameState = GameState.MakingJump;
                    return tileOfPieceToRemove;
                }
                // non-jump moves
                else if (checkersBoard.getBoard()[neighbourCoOrdinates.get(row).x][neighbourCoOrdinates.get(row).y].isDarkBrown()
                        && checkersBoard.getBoard()[neighbourCoOrdinates.get(row).x][neighbourCoOrdinates.get(row).y].getPiece() == null
                        && !tilesThatCanBeMovedTo.contains(checkersBoard.getBoard()[neighbourCoOrdinates.get(row).x][neighbourCoOrdinates.get(row).y])) {
                    if (playerPiece.getPlayerColour().equals("black") && neighbourCoOrdinates.get(row).x == playerPiece.getRowPos() - 1) {
                        tilesThatCanBeMovedTo.add(checkersBoard.getBoard()[neighbourCoOrdinates.get(row).x][neighbourCoOrdinates.get(row).y]);
                    } else if (playerPiece.getPlayerColour().equals("white") && neighbourCoOrdinates.get(row).x == playerPiece.getRowPos() + 1) {
                        tilesThatCanBeMovedTo.add(checkersBoard.getBoard()[neighbourCoOrdinates.get(row).x][neighbourCoOrdinates.get(row).y]);
                    }
                }
            }
        }
        clearLegalMovesIfJumpCanBeMade();
        return tilesThatCanBeMovedTo;
    }

   private void clearLegalMovesIfJumpCanBeMade()
   {
       if (gameState == GameState.MakingJump) {
           for (int i = 0; i < playerWhite.getPlayerPieces().size(); i++) {
               if (playerWhite.getPlayerPieces().get(i).getCanMakeJump() == false) {
                   playerWhite.getPlayerPieces().get(i).setCanMakeLegalMove(false);
               }
           }
           for (int i = 0; i < playerBlack.getPlayerPieces().size(); i++) {
               if (playerBlack.getPlayerPieces().get(i).getCanMakeJump() == false) {
                   playerBlack.getPlayerPieces().get(i).setCanMakeLegalMove(false);
               }
           }
       }
   }

    public ArrayList<Tile> findTilesThatCanBeMovedToNoJumps(PlayerPiece playerPiece) {
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
     * Takes a player piece and finds the co-ordinates of its neighbouring tiles
     *
     * @param playerPiece
     * @return the co-ordinates of each neighbour tile
     */
    public ArrayList<Point> getNeighbours(PlayerPiece playerPiece) {
        ArrayList<Point> neighbourCoOrdinates = new ArrayList<>();
        int[] rowNbr = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colNbr = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};
        for (int k = 0; k < 8; ++k) {
            if (inBounds(playerPiece.getRowPos() + rowNbr[k], playerPiece.getColPos() + colNbr[k])) {
                neighbourCoOrdinates.add(new Point(playerPiece.getRowPos() + rowNbr[k], playerPiece.getColPos() + colNbr[k]));
            }
        }
        return neighbourCoOrdinates;
    }

    // helper method to check if a tile index is in bounds
    private boolean inBounds(int row, int col) {
        return row >= 0 && row < checkersBoard.getRows() &&
                col >= 0 && col < checkersBoard.getCols();
    }

    public CheckersBoard getCheckersBoard() {
        return checkersBoard;
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

    public GameState getGameState() {
        return gameState;
    }

    /**
     * Returns the tiles for which the highlighted attribute == true
     *
     * @return the currently highlighted tiles
     */
    public ArrayList<Tile> getHighlightedTiles() {
        ArrayList<Tile> highlightedTiles = new ArrayList<>();
        for (int row = 0; row < checkersBoard.getRows(); row++) {
            for (int col = 0; col < checkersBoard.getCols(); col++) {
                if (checkersBoard.getBoard()[row][col].isHighlighted()) {
                    highlightedTiles.add(checkersBoard.getBoard()[row][col]);
                }

            }
        }
        return highlightedTiles;
    }
}
