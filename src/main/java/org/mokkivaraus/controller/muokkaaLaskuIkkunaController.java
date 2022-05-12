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
        //määritellään tietokantaan yhteys ja yhdistetään
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try{
            //luodaan statement sql-lausetta varten
            java.sql.Statement stmt = con.createStatement();
            //muutetaan laskuid textfieldin arvo integeriksi
            int laskuid = Integer.parseInt(labelId.getText());
            //muutetaan varausid textfieldin arvo integeriksi
            int varaus_id = Integer.parseInt(tfVarausId.getText());
            //muutetaan summa textfieldin arvo doubleksi
            double summa = Double.parseDouble(tfHinta.getText());

            //sql-lause päivittää tietokannan laskupöytään uuden varausidn ja summan
            stmt.executeUpdate("UPDATE lasku set varaus_id = "+varaus_id+", summa = "+summa+" WHERE lasku_id = "+laskuid+" ;");
        }
        //poikkeuksenkäsittelyä
        catch(Exception e){
            //tulostetaan error
            System.out.println(e);
            //luodaan alertbox errorille
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
    // Initdata metodi välittää edelliset arvot ikkunaan, jotta niitä voidaan muokata
    public void initdata(int lasku_id, double summa, int varaus_id) {
       labelId.setText(Integer.toString(lasku_id));
       tfHinta.setText(Double.toString(summa));
       tfVarausId.setText(Integer.toString(varaus_id));
    }

}
