package org.mokkivaraus;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
* Dymaaninen kommentti sovelluksen käynnistävästä luokasta.
*/
public class Mokinvaraus extends Application {
    private static Scene scene;

    
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("alkuIkkuna"), 640, 480);
        stage.setScene(scene);
        stage.setTitle("Aloitus");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Mokinvaraus.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
