package hiandris.radames;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Radames extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        double width = bounds.getWidth() * 0.5;
        double height = bounds.getHeight() * 0.6;

        FXMLLoader fxmlLoader = new FXMLLoader(Radames.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle("RadamesDoga");
        stage.setScene(scene);
        stage.setX((bounds.getWidth() - width) / 2);
        stage.setY((bounds.getHeight() - height) / 2);
        stage.show();
    }
}
