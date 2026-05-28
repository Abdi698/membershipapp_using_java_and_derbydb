package membershipapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RegistrationForm {
    // Styling constants for better visibility and focus
    private static final String DARK_LABEL_STYLE = "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1d3557;";
    private static final String INPUT_DEFAULT_STYLE =
        "-fx-background-color: #f7f9fc; " + // Light gray background
        "-fx-border-color: #ced4da; " + // Default border color
        "-fx-border-width: 1px; " +
        "-fx-border-radius: 6px; " +
        "-fx-background-radius: 6px; " +
        "-fx-padding: 8px 12px; "; // Increased padding

    // Focus style (using a style class approach for JavaFX focus handling)
    private static final String INPUT_FOCUS_STYLE =
        "-fx-background-color: #ffffff; " +
        "-fx-border-color: #4a90e2; " + // Bright blue border on focus
        "-fx-border-width: 2px; " +
        "-fx-border-radius: 6px; " +
        "-fx-background-radius: 6px; " +
        "-fx-padding: 8px 12px; " +
        "-fx-effect: dropshadow(gaussian, rgba(74, 144, 226, 0.5), 10, 0.0, 0, 0);"; // Blue glow effect

    // --- Error Style Constant for Visual Feedback ---
    private static final String INPUT_ERROR_STYLE =
        "-fx-background-color: #ffeaea; " + // Very light red background
        "-fx-border-color: #dc3545; " + // Red border
        "-fx-border-width: 2px; " +
        "-fx-border-radius: 6px; " +
        "-fx-background-radius: 6px; " +
        "-fx-padding: 8px 12px; ";

    // Helper method to apply default and focus styles to input controls
    private void applyInputStyles(Control control) {
        control.setStyle(INPUT_DEFAULT_STYLE);
        // Add listeners to apply focus style dynamically
        control.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                // When gaining focus, apply the focus/glow style
                control.setStyle(INPUT_FOCUS_STYLE);
            } else {
                // When losing focus, revert to the default/error style
                // Check if validation failed previously (i.e., if it has the error border color)
                if (control.getStyle().contains("#dc3545")) {
                     control.setStyle(INPUT_ERROR_STYLE); // Stick to error style if validation failed
                } else {
                     control.setStyle(INPUT_DEFAULT_STYLE); // Otherwise, revert to clean default style
                }
            }
        });
    }

    // --- Helper method to specifically apply error style ---
    private void applyErrorStyle(Control control) {
        control.setStyle(INPUT_ERROR_STYLE);
    }

    // --- NEW HELPER METHOD: Checks if a control's value is empty ---
    private boolean isControlEmpty(Control control) {
        if (control instanceof TextField) {
            return ((TextField) control).getText().trim().isEmpty();
        } else if (control instanceof TextArea) {
            return ((TextArea) control).getText().trim().isEmpty();
        } else if (control instanceof ComboBox) {
            return ((ComboBox<?>) control).getValue() == null;
        }
        return true; 
    }

    // --- Comprehensive Validation Method ---
    private boolean validateForm(TextField firstNameField, TextField lastNameField,
                                 TextField emailField, TextField phoneField,
                                 TextField address1Field, ComboBox<String> countryBox,
                                 ToggleGroup genderGroup, ToggleGroup membershipGroup,
                                 TextArea reasonArea, CheckBox termsBox) {
        boolean isValid = true;
        StringBuilder errors = new StringBuilder();

        // 1. Reset all styles to default for re-validation
        applyInputStyles(firstNameField);
        applyInputStyles(lastNameField);
        applyInputStyles(emailField);
        applyInputStyles(phoneField);
        applyInputStyles(address1Field);
        applyInputStyles(countryBox);
        applyInputStyles(reasonArea);

        // 2. Check required fields using the new helper method and update state
        if (isControlEmpty(firstNameField)) {
            applyErrorStyle(firstNameField);
            errors.append("• First Name is required.\n");
            isValid = false;
        }
        if (isControlEmpty(lastNameField)) {
            applyErrorStyle(lastNameField);
            errors.append("• Last Name is required.\n");
            isValid = false;
        }
        if (isControlEmpty(emailField)) {
            applyErrorStyle(emailField);
            errors.append("• Email Address is required.\n");
            isValid = false;
        }
        if (isControlEmpty(address1Field)) {
            applyErrorStyle(address1Field);
            errors.append("• Address Line 1 is required.\n");
            isValid = false;
        }
        if (isControlEmpty(countryBox)) {
            applyErrorStyle(countryBox);
            errors.append("• Your Region is required.\n");
            isValid = false;
        }
        if (isControlEmpty(reasonArea)) {
            applyErrorStyle(reasonArea);
            errors.append("• Talent & Awards description is required.\n");
            isValid = false;
        }

        // 3. Phone Number Validation (Numeric and 10 digits)
        String phoneNumber = phoneField.getText().trim();
        if (phoneNumber.isEmpty()) {
            applyErrorStyle(phoneField);
            errors.append("• Phone Number is required.\n");
            isValid = false;
        } else if (!phoneNumber.matches("\\d{10}")) { // Check for exactly 10 digits, numbers only
            applyErrorStyle(phoneField);
            errors.append("• Phone Number must be exactly 10 digits (numbers only).\n");
            isValid = false;
        }

        // 4. Radio Button and Checkbox checks
        if (genderGroup.getSelectedToggle() == null) {
            errors.append("• Gender selection is required.\n");
            isValid = false;
        }
        if (membershipGroup.getSelectedToggle() == null) {
            errors.append("• Membership Type selection is required.\n");
            isValid = false;
        }
        if (!termsBox.isSelected()) {
            errors.append("• You must agree to the terms and conditions.\n");
            isValid = false;
        }

        if (!isValid) {
            // Show consolidated error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Error");
            alert.setHeaderText("Please correct the following errors:");
            alert.setContentText(errors.toString());
            alert.showAndWait();
        }

        return isValid;
    }

    // --- NEW METHOD: Clears and resets all form fields ---
    private void resetForm(TextField firstNameField, TextField lastNameField,
                           TextField emailField, TextField phoneField,
                           TextField address1Field, TextField address2Field,
                           ComboBox<String> countryBox,
                           ToggleGroup genderGroup, ToggleGroup membershipGroup,
                           CheckBox football, CheckBox basketball, CheckBox soccer,
                           TextArea reasonArea, CheckBox termsBox) {
        
        // Clear Text Inputs
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        phoneField.clear();
        address1Field.clear();
        address2Field.clear();
        reasonArea.clear();

        // Reset Combobox
        countryBox.setValue(null);

        // Clear Toggle Groups (Radio Buttons)
        if (genderGroup.getSelectedToggle() != null) {
            genderGroup.getSelectedToggle().setSelected(false);
        }
        if (membershipGroup.getSelectedToggle() != null) {
            membershipGroup.getSelectedToggle().setSelected(false);
        }

        // Clear Checkboxes
        football.setSelected(false);
        basketball.setSelected(false);
        soccer.setSelected(false);
        termsBox.setSelected(false);

        // Reset Styles to default (if any error style was still applied)
        applyInputStyles(firstNameField);
        applyInputStyles(lastNameField);
        applyInputStyles(emailField);
        applyInputStyles(phoneField);
        applyInputStyles(address1Field);
        applyInputStyles(address2Field);
        applyInputStyles(countryBox);
        applyInputStyles(reasonArea);
    }

    public RegistrationForm(Stage stage) {
        // --- 1. Main Card Container (GridPane) ---
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(40)); // Increased padding
        grid.setHgap(20); // Increased horizontal gap
        grid.setVgap(18); // Increased vertical gap
        grid.setAlignment(Pos.CENTER);

        // Apply Card Styling (White background, rounded corners, subtle shadow)
        grid.setStyle("-fx-background-color: #ffffff; " +
                      "-fx-border-radius: 15px; " +
                      "-fx-background-radius: 15px; " +
                      "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 35, 0.5, 0, 5);"); // Stronger shadow

        // Set up Column Constraints for better alignment (e.g., Label - Input - Label - Input)
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER); // Labels
        col1.setMinWidth(100);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS); // Fields
        col2.setMinWidth(200);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.NEVER); // Labels
        col3.setMinWidth(100);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setHgrow(Priority.ALWAYS); // Fields
        col4.setMinWidth(200);
        grid.getColumnConstraints().addAll(col1, col2, col3, col4);


        // --- 2. Header and Title ---
        Label header = new Label("Ethiopian Talented Athlete Registration");
        header.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #1a73e8;");
        GridPane.setMargin(header, new Insets(0, 0, 10, 0)); // Extra bottom margin
        grid.add(header, 0, 0, 4, 1);

        // Separator line
        Separator separator = new Separator();
        grid.add(separator, 0, 1, 4, 1);

        int row = 2; // Start content from row 2

        // --- 3. Name Fields (Row 2) ---
        TextField firstNameField = new TextField();
        applyInputStyles(firstNameField);
        firstNameField.setPromptText("Enter First Name");

        TextField lastNameField = new TextField();
        applyInputStyles(lastNameField);
        lastNameField.setPromptText("Enter Last Name");

        Label fnLabel = new Label("First Name"); fnLabel.setStyle(DARK_LABEL_STYLE);
        Label lnLabel = new Label("Last Name"); lnLabel.setStyle(DARK_LABEL_STYLE);
        grid.add(fnLabel, 0, row);
        grid.add(firstNameField, 1, row);
        grid.add(lnLabel, 2, row);
        grid.add(lastNameField, 3, row);

        row++; // row = 3

        // --- 4. Contact Fields (Row 3) ---
        TextField emailField = new TextField();
        applyInputStyles(emailField);
        emailField.setPromptText("example@domain.com");

        TextField phoneField = new TextField();
        applyInputStyles(phoneField);
        phoneField.setPromptText("e.g., 912345678 (10 digits)");

        Label emailLabel = new Label("Email Address"); emailLabel.setStyle(DARK_LABEL_STYLE);
        Label phoneLabel = new Label("Phone Number"); phoneLabel.setStyle(DARK_LABEL_STYLE);
        grid.add(emailLabel, 0, row);
        grid.add(emailField, 1, row);
        grid.add(phoneLabel, 2, row);
        grid.add(phoneField, 3, row);

        row++; // row = 4

        // --- 5. Address Line 1 (Row 4) ---
        TextField address1Field = new TextField();
        applyInputStyles(address1Field);
        address1Field.setPromptText("Street address, Kebele, or Subcity");

        Label a1Label = new Label("Address Line 1"); a1Label.setStyle(DARK_LABEL_STYLE);
        grid.add(a1Label, 0, row);
        grid.add(address1Field, 1, row, 3, 1); // Span 3 columns

        row++; // row = 5

        // --- 6. Address Line 2 (Row 5) ---
        TextField address2Field = new TextField();
        applyInputStyles(address2Field);
        address2Field.setPromptText("Apt, Building, or P.O. Box (Optional)");

        Label a2Label = new Label("Address Line 2"); a2Label.setStyle(DARK_LABEL_STYLE);
        grid.add(a2Label, 0, row);
        grid.add(address2Field, 1, row, 3, 1); // Span 3 columns

        row++; // row = 6

        // --- 7. Region/Country & Gender (Row 6) ---

        // Region Dropdown
        ComboBox<String> countryBox = new ComboBox<>();
        countryBox.getItems().addAll("Oromiyaa", "Sidama", "Amhara", "Tigray", "Other");
        countryBox.setPromptText("Select Region");
        countryBox.setMaxWidth(Double.MAX_VALUE); // Make it fill the cell
        applyInputStyles(countryBox);

        // Gender Radio Buttons
        Label genderLabel = new Label("Gender"); genderLabel.setStyle(DARK_LABEL_STYLE);
        RadioButton male = new RadioButton("Male");
        RadioButton female = new RadioButton("Female");
        ToggleGroup genderGroup = new ToggleGroup();
        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);
        HBox genderBox = new HBox(20, male, female);
        genderBox.setAlignment(Pos.CENTER_LEFT);

        Label regionLabel = new Label("Your Region"); regionLabel.setStyle(DARK_LABEL_STYLE);
        grid.add(regionLabel, 0, row);
        grid.add(countryBox, 1, row);
        grid.add(genderLabel, 2, row);
        grid.add(genderBox, 3, row);

        row++; // row = 7

        // --- 8. Membership Type (Row 7) ---
        Label memberLabel = new Label("Membership Type"); memberLabel.setStyle(DARK_LABEL_STYLE);
        grid.add(memberLabel, 0, row);
        RadioButton basic = new RadioButton("Basic");
        RadioButton premium = new RadioButton("Premium");
        RadioButton elite = new RadioButton("Elite");
        ToggleGroup membershipGroup = new ToggleGroup();
        basic.setToggleGroup(membershipGroup);
        premium.setToggleGroup(membershipGroup);
        elite.setToggleGroup(membershipGroup);
        HBox membershipBox = new HBox(25, basic, premium, elite);
        grid.add(membershipBox, 1, row, 3, 1);

        row++; // row = 8

        // --- 9. Favorite Sports (Row 8) ---
        Label interestsLabel = new Label("Favorite Sports"); interestsLabel.setStyle(DARK_LABEL_STYLE);
        CheckBox football = new CheckBox("Football");
        CheckBox basketball = new CheckBox("Basketball");
        CheckBox soccer = new CheckBox("Soccer"); // Original Checkbox name is kept

        // Group checkboxes horizontally for better space usage
        HBox interestsBox = new HBox(25, football, basketball, soccer);
        grid.add(interestsLabel, 0, row);
        grid.add(interestsBox, 1, row, 3, 1);

        row++; // row = 9

        // --- 10. Talent/Award Reason (Row 9) ---
        Label reasonLabel = new Label("Talent & Awards"); reasonLabel.setStyle(DARK_LABEL_STYLE);

        TextArea reasonArea = new TextArea();
        applyInputStyles(reasonArea);
        reasonArea.setPrefRowCount(3);
        reasonArea.setPromptText("Please mention your talent and individual award you achieved so far (e.g., 5000m long-distance runner, gold medal in regional youth championships)");
        reasonArea.setWrapText(true); // Ensure text wraps nicely
        grid.add(reasonLabel, 0, row);
        grid.add(reasonArea, 1, row, 3, 1);

        row++; // row = 10

        // --- 11. Terms Checkbox (Row 10) ---
        CheckBox termsBox = new CheckBox("I agree to the terms and conditions.");
        termsBox.setStyle("-fx-font-size: 13px;");
        GridPane.setMargin(termsBox, new Insets(10, 0, 0, 0)); // Extra top margin
        grid.add(termsBox, 1, row, 3, 1);

        row++; // row = 11

        // --- 12. Submit Button & Status (Row 11 & 12) ---
        Button submitBtn = new Button("Submit Registration");
        // Styled button for a modern, click-worthy look
        submitBtn.setStyle("-fx-background-color: #1a73e8; " +
                           "-fx-text-fill: white; " +
                           "-fx-font-size: 16px; " +
                           "-fx-padding: 10 30; " +
                           "-fx-border-radius: 8px; " +
                           "-fx-background-radius: 8px; " +
                           "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0.0, 0, 2);"); // Added button shadow

        Label statusLabel = new Label();
        statusLabel.setTextFill(Color.web("#28a745")); // Success color (Green)
        statusLabel.setStyle("-fx-font-weight: bold;");

        HBox buttonContainer = new HBox(20, submitBtn, statusLabel);
        buttonContainer.setAlignment(Pos.CENTER_LEFT);
        GridPane.setMargin(buttonContainer, new Insets(15, 0, 0, 0));

        grid.add(buttonContainer, 1, row, 3, 1);

        row++; // row = 12

        // --- 13. Login Link (Row 13) ---
        Hyperlink loginLink = new Hyperlink("Already have an account? Login here");
        loginLink.setStyle("-fx-text-fill: #1a73e8; -fx-font-weight: bold;");
        GridPane.setMargin(loginLink, new Insets(5, 0, 0, 0));
        grid.add(loginLink, 1, row, 3, 1);


        // --- 14. Core Logic (Updated with validation call and reset form) ---
        submitBtn.setOnAction(e -> {
            // Call the new comprehensive validation method
            if (!validateForm(firstNameField, lastNameField, emailField, phoneField,
                              address1Field, countryBox, genderGroup, membershipGroup,
                              reasonArea, termsBox)) {
                // If validation fails, the method already showed an alert and applied error styles
                statusLabel.setTextFill(Color.web("#dc3545"));
                statusLabel.setText("Registration failed due to validation errors.");
                return;
            }

            // --- SUCCESSFUL REGISTRATION PATH ---

            // Core part: Keep data extraction identical
            String gender = ((RadioButton) genderGroup.getSelectedToggle()).getText();
            String memberType = ((RadioButton) membershipGroup.getSelectedToggle()).getText();
            String sports = (football.isSelected() ? "Football " : "") +
                            (basketball.isSelected() ? "Basketball " : "") +
                            (soccer.isSelected() ? "Soccer " : "");

            // Core part: The DatabaseManager.insertMember call must not be changed.
            // DatabaseManager is assumed to exist in the same package.
            DatabaseManager.insertMember(
                firstNameField.getText(), lastNameField.getText(),
                emailField.getText(), phoneField.getText(),
                address1Field.getText(), address2Field.getText(),
                countryBox.getValue(), gender, memberType,
                sports, reasonArea.getText(), true
            );
            
            // 1. Reset the form fields
            resetForm(firstNameField, lastNameField, emailField, phoneField, address1Field, address2Field, 
                      countryBox, genderGroup, membershipGroup, 
                      football, basketball, soccer, reasonArea, termsBox);

            // 2. Display success message
            statusLabel.setTextFill(Color.web("#28a745"));
            statusLabel.setText("Registration successfully submitted!");
        });

        loginLink.setOnAction(e -> new LoginForm(stage));

        // --- 15. Scene Setup ---
        // Use a StackPane as the root to center the card on a light, subtle background
        StackPane root = new StackPane(grid);
        root.setStyle("-fx-background-color: #e3e8ee;"); // Slightly darker background for better card contrast

        stage.setTitle("Talent Registration Form");
        stage.setScene(new Scene(root, 900, 750)); // Adjusted size for better card visibility
        stage.show();
    }
}
