package Core;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Octogonapus
 */

public class RankPair {
    private SimpleStringProperty teamName = new SimpleStringProperty();
    private SimpleStringProperty winPercentage = new SimpleStringProperty();

    public RankPair() {
        this.teamName.set("");
        this.winPercentage.set("");
    }

    public RankPair(String teamName) {
        this.teamName.set(teamName);
        this.winPercentage.set("");
    }

    public RankPair(String teamName, String winPercentage) {
        this.teamName.set(teamName);
        this.winPercentage.set(winPercentage);
    }

    public String getTeamName() {
        return teamName.get();
    }

    public SimpleStringProperty teamNameProperty() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName.set(teamName);
    }

    public String getWinPercentage() {
        return winPercentage.get();
    }

    public SimpleStringProperty winPercentageProperty() {
        return winPercentage;
    }

    public void setWinPercentage(String winPercentage) {
        this.winPercentage.set(winPercentage);
    }
}
