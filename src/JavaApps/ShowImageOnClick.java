package JavaApps;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ShowImageOnClick extends Application {

    @Override
    public void start(Stage stage) {
        Button showImageBtn = new Button("Click Me");

        showImageBtn.setStyle("-fx-background-color : White");
        showImageBtn.setOnAction(e -> {
            // Load image from outside the project (e.g., Desktop)
            Image image = new Image("file:C:/Users/WassimIssa/Desktop/test.jpg");
            System.out.println("Image loaded successfully? " + !image.isError());

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(300);
            imageView.setPreserveRatio(true);

            StackPane imagePane = new StackPane(imageView);
            Scene imageScene = new Scene(imagePane, 600, 400);
            imagePane.setStyle("-fx-background-color : Black");
            stage.setScene(imageScene);
            stage.setTitle("Image Viewer");
        });

        StackPane root = new StackPane(showImageBtn);
        root.setStyle("-fx-background-color : Black");
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Click to Show Image");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}