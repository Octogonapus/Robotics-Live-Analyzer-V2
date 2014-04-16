package Core;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Octogonapus
 */

public class EditTeamDialogController implements Initializable {
    /**
     * Tabs
     */
    @FXML
    private TabPane mainPane;
    @FXML
    private Tab tabOne, tabTwo;

    /**
     * Tab One
     */
    @FXML
    private TextField teamNameTextField;
    @FXML
    private CheckBox autoFlag, hangFlag;
    @FXML
    private LineChart<Number, Number> teamGraph;
    @FXML
    private NumberAxis xAxis, yAxis;
    private XYChart.Series series;

    /**
     * Tab Two
     */
    @FXML
    private HTMLEditor teamNotesEditor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * Tabs
         */
        mainPane.getSelectionModel().selectedItemProperty().addListener((observableValue, tab, tab2) -> {
            if (tab2 == tabOne)
                DataManager.setTeamNotes(teamNameTextField.getText(), teamNotesEditor.getHtmlText());
            else if (tab2 == tabTwo)
                teamNotesEditor.setHtmlText(DataManager.getTeam(teamNameTextField.getText()).getTeamNotes());
        });

        /**
         * Tab One
         */
        teamNameTextField.setEditable(false);
        teamNameTextField.setText(Controller.currentTeamSelection);
        autoFlag.setSelected(DataManager.getTeam(Controller.currentTeamSelection).getAutoFlag());
        hangFlag.setSelected(DataManager.getTeam(Controller.currentTeamSelection).getHangFlag());
        autoFlag.selectedProperty().addListener((observableValue, aBoolean, aBoolean2) -> DataManager.getTeam(Controller.currentTeamSelection).setAutoFlag(aBoolean2));
        hangFlag.selectedProperty().addListener((observableValue, aBoolean, aBoolean2) -> DataManager.getTeam(Controller.currentTeamSelection).setHangFlag(aBoolean2));

        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        teamGraph = new LineChart<>(xAxis, yAxis);
        series = new XYChart.Series<>();
        series.setName(Controller.currentTeamSelection + " Rating");
        populateTeamGraph();
    }

    @FXML
    private void deleteTeamButtonPressed(ActionEvent actionEvent) {
        DataManager.removeTeam(Controller.currentTeamSelection);
    }

    private void populateTeamGraph() {
        //
    }
}
