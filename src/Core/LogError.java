package Core;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * @author Octogonapus
 */

public class LogError {
    private static FileHandler fileHandler;
    private static boolean doClose = true;

    private LogError() { }

    /**
     * Open a new logging session.
     */
    public static void openSession() {
        try {
            fileHandler = new FileHandler(Controller.mainDirectory.toString() + Controller.fileSeparator + "Logs" + Controller.fileSeparator + "Data", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Log a new error.
     *
     * @param level The severity level of the error
     * @param msg   The message to be logged with the error
     */
    public static void log(Level level, String msg) {
        fileHandler.publish(new LogRecord(level, msg));
    }

    /**
     * Close the current logging session.
     */
    public static void closeSession() {
        if (doClose) {
            fileHandler.close();
            doClose = false;
        }
    }
}