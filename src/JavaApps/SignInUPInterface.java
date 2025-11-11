package JavaApps;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;


public class SignInUPInterface extends Application {
    @Override
    public void start(Stage primaryStage) 
    {
    	
        Button btn = new Button("Click Me");
        StackPane root = new StackPane(btn);
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("New Package UI");
        primaryStage.show();
        
    
    
    Image image = new Image("file:C:/Users/Wassim/Desktop/myimage.jpg");
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(300);
    imageView.setPreserveRatio(true);
    
    
    Button backBtn = new Button("← Back");
    backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: red;");
   // backBtn.setOnAction(e -> showPreviousScene(stage)); // define this method
    
    BorderPane layout = new BorderPane();
    layout.setCenter(imageView);
    layout.setTop(backBtn);
    BorderPane.setAlignment(backBtn, Pos.TOP_LEFT);
    
    btn.setOnAction(e -> {
        Scene imageScene = new Scene(layout, 400, 300);
    //    stage.setScene(imageScene);
    });
    
    
    }
    public static void main(String[] args) {
        launch(args);
    }
}

