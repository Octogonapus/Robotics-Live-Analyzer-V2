package Core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Octogonapus
 */

public class Dialogs {
    public static void showEditTeamDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("EditTeamDialog.fxml"));
        Parent page = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Edit Team");
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public static BetterPair2f<String, Boolean> showSaveDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("SaveDialog.fxml"));
        Parent page = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Save");
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        stage.setScene(scene);
        SaveDialogController controller = loader.getController();
        stage.showAndWait();
        return controller.getSavePackage();
    }

    public static void showRankTeamsDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("RankTeamsDialog.fxml"));
        Parent page = loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public static void showConsoleDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("ConsoleDialog.fxml"));
        Parent page = loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public static void showAddMatchDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("AddMatchDialog.fxml"));
        Parent page = loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public static boolean showOpenDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("OpenDialog.fxml"));
        Parent page = loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        stage.setScene(scene);
        OpenDialogController controller = loader.getController();
        stage.showAndWait();
        return controller.getButtonPressed();
    }
}
