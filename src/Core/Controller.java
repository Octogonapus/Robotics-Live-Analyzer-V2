package Core;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * @author Octogonapus
 */

public class Controller implements Initializable {
    public static final String fileSeparator = System.getProperty("file.separator");
    public static File mainDirectory = new File(System.getProperty("user.home") + Controller.fileSeparator + "RoboDogs Live Analyzer");
    public static boolean firstSave = true;

    @FXML
    private BorderPane borderPane = new BorderPane();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * BorderPane Center
     */
    @FXML
    private TableView<Match> matchTableView = new TableView<>();
    private ObservableList<Match> matchTableViewItems = FXCollections.observableArrayList();
    public static String currentMatchSelection;
    @FXML
    private TableColumn<Match, String> matchNameTableColumn;
    @FXML
    private TableColumn<Match, String> redOneTableColumn;
    @FXML
    private TableColumn<Match, String> redTwoTableColumn;
    @FXML
    private TableColumn<Match, String> blueOneTableColumn;
    @FXML
    private TableColumn<Match, String> blueTwoTableColumn;
    @FXML
    private TableColumn<Match, String> redScoreTableColumn;
    @FXML
    private TableColumn<Match, String> blueScoreTableColumn;

    /**
     * BorderPane Left
     */
    @FXML
    private TextField teamNameTextField;
    @FXML
    private ListView<String> teamList = new ListView<>();
    private ObservableList<String> teamListItems = FXCollections.observableArrayList();
    private ContextMenu teamListCM = new ContextMenu();
    public static String currentTeamSelection;
    @FXML
    private TextField matchNameTextField;

    public Controller() { }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * Set sizes
         */
        System.setProperty("java.awt.headless", "false");
        borderPane.setPrefWidth(screenSize.getWidth());
        borderPane.setPrefHeight(screenSize.getHeight());

        /**
         * Team List Context menu
         */
        MenuItem tlcmItem1 = new MenuItem("Edit Team");
        MenuItem tlcmItem2 = new MenuItem("Delete Team");
        tlcmItem1.setOnAction((ActionEvent e) -> editTeamContextButtonPressed());
        tlcmItem2.setOnAction((ActionEvent e) -> deleteTeamContextButtonPressed());
        teamListCM.getItems().add(tlcmItem1);
        teamListCM.getItems().add(tlcmItem2);
        teamList.setItems(teamListItems);
        teamList.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            currentTeamSelection = teamList.getSelectionModel().getSelectedItem();
            if (e.getButton() == MouseButton.SECONDARY && teamList.getSelectionModel().getSelectedItem() != null) {
                teamListCM.show(teamList, e.getScreenX(), e.getSceneY());
            }
            if (e.getButton() == MouseButton.PRIMARY) {
                teamListCM.hide();
            }
        });

        /**
         * TableView
         */
        matchTableView.getSelectionModel().selectedItemProperty().addListener((observableValue, match, match2) -> {
            if (match2 != null)
                currentMatchSelection = match2.getMatchName();
        });
        matchTableView.setItems(matchTableViewItems);
        matchTableView.setEditable(true);

        /**
         * TableView Columns
         */
        matchNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("matchName"));
        matchNameTableColumn.setCellFactory(TextFieldTableCell.<Match>forTableColumn());
        matchNameTableColumn.setOnEditCommit((t) -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setMatchName(t.getNewValue()));

        redOneTableColumn.setCellValueFactory(new PropertyValueFactory<>("redAlliance1"));
        redOneTableColumn.setCellFactory(TextFieldTableCell.<Match>forTableColumn());
        redOneTableColumn.setOnEditCommit((t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setRedAlliance1(t.getNewValue());
            DataManager.setRedAlliance1(currentMatchSelection, t.getNewValue());
        });

        redTwoTableColumn.setCellValueFactory(new PropertyValueFactory<>("redAlliance2"));
        redTwoTableColumn.setCellFactory(TextFieldTableCell.<Match>forTableColumn());
        redTwoTableColumn.setOnEditCommit((t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setRedAlliance2(t.getNewValue());
            DataManager.setRedAlliance2(currentMatchSelection, t.getNewValue());
        });

        blueOneTableColumn.setCellValueFactory(new PropertyValueFactory<>("blueAlliance1"));
        blueOneTableColumn.setCellFactory(TextFieldTableCell.<Match>forTableColumn());
        blueOneTableColumn.setOnEditCommit((t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setBlueAlliance1(t.getNewValue());
            DataManager.setBlueAlliance1(currentMatchSelection, t.getNewValue());
        });

        blueTwoTableColumn.setCellValueFactory(new PropertyValueFactory<>("blueAlliance2"));
        blueTwoTableColumn.setCellFactory(TextFieldTableCell.<Match>forTableColumn());
        blueTwoTableColumn.setOnEditCommit((t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setBlueAlliance2(t.getNewValue());
            DataManager.setBlueAlliance2(currentMatchSelection, t.getNewValue());
        });

        redScoreTableColumn.setCellValueFactory(new PropertyValueFactory<>("redScore"));
        redScoreTableColumn.setCellFactory(TextFieldTableCell.<Match>forTableColumn());
        redScoreTableColumn.setOnEditCommit((t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setRedScore(t.getNewValue());
            DataManager.setRedScore(currentMatchSelection, t.getNewValue());
        });

        blueScoreTableColumn.setCellValueFactory(new PropertyValueFactory<>("blueScore"));
        blueScoreTableColumn.setCellFactory(TextFieldTableCell.<Match>forTableColumn());
        blueScoreTableColumn.setOnEditCommit((t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setBlueScore(t.getNewValue());
            DataManager.setBlueScore(currentMatchSelection, t.getNewValue());
        });
    }

    public static void setupApp() {
        try {
            DataManager.setupMainFolder();
            LogError.openSession();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    LogError.closeSession();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * BorderPane Left
     */
    @FXML
    private void addTeamButtonPressed(ActionEvent actionEvent) {
        String teamName = teamNameTextField.getText();
        if (teamName != null && !teamName.equals("") && !teamListItems.contains(teamName)) {
            teamListItems.add(teamName);
            DataManager.newTeam(teamName);
        }
    }

    private void deleteTeamContextButtonPressed() {
        teamListItems.remove(Controller.currentTeamSelection);
        DataManager.removeTeam(Controller.currentTeamSelection);
    }

    private void editTeamContextButtonPressed() {
        try {
            Dialogs.showEditTeamDialog();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addMatchButtonPressed(ActionEvent actionEvent) {
        String matchName = matchNameTextField.getText();
        if (matchName != null && !matchName.equals("")) {
            matchTableViewItems.add(new Match(matchName));
            DataManager.newMatch(matchName);
        }
    }

    /**
     * Menu Bar
     */

    /**
     * File
     */
    @FXML
    private void openMenuButtonPressed(ActionEvent actionEvent) {
        boolean openNewWindow = false;
        try {
            openNewWindow = Dialogs.showOpenDialog();
        } catch (IOException e) {
            LogError.log(Level.SEVERE, e.toString() + "Controller.openMenuButtonPressed");
        }
        if (openNewWindow) {
            Main.main(null);
        } else {
            Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open XML Resource File");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml", "*.xml"));
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                DataManager.openAndLoadFile(file);
                ArrayList<String> teamsLoaded = new ArrayList<>(0);
                teamsLoaded.addAll(DataManager.getTeams().stream().map(Team::toString).collect(Collectors.toList()));
                Collections.addAll(teamListItems, teamsLoaded.toArray(new String[teamsLoaded.size()]));
                Collections.addAll(matchTableViewItems, DataManager.getMatches().toArray(new Match[DataManager.getMatches().size()]));
                Controller.firstSave = false;
            }
        }
    }

    @FXML
    private void saveMenuButtonPressed(ActionEvent actionEvent) {
        if (Controller.firstSave) {
            try {
                BetterPair2f<String, Boolean> savePackage = Dialogs.showSaveDialog();
                if (savePackage.getValue()) {
                    DataManager.saveFile(savePackage.getKey());
                    Controller.firstSave = false;
                }
            } catch (IOException e) {
                LogError.log(Level.SEVERE, e.toString() + "Controller.saveMenuButtonPressed {if block}");
            }
        } else {
            DataManager.saveFile();
        }
    }

    @FXML
    private void saveAsMenuButtonPressed(ActionEvent actionEvent) {
        try {
            BetterPair2f<String, Boolean> savePackage = Dialogs.showSaveDialog();
            if (savePackage.getValue()) {
                DataManager.saveFile(savePackage.getKey());
                Controller.firstSave = false;
            }
        } catch (IOException e) {
            LogError.log(Level.SEVERE, e.toString() + "Controller.saveAsMenuButtonPressed");
        }
    }

    @FXML
    private void closeMenuButtonPressed(ActionEvent actionEvent) {
        LogError.closeSession();
        System.exit(0);
    }

    @FXML
    private void consoleMenuButtonPressed(ActionEvent actionEvent) {
        try {
            Dialogs.showConsoleDialog();
        } catch (IOException e) {
            LogError.log(Level.SEVERE, e.toString() + "Controller.consoleMenuButtonPressed");
        }
    }

    /**
     * Edit
     */
    @FXML
    private void addMatchMenuButtonPressed(ActionEvent actionEvent) {
        try {
            Dialogs.showAddMatchDialog();
            matchTableViewItems.clear();
            matchTableViewItems.addAll(DataManager.getMatches());
        } catch (IOException e) {
            LogError.log(Level.SEVERE, e.toString() + "Controller.addMatchMenuButtonPressed");
        }
    }

    @FXML
    private void deleteTeamMenuButtonPressed(ActionEvent actionEvent) {
        teamListItems.remove(Controller.currentTeamSelection);
        DataManager.removeTeam(Controller.currentTeamSelection);
    }

    @FXML
    private void deleteMatchMenuButtonPressed(ActionEvent actionEvent) {
        matchTableViewItems.remove(DataManager.getMatch(Controller.currentMatchSelection));
        DataManager.removeMatch(Controller.currentMatchSelection);
        //I have no idea why the list has to be reloaded
        matchTableViewItems.clear();
        matchTableViewItems.addAll(DataManager.getMatches());
    }

    /**
     * Analyze
     */
    @FXML
    private void rankTeamsMenuButtonPressed(ActionEvent actionEvent) {
        try {
            Dialogs.showRankTeamsDialog();
        } catch (IOException e) {
            LogError.log(Level.SEVERE, e.toString() + "Controller.rankTeamsMenuButtonPressed");
        }
    }

    /**
     * Help
     */
    @FXML
    private void aboutMenuButtonPressed(ActionEvent actionEvent) {
        //
    }
}
