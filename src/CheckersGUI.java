import com.sun.xml.internal.ws.util.StringUtils;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Represents the GUI for the game, which displays the current state of the Checkers class
 */
public class CheckersGUI extends Application {

    private Checkers checkers = new Checkers();
    private BorderPane boarderPane = new BorderPane();
    private GridPane grid = new GridPane();
    private Button[][] buttonsInGrid = new Button[checkers.getCheckersBoard().getRows()][checkers.getCheckersBoard().getCols()];

    /**
     * renders each of the tiles of the board as items of a grid pane
     *
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
        boarderPane.setCenter(grid);
        return grid;
    }

    /**
     * Renders the pieces onto the board for the tiles that currently have pieces on them
     */
    public void renderPieces() {
        for (int i = 0; i < checkers.getCheckersBoard().getRows(); i++) {
            for (int j = 0; j < checkers.getCheckersBoard().getCols(); j++) {
                Tile currTile = checkers.getCheckersBoard().getBoard()[i][j];
                if (currTile.getPiece() != null) {
                    Circle circle = new Circle();
                    circle.setRadius(35.0f);
                    if (currTile.getPiece().getPlayerColour().equals("black")) {
                        circle.setFill(javafx.scene.paint.Color.BLACK);
                    } else {
                        circle.setFill(javafx.scene.paint.Color.WHITE);
                    }
                    buttonsInGrid[i][j].setGraphic(circle);
                }
            }
        }
    }

    /**
     * Display the current players turn at the top of the GUI
     */
    public void refreshPlayerTurnDisplay() {
        Text playerTurnText = new Text();
        playerTurnText.setFont(Font.font("Verdana", 20));
        playerTurnText.setText("Current players turn: " + StringUtils.capitalize(checkers.getCurrentTurn()));
        boarderPane.setTop(playerTurnText);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Checkers");
        boarderPane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY)));
        renderBoard();
        renderPieces();
        refreshPlayerTurnDisplay(); // initialise the current player turn display
        // game loop
        for (int i = 0; i < checkers.getCheckersBoard().getRows(); i++) {
            for (int j = 0; j < checkers.getCheckersBoard().getCols(); j++) {
                buttonsInGrid[i][j].setOnMouseClicked(e -> {
                    checkers.changeCurrentPlayersTurn();
                    refreshPlayerTurnDisplay();
                });
            }
        }
        primaryStage.setScene(new Scene(boarderPane));
        primaryStage.show();
    }
}