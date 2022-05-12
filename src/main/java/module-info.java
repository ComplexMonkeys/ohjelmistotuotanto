module org.mokkivaraus {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.mail;
    requires jakarta.activation;
    

    opens org.mokkivaraus to javafx.fxml;
    exports org.mokkivaraus;
    opens org.mokkivaraus.controller to javafx.fxml;
    exports org.mokkivaraus.controller;
}
