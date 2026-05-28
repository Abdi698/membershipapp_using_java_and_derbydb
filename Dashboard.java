package membershipapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Dashboard {

    public Dashboard(Stage stage, String userName) {

        // --- Header ---
        Label title = new Label("Membership Dashboard");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label welcome = new Label("Welcome, " + userName + " 👋");
        welcome.setStyle("-fx-font-size: 16px; -fx-text-fill: #34495e;");

        VBox headerBox = new VBox(5, title, welcome);
        headerBox.setAlignment(Pos.CENTER);

        // --- Buttons ---
        Button messageBtn = createStyledButton("💬 Send Message");
        Button paymentBtn = createStyledButton("💳 Make Payment");
        Button editBtn = createStyledButton("🧍 Edit Profile");
        Button reportBtn = createStyledButton("⚠️ Report Problem");

        // --- Hyperlinks for navigation ---
        Hyperlink loginLink = new Hyperlink("← Back to Login");
        loginLink.setOnAction(e -> new LoginForm(stage));

        Hyperlink regLink = new Hyperlink("← Back to Registration");
        regLink.setOnAction(e -> new RegistrationForm(stage));

        styleHyperlink(loginLink);
        styleHyperlink(regLink);

        HBox navLinks = new HBox(15, loginLink, regLink);
        navLinks.setAlignment(Pos.CENTER);

        // --- Layout ---
        VBox root = new VBox(20, headerBox, messageBtn, paymentBtn, editBtn, reportBtn, navLinks);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #f9f9f9, #dfe9f3);"
                + "-fx-background-radius: 10;");

        // --- Button Actions ---
        messageBtn.setOnAction(e -> showInfo("Messaging feature coming soon!"));
        paymentBtn.setOnAction(e -> showInfo("Payment feature coming soon!"));
        editBtn.setOnAction(e -> showInfo("Edit profile feature coming soon!"));
        reportBtn.setOnAction(e -> showInfo("Problem reporting feature coming soon!"));

        // --- Scene setup ---
        Scene scene = new Scene(root, 420, 400);
        stage.setScene(scene);
        stage.setTitle("Dashboard - Membership App");
        stage.show();
    }

    // Utility method to create modern styled buttons
    private Button createStyledButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(250);
        btn.setStyle(
                "-fx-background-color: #3498db; "
                + "-fx-text-fill: white; "
                + "-fx-font-size: 14px; "
                + "-fx-font-weight: bold; "
                + "-fx-background-radius: 8;"
        );
        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 8;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 8;"
        ));
        return btn;
    }

    // Utility method to style hyperlinks
    private void styleHyperlink(Hyperlink link) {
        link.setStyle("-fx-text-fill: #2c3e50; -fx-font-size: 13px;");
        link.setOnMouseEntered(e -> link.setStyle("-fx-text-fill: #2980b9; -fx-underline: true;"));
        link.setOnMouseExited(e -> link.setStyle("-fx-text-fill: #2c3e50; -fx-underline: false;"));
    }

    // Utility to show info alerts
    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
