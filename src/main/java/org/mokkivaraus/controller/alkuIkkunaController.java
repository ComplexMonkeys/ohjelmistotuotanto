package org.mokkivaraus.controller;

import org.mokkivaraus.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import java.io.*;

public class alkuIkkunaController {

    @FXML
    private TitledPane alkuIkkuna;

    @FXML
    private Button btAlue;

    @FXML
    private Button btMokit;

    @FXML
    private Button btAsiakas;

    @FXML
    private Button btPalvelu;

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

    @FXML
    void btAsiakasAction(ActionEvent event){
        
    }

    @FXML
    void btPalveluAction(ActionEvent event){
        
    }
}