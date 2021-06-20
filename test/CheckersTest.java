import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
}
