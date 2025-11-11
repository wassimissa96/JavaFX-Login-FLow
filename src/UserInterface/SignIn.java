package UserInterface;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.geometry.Pos;
import javafx.util.Duration;

public class SignIn {

	private TextField usernameField;
	private PasswordField passwordField;
	private Text errorMessage;

	public Scene getScene(Stage stage) {
		Connection conn = null;
		

		usernameField = new TextField();
		passwordField = new PasswordField();
		errorMessage = new Text();

		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		layout.setStyle("-fx-background-color : #55cef0;");
		layout.getChildren().addAll(createWelcomeText(), createLoginImage(), createUsernameRow(), createPasswordRow(),
				createSignInButton(stage), errorMessage, createSignUpPrompt(stage));

		return new Scene(layout, 600, 600);
	}

	private VBox createWelcomeText() {
		Text welcomeText = new Text("👋 Welcome Back!");
		FadeTransition fade = new FadeTransition(Duration.seconds(1.5), welcomeText);
		fade.setFromValue(0);
		fade.setToValue(1);
		fade.play();
		welcomeText.setStyle("""
				    -fx-font-size: 28px;
				    -fx-font-weight: bold;
				    -fx-font-style: italic;
				    -fx-fill: #1a4854;
				    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 4, 0.3, 0, 2);
				""");

		VBox box = new VBox(welcomeText);
		box.setAlignment(Pos.TOP_CENTER);
		box.setPrefHeight(100);
		box.setTranslateY(10);
		return box;

	}

	private VBox createLoginImage() {
		ImageView icon = new ImageView(new Image("file:C:/Users/WassimIssa/Desktop/UserInterface/personvector1.png"));
		icon.setFitHeight(160);
		icon.setFitWidth(160);
		Circle clip = new Circle(80, 80, 80);
		icon.setClip(clip);

		VBox box = new VBox(icon);
		box.setAlignment(Pos.TOP_CENTER);
		box.setTranslateY(-10);
		return box;

	}

	private VBox createUsernameRow() {
		Text label = new Text("📧 Email / Username:");
		label.setStyle("""
				    -fx-font-size: 16px;
				    -fx-underline: true;
				    -fx-font-style: italic;
				    -fx-fill: #1a4854;
				""");

		usernameField.setStyle("""
				    -fx-background-color: transparent;
				    -fx-border-color: transparent transparent #1a4854 transparent;
				    -fx-border-style: none none solid none;
				    -fx-border-width: 0 0 1px 0;
				    -fx-border-insets: 0;
				    -fx-font-size: 14px;
				    -fx-text-fill: #1a4854;
				""");

		Tooltip.install(usernameField, new Tooltip("Enter your email or username"));

		VBox box = new VBox(label, usernameField);
		box.setAlignment(Pos.CENTER_LEFT);
		return box;
	}

	private VBox createPasswordRow() {
		Text label = new Text("🔒 Password:");
		label.setStyle("""
				    -fx-font-size: 16px;
				    -fx-underline: true;
				    -fx-font-style: italic;
				    -fx-fill: #1a4854;
				""");

		passwordField.setStyle("""
				    -fx-background-color: transparent;
				    -fx-border-color: transparent transparent #1a4854 transparent;
				    -fx-border-style: none none solid none;
				    -fx-border-width: 0 0 1px 0;
				    -fx-font-size: 14px;
				    -fx-text-fill: #1a4854;
				""");

		TextField visiblePasswordField = new TextField();
		visiblePasswordField.setStyle(passwordField.getStyle());
		visiblePasswordField.setManaged(false);
		visiblePasswordField.setVisible(false);
		visiblePasswordField.textProperty().bindBidirectional(passwordField.textProperty());

		CheckBox toggle = new CheckBox("Show Password");
		toggle.setStyle("-fx-text-fill: #1a4854; -fx-font-size: 12px;");
		toggle.setOnAction(e -> {
			boolean show = toggle.isSelected();
			visiblePasswordField.setVisible(show);
			visiblePasswordField.setManaged(show);
			passwordField.setVisible(!show);
			passwordField.setManaged(!show);
		});

		Tooltip.install(passwordField, new Tooltip("Enter your password"));

		VBox box = new VBox(10, label, passwordField, visiblePasswordField, toggle);
		box.setAlignment(Pos.CENTER_LEFT);
		return box;
	}

	private Button createSignInButton(Stage stage) {
	    Button button = new Button("🚀 Sign In");
	    button.setStyle("""
	        -fx-background-color: linear-gradient(to right, #6a11cb, #2575fc);
	        -fx-text-fill: white;
	        -fx-font-size: 14px;
	        -fx-font-weight: bold;
	        -fx-padding: 8px 20px;
	        -fx-background-radius: 20px;
	        -fx-cursor: hand;
	        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0.3, 0, 2);
	    """);

	    button.setOnMouseEntered(e -> button.setStyle("""
	        -fx-background-color: linear-gradient(to right, #2575fc, #6a11cb);
	        -fx-text-fill: white;
	        -fx-font-size: 14px;
	        -fx-font-weight: bold;
	        -fx-padding: 8px 20px;
	        -fx-background-radius: 20px;
	        -fx-cursor: hand;
	        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 6, 0.4, 0, 3);
	    """));

	    button.setOnMouseExited(e -> button.setStyle("""
	        -fx-background-color: linear-gradient(to right, #6a11cb, #2575fc);
	        -fx-text-fill: white;
	        -fx-font-size: 14px;
	        -fx-font-weight: bold;
	        -fx-padding: 8px 20px;
	        -fx-background-radius: 20px;
	        -fx-cursor: hand;
	        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0.3, 0, 2);
	    """));

	    errorMessage.setStyle("-fx-fill: red; -fx-font-size: 16px;");

	    button.setOnAction(e -> {
	        String username = usernameField.getText().trim();
	        String password = passwordField.getText().trim();

	        if (username.isEmpty() || password.isEmpty()) {
	            errorMessage.setText("⚠️ Username and Password are required.");
	            return;
	        }

	        errorMessage.setText("");
	        System.out.println("🔁 Login handler triggered");

	        System.out.println("Entered username: '" + username + "'");
	        System.out.println("Entered password: '" + password + "'");
	        
	        try (Connection conn = DBConnection.connect();
	        	     Statement stmt = conn.createStatement();
	        	     ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {

	        	    while (rs.next()) {
	        	        System.out.println("Row: " + rs.getString("username") + " / " + rs.getString("password"));
	        	    }

	        	} catch (SQLException ed) {
	        	    ed.printStackTrace();
	        	}
	        
	        try (Connection conn = DBConnection.connect();
	             PreparedStatement stmt = conn.prepareStatement(
	                 "SELECT * FROM users WHERE username = ? AND password = ?")) {

	            stmt.setString(1, username);
	            stmt.setString(2, password);

	            ResultSet rs = stmt.executeQuery();

	            if (rs.next()) {
	                System.out.println("✅ Login successful");
	                stage.setScene(createSuccessScene(stage));
	            } else {
	                System.out.println("❌ Invalid credentials");
	                errorMessage.setText("❌ Invalid username or password.");
	            }

	        } catch (SQLException ed) {
	            ed.printStackTrace();
	            errorMessage.setText("⚠️ Database error occurred.");
	        }
	    });

	    return button;
	}

	private Scene createSuccessScene(Stage stage) {
		ImageView successImage = new ImageView(new Image("file:C:/Users/WassimIssa/Desktop/UserInterface/test.jpg"));
		successImage.setFitWidth(300);
		successImage.setFitHeight(300);
		FadeTransition fade = new FadeTransition(Duration.seconds(1), successImage);
		fade.setFromValue(0);
		fade.setToValue(1);
		fade.play();

		VBox centerBox = new VBox(successImage);
		centerBox.setAlignment(Pos.CENTER);

		Button backButton = new Button("← Back");
		backButton.setStyle("""
				    -fx-background-color: transparent;
				    -fx-text-fill: #1a4854;
				    -fx-font-size: 14px;
				    -fx-font-weight: bold;
				    -fx-cursor: hand;
				    -fx-padding: 2px 6px;
				""");
		backButton.setOnAction(ev -> stage.setScene(getScene(stage)));

		HBox imageBackBox = new HBox(backButton);
		imageBackBox.setAlignment(Pos.CENTER_LEFT);
		imageBackBox.setPadding(new Insets(0, 0, 10, 10));

		BorderPane layout = new BorderPane();
		layout.setCenter(centerBox);
		layout.setBottom(imageBackBox);
		layout.setStyle("-fx-background-color: #55cef0;");

		return new Scene(layout, 500, 600);
	}

	private HBox createSignUpPrompt(Stage stage) {
		Text prompt = new Text("🆕 Not registered yet? ");
		prompt.setStyle("-fx-text-fill: #1a4854; -fx-font-size: 14px;");

		Hyperlink link = new Hyperlink("Sign Up!");
		link.setStyle("-fx-text-fill: blue; -fx-underline: true; -fx-font-size: 16px;");
		link.setOnAction(e -> stage.setScene(new SignUp().getScene(stage)));

		HBox box = new HBox(prompt, link);
		box.setAlignment(Pos.CENTER);
		return box;
	}

	
}