package Core;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author Octogonapus
 *
 * Ignore this for now.
 */

public class ConsoleDialogController implements Initializable {
    @FXML
    private TextArea consoleTextArea;
    @FXML
    private TextField consoleTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //
    }

    @FXML
    private void goButtonPressed(ActionEvent actionEvent) {
        try {
            Object o = DataManager.class.getDeclaredMethod("getTeams").invoke(null);
            System.out.println(Class.forName("DataManager").getName());
            Object i = ArrayList.class.getDeclaredMethod("size").invoke(o);
            System.out.println(DataManager.class.getDeclaredMethod("getTeams").getGenericReturnType().getTypeName());
            System.out.println(i);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
