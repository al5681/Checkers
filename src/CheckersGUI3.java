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
public class CheckersGUI3 extends Application {

    private Checkers checkers = new Checkers();
    private BorderPane boarderPane = new BorderPane();
    private GridPane grid = new GridPane();
    private Button[][] buttonsInGrid = new Button[checkers.getCheckersBoard().getRows()][checkers.getCheckersBoard().getCols()];

    /**
     * renders each of the tiles of the board as buttons of a grid pane
     */
    public void renderBoard() {
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
    }

    /**
     * Updates the board to show the current state of the game
     */
    public void updateBoardRender() {
        for (int i = 0; i < buttonsInGrid.length; i++) {
            for (int j = 0; j < buttonsInGrid.length; j++) {
                Tile currTile = checkers.getCheckersBoard().getBoard()[i][j];
                if (checkers.getHighlightedTiles().contains(currTile)) {
                    buttonsInGrid[i][j].setStyle("-fx-background-color:#009900; -fx-background-radius: 0");
                } else if (currTile.isDarkBrown()) {
                    buttonsInGrid[i][j].setStyle("-fx-background-color:#96652c; -fx-background-radius: 0");
                } else {
                    buttonsInGrid[i][j].setStyle("-fx-background-color:#e6c9aa; -fx-background-radius: 0");
                }
            }
        }
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
                        if (!currTile.getPiece().getCanMakeLegalJump() && currTile.getPiece().getCanMakeLegalMove() && checkers.getCurrentTurn().equals("black") && checkers.getPlayerAction() == PlayerAction.SelectingPiece) {
                            circle.setStroke(Color.GOLD);
                            circle.setStrokeWidth(5.00);
                        } else if (currTile.getPiece().getCanMakeLegalJump() && checkers.getCurrentTurn().equals("black") && checkers.getPlayerAction() == PlayerAction.SelectingPiece) {
                            circle.setStroke(Color.GOLD);
                            circle.setStrokeWidth(5.00);
                        }
                    } else {
                        circle.setFill(javafx.scene.paint.Color.WHITE);
                        if (!currTile.getPiece().getCanMakeLegalJump() && currTile.getPiece().getCanMakeLegalMove() && checkers.getCurrentTurn().equals("white") && checkers.getPlayerAction() == PlayerAction.SelectingPiece) {
                            circle.setStroke(Color.GOLD);
                            circle.setStrokeWidth(5.00);
                        } else if (currTile.getPiece().getCanMakeLegalJump() && checkers.getCurrentTurn().equals("white") && checkers.getPlayerAction() == PlayerAction.SelectingPiece) {
                            circle.setStroke(Color.GOLD);
                            circle.setStrokeWidth(5.00);
                        }
                    }
                    if (currTile.getPiece().isSelected() && (checkers.getPlayerAction() == PlayerAction.SelectingTileToMoveTo || checkers.getPlayerAction() == PlayerAction.MakingJump)) {
                        circle.setStroke(Color.GOLD);
                        circle.setStrokeWidth(5.00);
                    }
                    buttonsInGrid[i][j].setGraphic(circle);
                } else {
                    buttonsInGrid[i][j].setGraphic(null);
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
        // // initialise the display
        renderBoard();
        renderPieces();
        refreshPlayerTurnDisplay();
        // call the game loop
        gameLoop();
        primaryStage.setScene(new Scene(boarderPane));
        primaryStage.show();
    }

    /**
     * Gets button clicks from the players and updates the state of the game accordingly each time
     */
    public void gameLoop() {
        for (int i = 0; i < checkers.getCheckersBoard().getRows(); i++) {
            for (int j = 0; j < checkers.getCheckersBoard().getCols(); j++) {
                int currentI = i;
                int currentJ = j;
                buttonsInGrid[i][j].setOnMouseClicked(e -> {
                    Tile currTile = checkers.getCheckersBoard().getBoard()[currentI][currentJ];
                    if (checkers.getPlayerAction() == PlayerAction.SelectingPiece) {
                        checkers.selectPiece(currTile);
                    } else if (checkers.getPlayerAction() == PlayerAction.SelectingTileToMoveTo) {
                        checkers.movePiece(currTile);
                    }
                    else if(checkers.getPlayerAction() == PlayerAction.MakingJump)
                    {
                        checkers.makeJump(currTile);
                    }
                    updateBoardRender();
                    renderPieces(); // render the pieces in their new position
                    refreshPlayerTurnDisplay();
                    System.out.println(checkers.getPlayerAction());
                });
            }
        }
    }
}