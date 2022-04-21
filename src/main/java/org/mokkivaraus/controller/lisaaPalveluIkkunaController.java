package org.mokkivaraus.controller;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class lisaaPalveluIkkunaController {

    @FXML
    private Button btPeruuta;

    @FXML
    private Button btTallenna;

    @FXML
    private TextField tfAlueId;

    @FXML
    private TextField tfAlv;

    @FXML
    private TextField tfHinta;

    @FXML
    private TextField tfKuvaus;

    @FXML
    private TextField tfNimi;

    @FXML
    private TextField tfOsoite;

    @FXML
    private TextField tfTyyppi;

    @FXML
    void btPeruutaAction(ActionEvent event) {
        Stage stage = (Stage) btPeruuta.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btTallennaAction(ActionEvent event) throws Exception {
        if (tfAlueId.getText() != "" && tfAlv.getText() != "" && tfHinta.getText() != "" && tfKuvaus.getText() != ""
        && tfNimi.getText() != "" && tfOsoite.getText() != "" && tfTyyppi.getText() != ""){

            Connection con = DriverManager.getConnection(
                // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
                "jdbc:mysql://localhost:3306/vn", "employee", "password");
            try{
                java.sql.Statement stmt = con.createStatement();
                int alueenid = Integer.parseInt(tfAlueId.getText());
                int alv = Integer.parseInt(tfAlv.getText());
                int hinta = Integer.parseInt(tfHinta.getText());
                String kuvaus = tfKuvaus.getText();
                String nimi = tfNimi.getText();
                String osoite = tfOsoite.getText();
                String tyyppi = tfTyyppi.getText();

                stmt.executeUpdate(
                    "INSERT INTO palvelu (alue_id, alv, hinta, kuvaus, nimi, osoite, tyyppi) VALUES('"+ alueenid + "','" + alv + "','" +  hinta + "','" + kuvaus + "','" + nimi + "','" + osoite + "','" + tyyppi + "');");
                
                 } catch(Exception e){
                     System.out.println(e);
                 } finally{
                     con.close();
                 }
                 Stage stage = (Stage) btTallenna.getScene().getWindow();
                 stage.close();
        }
    }

}