package org.mokkivaraus.controller;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class muokkaaPalveluIkkunaController {

    @FXML
    private Button btPeruuta;

    @FXML
    private Button btTallenna;

    @FXML
    private Label labelId;

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
    private TextField tfTyyppi;

    @FXML
    void btPeruutaAction(ActionEvent event) {
        Stage stage = (Stage) btPeruuta.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btTallennaAction(ActionEvent event) throws Exception {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try{
            java.sql.Statement stmt = con.createStatement();
            int alueenid = Integer.parseInt(labelId.getText());
            double alv = Double.parseDouble(tfAlv.getText());
            double hinta = Double.parseDouble(tfHinta.getText());
            String kuvaus = tfKuvaus.getText();
            String nimi = tfNimi.getText();
            String tyyppi = tfTyyppi.getText();


            stmt.executeUpdate("UPDATE palvelu set nimi ='"+nimi+"', tyyppi = '"+tyyppi+"', kuvaus = '"+kuvaus+"', hinta = '"+hinta+"', alv = '"+alv+"' ;");
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally {
            con.close();
        }
        Stage stage = (Stage) btTallenna.getScene().getWindow();
        stage.close();
    }
    public void initdata(int alue_id) {
        labelId.setText(Integer.toString(alue_id));
    }
}
