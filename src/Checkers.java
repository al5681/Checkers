import javafx.util.Pair;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.awt.*;
import java.util.ArrayList;

/**
 * Represents the model for the game
 */
public class Checkers implements Serializable {

    private CheckersBoard checkersBoard;
    private Player playerBlack = new Player("black");
    private Player playerWhite = new Player("white");
    private String currentTurn = "black"; // black goes first
    private PlayerAction playerAction;
    private boolean gameOver;
    private int difficulty = 4; // initialise to medium difficulty

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
            setLegalOptionsForTurn(playerBlack.getPlayerPieces().get(i));
        }
        playerAction = PlayerAction.SelectingPiece; // initialise game state
    }

    /**
     * Takes a tile, if it contains a piece, sets that piece to selected and highlights the tiles it can move/jump to
     *
     * @param tileOfPiece
     */
    public boolean selectPiece(Tile tileOfPiece) {
        if (tileOfPiece.getPiece() != null) {
            PlayerPiece playerPiece = tileOfPiece.getPiece();
            if (playerPiece.getCanMakeLegalMove() && playerPiece.getPlayerColour().equals(getCurrentTurn())) {
                ArrayList<Tile> tiles = findTilesThatCanBeMovedTo(playerPiece);
                playerPiece.setSelected(true);
                playerAction = PlayerAction.SelectingTileToMoveTo;
                for (Tile tile : tiles) {
                    tile.setHighlighted(true);
                }
                return true;
            } else if (playerPiece.getCanMakeLegalJump() && playerPiece.getPlayerColour().equals(getCurrentTurn())) {
                ArrayList<Pair<Tile, Tile>> tileToDeleteAndJumpToPairs = findTilesThatCanBeJumpedTo(playerPiece);
                playerPiece.setSelected(true);
                playerAction = PlayerAction.MakingJump;
                for (int i = 0; i < tileToDeleteAndJumpToPairs.size(); i++) {
                    tileToDeleteAndJumpToPairs.get(i).getValue().setHighlighted(true);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Takes a tile for a player piece to move to, and if that tile is available for that player piece they are
     * moved there
     *
     * @param tileToMoveTo
     */
    public boolean movePiece(Tile tileToMoveTo) {
        if (getHighlightedTiles().contains(tileToMoveTo)) {
            if (currentTurn.equals("black")) {
                for (int i = 0; i < playerBlack.getPlayerPieces().size(); i++) {
                    if (playerBlack.getPlayerPieces().get(i).isSelected()) {
                        // remove from current tile
                        checkersBoard.getBoard()[playerBlack.getPlayerPieces().get(i).getRowPos()][playerBlack.getPlayerPieces().get(i).getColPos()].setPiece(null);
                        // get and set new positions for piece
                        playerBlack.getPlayerPieces().get(i).setRowPos(tileToMoveTo.getRow());
                        playerBlack.getPlayerPieces().get(i).setColPos(tileToMoveTo.getCol());
                        // update the state of the tile
                        checkersBoard.getBoard()[tileToMoveTo.getRow()][tileToMoveTo.getCol()].setPiece(playerBlack.getPlayerPieces().get(i));
                        // check if the piece has reached its respective end of the board and can be crowned
                        crownPiece();
                        // the player piece is no longer selected
                        if (playerAction != playerAction.MakingJump) {
                            playerBlack.getPlayerPieces().get(i).setSelected(false);
                        }

                    }
                }
            }
            if (currentTurn.equals("white")) {
                for (int i = 0; i < playerWhite.getPlayerPieces().size(); i++) {
                    if (playerWhite.getPlayerPieces().get(i).isSelected()) {
                        checkersBoard.getBoard()[playerWhite.getPlayerPieces().get(i).getRowPos()][playerWhite.getPlayerPieces().get(i).getColPos()].setPiece(null);
                        playerWhite.getPlayerPieces().get(i).setRowPos(tileToMoveTo.getRow());
                        playerWhite.getPlayerPieces().get(i).setColPos(tileToMoveTo.getCol());
                        checkersBoard.getBoard()[tileToMoveTo.getRow()][tileToMoveTo.getCol()].setPiece(playerWhite.getPlayerPieces().get(i));
                        crownPiece();
                        if (playerAction != playerAction.MakingJump) {
                            playerWhite.getPlayerPieces().get(i).setSelected(false);
                        }

                    }
                }
            }
            // unhighlight tiles
            ArrayList<Tile> currentlyHighlighted = getHighlightedTiles();
            for (Tile tile : currentlyHighlighted) {
                tile.setHighlighted(false);
            }
            if (playerAction != playerAction.MakingJump) {
                playerAction = PlayerAction.SelectingPiece; // reset the game state
                changeCurrentPlayersTurn(); // end the turn
            }
            return true;
        }
        return false;
    }

    /**
     * Takes a tile to jump to, moves the player piece to that tile and deletes the piece of the tiles it has jumped over
     *
     * @param tileToJumpTo
     */
    public boolean makeJump(Tile tileToJumpTo) {
        if (getHighlightedTiles().contains(tileToJumpTo)) {
            PlayerPiece selectedPiece = getSelectedPiece();
            Tile tileOfPieceToDelete = null;
            ArrayList<Pair<Tile, Tile>> tileToDeleteAndJumpToPairs = findTilesThatCanBeJumpedTo(selectedPiece);
            for (int i = 0; i < tileToDeleteAndJumpToPairs.size(); i++) {
                if (tileToDeleteAndJumpToPairs.get(i).getValue().equals(tileToJumpTo)) {
                    tileOfPieceToDelete = tileToDeleteAndJumpToPairs.get(i).getKey();
                }
            }
            boolean regicide = false;
            if (tileOfPieceToDelete != null) {
                if (tileOfPieceToDelete.getPiece().isKing() && !getSelectedPiece().isKing()) { // if a king is being jumped over
                    getSelectedPiece().setKing(true); // regicide
                    regicide = true;
                }
                if (currentTurn.equals("black")) {
                    playerWhite.getPlayerPieces().remove(tileOfPieceToDelete.getPiece());
                } else {
                    playerBlack.getPlayerPieces().remove(tileOfPieceToDelete.getPiece());
                }
                checkersBoard.getBoard()[tileOfPieceToDelete.getRow()][tileOfPieceToDelete.getCol()].setPiece(null);
                movePiece(tileToJumpTo);
            }
            if (getSelectedPiece().isKing() && regicide) { // the turn must end if regicide has occurred
                selectedPiece.setSelected(false);
                playerAction = PlayerAction.SelectingPiece; // reset the game state
                changeCurrentPlayersTurn(); // end the turn
                return true;
            }
            // MULTI-LEG JUMPS
            setLegalOptionsForTurn(selectedPiece); // set the legal options the piece can make in its new position
            ArrayList<Pair<Tile, Tile>> multiLegJumps = findTilesThatCanBeJumpedTo(selectedPiece); // find if any other jumps can be made, with this new legal options
            if (multiLegJumps.size() > 0) { // if it can call select piece again so one of the possible jumps can be made
                int row = selectedPiece.getRowPos();
                int col = selectedPiece.getColPos();
                selectPiece(checkersBoard.getBoard()[row][col]);
            } else { // if no more jumps can be made end the turn
                selectedPiece.setSelected(false);
                playerAction = PlayerAction.SelectingPiece; // reset the game state
                changeCurrentPlayersTurn(); // end the turn
            }
            return true;
        }
        return false;
    }

    // checks if a piece has reach its respective end of the board, and if it has, crown the piece
    private void crownPiece() {
        if (currentTurn.equals("white") && getSelectedPiece().getRowPos() == 7) {
            getSelectedPiece().setKing(true);
        } else if (currentTurn.equals("black") && getSelectedPiece().getRowPos() == 0) {
            getSelectedPiece().setKing(true);
        }
    }

    // helper method to obtain the piece currently selected
    private PlayerPiece getSelectedPiece() {
        PlayerPiece selectedPiece = null;
        if (currentTurn.equals("black")) {
            for (int i = 0; i < playerBlack.getPlayerPieces().size(); i++) {
                if (playerBlack.getPlayerPieces().get(i).isSelected()) {
                    selectedPiece = playerBlack.getPlayerPieces().get(i);
                }
            }
        } else {
            for (int i = 0; i < playerWhite.getPlayerPieces().size(); i++) {
                if (playerWhite.getPlayerPieces().get(i).isSelected()) {
                    selectedPiece = playerWhite.getPlayerPieces().get(i);
                }
            }
        }
        return selectedPiece;
    }

    /**
     * Changes the turn to the other player
     */
    private void changeCurrentPlayersTurn() {
        if (currentTurn.equals("black")) {
            currentTurn = "white";
            for (int i = 0; i < playerWhite.getPlayerPieces().size(); i++) {
                setLegalOptionsForTurn(playerWhite.getPlayerPieces().get(i));
            }
        } else {
            currentTurn = "black";
            for (int i = 0; i < playerBlack.getPlayerPieces().size(); i++) {
                setLegalOptionsForTurn(playerBlack.getPlayerPieces().get(i));
            }
        }
        setLegalMovesToFalseIfJumpsCanBeMade();

    }

    // helper method to set legal moves to false for all pieces if a jump has to be made
    private void setLegalMovesToFalseIfJumpsCanBeMade() {
        if (currentTurn.equals("black")) {
            for (int i = 0; i < playerBlack.getPlayerPieces().size(); i++) {
                if (playerBlack.getPlayerPieces().get(i).getCanMakeLegalJump()) {
                    for (int j = 0; j < playerBlack.getPlayerPieces().size(); j++) {
                        playerBlack.getPlayerPieces().get(j).setCanMakeLegalMove(false);
                    }
                }
            }
        } else {
            for (int i = 0; i < playerWhite.getPlayerPieces().size(); i++) {
                if (playerWhite.getPlayerPieces().get(i).getCanMakeLegalJump()) {
                    for (int j = 0; j < playerWhite.getPlayerPieces().size(); j++) {
                        playerWhite.getPlayerPieces().get(j).setCanMakeLegalMove(false);
                    }
                }
            }
        }
    }

    /**
     * Takes a player piece and sets its 'canMakeLegalMove' and 'canMakeLegalJump' attribute to the right value
     * for the current state of the game
     *
     * @param playerPiece
     * @return
     */
    private void setLegalOptionsForTurn(PlayerPiece playerPiece) {
        ArrayList<Tile> tilesThatCanBeMovedTo = findTilesThatCanBeMovedTo(playerPiece);
        ArrayList<Pair<Tile, Tile>> tilesThatCanBeJumpedTo = findTilesThatCanBeJumpedTo(playerPiece);
        playerPiece.setCanMakeLegalMove(tilesThatCanBeMovedTo.size() != 0);
        playerPiece.setCanMakeLegalJump(tilesThatCanBeJumpedTo.size() != 0);
        if (tilesThatCanBeMovedTo.size() > 0 && tilesThatCanBeJumpedTo.size() != 0) {
            playerPiece.setCanMakeLegalMove(false);
        }
    }

    // helper method to obtain the legal options a piece has during a turn
    private ArrayList<Tile> getLegalOptionsForTurn(PlayerPiece playerPiece) {
        ArrayList<Tile> legalTiles = new ArrayList<>();
        if (playerPiece.getCanMakeLegalMove()) {
            ArrayList<Tile> moves = findTilesThatCanBeMovedTo(playerPiece);
            for (int i = 0; i < moves.size(); i++) {
                legalTiles.add(moves.get(i));
            }
        } else if (playerPiece.getCanMakeLegalJump()) {
            ArrayList<Pair<Tile, Tile>> jumps = findTilesThatCanBeJumpedTo(playerPiece);
            for (int i = 0; i < jumps.size(); i++) {
                legalTiles.add(jumps.get(i).getValue());
            }
        }
        return legalTiles;
    }

    /**
     * Checks the states of the neighbour tiles of a player piece, if the tile
     * is dark brown, has no current player piece on it (is not occupied),
     * and is not behind the player piece, it is added to a list of tiles that piece can move to
     *
     * @param playerPiece
     * @return an array list of the tiles that the player piece can move to
     */
    public ArrayList<Tile> findTilesThatCanBeMovedTo(PlayerPiece playerPiece) {
        ArrayList<Tile> tilesThatCanBeMovedTo = new ArrayList<>();
        ArrayList<Point> neighbourCoOrdinates = getNeighbours(playerPiece);
        for (int row = 0; row < neighbourCoOrdinates.size(); row++) {
            for (int col = 0; col < neighbourCoOrdinates.size(); col++) {
                if (checkersBoard.getBoard()[neighbourCoOrdinates.get(row).x][neighbourCoOrdinates.get(col).y].isDarkBrown()
                        && checkersBoard.getBoard()[neighbourCoOrdinates.get(row).x][neighbourCoOrdinates.get(col).y].getPiece() == null
                        && !tilesThatCanBeMovedTo.contains(checkersBoard.getBoard()[neighbourCoOrdinates.get(row).x][neighbourCoOrdinates.get(col).y])) {
                    if ((playerPiece.getPlayerColour().equals("black") || playerPiece.isKing()) && neighbourCoOrdinates.get(row).x == playerPiece.getRowPos() - 1) {
                        tilesThatCanBeMovedTo.add(checkersBoard.getBoard()[neighbourCoOrdinates.get(row).x][neighbourCoOrdinates.get(col).y]);
                    } else if ((playerPiece.getPlayerColour().equals("white") || playerPiece.isKing()) && neighbourCoOrdinates.get(row).x == playerPiece.getRowPos() + 1) {
                        tilesThatCanBeMovedTo.add(checkersBoard.getBoard()[neighbourCoOrdinates.get(row).x][neighbourCoOrdinates.get(col).y]);
                    }
                }
            }
        }
        return tilesThatCanBeMovedTo;
    }

    /**
     * Checks the state of the neighbour tiles of a player piece, if that tile is dark brown,
     * has a current player piece on it of the opposing colour, and the dark brown tiles behind that player piece
     * are not occupied and is not out of bounds, the tile behind that piece is taken as a tile to jump to,
     * and the tile the piece occupies is taken as the tile of a piece to delete. They are added to a list as a pair
     *
     * @param playerPiece
     */
    private ArrayList<Pair<Tile, Tile>> findTilesThatCanBeJumpedTo(PlayerPiece playerPiece) {
        if (playerPiece.isKing() && playerPiece.getPlayerColour().equals("black")) {
            return findTilesThatCanBeJumpedToKings(playerPiece, "black", "white");
        } else if (playerPiece.isKing() && playerPiece.getPlayerColour().equals("white")) {
            return findTilesThatCanBeJumpedToKings(playerPiece, "white", "black");
        } else {
            ArrayList<Pair<Tile, Tile>> listOfDeleteTileAndJumpTilePairs = new ArrayList<>();
            ArrayList<Point> neighbourCoOrdinates = getNeighbours(playerPiece);
            for (int row = 0; row < neighbourCoOrdinates.size(); row++) {
                for (int col = 0; col < neighbourCoOrdinates.size(); col++) {
                    Tile tileToDelete = checkersBoard.getBoard()[neighbourCoOrdinates.get(row).x][neighbourCoOrdinates.get(col).y];
                    // UNIVERSAL CASES FOR BOTH BLACK AND WHITE
                    if (tileToDelete.isDarkBrown() && tileToDelete.getPiece() != null) {
                        Tile tileToJumpTo;
                        int tileToJumpToCol;
                        // SPECIFIC CASES FOR BLACK PIECES
                        if (playerPiece.getPlayerColour().equals("black") && tileToDelete.getRow() == playerPiece.getRowPos() - 1 && tileToDelete.getPiece().getPlayerColour().equals("white")) {
                            int tileToJumpToRow = tileToDelete.getRow() - 1;
                            if (playerPiece.getColPos() < tileToDelete.getCol()) {
                                tileToJumpToCol = tileToDelete.getCol() + 1;
                            } else {
                                tileToJumpToCol = tileToDelete.getCol() - 1;
                            }
                            if (inBounds(tileToJumpToRow, tileToJumpToCol)) {
                                tileToJumpTo = checkersBoard.getBoard()[tileToJumpToRow][tileToJumpToCol];
                                if (tileToJumpTo.getPiece() == null) {
                                    listOfDeleteTileAndJumpTilePairs.add(new Pair<>(tileToDelete, tileToJumpTo));
                                }
                            }
                        }
                        // SPECIFIC CASES FOR WHITE PLAYERS
                        else if (playerPiece.getPlayerColour().equals("white") && neighbourCoOrdinates.get(row).x == playerPiece.getRowPos() + 1 && tileToDelete.getPiece().getPlayerColour().equals("black")) {
                            int tileToJumpToRow = tileToDelete.getRow() + 1;
                            if (playerPiece.getColPos() < tileToDelete.getCol()) {
                                tileToJumpToCol = tileToDelete.getCol() + 1;
                            } else {
                                tileToJumpToCol = tileToDelete.getCol() - 1;
                            }
                            if (inBounds(tileToJumpToRow, tileToJumpToCol)) {
                                tileToJumpTo = checkersBoard.getBoard()[tileToJumpToRow][tileToJumpToCol];
                                if (tileToJumpTo.getPiece() == null) {
                                    listOfDeleteTileAndJumpTilePairs.add(new Pair<>(tileToDelete, tileToJumpTo));
                                }
                            }
                        }
                    }
                }
            }
            return listOfDeleteTileAndJumpTilePairs;
        }
    }

    // The same method as above but for kings, that takes into account kings can jump in both directions
    private ArrayList<Pair<Tile, Tile>> findTilesThatCanBeJumpedToKings(PlayerPiece playerPiece, String kingColour, String enemyColour) {
        ArrayList<Pair<Tile, Tile>> listOfDeleteTileAndJumpTilePairs = new ArrayList<>();
        ArrayList<Point> neighbourCoOrdinates = getNeighbours(playerPiece);
        for (int row = 0; row < neighbourCoOrdinates.size(); row++) {
            for (int col = 0; col < neighbourCoOrdinates.size(); col++) {
                Tile tileToDelete = checkersBoard.getBoard()[neighbourCoOrdinates.get(row).x][neighbourCoOrdinates.get(col).y];
                if (tileToDelete.isDarkBrown() && tileToDelete.getPiece() != null) {
                    Tile tileToJumpTo;
                    int tileToJumpToCol;
                    if (playerPiece.getPlayerColour().equals(kingColour) && tileToDelete.getRow() == playerPiece.getRowPos() - 1 && tileToDelete.getPiece().getPlayerColour().equals(enemyColour)) {
                        int tileToJumpToRow = tileToDelete.getRow() - 1;
                        if (playerPiece.getColPos() < tileToDelete.getCol()) {
                            tileToJumpToCol = tileToDelete.getCol() + 1;
                        } else {
                            tileToJumpToCol = tileToDelete.getCol() - 1;
                        }
                        if (inBounds(tileToJumpToRow, tileToJumpToCol)) {
                            tileToJumpTo = checkersBoard.getBoard()[tileToJumpToRow][tileToJumpToCol];
                            if (tileToJumpTo.getPiece() == null) {
                                listOfDeleteTileAndJumpTilePairs.add(new Pair<>(tileToDelete, tileToJumpTo));
                            }
                        }
                    } else if (playerPiece.getPlayerColour().equals(kingColour) && neighbourCoOrdinates.get(row).x == playerPiece.getRowPos() + 1 && tileToDelete.getPiece().getPlayerColour().equals(enemyColour)) {
                        int tileToJumpToRow = tileToDelete.getRow() + 1;
                        if (playerPiece.getColPos() < tileToDelete.getCol()) {
                            tileToJumpToCol = tileToDelete.getCol() + 1;
                        } else {
                            tileToJumpToCol = tileToDelete.getCol() - 1;
                        }
                        if (inBounds(tileToJumpToRow, tileToJumpToCol)) {
                            tileToJumpTo = checkersBoard.getBoard()[tileToJumpToRow][tileToJumpToCol];
                            if (tileToJumpTo.getPiece() == null) {
                                listOfDeleteTileAndJumpTilePairs.add(new Pair<>(tileToDelete, tileToJumpTo));
                            }
                        }
                    }
                }
            }
        }
        return listOfDeleteTileAndJumpTilePairs;
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

    /**
     * Returns a score for the state of a Checkers object that can be used by Minimax,
     * the score is depdent on the number of pieces and number of crowned pieces for both
     * players
     *
     * @return an integer representing a score for a Checkers object
     */
    private int evaluate() {
        int evaluation = 0;
        for (int i = 0; i < checkersBoard.getRows(); i++) {
            for (int j = 0; j < checkersBoard.getCols(); j++) {
                if (checkersBoard.getBoard()[i][j].getPiece() != null) {
                    PlayerPiece currPiece = checkersBoard.getBoard()[i][j].getPiece();
                    if (currPiece.getPlayerColour().equals("white")) {
                        evaluation += 10;
                        if (currPiece.isKing()) {
                            evaluation += 20;
                        }
                    } else if (currPiece.getPlayerColour().equals("black")) {
                        evaluation -= 10;
                        if (currPiece.isKing()) {
                            evaluation -= 20;
                        }
                    }
                }
            }
        }
        return evaluation;
    }


    /**
     * Iterates through the players pieces of the player for the current turn, finds all the tiles a piece can move to (through a jump or regular move),
     * selects that piece and then simulates either a jump or move being made
     *
     * @return possibleMoves, An array list of all the possible outcomes of the moves being made
     */
    private ArrayList<Checkers> getAllMoves(Checkers checkers) {
        ArrayList<Checkers> possibleMoves = new ArrayList<>();
        if (currentTurn.equals("white")) {
            for (int i = 0; i < playerWhite.getPlayerPieces().size(); i++) {
                PlayerPiece currPiece = playerWhite.getPlayerPieces().get(i);
                ArrayList<Tile> legalOptions = getLegalOptionsForTurn(currPiece);
                for (int j = 0; j < legalOptions.size(); j++) {
                    Checkers checkersCopy = deepClone(checkers);
                    int row = playerWhite.getPlayerPieces().get(i).getRowPos();
                    int col = playerWhite.getPlayerPieces().get(i).getColPos();
                    Tile tileCopy = checkersCopy.getCheckersBoard().getBoard()[legalOptions.get(j).getRow()][legalOptions.get(j).getCol()];
                    Tile playerTile = checkersCopy.getCheckersBoard().getBoard()[row][col];
                    possibleMoves.add(simulateMove(playerTile, tileCopy, checkersCopy));
                }
            }
        } else if (currentTurn.equals("black")) {
            for (int i = 0; i < playerBlack.getPlayerPieces().size(); i++) {
                PlayerPiece currPiece = playerBlack.getPlayerPieces().get(i);
                ArrayList<Tile> legalOptions = getLegalOptionsForTurn(currPiece);
                for (int j = 0; j < legalOptions.size(); j++) {
                    Checkers checkersCopy = deepClone(checkers);
                    int row = playerBlack.getPlayerPieces().get(i).getRowPos();
                    int col = playerBlack.getPlayerPieces().get(i).getColPos();
                    Tile tileCopy = checkersCopy.getCheckersBoard().getBoard()[legalOptions.get(j).getRow()][legalOptions.get(j).getCol()];
                    Tile playerTile = checkersCopy.getCheckersBoard().getBoard()[row][col];
                    possibleMoves.add(simulateMove(playerTile, tileCopy, checkersCopy));
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Carries out and returns the state of a checkers object for a move that can be made
     * for a turn
     *
     * @param playerTileCopy
     * @param tileCopy
     * @param checkersCopy
     * @return
     */
    private Checkers simulateMove(Tile playerTileCopy, Tile tileCopy, Checkers checkersCopy) {
        checkersCopy.selectPiece(playerTileCopy);
        if (checkersCopy.getPlayerAction() == PlayerAction.SelectingTileToMoveTo) {
            checkersCopy.movePiece(tileCopy);
        } else if (checkersCopy.getPlayerAction() == PlayerAction.MakingJump) {
            checkersCopy.makeJump(tileCopy);
        }
        return checkersCopy;
    }

    /**
     * The minimax method with alpha-beta pruning, used to get moves for the AI player
     *
     * @param checkers, a checkers object
     * @param depth,    the depth of the search tree
     * @param maxPlayer true if the turn of the maximising player, false otherwise
     * @param alpha
     * @param beta
     * @return
     */
    private Pair<Integer, Checkers> miniMax(Checkers checkers, int depth, boolean maxPlayer, int alpha, int beta) {
        if (depth == 0 || checkers.checkIfGameIsOver()) {
            return new Pair<>(checkers.evaluate(), checkers);
        }
        if (maxPlayer) {
            int maxEval = Integer.MIN_VALUE;
            Checkers bestMove = null;
            ArrayList<Checkers> moves = getAllMoves(checkers);
            for (int i = 0; i < moves.size(); i++) {
                int evaluation = miniMax(deepClone(moves.get(i)), depth - 1, false, alpha, beta).getKey();
                maxEval = Math.max(maxEval, evaluation);
                alpha = Math.max(alpha, evaluation);
                if (maxEval == evaluation) {
                    bestMove = moves.get(i);
                }
                if (alpha >= beta) {
                    break;
                }
            }
            return new Pair<>(maxEval, bestMove);
        } else {
            int minEval = Integer.MAX_VALUE;
            Checkers bestMove = null;
            ArrayList<Checkers> moves = getAllMoves(checkers);
            for (int i = 0; i < moves.size(); i++) {
                int evaluation = miniMax(deepClone(moves.get(i)), depth - 1, true, alpha, beta).getKey();
                minEval = Math.min(minEval, evaluation);
                beta = Math.min(beta, evaluation);
                if (minEval == evaluation) {
                    bestMove = moves.get(i);
                }
                if (alpha >= beta) {
                    break;
                }
            }
            return new Pair<>(minEval, bestMove);
        }
    }

    /**
     * Checks if the game is over, if not calls minimax to return a new Checkers object in order to make a move
     * for the AI player
     */
    public Checkers aiMove(boolean maxPlayer) {
        if (checkIfGameIsOver()) {
            return this;
        }
        Checkers newCheckers = miniMax(deepClone(this), difficulty, maxPlayer, Integer.MIN_VALUE, Integer.MAX_VALUE).getValue();
        return newCheckers;
    }

    /**
     * Returns true if the game is over, returns false otherwise, does so by calling possibleMoves(), if possibleMoves is empty
     * the game must be over and true is returned
     *
     * @return
     */
    public boolean checkIfGameIsOver() {
        ArrayList<Checkers> possibleMoves = getAllMoves(this);
        if (possibleMoves.size() == 0) {
            this.gameOver = true;
            return true;
        }
        return false;
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

    public boolean isGameOver() {
        return gameOver;
    }

    public PlayerAction getPlayerAction() {
        return playerAction;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
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

    @SuppressWarnings("unchecked")
    /**
     * Helper method to create deep copies/clones in Java,
     * I did not write this method I found it here: https://stackoverflow.com/questions/64036/how-do-you-make-a-deep-copy-of-an-object
     * it's the answer from user "Unmitigated"
     */
    public static <T extends Serializable> T deepClone(T t) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);) {
            oos.writeObject(t);
            byte[] bytes = baos.toByteArray();
            try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
                return (T) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}