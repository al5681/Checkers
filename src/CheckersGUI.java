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

import java.util.ArrayList;

/**
 * Represents the GUI for the game, which displays the current state of the Checkers class
 */
public class CheckersGUI extends Application {

    private Checkers checkers = new Checkers();
    private BorderPane boarderPane = new BorderPane();
    private GridPane grid = new GridPane();
    private Button[][] buttonsInGrid = new Button[checkers.getCheckersBoard().getRows()][checkers.getCheckersBoard().getCols()];
    private ArrayList<Tile> tilesToCurrentlyHighlight = new ArrayList<>(); // this should be in checkers needs to be refactored

    /**
     * renders each of the tiles of the board as buttons of a grid pane
     *
     * @return grid
     */
    public GridPane renderBoard() {
        for (int i = 0; i < checkers.getCheckersBoard().getRows(); i++) {
            for (int j = 0; j < checkers.getCheckersBoard().getCols(); j++) {
                Tile currTile = checkers.getCheckersBoard().getBoard()[i][j];
                Button currButton = new Button();
                if (tilesToCurrentlyHighlight.contains(currTile)) {
                    currButton.setStyle("-fx-background-color:#009900; -fx-background-radius: 0");
                } else if (currTile.isDarkBrown()) {
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

public void updateBoardRender() {
        for(int i = 0; i < buttonsInGrid.length; i++) {
            for(int j = 0; j < buttonsInGrid.length; j++) {
                Tile currTile = checkers.getCheckersBoard().getBoard()[i][j];
                if (tilesToCurrentlyHighlight.contains(currTile)) {
                    buttonsInGrid[i][j].setStyle("-fx-background-color:#009900; -fx-background-radius: 0");
                } else if (currTile.isDarkBrown()) {
                    buttonsInGrid[i][j].setStyle("-fx-background-color:#96652c; -fx-background-radius: 0");
                } else {
                    buttonsInGrid[i][j].setStyle("-fx-background-color:#e6c9aa; -fx-background-radius: 0");
                }
            }
        }
}

    public void updateBoardRenderNoHightLights() {
        for(int i = 0; i < buttonsInGrid.length; i++) {
            for(int j = 0; j < buttonsInGrid.length; j++) {
                Tile currTile = checkers.getCheckersBoard().getBoard()[i][j];
                if (currTile.isDarkBrown()) {
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
                        if (currTile.getPiece().getCanMakeLegalMove() && checkers.getCurrentTurn().equals("black") && checkers.getGameState() == GameState.SelectingPiece) {
                            circle.setStroke(Color.GOLD);
                            circle.setStrokeWidth(5.00);
                        }
                    } else {
                        circle.setFill(javafx.scene.paint.Color.WHITE);
                        if (currTile.getPiece().getCanMakeLegalMove() && checkers.getCurrentTurn().equals("white") && checkers.getGameState() == GameState.SelectingPiece) {
                            circle.setStroke(Color.GOLD);
                            circle.setStrokeWidth(5.00);
                        }
                    }
                    if (currTile.getPiece().isSelected() && checkers.getGameState() == GameState.SelectingTileToMoveTo) {
                        circle.setStroke(Color.GOLD);
                        circle.setStrokeWidth(5.00);
                    }
                    buttonsInGrid[i][j].setGraphic(circle);
                } else if (currTile.getPiece() == null) {
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
        renderBoard();
        renderPieces();
        refreshPlayerTurnDisplay(); // initialise the current player turn display
        // game loop
        for (int i = 0; i < checkers.getCheckersBoard().getRows(); i++) {
            for (int j = 0; j < checkers.getCheckersBoard().getCols(); j++) {
                int currenti = i;
                int currentj = j;
                buttonsInGrid[i][j].setOnMouseClicked(e -> {
                    Tile currTile = checkers.getCheckersBoard().getBoard()[currenti][currentj];
                    if (currTile.getPiece() != null && checkers.getGameState() == GameState.SelectingPiece) {
                        tilesToCurrentlyHighlight = checkers.takeTurn(currTile.getPiece());
                        updateBoardRender();
                    } else if (checkers.getGameState() == GameState.SelectingTileToMoveTo) {
                        updateBoardRenderNoHightLights();
                        checkers.takeTurn(currTile, tilesToCurrentlyHighlight);
                        tilesToCurrentlyHighlight = null;
                    }
                    renderPieces(); // render the pieces in their new position
                    refreshPlayerTurnDisplay();
                });
            }
        }
        primaryStage.setScene(new Scene(boarderPane));
        primaryStage.show();
    }

}