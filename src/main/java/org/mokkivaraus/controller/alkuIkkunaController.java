package org.mokkivaraus.controller;

import java.io.IOException;

import org.mokkivaraus.Mokinvaraus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

public class alkuIkkunaController {

    @FXML
    private TitledPane alkuIkkuna;

    @FXML
    private Button btAlue;

    @FXML
    private Button btMokit;

    @FXML
    void btAlueAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Mokinvaraus.class.getResource("alueIkkuna.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Aluehallinta");
            stage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Painiketta painaessa ohjelma hakee seuraavan ikkunan fxml-tiedoston, avaa uuden ikkunan sen pohjalta ja piilottaa nykyisen ikkunan
    // IOException: Jostakin syystä tiedostoa mokitIkkuna.fxml ei löydy. Tarkista tiedostopolut.
    @FXML
    void btMokitAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Mokinvaraus.class.getResource("mokitIkkuna.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Mökkien hallinta");
            stage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}