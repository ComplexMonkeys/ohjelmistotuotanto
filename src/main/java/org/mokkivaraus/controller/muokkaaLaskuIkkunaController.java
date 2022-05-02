package org.mokkivaraus.controller;

import java.sql.Connection;
import java.sql.DriverManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class muokkaaLaskuIkkunaController {

    @FXML
    private Button btPeruuta;

    @FXML
    private Button btTallenna;

    @FXML
    private Label labelId;

    @FXML
    private TextField tfHinta;

    @FXML
    private TextField tfVarausId;

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
            int laskuid = Integer.parseInt(labelId.getText());
            int varaus_id = Integer.parseInt(tfVarausId.getText());
            double summa = Double.parseDouble(tfHinta.getText());


            stmt.executeUpdate("UPDATE lasku set varaus_id = "+varaus_id+", summa = "+summa+" WHERE lasku_id = "+laskuid+" ;");
        }
        catch(Exception e){
            System.out.println(e);
            Alert constraitAlert = new Alert(AlertType.ERROR);
            constraitAlert.setHeaderText("Jotain meni vikaan");
            constraitAlert.setContentText("Tarkista, että varaus_id on olemassa");
            constraitAlert.showAndWait();
        }
        finally {
            con.close();
        }
        Stage stage = (Stage) btTallenna.getScene().getWindow();
        stage.close();
    }

    public void initdata(int lasku_id, double summa, int varaus_id) {
       labelId.setText(Integer.toString(lasku_id));
       tfHinta.setText(Double.toString(summa));
       tfVarausId.setText(Integer.toString(varaus_id));
    }

}
