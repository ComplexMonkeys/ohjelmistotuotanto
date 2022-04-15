module org.mokkivaraus {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.mokkivaraus to javafx.fxml;
    exports org.mokkivaraus;
    opens org.mokkivaraus.controller to javafx.fxml;
    exports org.mokkivaraus.controller;
}
