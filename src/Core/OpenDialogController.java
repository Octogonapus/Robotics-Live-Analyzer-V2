package Core;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * @author Octogonapus
 */

public class OpenDialogController {
    @FXML
    private Text text;
    private boolean buttonPressed;

    @FXML
    private void sameWindowButtonPressed(ActionEvent actionEvent) {
        buttonPressed = false;
    }

    @FXML
    private void newWindowButtonPressed(ActionEvent actionEvent) {
        buttonPressed = true;
    }

    public boolean getButtonPressed() {
        return buttonPressed;
    }
}
