import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CheckersBoardTest {

    private CheckersBoard board = new CheckersBoard();

    @Test
    public void constructorTest() {
        Tile[][] boardGetter = board.getBoard();
        for (int row = 0; row < boardGetter.length; row++) {
            for (int col = 0; col < boardGetter[row].length; col++) {
                if (row % 2 == 0 && col % 2 == 0) {
                    assertEquals(false, board.getBoard()[row][col].isDarkBrown());
                }

            }
        }
    }

    @Test
    public void printBoard() {
        board.printBoard();
    }
}
