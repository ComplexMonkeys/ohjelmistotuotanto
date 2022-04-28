package org.mokkivaraus.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
            int varauksenid = Integer.parseInt(labelId.getText());
            int asiakkaanid = Integer.parseInt(tfAsiakas.getText());
            String aloituspvm = dpAloitus.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String lopetuspvm = Lopetus.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String vahvistuspvm = dpVahvistus.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int mokinid = Integer.parseInt(tfMokki.getText());

            stmt.executeUpdate("UPDATE varaus set asiakas_id = "+ asiakkaanid +", mokki_mokki_id = "+ mokinid +" , vahvistus_pvm = '"+ vahvistuspvm +"' , varattu_alkupvm = '"+ aloituspvm +"' , varattu_loppupvm = '"+ lopetuspvm +"' where varaus_id = "+ varauksenid +";");
        }
        catch(Exception e){
            System.out.println(e);
            Alert constraitAlert = new Alert(AlertType.ERROR);
            constraitAlert.setHeaderText("Jotain meni vikaan");
            constraitAlert.setContentText("Tarkista, että asiakas ja mökki ovat olemassa");
            constraitAlert.showAndWait();

        }
        finally{
            con.close();
        }
        Stage stage = (Stage) btTallenna.getScene().getWindow();
        stage.close();
    }
    public void initdata(int varausId, String Varattupvm, int mokkiId, int asiakasId) {
        labelId.setText(Integer.toString(varausId));
        labelPvm.setText(Varattupvm);
        tfMokki.setText(Integer.toString(mokkiId));
        tfAsiakas.setText(Integer.toString(asiakasId));
        }
}
