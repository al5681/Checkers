import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * Represents the GUI for the game, which displays the current state of the Checkers class
 */
public class CheckersGUI extends Application {

    private Checkers checkers = new Checkers();
    private GridPane grid = new GridPane();
    private Button[][] buttonsInGrid  = new Button[checkers.getCheckersBoard().getRows()][ checkers.getCheckersBoard().getCols()];

    /**
     * renders each of the tiles of the board as items of a grid pane
     * @return grid
     */
    public GridPane renderBoard() {
        for (int i = 0; i < checkers.getCheckersBoard().getRows(); i++) {
            for (int j = 0; j < checkers.getCheckersBoard().getCols(); j++) {
                Tile currTile = checkers.getCheckersBoard().getBoard()[i][j];
                Button currButton = new Button();
                if (currTile.isDarkBrown()) {
                    currButton.setStyle("-fx-background-color:#96652c; -fx-background-radius: 0");
                } else {
                    currButton.setStyle("-fx-background-color:#e6c9aa; -fx-background-radius: 0");
                }
                currButton.setPrefHeight(100);
                currButton.setPrefWidth(100);
                GridPane.setRowIndex(currButton, i);
                GridPane.setColumnIndex(currButton, j);
                grid.getChildren().add(currButton);
                buttonsInGrid[i][j] = currButton;
            }
        }
        return grid;
    }

    /**
     * Renders the pieces onto the board for the tiles that currently have pieces on them
     */
    public void renderPieces()
    {
        for (int i = 0; i < checkers.getCheckersBoard().getRows(); i++) {
            for (int j = 0; j < checkers.getCheckersBoard().getCols(); j++) {
                Tile currTile = checkers.getCheckersBoard().getBoard()[i][j];
                if(currTile.getPiece() != null)
                {
                    Circle circle = new Circle();
                    circle.setRadius(35.0f);
                    if(currTile.getPiece().getPlayerColour() == "black") {
                        circle.setFill(javafx.scene.paint.Color.BLACK);
                    }
                    else {
                        circle.setFill(javafx.scene.paint.Color.WHITE);
                    }
                    buttonsInGrid[i][j].setGraphic(circle);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Checkers");
        renderBoard();
        renderPieces();
        primaryStage.setScene(new Scene(grid));
        primaryStage.show();
    }
}