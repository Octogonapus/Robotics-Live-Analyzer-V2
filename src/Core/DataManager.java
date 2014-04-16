package Core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Octogonapus
 */

public class DataManager {
    private static ArrayList<Match> matches = new ArrayList<>(0);
    private static ArrayList<Team> teams = new ArrayList<>(0);
    public static String fileName = null;

    public static void setupMainFolder() throws IOException {
        String staringPath = System.getProperty("user.home") + Controller.fileSeparator + "RoboDogs Live Analyzer";
        DirectoryTools.makeDirectory(staringPath, "", false);
        DirectoryTools.makeDirectory(staringPath, "Logs", true);
    }

    public static void openAndLoadFile(File file) {
        matches.addAll(XOMHandler.loadMatches(file));
        teams.addAll(XOMHandler.loadTeams(file));
        DataManager.fileName = file.getName().replace(".xml", "");
    }

    public static void saveFile() {
        XOMHandler.save(matches, teams, DataManager.fileName);
    }

    public static void saveFile(String fileName) {
        DataManager.fileName = fileName;
        XOMHandler.save(matches, teams, fileName);
        Controller.firstSave = false;
    }

    public static int getTeamIndex(String teamName) {
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getTeamName().equals(teamName))
                return i;
        }
        return -1;
    }

    public static Team getTeam(String teamName) {
        for (Team element : teams) {
            if (element.getTeamName().equals(teamName))
                return element;
        }
        return null;
    }

    public static int getMatchIndex(String matchName) {
        for (int i = 0; i < matches.size(); i++) {
            if (matches.get(i).getMatchName().equals(matchName))
                return i;
        }
        return -1;
    }

    public static Match getMatch(String matchName) {
        for (Match element : matches)
            if (element.getMatchName().equals(matchName))
                return element;
        return null;
    }

    public static Match getMatch(int index) {
        return matches.get(index);
    }

    public static Match getLastMatch() {
        if (matches.size() == 0)
            return null;
        else
            return matches.get(matches.size() - 1);
    }

    public static int getMatchCount(String teamName) {
        int total = 0;
        for (Match element : matches)
            if (element.getRedAlliance1().equals(teamName) || element.getRedAlliance2().equals(teamName) || element.getBlueAlliance1().equals(teamName) || element.getBlueAlliance2().equals(teamName))
                total++;
        return total;
    }

    public static ArrayList<Team> getTeams() {
        return teams;
    }

    public static void newTeam(String teamName) {
        teams.add(new Team(teamName));
    }

    public static void setAutoFlag(String teamName, boolean autoFlag) {
        getTeam(teamName).setAutoFlag(autoFlag);
    }

    public static void setHangFlag(String teamName, boolean hangFlag) {
        getTeam(teamName).setHangFlag(hangFlag);
    }

    public static void setTeamNotes(String teamName, String teamNotes) {
        getTeam(teamName).setTeamNotes(teamNotes);
    }

    public static void removeTeam(String teamName) {
        teams.remove(getTeam(teamName));
    }

    public static ArrayList<Match> getMatches() {
        return matches;
    }

    public static void newMatch(String matchName) {
        matches.add(new Match(matchName));
    }

    public static void setMatchName(String matchName, String newMatchName) {
        matches.get(getMatchIndex(matchName)).setMatchName(newMatchName);
    }

    public static void setRedAlliance1(String matchName, String allianceName) {
        getMatch(matchName).setRedAlliance1(allianceName);
    }
    public static void setRedAlliance2(String matchName, String allianceName) {
        getMatch(matchName).setRedAlliance2(allianceName);
    }
    public static void setBlueAlliance1(String matchName, String allianceName) {
        getMatch(matchName).setBlueAlliance1(allianceName);
    }
    public static void setBlueAlliance2(String matchName, String allianceName) {
        getMatch(matchName).setBlueAlliance2(allianceName);
    }
    public static void setRedScore(String matchName, String score) {
        getMatch(matchName).setRedScore(score);
    }
    public static void setBlueScore(String matchName, String score) {
        getMatch(matchName).setBlueScore(score);
    }

    public static void removeMatch(String matchName) {
        matches.remove(getMatch(matchName));
    }
}
