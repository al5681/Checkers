public class CheckersBoard {

    private final Player playerBlack= new Player("black");
    private final Player playerwhite= new Player("white");
    private Tile[][] board;
    final int rows = 8;
    final int cols = 8;

    // creates an 8x8 board, every other tile is usable (dark brown)
    public CheckersBoard()
    {

        board = new Tile[rows][cols];
        for(int row = 0; row < rows; row++)
        {
            for(int col = 0; col < cols; col++)
            {
                if(row % 2 == 0 && col % 2 == 0) {
                    board[row][col] = new Tile(false);
                }
                else if(row % 2 != 0 && col % 2 != 0)
                {
                    board[row][col] = new Tile(false);
                }
                else {
                    board[row][col] = new Tile(true);
                }
            }
        }

    }

    public Player getPlayerBlack() {
        return playerBlack;
    }


    public Player getPlayerwhite() {
        return playerwhite;
    }


    public Tile[][] getBoard() {
        return board;
    }

    public void setBoard(Tile[][] board) {
        this.board = board;
    }

    public void printBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }
}
