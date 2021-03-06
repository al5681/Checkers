import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;

/**
 * Represents the GUI for the game, which displays the current state of the Checkers class
 */
public class CheckersGUI extends Application {

    private Checkers checkers = new Checkers();
    private BorderPane borderPane = new BorderPane();
    BorderPane topOfGUI = new BorderPane();
    private GridPane grid = new GridPane();
    private Button[][] buttonsInGrid = new Button[checkers.getCheckersBoard().getRows()][checkers.getCheckersBoard().getCols()];
    private boolean hintsOn = true;

    /**
     * Creates the top part of the GUI, which includes the button to turn hints on and off,
     * the drop-down list to select difficulty, a button to display the rules, and a status message
     */
    public void renderTopOfDisplay() {
        BorderPane localBorderPane = new BorderPane();
        HBox hbox = new HBox();

        Text hintsText = new Text("Hints: ");
        hintsText.setFont(Font.font("Verdana", 20));
        hbox.getChildren().add(hintsText);

        Button onOfButtonForHints = new Button("On");
        hbox.getChildren().add(onOfButtonForHints);


        onOfButtonForHints.setOnMouseClicked(e ->
        {
            if (onOfButtonForHints.getText().equals("On")) {
                onOfButtonForHints.setText("Off");
                hintsOn = false;
            } else {
                onOfButtonForHints.setText("On");
                hintsOn = true;
            }
            updateBoardRender();
            renderPieces();
        });

        Button rulesButton = new Button("Read rules!");

        rulesButton.setOnMouseClicked(f -> {
            RulesGUI rulesGUI = new RulesGUI();
            rulesGUI.display();
        });

        HBox hbox2 = new HBox();

        ObservableList<String> difficultyOptions =
                FXCollections.observableArrayList(
                        "Easy", // depth = 3
                        "Medium", // depth = 4
                        "Hard" // depth = 6
                );
        ComboBox comboBox = new ComboBox(difficultyOptions);
        comboBox.getSelectionModel().select(1); // default difficult is medium

        Text difficultyText = new Text("  Difficulty: ");
        difficultyText.setFont(Font.font("Verdana", 20));

        hbox2.getChildren().add(difficultyText);
        hbox2.getChildren().add(comboBox);
        hbox2.getChildren().add(new Text("      "));

        comboBox.setOnAction(e -> {
            if (comboBox.getValue().equals("Easy")) {
                checkers.setDifficulty(3);
            } else if (comboBox.getValue().equals("Medium")) {
                checkers.setDifficulty(4);
            } else {
                checkers.setDifficulty(6);
            }
        });


        localBorderPane.setLeft(hbox);
        localBorderPane.setCenter(hbox2);
        localBorderPane.setRight(rulesButton);

        topOfGUI.setLeft(localBorderPane);

        borderPane.setTop(topOfGUI);
    }

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
        borderPane.setCenter(grid);
    }

    /**
     * Updates the board to show the current state of the game
     */
    public void updateBoardRender() {
        for (int i = 0; i < buttonsInGrid.length; i++) {
            for (int j = 0; j < buttonsInGrid.length; j++) {
                Tile currTile = checkers.getCheckersBoard().getBoard()[i][j];
                if (checkers.getHighlightedTiles().contains(currTile) && hintsOn) {
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
                        if (!currTile.getPiece().isKing()) {
                            circle.setFill(javafx.scene.paint.Color.BLACK);
                        } else {
                            String kingPath = "assests/CrownBlack.png";
                            File kingFile = new File(kingPath);
                            Image im = new Image(kingFile.toURI().toString());
                            circle.setFill(new ImagePattern(im));
                        }
                        if (!currTile.getPiece().getCanMakeLegalJump() && currTile.getPiece().getCanMakeLegalMove()
                                && checkers.getCurrentTurn().equals("black")
                                && checkers.getPlayerAction() == PlayerAction.SelectingPiece
                                && hintsOn) {
                            circle.setStroke(Color.GOLD);
                            circle.setStrokeWidth(5.00);
                        } else if (currTile.getPiece().getCanMakeLegalJump() && checkers.getCurrentTurn().equals("black")
                                && checkers.getPlayerAction() == PlayerAction.SelectingPiece && hintsOn) {
                            circle.setStroke(Color.GOLD);
                            circle.setStrokeWidth(5.00);
                        }
                    } else {
                        if (!currTile.getPiece().isKing()) {
                            circle.setFill(Color.WHITE);
                        } else {
                            String kingPath = "assests/CrownWhite.png";
                            File kingFile = new File(kingPath);
                            Image im = new Image(kingFile.toURI().toString());
                            circle.setFill(new ImagePattern(im));
                        }
                        if (!currTile.getPiece().getCanMakeLegalJump() && currTile.getPiece().getCanMakeLegalMove()
                                && checkers.getCurrentTurn().equals("white")
                                && checkers.getPlayerAction() == PlayerAction.SelectingPiece
                                && hintsOn) {
                            circle.setStroke(Color.GOLD);
                            circle.setStrokeWidth(5.00);
                        } else if (currTile.getPiece().getCanMakeLegalJump()
                                && checkers.getCurrentTurn().equals("white")
                                && checkers.getPlayerAction() == PlayerAction.SelectingPiece
                                && hintsOn) {
                            circle.setStroke(Color.GOLD);
                            circle.setStrokeWidth(5.00);
                        }
                    }
                    if (currTile.getPiece().isSelected() && (checkers.getPlayerAction() == PlayerAction.SelectingTileToMoveTo
                            || checkers.getPlayerAction() == PlayerAction.MakingJump)) {
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

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Launches the GUI
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Checkers");
        borderPane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY)));
        // // initialise the displays
        renderBoard();
        renderPieces();
        renderTopOfDisplay();
        // call the game loop
        gameLoop();
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.show();
    }

    /**
     * Gets button clicks from the players and updates the state of the game accordingly each time
     */
    public void gameLoop() {
        if (!checkers.isGameOver()) {
            if (checkers.getCurrentTurn().equals("black")) {
                blackMove();
            } else {
                whiteMove();
            }
        }
    }

    /**
     * The AI players move, which calls the Minimax algorithm,
     * and sets the state of the checkers game to what is returned
     */
    public void whiteMove() {
        this.checkers = checkers.aiMove(true);
        update();
    }

    /**
     * The human players move which gets mouse clicks from the user and updates the
     * state accordingly, if they make an invalid move a status message is displayed in the GUI
     */
    public void blackMove() {
        for (int i = 0; i < checkers.getCheckersBoard().getRows(); i++) {
            for (int j = 0; j < checkers.getCheckersBoard().getCols(); j++) {
                int currentI = i;
                int currentJ = j;
                buttonsInGrid[i][j].setOnMouseClicked(e -> {
                    if (checkers.getCurrentTurn().equals("black")) {
                        Tile currTile = checkers.getCheckersBoard().getBoard()[currentI][currentJ];
                        if (checkers.getPlayerAction() == PlayerAction.SelectingPiece) {
                            if (!checkers.selectPiece(currTile)) {
                                topOfGUI.setCenter(new Text("Invalid move! You must select a piece."));
                            } else {
                                topOfGUI.setCenter(new Text(""));
                                checkers.selectPiece(currTile);
                            }
                        } else if (checkers.getPlayerAction() == PlayerAction.SelectingTileToMoveTo) {
                            if (!checkers.movePiece(currTile)) {
                                topOfGUI.setCenter(new Text("Invalid move! You must select a tile to move to."));
                            } else {
                                topOfGUI.setCenter(new Text(""));
                                checkers.movePiece(currTile);
                            }
                        } else if (checkers.getPlayerAction() == PlayerAction.MakingJump) {
                            if (!checkers.makeJump(currTile)) {
                                topOfGUI.setCenter(new Text("Invalid move! You must make a jump."));
                            } else {
                                topOfGUI.setCenter(new Text(""));
                                checkers.makeJump(currTile);
                            }
                        }
                        update();
                    }
                });
            }
        }
    }

    /**
     * Updates the GUI render
     */
    private void update() {
        updateBoardRender();
        renderPieces();
        gameLoop();
    }
}

