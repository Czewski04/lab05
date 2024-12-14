module lab05 {
    requires javafx.fxml;
    requires javafx.controls;

    opens org.wilczewski.gui to javafx.fxml;

    exports org.wilczewski to javafx.fxml, javafx.graphics;
    exports org.wilczewski.gui to javafx.fxml, javafx.graphics;
}