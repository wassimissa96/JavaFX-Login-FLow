package UserInterface;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Loggedin {

    public Scene getScene(Stage stage) {
        Label message = new Label("🎉 You are now logged in!");
        message.setStyle("-fx-font-size: 24px; -fx-text-fill: #1a4854;");

        StackPane layout = new StackPane(message);
        layout.setStyle("-fx-background-color: #55cef0;");

        return new Scene(layout, 600, 600);
    }
}