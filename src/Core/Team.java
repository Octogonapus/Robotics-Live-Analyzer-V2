package Core;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Octogonapus
 */

public class Team {
    private final SimpleStringProperty teamName = new SimpleStringProperty();
    private final SimpleBooleanProperty autoFlag = new SimpleBooleanProperty();
    private final SimpleBooleanProperty hangFlag = new SimpleBooleanProperty();
    private final SimpleStringProperty teamNotes = new SimpleStringProperty();

    public Team() {
        this.teamName.set("");
        this.autoFlag.set(false);
        this.hangFlag.set(false);
        this.teamNotes.set("");
    }

    public Team(String teamName) {
        this.teamName.set(teamName);
        this.autoFlag.set(false);
        this.hangFlag.set(false);
        this.teamNotes.set("");
    }

    public Team(String teamName, boolean autoFlag, boolean hangFlag, String teamNotes) {
        this.teamName.set(teamName);
        this.autoFlag.set(autoFlag);
        this.hangFlag.set(hangFlag);
        this.teamNotes.set(teamNotes);
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

    public boolean getAutoFlag() {
        return autoFlag.get();
    }

    public SimpleBooleanProperty autoFlagProperty() {
        return autoFlag;
    }

    public void setAutoFlag(boolean autoFlag) {
        this.autoFlag.set(autoFlag);
    }

    public boolean getHangFlag() {
        return hangFlag.get();
    }

    public SimpleBooleanProperty hangFlagProperty() {
        return hangFlag;
    }

    public void setHangFlag(boolean hangFlag) {
        this.hangFlag.set(hangFlag);
    }

    public String getTeamNotes() {
        return teamNotes.get();
    }

    public SimpleStringProperty teamNotesProperty() {
        return teamNotes;
    }

    public void setTeamNotes(String teamNotes) {
        this.teamNotes.set(teamNotes);
    }

    @Override
    public String toString() {
        return teamName.get();
    }
}
