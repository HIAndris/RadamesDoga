module hiandris_radames.radamesdoga {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;

    opens hiandris_radames.radamesdoga to javafx.fxml;
    exports hiandris_radames.radamesdoga;
}