package hiandris.radames;

import javafx.application.Application;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;
import java.time.LocalDateTime;

/**
 * Launches RadamesDoga, creates crash log if something goes wrong
 */
public class Launcher {
    public static void main(String[] args) {
        try {
            Application.launch(Radames.class, args);
        } catch (Throwable e) {
            logError(e);
        }
    }

    private static void logError(Throwable e) {
        try {
            String home = System.getProperty("user.home");
            File logFile = new File(home, "radames_crash_log.txt");

            try (PrintWriter writer = new PrintWriter(new FileWriter(logFile, true))) {
                writer.println("--- CRASH REPORT: " + LocalDateTime.now() + " ---");
                e.printStackTrace(writer);
            }
        } catch (Exception ioException) {
            // If we get here, we have a huge problem...
            System.err.println(ioException.getMessage());
        }
    }
}
