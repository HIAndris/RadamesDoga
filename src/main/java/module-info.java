module hiandris.radames {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;

    opens hiandris.radames to javafx.fxml;
    exports hiandris.radames;
}