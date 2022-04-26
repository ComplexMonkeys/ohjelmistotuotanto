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
            int palvelunid = Integer.parseInt(labelId.getText());
            int alue_id = Integer.parseInt(tfAlueId.getText());
            double alv = Double.parseDouble(tfAlv.getText());
            double hinta = Double.parseDouble(tfHinta.getText());
            String kuvaus = tfKuvaus.getText();
            String nimi = tfNimi.getText();
            int tyyppi = Integer.parseInt(tfTyyppi.getText());


            stmt.executeUpdate("UPDATE palvelu set palvelu_id = "+ palvelunid + ", alue_id = "+alue_id+", nimi = '"+nimi+"', alv = "+ alv +" , hinta = "+ hinta +" , tyyppi = "+ tyyppi +" , kuvaus = '"+kuvaus+"' WHERE palvelu_id = "+palvelunid+" ;");
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
    public void initdata(int palvelu_id) {
        labelId.setText(Integer.toString(palvelu_id));
    }
}
