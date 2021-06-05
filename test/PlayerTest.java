import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

    private Player player = new Player("orange");

    @Test
    public void testAmountOfPlayerPieces()
    {
        assertEquals(12, player.getPlayerPieces().size());
    }

    @Test
    public void testCorrectName()
    {
        assertEquals("orange", player.getPlayerPieces().get(0).getPlayerColour());
    }


}
