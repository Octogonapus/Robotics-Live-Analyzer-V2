package Core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Octogonapus
 */

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainGUI.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("RoboDogs Live Analyzer V2");
        primaryStage.setScene(new Scene(root, 1700, 961));
        primaryStage.setResizable(true);
        primaryStage.show();
        Controller.setupApp();
    }

    public static void main(String[] args) {
        launch(args);
    }
}