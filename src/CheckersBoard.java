import java.io.Serializable;

/**
 * Represents the board for the game
 */
public class CheckersBoard implements Serializable {


    private Tile[][] board;
    private final int rows = 8;
    private final int cols = 8;


    /**
     * Creates an 8x8 board, tiles are usable according to the original
     * board layout (false - true... for odd rows, true-false... for even rows)
     */
    public CheckersBoard() {
        board = new Tile[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (row % 2 == 0 && col % 2 == 0) {
                    board[row][col] = new Tile(false, row, col);
                } else if (row % 2 != 0 && col % 2 != 0) {
                    board[row][col] = new Tile(false, row, col);
                } else {
                    board[row][col] = new Tile(true, row, col);
                }
            }
        }

    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Tile[][] getBoard() {
        return board;
    }

    // convenience method for printing the board
    public boolean printBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
        return false;
    }
}
