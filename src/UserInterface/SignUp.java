package UserInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.IntStream;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.geometry.Pos;
import javafx.util.Duration;

public class SignUp {

	// TextFields
	private TextField usernameField;
	private Text usernameHint;
	private Text usernameError;

	private TextField emailField;
	private Text emailHint;
	private Text emailError;

	private PasswordField passwordField;
	private Text passwordHint;
	private Text passwordError;

	private PasswordField confirmPasswordField;

	private TextField phoneField;
	private Text phoneHint;
	private Text phoneError;

	private TextField addressField;

	private Text confirmPasswordError;

	private Text dobHint;
	private Text dobError;

	private LocalDate selectedDOB;

	// ComboBoxes
	private ComboBox<String> dayBox;
	private ComboBox<String> monthBox;
	private ComboBox<String> yearBox;
	private ComboBox<String> maritalBox;
	private ComboBox<String> countryBox;
	// Gender
	private ToggleGroup genderGroup;
	private RadioButton maleRadio;
	private RadioButton femaleRadio;
	private RadioButton xyRadio;
	// Terms
	private CheckBox termsCheckbox;
	private Text termsLabel;
	// Error message
	private Text errorMessage;

	public Scene getScene(Stage stage) {

		Connection conn = null;
		try {
			conn = DBConnection.connect();
		} catch (SQLException e) {
			e.printStackTrace(); // or show error in UI
		}

		// Create the submit button and center it
		Button submit = createSubmitButton(stage);
		HBox buttonBox = new HBox(submit);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setPadding(new Insets(10, 0, 0, 0));

		// Build the form layout
		// Build the form layout
		VBox formBox = new VBox(10);
		formBox.setAlignment(Pos.TOP_CENTER);
		formBox.setPadding(new Insets(20));
		formBox.setMaxWidth(450);

		formBox.getChildren().addAll(createUsernameRow(), createEmailRow(), createPhoneRow(), createAddressRow(),
				createCountryRow(), createPasswordRow(), createConfirmPasswordRow(), createDOBRow(),
				createMaritalStatusRow(), createGenderRow(), createTermsCheckboxRow(), createErrorMessage(), buttonBox);

		// ✅ Fade after children are added
		FadeTransition fade = new FadeTransition(Duration.seconds(1.2), formBox);
		fade.setFromValue(0);
		fade.setToValue(1);
		fade.play();

		// Wrap formBox in a StackPane to center it inside ScrollPane
		StackPane centerWrapper = new StackPane(formBox);
		centerWrapper.setAlignment(Pos.TOP_CENTER); // ✅ keeps form centered as a block

		ScrollPane scrollPane = new ScrollPane(centerWrapper);
		scrollPane.setFitToWidth(true);
		scrollPane.setStyle("-fx-background: #55cef0;");
		scrollPane.setPadding(new Insets(20));

		// Build main layout
		BorderPane layout = new BorderPane();
		layout.setTop(createWelcomeText());
		layout.setCenter(scrollPane);
		layout.setBottom(createBackButton(stage));
		layout.setStyle("-fx-background-color: #55cef0;");

		return new Scene(layout, 600, 600);
	}

	private VBox createWelcomeText() {
		Text welcomeText = new Text("Welcome New User");
		FadeTransition fade = new FadeTransition(Duration.seconds(2), welcomeText);
		fade.setFromValue(0);
		fade.setToValue(1);
		fade.play();
		welcomeText.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-font-style: italic; -fx-fill: #1a4854;");
		VBox box = new VBox(welcomeText);
		box.setAlignment(Pos.TOP_CENTER);
		box.setPrefHeight(100);
		box.setTranslateY(10);
		return box;
	}

	private static HBox createBackButton(Stage stage) {
		Button backButton = new Button("←");
		backButton.setStyle("""
				    -fx-background-color: transparent;
				    -fx-text-fill: #1a4854;
				    -fx-font-size: 20px;
				    -fx-font-weight: bold;
				    -fx-cursor: hand;
				    -fx-padding: 2px 6px;
				""");
		backButton.setOnAction(ev -> {
			stage.setScene(new SignIn().getScene(stage));
		});

		HBox backBox = new HBox(backButton);
		backBox.setAlignment(Pos.CENTER_LEFT);
		backBox.setPadding(new Insets(0, 0, 10, 10)); // bottom-left spacing
		return backBox;
	}

	private HBox createUsernameRow() {
		Text label = new Text("UserName:*");
		label.setStyle("""
				    -fx-font-size: 16px;
				    -fx-underline: true;
				    -fx-font-style: italic;
				    -fx-fill: #1a4854;
				""");

		usernameField = new TextField();
		usernameField.setStyle("""
				    -fx-background-color: transparent;
				    -fx-border-color: transparent transparent #1a4854 transparent;
				    -fx-border-style: none none solid none;
				    -fx-border-width: 0 0 1px 0;
				    -fx-border-insets: 0;
				    -fx-font-size: 14px;
				    -fx-text-fill: #1a4854;
				""");

		usernameHint = new Text("Must include 1 capital letter and 1 number");
		usernameHint.setStyle("-fx-fill: black; -fx-font-size: 12px;");
		usernameHint.setOpacity(0.6); // subtle appearance
		usernameHint.setWrappingWidth(300);

		usernameError = new Text();
		usernameError.setStyle("-fx-fill: red; -fx-font-size: 12px;");
		usernameError.setWrappingWidth(300);
		usernameError.setText("");

		VBox usernameBox = new VBox(2, usernameHint, usernameError, usernameField);
		usernameBox.setAlignment(Pos.CENTER_LEFT);

		HBox row = new HBox(10, label, usernameBox);
		row.setAlignment(Pos.CENTER_LEFT);
		return row;
	}

	private HBox createEmailRow() {
		Text label = new Text("📧 Email:");
		label.setStyle("""
				    -fx-font-size: 16px;
				    -fx-underline: true;
				    -fx-font-style: italic;
				    -fx-fill: #1a4854;
				""");

		emailField = new TextField();
		emailField.setStyle(usernameField.getStyle());
		Tooltip.install(emailField, new Tooltip("Enter a valid email address"));

		emailHint = new Text("Must be a valid email like user@example.com");
		emailHint.setStyle("-fx-fill: black; -fx-font-size: 12px;");
		emailHint.setOpacity(0.6);
		emailHint.setWrappingWidth(300);

		emailError = new Text();
		emailError.setStyle("-fx-fill: red; -fx-font-size: 12px;");
		emailError.setWrappingWidth(300);
		emailError.setText("");

		VBox emailBox = new VBox(2, emailHint, emailError, emailField);
		emailBox.setAlignment(Pos.CENTER_LEFT);

		HBox row = new HBox(10, label, emailBox);
		row.setAlignment(Pos.CENTER_LEFT);
		return row;
	}

	private HBox createPasswordRow() {
		Text label = new Text("🔒 Password:");
		label.setStyle("""
				    -fx-font-size: 16px;
				    -fx-underline: true;
				    -fx-font-style: italic;
				    -fx-fill: #1a4854;
				""");

		passwordField = new PasswordField();
		TextField visiblePasswordField = new TextField();
		CheckBox showPasswordCheck = new CheckBox("Show");

		passwordField.setStyle(usernameField.getStyle());
		visiblePasswordField.setStyle(usernameField.getStyle());
		showPasswordCheck.setStyle("-fx-text-fill: #1a4854;");

		visiblePasswordField.textProperty().bindBidirectional(passwordField.textProperty());
		visiblePasswordField.setManaged(false);
		visiblePasswordField.setVisible(false);

		showPasswordCheck.setOnAction(e -> {
			boolean show = showPasswordCheck.isSelected();
			visiblePasswordField.setManaged(show);
			visiblePasswordField.setVisible(show);
			passwordField.setManaged(!show);
			passwordField.setVisible(!show);
		});

		Tooltip.install(passwordField, new Tooltip("Create a secure password"));

		passwordHint = new Text("Password must be at least 8 characters.");
		passwordHint.setStyle("-fx-fill: black; -fx-font-size: 12px;");
		passwordHint.setOpacity(0.6);
		passwordHint.setWrappingWidth(300);

		passwordError = new Text();
		passwordError.setStyle("-fx-fill: red; -fx-font-size: 12px;");
		passwordError.setWrappingWidth(300);
		passwordError.setText("");

		VBox passwordBox = new VBox(2, passwordHint, passwordError, passwordField, visiblePasswordField);
		passwordBox.setAlignment(Pos.CENTER_LEFT);

		HBox row = new HBox(10, label, passwordBox, showPasswordCheck);
		row.setAlignment(Pos.CENTER_LEFT);
		return row;
	}

	private HBox createConfirmPasswordRow() {
		Text label = new Text("🔐 Confirm Password:");
		label.setStyle("""
				    -fx-font-size: 16px;
				    -fx-underline: true;
				    -fx-font-style: italic;
				    -fx-fill: #1a4854;
				""");

		confirmPasswordField = new PasswordField();
		TextField visibleConfirmField = new TextField();
		CheckBox showConfirmCheck = new CheckBox("");

		confirmPasswordField.setStyle(usernameField.getStyle());
		visibleConfirmField.setStyle(usernameField.getStyle());
		showConfirmCheck.setStyle("-fx-text-fill: #1a4854;");

		visibleConfirmField.textProperty().bindBidirectional(confirmPasswordField.textProperty());
		visibleConfirmField.setManaged(false);
		visibleConfirmField.setVisible(false);

		showConfirmCheck.setOnAction(e -> {
			boolean show = showConfirmCheck.isSelected();
			visibleConfirmField.setManaged(show);
			visibleConfirmField.setVisible(show);
			confirmPasswordField.setManaged(!show);
			confirmPasswordField.setVisible(!show);
		});

		Tooltip.install(confirmPasswordField, new Tooltip("Re-enter your password"));

		confirmPasswordError = new Text(); // Inline error message
		confirmPasswordError.setStyle("-fx-fill: red; -fx-font-size: 12px;");
		confirmPasswordError.setWrappingWidth(300);
		confirmPasswordError.setText(""); // default empty

		VBox confirmBox = new VBox(2, confirmPasswordError, confirmPasswordField, visibleConfirmField);
		confirmBox.setAlignment(Pos.CENTER_LEFT);

		HBox row = new HBox(10, label, confirmBox, showConfirmCheck);
		row.setAlignment(Pos.CENTER_LEFT);
		return row;
	}

	private HBox createPhoneRow() {
		Text label = new Text("📱 Phone Number:");
		label.setStyle("""
				    -fx-font-size: 16px;
				    -fx-underline: true;
				    -fx-font-style: italic;
				    -fx-fill: #1a4854;
				""");

		phoneField = new TextField();
		phoneField.setPromptText("Optional");
		phoneField.setStyle("""
				    -fx-background-color: transparent;
				    -fx-border-color: transparent transparent #1a4854 transparent;
				    -fx-border-style: none none solid none;
				    -fx-border-width: 0 0 1px 0;
				    -fx-font-size: 14px;
				    -fx-text-fill: #1a4854;
				""");
		Tooltip.install(phoneField, new Tooltip("Enter your mobile number"));

		// Allow only digits and optional '+' at start
		phoneField.textProperty().addListener((obs, oldVal, newVal) -> {
			if (!newVal.matches("\\+?\\d*")) {
				phoneField.setText(newVal.replaceAll("[^\\d+]", ""));
			}
		});

		phoneHint = new Text("Optional field. Format: +961XXXXXXXX");
		phoneHint.setStyle("-fx-fill: black; -fx-font-size: 12px;");
		phoneHint.setOpacity(0.6);
		phoneHint.setWrappingWidth(300);

		phoneError = new Text();
		phoneError.setStyle("-fx-fill: red; -fx-font-size: 12px;");
		phoneError.setWrappingWidth(300);
		phoneError.setText("");

		VBox phoneBox = new VBox(2, phoneHint, phoneError, phoneField);
		phoneBox.setAlignment(Pos.CENTER_LEFT);

		HBox row = new HBox(10, label, phoneBox);
		row.setAlignment(Pos.CENTER_LEFT);
		return row;
	}

	private HBox createAddressRow() {
		Text label = new Text("🏠 Address:");
		label.setStyle("""
				    -fx-font-size: 16px;
				    -fx-underline: true;
				    -fx-font-style: italic;
				    -fx-fill: #1a4854;
				""");

		addressField = new TextField();
		addressField.setStyle(phoneField.getStyle());
		Tooltip.install(addressField, new Tooltip("Enter your full address"));

		HBox row = new HBox(10, label, addressField);
		row.setAlignment(Pos.CENTER_LEFT);
		return row;
	}

	private HBox createCountryRow() {
		Text label = new Text("🌍 Country:");
		label.setStyle("""
				    -fx-font-size: 16px;
				    -fx-underline: true;
				    -fx-font-style: italic;
				    -fx-fill: #1a4854;
				""");

		countryBox = new ComboBox<>();
		countryBox.getItems().addAll("Lebanon", "United States", "Canada", "France", "Germany", "United Kingdom",
				"Italy", "Spain", "Australia", "India", "Japan", "China", "Brazil", "Egypt");
		countryBox.setPromptText("Select Country");
		countryBox.setStyle("""
				    -fx-background-color: transparent;
				    -fx-border-color: transparent transparent #1a4854 transparent;
				    -fx-border-style: none none solid none;
				    -fx-border-width: 0 0 1px 0;
				    -fx-font-size: 14px;
				    -fx-text-fill: #1a4854;
				""");

		Tooltip.install(countryBox, new Tooltip("Choose your country of residence"));
		HBox row = new HBox(10, label, countryBox);
		row.setAlignment(Pos.CENTER_LEFT);
		return row;
	}

	private HBox createDOBRow() {
		Text label = new Text("🎂 Date of Birth:");
		label.setStyle("""
				    -fx-font-size: 16px;
				    -fx-underline: true;
				    -fx-font-style: italic;
				    -fx-fill: #1a4854;
				""");

		dayBox = new ComboBox<>();
		monthBox = new ComboBox<>();
		yearBox = new ComboBox<>();

		dayBox.getItems().addAll(IntStream.rangeClosed(1, 31).mapToObj(String::valueOf).toList());
		monthBox.getItems()
				.addAll(List.of("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"));
		yearBox.getItems().addAll(IntStream.rangeClosed(1950, 2025).mapToObj(String::valueOf).toList());

		dayBox.setPromptText("dd");
		monthBox.setPromptText("mm");
		yearBox.setPromptText("yy");

		dayBox.setStyle(usernameField.getStyle());
		monthBox.setStyle(usernameField.getStyle());
		yearBox.setStyle(usernameField.getStyle());

		Tooltip.install(dayBox, new Tooltip("Day"));
		Tooltip.install(monthBox, new Tooltip("Month"));
		Tooltip.install(yearBox, new Tooltip("Year"));

		dobHint = new Text("Select your full birth date.");
		dobHint.setStyle("-fx-fill: black; -fx-font-size: 12px;");
		dobHint.setOpacity(0.6);
		dobHint.setWrappingWidth(300);

		dobError = new Text();
		dobError.setStyle("-fx-fill: red; -fx-font-size: 12px;");
		dobError.setWrappingWidth(300);
		dobError.setText("");

		HBox dateBoxes = new HBox(10, dayBox, monthBox, yearBox);
		dateBoxes.setAlignment(Pos.CENTER_LEFT);

		VBox dobBox = new VBox(2, dobHint, dobError, dateBoxes);
		dobBox.setAlignment(Pos.CENTER_LEFT);

		HBox row = new HBox(10, label, dobBox);
		row.setAlignment(Pos.CENTER_LEFT);
		return row;
	}

	private HBox createMaritalStatusRow() {
		Text label = new Text("💍 Marital Status:");
		label.setStyle("""
				    -fx-font-size: 16px;
				    -fx-underline: true;
				    -fx-font-style: italic;
				    -fx-fill: #1a4854;
				""");

		maritalBox = new ComboBox<>();
		maritalBox.getItems().addAll("Single", "Married", "Divorced", "Widowed");
		maritalBox.setPromptText("Select status");
		maritalBox.setStyle(usernameField.getStyle());

		Tooltip.install(maritalBox, new Tooltip("Choose your marital status"));

		HBox row = new HBox(10, label, maritalBox);
		row.setAlignment(Pos.CENTER_LEFT);
		return row;
	}

	private HBox createGenderRow() {
		Text label = new Text("⚧ Gender:");
		label.setStyle("""
				    -fx-font-size: 16px;
				    -fx-underline: true;
				    -fx-font-style: italic;
				    -fx-fill: #1a4854;
				""");

		genderGroup = new ToggleGroup();

		maleRadio = new RadioButton("Male");
		femaleRadio = new RadioButton("Female");
		xyRadio = new RadioButton("XY");

		maleRadio.setToggleGroup(genderGroup);
		femaleRadio.setToggleGroup(genderGroup);
		xyRadio.setToggleGroup(genderGroup);

		maleRadio.setStyle("-fx-text-fill: #1a4854;");
		femaleRadio.setStyle("-fx-text-fill: #1a4854;");
		xyRadio.setStyle("-fx-text-fill: #1a4854;");

		Tooltip.install(maleRadio, new Tooltip("Identify as male"));
		Tooltip.install(femaleRadio, new Tooltip("Identify as female"));
		Tooltip.install(xyRadio, new Tooltip("Other or prefer not to say"));

		HBox row = new HBox(10, label, maleRadio, femaleRadio, xyRadio);
		row.setAlignment(Pos.CENTER_LEFT);
		return row;
	}

	private VBox createTermsCheckboxRow() {
		termsCheckbox = new CheckBox("✅ I agree to the terms and conditions.");
		termsCheckbox.setStyle("-fx-font-size: 14px; -fx-text-fill: #1a4854;");
		Tooltip.install(termsCheckbox, new Tooltip("You must agree before submitting"));

		termsLabel = new Text("""
				    📜 By creating an account, you confirm that:
				    • You agree to our Terms and Conditions
				    • You acknowledge our Privacy Policy
				    • You are responsible for the accuracy of your information
				""");
		termsLabel.setStyle("-fx-font-size: 14px; -fx-fill: #1a4854;");

		VBox row = new VBox(10, termsCheckbox, termsLabel);
		row.setAlignment(Pos.CENTER_LEFT);
		return row;
	}

	private boolean validateForm() {
		// TODO: Add full validation logic here
		return true; // Accepts everything for now
	}

	private Button createSubmitButton(Stage stage) {
		Button submit = new Button("🚀 Create Account");
		submit.setStyle("""
				    -fx-background-color: linear-gradient(to right, #6a11cb, #2575fc);
				    -fx-text-fill: white;
				    -fx-font-size: 14px;
				    -fx-font-weight: bold;
				    -fx-padding: 8px 20px;
				    -fx-background-radius: 20px;
				    -fx-cursor: hand;
				    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0.3, 0, 2);
				""");

		submit.setOnMouseEntered(e -> submit.setStyle("""
				    -fx-background-color: linear-gradient(to right, #2575fc, #6a11cb);
				    -fx-text-fill: white;
				    -fx-font-size: 14px;
				    -fx-font-weight: bold;
				    -fx-padding: 8px 20px;
				    -fx-background-radius: 20px;
				    -fx-cursor: hand;
				    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 6, 0.4, 0, 3);
				"""));

		submit.setOnMouseExited(e -> submit.setStyle("""
				    -fx-background-color: linear-gradient(to right, #6a11cb, #2575fc);
				    -fx-text-fill: white;
				    -fx-font-size: 14px;
				    -fx-font-weight: bold;
				    -fx-padding: 8px 20px;
				    -fx-background-radius: 20px;
				    -fx-cursor: hand;
				    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0.3, 0, 2);
				"""));

		submit.setOnAction(e -> {
			boolean valid = validateForm();
			// Hide hints immediately
			usernameHint.setVisible(false);
			emailHint.setVisible(false);
			passwordHint.setVisible(false);
			dobHint.setVisible(false);

			boolean hasError = false;
			String redBorder = "-fx-border-color: red; -fx-border-width: 0 0 2px 0;";
			String defaultBorder = "-fx-border-color: transparent transparent #1a4854 transparent; -fx-border-width: 0 0 1px 0;";
			StringBuilder errorBuilder = new StringBuilder();

			// Reset styles
			usernameField.setStyle(defaultBorder);
			emailField.setStyle(defaultBorder);
			passwordField.setStyle(defaultBorder);
			confirmPasswordField.setStyle(defaultBorder);
			termsLabel.setStyle("-fx-fill: #1a4854;");
			dobError.setText("");

			// Username validation
			usernameError.setText("");
			String username = usernameField.getText().trim();
			if (username.isEmpty()) {
				usernameField.setStyle(redBorder);
				usernameError.setText("Username is required.");
				hasError = true;
			} else if (!username.matches(".*[A-Z].*")) {
				usernameField.setStyle(redBorder);
				usernameError.setText("Username must include at least one capital letter.");
				hasError = true;
			} else if (!username.matches(".*[0-9].*")) {
				usernameField.setStyle(redBorder);
				usernameError.setText("Username must include at least one number.");
				hasError = true;
			}

			// Email validation
			emailError.setText("");
			String email = emailField.getText().trim();
			if (email.isEmpty()) {
				emailField.setStyle(redBorder);
				emailError.setText("Email is required.");
				hasError = true;
			} else if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
				emailField.setStyle(redBorder);
				emailError.setText("Invalid email format.");
				hasError = true;
			}

			// Password validation
			passwordError.setText("");
			String password = passwordField.getText().trim();
			if (password.isEmpty()) {
				passwordField.setStyle(redBorder);
				passwordError.setText("Password is required.");
				hasError = true;
			} else if (password.length() < 8) {
				passwordField.setStyle(redBorder);
				passwordError.setText("Password must be at least 8 characters.");
				hasError = true;
			} else if (!password.matches(".*[A-Z].*")) {
				passwordField.setStyle(redBorder);
				passwordError.setText("Password must include at least one capital letter.");
				hasError = true;
			} else if (!password.matches(".*[0-9].*")) {
				passwordField.setStyle(redBorder);
				passwordError.setText("Password must include at least one number.");
				hasError = true;
			} else if (!password.matches(".*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/\\\\|].*")) {
				passwordField.setStyle(redBorder);
				passwordError.setText("Password must include at least one special character.");
				hasError = true;
			}

			// Confirm password validation
			confirmPasswordError.setText("");
			String confirm = confirmPasswordField.getText().trim();
			if (confirm.isEmpty()) {
				confirmPasswordField.setStyle(redBorder);
				confirmPasswordError.setText("Confirm Password is required.");
				hasError = true;
			} else if (!password.equals(confirm)) {
				passwordField.setStyle(redBorder);
				confirmPasswordField.setStyle(redBorder);
				confirmPasswordError.setText("Passwords do not match.");
				hasError = true;
			}

			// DOB validation and age check
			String day = dayBox.getValue();
			String month = monthBox.getValue();
			String year = yearBox.getValue();

			if (day == null || month == null || year == null) {
				dobError.setText("Please select a complete date of birth.");
				hasError = true;
			} else {
				int dayInt = Integer.parseInt(day);
				int monthInt = List
						.of("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
						.indexOf(month) + 1;
				int yearInt = Integer.parseInt(year);

				try {
					selectedDOB = LocalDate.of(yearInt, monthInt, dayInt);
					LocalDate today = LocalDate.now();
					Period age = Period.between(selectedDOB, today);

					if (age.getYears() < 18) {
						dobError.setText("You cannot create an account if you are under 18.");
						hasError = true;
					}
				} catch (DateTimeException ex) {
					dobError.setText("Invalid date selected.");
					hasError = true;
				}
			}

			// Terms checkbox
			if (!termsCheckbox.isSelected()) {
				termsLabel.setStyle("-fx-fill: red;");
				errorBuilder.append("• You must accept the terms.\n");
				hasError = true;
			}

			// Final error check
			if (hasError) {
				errorMessage.setText(errorBuilder.toString());
				return;
			}

			errorMessage.setText("");
			System.out.println("✅ Form submitted successfully!");
			System.out.println("📅 DOB stored as: " + selectedDOB);

			if (valid) {
				stage.setScene(new Loggedin().getScene(stage)); // ✅ switch to Loggedin.java
			}
			try (Connection con = DBConnection.connect();
					PreparedStatement stmt = con
							.prepareStatement("INSERT INTO users (username, email, password) VALUES (?, ?, ?)")) {

				stmt.setString(1, usernameField.getText());
				stmt.setString(2, emailField.getText());
				stmt.setString(3, passwordField.getText());

				stmt.executeUpdate();
				System.out.println("✅ User inserted successfully");

			} catch (SQLException ex) {
				ex.printStackTrace();
			}

		});

		return submit;

	}

	private HBox createErrorMessage() {
		errorMessage = new Text();
		errorMessage.setStyle("""
				    -fx-fill: red;
				    -fx-font-size: 14px;
				    -fx-font-weight: bold;
				""");

		// ✅ Add this for multiline wrapping
		errorMessage.setWrappingWidth(400);

		// ✅ Optional: add ID for external CSS
		errorMessage.setId("error-text");

		HBox box = new HBox(errorMessage);
		box.setAlignment(Pos.CENTER_LEFT);
		box.setPadding(new Insets(5, 0, 0, 0));
		return box;
	}

}
