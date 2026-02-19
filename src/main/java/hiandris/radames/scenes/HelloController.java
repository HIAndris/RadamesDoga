package hiandris.radames.scenes;

import hiandris.radames.Radames;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloController {
    @FXML
    private Button buttonNew;

    @FXML
    private Button buttonOpen;

    @FXML
    private Button buttonSettings;

    @FXML
    private Button buttonQuit;

    @FXML
    protected void onSettingsClick(ActionEvent event) {
        try {
            FXMLLoader settingsWindow = new FXMLLoader(Radames.class.getResource("scenes/settings.fxml"));
            Parent parent = settingsWindow.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(parent, 800, 600);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Hiba az oldal betöltésekor: " + e.getMessage());
        }
    }
}
