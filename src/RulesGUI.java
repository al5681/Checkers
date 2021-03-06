import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;

/**
 * A display for the rules of the game that can be accessed from the main GUI
 * by clicked on the "Read rules" button
 */
public class RulesGUI {

    private Stage primaryStage;
    private BorderPane borderPane = new BorderPane();
    WebView viewWeb = new WebView();
    WebEngine webEngine = viewWeb.getEngine();
    Button close = new Button("Close");

    public void setUp() {
        File f = new File("Rules/CheckersGameRules.htm");
        webEngine.load(f.toURI().toString());
        borderPane.setCenter(viewWeb);
        close.setStyle("-fx-background-color:white;-fx-text-fill:black;"
                + " -fx-border-color: black;-fx-font-size:30");
        borderPane.setBottom(close);
    }

    public void display() {
        primaryStage = new Stage();
        primaryStage.setTitle("Checkers rules");
        setUp();
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.show();

        close.setOnMouseClicked(e -> {
            primaryStage.close();
        });
    }
}