
package membershipapp;

import javafx.application.Application;
import javafx.stage.Stage;

public class MembershipApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        new RegistrationForm(primaryStage);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
