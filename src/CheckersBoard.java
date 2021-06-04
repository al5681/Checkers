public class CheckersBoard {

    private Player playerBlack;
    private Player playerwhite;
    private Tile[][] board;

    // creates an 8x8 board, every other tile is usable (dark brown)
    public CheckersBoard()
    {
        for(int row = 1; row < board.length+1; row++)
        {
            for(int col = 1; col < board[row].length+1; col++)
            {
                if(row % 2 == 0)
                {
                    board[row][col] = new Tile(false);
                }
                else {
                    board[row][col] = new Tile(true);
                }
            }
        }
    }
}
