package membershipapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginForm {

    public LoginForm(Stage stage) {
        // Title and subtitle
        Label title = new Label("Welcome Back 👋");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label subtitle = new Label("Please log in to your account");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        // Input fields
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.setStyle("-fx-background-radius: 8; -fx-border-radius: 8; -fx-padding: 6 10;");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter your phone number");
        phoneField.setStyle("-fx-background-radius: 8; -fx-border-radius: 8; -fx-padding: 6 10;");

        // Labels
        Label emailLabel = new Label("Email:");
        Label phoneLabel = new Label("Phone:");

        emailLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e;");
        phoneLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e;");

        // Login Button
        Button loginBtn = new Button("Login");
        loginBtn.setPrefWidth(250);
        loginBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; "
                + "-fx-background-radius: 8; -fx-font-weight: bold;");
        loginBtn.setOnMouseEntered(e -> loginBtn.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-background-radius: 8; -fx-font-weight: bold;"));
        loginBtn.setOnMouseExited(e -> loginBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-background-radius: 8; -fx-font-weight: bold;"));

        // Register Link
        Hyperlink registerLink = new Hyperlink("Don’t have an account? Register here");
        registerLink.setStyle("-fx-text-fill: #2c3e50;");
        registerLink.setOnMouseEntered(e -> registerLink.setStyle("-fx-text-fill: #2980b9; -fx-underline: true;"));
        registerLink.setOnMouseExited(e -> registerLink.setStyle("-fx-text-fill: #2c3e50; -fx-underline: false;"));
        registerLink.setOnAction(e -> new RegistrationForm(stage));

        // Grid layout for form fields
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(15);
        formGrid.add(emailLabel, 0, 0);
        formGrid.add(emailField, 1, 0);
        formGrid.add(phoneLabel, 0, 1);
        formGrid.add(phoneField, 1, 1);
        formGrid.add(loginBtn, 1, 2);
        formGrid.add(registerLink, 1, 3);

        // Center alignment
        formGrid.setAlignment(Pos.CENTER);

        // Main layout
        VBox root = new VBox(20, title, subtitle, formGrid);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #f9f9f9, #dfe9f3);");

        // Login button action (original functionality preserved)
        loginBtn.setOnAction(e -> {
            try (Connection conn = DatabaseManager.getConnection()) {
                if (conn == null) return;
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM members WHERE email = ? AND phone = ?");
                ps.setString(1, emailField.getText());
                ps.setString(2, phoneField.getText());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    new Dashboard(stage, rs.getString("first_name"));
                } else {
                    new Alert(Alert.AlertType.ERROR, "Invalid email or phone!").show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Scene setup
        Scene scene = new Scene(root, 420, 330);
        stage.setTitle("Login - Membership App");
        stage.setScene(scene);
        stage.show();
    }
}
