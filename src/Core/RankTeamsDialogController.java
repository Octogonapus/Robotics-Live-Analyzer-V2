package Core;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author Octogonapus
 */

public class RankTeamsDialogController implements Initializable {
    @FXML
    private TableView<RankPair> rankingTableView;
    private ObservableList<RankPair> rankingTableViewItems = FXCollections.observableArrayList();
    @FXML
    private TableColumn<RankPair, String> teamNameTableColumn;
    @FXML
    private TableColumn<RankPair, String> winPercentageTableColumn;

    private Boolean autoFlagBoolean = false;
    private Boolean hangFlagBoolean = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * Table View
         */
        rankingTableView.setItems(rankingTableViewItems);
        rankingTableView.setEditable(false);

        /**
         * Table View Columns
         */
        teamNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        teamNameTableColumn.setCellFactory(TextFieldTableCell.<RankPair>forTableColumn());
        winPercentageTableColumn.setCellValueFactory(new PropertyValueFactory<>("winPercentage"));
        winPercentageTableColumn.setCellFactory(TextFieldTableCell.<RankPair>forTableColumn());

        /**
         * Populate with proper data
         */
        rankingTableViewItems.clear();
        getWinPercent();
    }

    @FXML
    private void hasAutoButtonPressed(ActionEvent actionEvent) {
        autoFlagBoolean = !autoFlagBoolean;
        decideRun();
    }

    @FXML
    private void hasHangButtonPressed(ActionEvent actionEvent) {
        hangFlagBoolean = !hangFlagBoolean;
        decideRun();
    }

    private void decideRun() {
        String combined = autoFlagBoolean.toString() + hangFlagBoolean.toString();
        switch (combined) {
            case "falsefalse" :
                getWinPercent();
                break;

            case "falsetrue" :
                getWinPercentHang();
                break;

            case "truefalse" :
                getWinPercentAuto();
                break;

            case "truetrue" :
                getWinPercentAutoHang();
                break;
        }
    }

    private void getWinPercent() {
        //List of teams that apply
        ArrayList<BetterPair2f<String, Double>> teams = new ArrayList<>(0);
        //Sort through all teams, and find only teams that apply (no auto or hang)
        //Cannot be a stream call (or can it?)
        for (Team element : DataManager.getTeams())
            if (!element.getAutoFlag() && !element.getHangFlag())
                teams.add(new BetterPair2f<>(element.getTeamName()));
        evaluate(teams);
    }

    private void getWinPercentAuto() {
        //List of teams that apply
        ArrayList<BetterPair2f<String, Double>> teams = new ArrayList<>(0);
        //Sort through all teams, and find only teams that apply (auto only)
        //Cannot be a stream call (or can it?)
        for (Team element : DataManager.getTeams())
            if (element.getAutoFlag() && !element.getHangFlag())
                teams.add(new BetterPair2f<>(element.getTeamName()));
        evaluate(teams);
    }

    private void getWinPercentHang() {
        //List of teams that apply
        ArrayList<BetterPair2f<String, Double>> teams = new ArrayList<>(0);
        //Sort through all teams, and find only teams that apply (hang only)
        //Cannot be a stream call (or can it?)
        for (Team element : DataManager.getTeams())
            if (!element.getAutoFlag() && element.getHangFlag())
                teams.add(new BetterPair2f<>(element.getTeamName()));
        evaluate(teams);
    }

    private void getWinPercentAutoHang() {
        //List of teams that apply
        ArrayList<BetterPair2f<String, Double>> teams = new ArrayList<>(0);
        //Sort through all teams, and find only teams that apply (auto and hang)
        //Cannot be a stream call (or can it?)
        for (Team element : DataManager.getTeams())
            if (element.getAutoFlag() && element.getHangFlag())
                teams.add(new BetterPair2f<>(element.getTeamName()));
        evaluate(teams);
    }

    private void evaluate(ArrayList<BetterPair2f<String, Double>> teams) {
        rankingTableViewItems.clear();
        for (BetterPair2f<String, Double> teamPair : teams) {
            teamPair.setValue(0.0);
            for (Match element : DataManager.getMatches()) {
                if (((element.getRedAlliance1().equals(teamPair.getKey()) || element.getRedAlliance2().equals(teamPair.getKey())) && (Integer.valueOf(element.getRedScore()) > Integer.valueOf(element.getBlueScore()))) || ((element.getBlueAlliance1().equals(teamPair.getKey()) || element.getBlueAlliance2().equals(teamPair.getKey())) && (Integer.valueOf(element.getBlueScore()) > Integer.valueOf(element.getRedScore())))) {
                    if (teamPair.getValue() == null)
                        teamPair.setValue(1.0);
                    else
                        teamPair.setValue(teamPair.getValue() + 1.0);
                }
            }
            teamPair.setValue(teamPair.getValue() / DataManager.getMatchCount(teamPair.getKey()));
            //Add the ranked teams to the table view
            rankingTableViewItems.add(new RankPair(teamPair.getKey(), teamPair.getValue().toString()));
        }
    }
}
