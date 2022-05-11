package org.mokkivaraus.controller;
import java.sql.Connection;
import java.sql.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class lisaaLaskuIkkunaController {

    @FXML
    private Button btPeruuta;

    @FXML
    private Button btTallenna;

    @FXML
    private TextField tfHinta;

    @FXML
    private TextField tfVarausId;
   /*
    * peruuta nappi, joka vie takaisin laskuikkunaan
    */
    @FXML
    void btPeruutaAction(ActionEvent event) {
        Stage stage = (Stage) btPeruuta.getScene().getWindow();
        stage.close();
    }
    
    /*
    * tässä metodissa voidaan tehdä manuaalisesti lasku varaukselle.
    */
    @FXML
    void btTallennaAction(ActionEvent event) throws SQLException {
        //if-lauseessa tarkistetaan onko textfieldit tyhjiä ja jos ei ole niin edetään try-lohkoon.
        if (tfVarausId.getText() != "" && tfHinta.getText() != ""){
            //luodaan yhteys tietokantaan
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
            //try-lohkossa luodaan statement sql-lausetta varten ja lasketaan alv
            try{
                    java.sql.Statement stmt = con.createStatement();
                    int varaus_id = Integer.parseInt(tfVarausId.getText());
                    double hinta = Double.parseDouble(tfHinta.getText());
                    double alv = hinta - (hinta * 0.9);
                    //sql-lause jossa sijoitetaan lasku olion tiedot tietokantaan
                    stmt.executeUpdate("INSERT INTO lasku (varaus_id, summa, alv) VALUES ('" + varaus_id +"','" + hinta + "','" + alv + "');");
            //poikkeuksen tietojen tulostus jos textfieldit ovat tyhjiä
            } catch (Exception e){
                System.out.println(e);
            } finally {
                //suljetaan tietokantayhteys
                con.close();
            }
            //suljetaan ikkuna
            Stage stage = (Stage) btTallenna.getScene().getWindow();
            stage.close();
        }
    }

}
