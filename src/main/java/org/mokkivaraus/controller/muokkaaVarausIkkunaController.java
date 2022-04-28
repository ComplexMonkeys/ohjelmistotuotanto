package org.mokkivaraus.controller;

import java.sql.Connection;
import java.sql.DriverManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class muokkaaVarausIkkunaController {

    @FXML
    private DatePicker Lopetus;

    @FXML
    private Button btPeruuta;

    @FXML
    private Button btTallenna;

    @FXML
    private DatePicker dpAloitus;

    @FXML
    private DatePicker dpVahvistus;

    @FXML
    private Label labelId;

    @FXML
    private Label labelPvm;

    @FXML
    private TextField tfAsiakas;

    @FXML
    private TextField tfMokki;

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
            int asiakasId = Integer.parseInt(tfAsiakas.getText());
            int mokkiId = Integer.parseInt(tfMokki.getText());

            stmt.executeUpdate("UPDATE varaus set palvelu_id = "+ palvelunid +",asiakas_id = "+ asiakasId +",mokki_id = "+ mokkiId +";");
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally{
            con.close();
        }
    }
    public void initdata(int varausId, String vahvistusPvm) {
        labelId.setText(Integer.toString(varausId));
        labelPvm.setText(vahvistusPvm);
        }
}
