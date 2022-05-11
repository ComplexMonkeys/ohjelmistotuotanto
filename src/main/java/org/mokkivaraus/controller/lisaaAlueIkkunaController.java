package org.mokkivaraus.controller;

import java.sql.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.*;

public class lisaaAlueIkkunaController {

    @FXML
    private Button btPeruuta;

    @FXML
    private Button btTallenna;

    @FXML
    private TextField tfAlueNimi;

    /*
    * peruuta nappi, joka vie takaisin alueikkunaan
    */
    @FXML
    void btPeruutaAction(ActionEvent event) {
        Stage stage = (Stage) btPeruuta.getScene().getWindow();
        stage.close();
    }
    /*
    * tallenna nappi, joka tallentaa käyttäjän syöttämän alueen tietokantaan.
    */
    @FXML
    void btTallennaAction(ActionEvent event) throws SQLException {
        if (tfAlueNimi.getText() != "") {
        // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
            try {
                Statement stmt = con.createStatement();
                String alueNimi = tfAlueNimi.getText();
                // Määrittää SQL komennon ja lähettää sen tietokannalle.
                stmt.executeUpdate("INSERT INTO alue (nimi) VALUE ('" + alueNimi + "');");
                
                // Nappaa poikkeukset ja tulostaa ne.
            } catch (SQLException e) {
                System.out.println(e);
            }
            finally{
                // Yhteys tietokantaan suljetaan komennon jälkeen.
                con.close();
            }
            Stage stage = (Stage) btTallenna.getScene().getWindow();
            stage.close();
        }

    }
}
