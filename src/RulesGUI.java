import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;

public class RulesGUI {

    private Stage primaryStage;
    private BorderPane borderPane = new BorderPane();
    WebView viewWeb = new WebView();
    WebEngine webEngine = viewWeb.getEngine();
    Button close = new Button("Go back");

    public void setUp() {
        File f = new File("Rules/checkersrules.htm");
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