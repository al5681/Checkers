import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CheckersTest {

    private Checkers checkers = new Checkers();


    @Test
    public void printBoard() {
        checkers.getBoard().printBoard();
    }
}
