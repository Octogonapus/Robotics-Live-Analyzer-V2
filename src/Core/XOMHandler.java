package Core;

import nu.xom.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * @author Octogonapus
 */

public class XOMHandler {
    public static void save(ArrayList<Match> matches, ArrayList<Team> teams, String fileName) {
        Element root = new Element("tossUp");

        //Add teams
        for (Team element : teams) {
            Element team = new Element("team");
            team.addAttribute(new Attribute("name", element.getTeamName()));
            team.addAttribute(new Attribute("autoFlag", String.valueOf(element.getAutoFlag())));
            team.addAttribute(new Attribute("hangFlag", String.valueOf(element.getHangFlag())));
            team.addAttribute(new Attribute("notes", element.getTeamNotes()));
            root.appendChild(team);
        }

        //Add matches
        for(Match element : matches) {
            Element match = new Element("match");
            match.addAttribute(new Attribute("name", element.getMatchName()));
            match.addAttribute(new Attribute("redAlliance1", element.getRedAlliance1()));
            match.addAttribute(new Attribute("redAlliance2", element.getRedAlliance2()));
            match.addAttribute(new Attribute("blueAlliance1", element.getBlueAlliance1()));
            match.addAttribute(new Attribute("blueAlliance2", element.getBlueAlliance2()));
            match.addAttribute(new Attribute("redScore", element.getRedScore()));
            match.addAttribute(new Attribute("blueScore", element.getBlueScore()));
            root.appendChild(match);
        }

        //Finalize and print document
        Document document = new Document(root);
        try {
            if (fileName != null) {
                Serializer serializer = new Serializer(new PrintStream(Controller.mainDirectory + Controller.fileSeparator + fileName + ".xml"), "ISO-8859-1");
                serializer.setIndent(0);
                serializer.write(document);
            }
        } catch (FileNotFoundException e) {
            LogError.log(Level.SEVERE, e.toString() + "XOMHandler.save");
        } catch (IOException e) {
            LogError.log(Level.WARNING, e.toString() + "XOMHandler.save");
        }
    }

    public static ArrayList<Match> loadMatches(File file) {
        Builder builder = new Builder();
        ArrayList<Match> matches = new ArrayList<>(0);
        try {
            Document document = builder.build(file);
            Element root = document.getRootElement();
            for (int i = 0; i < root.getChildElements("match").size(); i++) {
                Element match = root.getChildElements("match").get(i);
                String matchName = match.getAttributeValue("name");
                String redAlliance1 = match.getAttributeValue("redAlliance1");
                String redAlliance2 = match.getAttributeValue("redAlliance2");
                String blueAlliance1 = match.getAttributeValue("blueAlliance1");
                String blueAlliance2 = match.getAttributeValue("blueAlliance2");
                String redScore = match.getAttributeValue("redScore");
                String blueScore = match.getAttributeValue("blueScore");
                matches.add(new Match(matchName, redAlliance1, redAlliance2, blueAlliance1, blueAlliance2, redScore, blueScore));
            }
        } catch (ParsingException e) {
            LogError.log(Level.SEVERE, e.toString() + "XOMHandler.loadMatches");
        } catch (IOException e) {
            LogError.log(Level.WARNING, e.toString() + "XOMHandler.loadMatches");
        }
        return matches;
    }

    public static ArrayList<Team> loadTeams(File file) {
        Builder builder = new Builder();
        ArrayList<Team> teams = new ArrayList<>(0);
        try {
            Document document = builder.build(file);
            Element root = document.getRootElement();
            for (int i = 0; i < root.getChildElements("team").size(); i++) {
                Element team = root.getChildElements("team").get(i);
                String teamName = team.getAttributeValue("name");
                String autoFlag = team.getAttributeValue("autoFlag");
                String hangFlag = team.getAttributeValue("hangFlag");
                String notes = team.getAttributeValue("notes");
                boolean autoFlagBool = false;
                boolean hangFlagBool = false;
                if (autoFlag.equals("true"))
                    autoFlagBool = true;
                if (hangFlag.equals("true"))
                    hangFlagBool = true;
                teams.add(new Team(teamName, autoFlagBool, hangFlagBool, notes));
            }
        } catch (ParsingException e) {
            LogError.log(Level.SEVERE, e.toString() + "XOMHandler.loadTeams");
        } catch (IOException e) {
            LogError.log(Level.WARNING, e.toString() + "XOMHandler.loadTeams");
        }
        return teams;
    }
}
