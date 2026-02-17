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
     * Main thread of RadamesDoga, called by Launcher
     * @param stage the primary stage for this application, onto which the application scene can be set.
     * Applications may create other stages, if needed, but they will not be primary stages.
     * @throws IOException Usually if a resource can't be found or runs into permission issues.
     */
    @Override
    public void start(Stage stage) throws IOException {
        Settings settings = Settings.getInstance();

        Locale locale = Locale.of(settings.get("language"));
        ResourceBundle resourceBundle = ResourceBundle.getBundle("hiandris.radames.language.lang", locale);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        double width = bounds.getWidth() * 0.5;
        double height = bounds.getHeight() * 0.6;

        FXMLLoader fxmlLoader = new FXMLLoader(Radames.class.getResource("scenes/hello-view.fxml"));
        fxmlLoader.setResources(resourceBundle);

        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle("RadamesDoga");
        stage.setScene(scene);
        stage.setMinWidth(Math.min((bounds.getWidth() - width) / 2, 300));
        stage.setMinHeight(Math.min((bounds.getHeight() - height) / 2, 200));
        stage.setX((bounds.getWidth() - width) / 2);
        stage.setY((bounds.getHeight() - height) / 2);
        stage.show();
    }

    protected static void applyTheme(Scene scene) {

    }
}
