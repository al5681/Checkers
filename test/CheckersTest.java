import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CheckersTest {

    private Checkers checkers = new Checkers();


    @Test
    public void testWhiteSpawnLocations() {
        // piece 1
        assertEquals(0, checkers.getPlayerWhite().getPlayerPieces().get(0).getRowPos());
        assertEquals(1, checkers.getPlayerWhite().getPlayerPieces().get(0).getColPos());

        // piece 2
        assertEquals(0, checkers.getPlayerWhite().getPlayerPieces().get(1).getRowPos());
        assertEquals(3, checkers.getPlayerWhite().getPlayerPieces().get(1).getColPos());

        // piece 3
        assertEquals(0, checkers.getPlayerWhite().getPlayerPieces().get(2).getRowPos());
        assertEquals(5, checkers.getPlayerWhite().getPlayerPieces().get(2).getColPos());

        // piece 4
        assertEquals(0, checkers.getPlayerWhite().getPlayerPieces().get(2).getRowPos());
        assertEquals(5, checkers.getPlayerWhite().getPlayerPieces().get(2).getColPos());

        // piece 5
        assertEquals(1, checkers.getPlayerWhite().getPlayerPieces().get(4).getRowPos());
        assertEquals(0, checkers.getPlayerWhite().getPlayerPieces().get(4).getColPos());

        // piece 6
        assertEquals(1, checkers.getPlayerWhite().getPlayerPieces().get(5).getRowPos());
        assertEquals(2, checkers.getPlayerWhite().getPlayerPieces().get(5).getColPos());

        // piece 7
        assertEquals(1, checkers.getPlayerWhite().getPlayerPieces().get(6).getRowPos());
        assertEquals(4, checkers.getPlayerWhite().getPlayerPieces().get(6).getColPos());

        // piece 8
        assertEquals(1, checkers.getPlayerWhite().getPlayerPieces().get(7).getRowPos());
        assertEquals(6, checkers.getPlayerWhite().getPlayerPieces().get(7).getColPos());

        // piece 9
        assertEquals(2, checkers.getPlayerWhite().getPlayerPieces().get(8).getRowPos());
        assertEquals(1, checkers.getPlayerWhite().getPlayerPieces().get(8).getColPos());

        // piece 10
        assertEquals(2, checkers.getPlayerWhite().getPlayerPieces().get(9).getRowPos());
        assertEquals(3, checkers.getPlayerWhite().getPlayerPieces().get(9).getColPos());

        // piece 11
        assertEquals(2, checkers.getPlayerWhite().getPlayerPieces().get(10).getRowPos());
        assertEquals(5, checkers.getPlayerWhite().getPlayerPieces().get(10).getColPos());

        // piece 12
        assertEquals(2, checkers.getPlayerWhite().getPlayerPieces().get(11).getRowPos());
        assertEquals(7, checkers.getPlayerWhite().getPlayerPieces().get(11).getColPos());
    }

    @Test
    public void printBoard() {
        checkers.getCheckersBoard().printBoard();
    }

    // based on the initial spawn points of the tiles
    @Test
    public void neighbourTilesTestBlack() {
        ArrayList<Point> neighbourCoOrdinates = new ArrayList<>();
        for (int i = 0; i < checkers.getPlayerBlack().getPlayerPieces().size(); i++) {
            neighbourCoOrdinates = checkers.getNeighbours(checkers.getPlayerBlack().getPlayerPieces().get(i));
            System.out.println(neighbourCoOrdinates); // print the co-ordinates out for inspection
        }
        ArrayList<Point> lastPieceNeighbours = new ArrayList<>();
        lastPieceNeighbours.add(new Point(6, 5));
        lastPieceNeighbours.add(new Point(6, 6));
        lastPieceNeighbours.add(new Point(6, 7));
        lastPieceNeighbours.add(new Point(7, 5));
        lastPieceNeighbours.add(new Point(7, 7));

        assertEquals(lastPieceNeighbours, neighbourCoOrdinates); // explicitly checks the coordinates of the neighbours for the last piece,
        // if this fails something has broken with spawns
    }

    @Test
    public void neighbourTilesTestWhite() {
        ArrayList<Point> neighbourCoOrdinates = new ArrayList<>();
        for (int i = 0; i < checkers.getPlayerBlack().getPlayerPieces().size(); i++) {
            neighbourCoOrdinates = checkers.getNeighbours(checkers.getPlayerWhite().getPlayerPieces().get(i));
            System.out.println(neighbourCoOrdinates); // print the co-ordinates out for inspection
        }
        ArrayList<Point> lastPieceNeighbours = new ArrayList<>();
        lastPieceNeighbours.add(new Point(1, 6));
        lastPieceNeighbours.add(new Point(1, 7));
        lastPieceNeighbours.add(new Point(2, 6));
        lastPieceNeighbours.add(new Point(3, 6));
        lastPieceNeighbours.add(new Point(3, 7));

        assertEquals(lastPieceNeighbours, neighbourCoOrdinates); // explicitly checks the coordinates of the neighbours for the last piece,
        // if this fails something has broken with spawns
    }

    @Test
    public void tilesToMoveToTestBlack() {
        ArrayList<ArrayList<Tile>> legalTiles = new ArrayList<>();
        for (int i = 0; i < checkers.getPlayerBlack().getPlayerPieces().size(); i++) {
            legalTiles.add(checkers.findTilesThatCanBeMovedTo(checkers.getPlayerBlack().getPlayerPieces().get(i)));
        }
        // the first four pieces should have tiles to move to
        for (int i = 0; i < 4; i++) {
            assertEquals(true, legalTiles.get(i).size() != 0);
        }
        // the remaining pieces should have no tiles to move to
        for (int i = 4; i < 12; i++) {
            assertEquals(false, legalTiles.get(i).size() != 0);
        }
    }

    @Test
    public void tilesToMoveToTestWhite() {
        ArrayList<ArrayList<Tile>> legalTiles = new ArrayList<>();
        for (int i = 0; i < checkers.getPlayerWhite().getPlayerPieces().size(); i++) {
            legalTiles.add(checkers.findTilesThatCanBeMovedTo(checkers.getPlayerWhite().getPlayerPieces().get(i)));
        }
        // the first 8 pieces should have no moves to make
        for (int i = 0; i < 8; i++) {
            assertEquals(false, legalTiles.get(i).size() != 0);
        }
        // the remaining pieces should be able to move
        for (int i = 8; i < 12; i++) {
            assertEquals(true, legalTiles.get(i).size() != 0);
        }
    }

    @Test
    public void randomMovesTest() {
        while (!checkers.checkIfGameIsOver()) {
            checkers = checkers.randomPlayerMove();
        }
    }

    @Test
    public void randomTestABunch() {
        for (int i = 0; i < 100; i++) {
            Checkers newCheckers = new Checkers();
            while (!newCheckers.isGameOver()) {
                newCheckers = newCheckers.randomPlayerMove();
            }
        }
    }
}
