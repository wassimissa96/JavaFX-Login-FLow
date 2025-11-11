package UserInterface;

import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

	
	public void start(Stage primaryStage)
	{
		System.out.println("CallAll launched");

		SignIn signIn = new SignIn();
		primaryStage.setScene(signIn.getScene(primaryStage));
		primaryStage.setTitle("TEST APP");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
        launch(args);
    }

}
