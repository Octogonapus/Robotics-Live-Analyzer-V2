package Core;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Octogonapus
 */

public class AddMatchDialogController implements Initializable {
    @FXML
    private TextField matchCountTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //
    }

    @FXML
    private void addMatchButtonPressed(ActionEvent actionEvent) {
        Match lastMatch = DataManager.getLastMatch();
        if (lastMatch == null)
            for (int i = 1; i < Integer.valueOf(matchCountTextField.getText()) + 1; i++)
                DataManager.newMatch("Match " + i);
        else
            for (int i = Integer.valueOf(lastMatch.getMatchName().substring(6)); i < Integer.valueOf(matchCountTextField.getText()) + Integer.valueOf(lastMatch.getMatchName().substring(6)); i++)
                DataManager.newMatch("Match " + i);
    }
}
