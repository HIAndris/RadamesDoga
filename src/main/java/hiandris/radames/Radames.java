package hiandris.radames;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Radames extends Application {
    /**
     * Instance of the Settings singleton class
     */
    Settings settings;

    /**
     * Main thread of RadamesDoga, called by Launcher
     * @param stage the primary stage for this application, onto which the application scene can be set.
     * Applications may create other stages, if needed, but they will not be primary stages.
     * @throws IOException Usually if a resource can't be found or runs into permission issues.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // SETUP:

        // Load settings
        settings = Settings.getInstance();

        // Load language
        Locale locale = Locale.of(settings.get("language"));
        ResourceBundle resourceBundle = ResourceBundle.getBundle("hiandris.radames.language.lang", locale);

        // Get screen properties
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // Set window width from settings if possible
        String widthStr = settings.get("windowWidth");
        double width;
        if (widthStr.isEmpty()) {
            width = bounds.getWidth() * 0.5;
        } else {
            width = Math.max(bounds.getWidth() * 0.5, Double.parseDouble(widthStr));
        }

        // Set window height from settings if possible
        String heightStr = settings.get("windowHeight");
        double height;
        if (heightStr.isEmpty()) {
            height = bounds.getHeight() * 0.6;
        } else {
            height = Math.max(bounds.getHeight() * 0.6, Double.parseDouble(heightStr));
        }

        // Load main menu scene and set its language resource bundle
        FXMLLoader fxmlLoader = new FXMLLoader(Radames.class.getResource("scenes/hello-view.fxml"));
        fxmlLoader.setResources(resourceBundle);

        // Build the scene with the calculated parameters and apply theme
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        applyTheme(scene);

        stage.setTitle("RadamesDoga");
        stage.setScene(scene);
        stage.setMinWidth(Math.min(bounds.getWidth() / 2, 300));
        stage.setMinHeight(Math.min(bounds.getHeight() / 2, 200));
        stage.setX((bounds.getWidth() - width) / 2);
        stage.setY((bounds.getHeight() - height) / 2);
        stage.show();
    }

    protected boolean applyTheme(Scene scene) {
        // Get required theme
        boolean darkMode = settings.getBoolean("darkMode");

        try {
            // Get css files
            var lightCssUrl = Radames.class.getResource("styles/light.css");
            var darkCssUrl = Radames.class.getResource("styles/dark.css");

            // Null check
            if (lightCssUrl == null || darkCssUrl == null) {
                System.err.println("Some css files are missing!");
                return false;
            }
            String lightCss = lightCssUrl.toExternalForm();
            String darkCss = darkCssUrl.toExternalForm();

            // Clear previous stylesheet
            scene.getStylesheets().remove(lightCss);
            scene.getStylesheets().remove(darkCss);

            // Apply new stylesheet
            if (darkMode) {
                scene.getStylesheets().add(darkCss);
            } else {
                scene.getStylesheets().add(lightCss);
            }

        } catch (Exception e) {
            System.err.println("Error applying theme: " + e.getMessage());
            return false;
        }
        return true;
    }
}
