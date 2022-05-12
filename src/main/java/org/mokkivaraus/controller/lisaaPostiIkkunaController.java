package org.mokkivaraus.controller;

import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class lisaaPostiIkkunaController {

    @FXML
    private Button btPeruuta;

    @FXML
    private Button btTallenna;

    @FXML
    private TextField tfPostinumero;

    @FXML
    private TextField tfToimipaikka;

    /*
     * peruuta nappi, joka vie takaisin posti-ikkunaan
     */
    @FXML
    void btPeruutaAction(ActionEvent event) {
        Stage stage = (Stage) btPeruuta.getScene().getWindow();
        stage.close();
    }

    /*
     * tallenna nappi, joka tallentaa postinumeron ja postitoimipaikan tietokantaan
     * samalla tarkistetaan ylittäääkö postinumero pituuden ja onko toimipaikka
     * tekstikenttä tyhjä
     */
    @FXML
    void btTallennaAction(ActionEvent event) throws SQLException {
        if (tfPostinumero.getText().length() >= 5 && tfToimipaikka.getText() != "") {
            // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
            try {
                Statement stmt = con.createStatement();
                String postinumero = tfPostinumero.getText();
                String toimipaikka = tfToimipaikka.getText();

                // Määrittää SQL komennon ja lähettää sen tietokannalle.
                stmt.executeUpdate("INSERT INTO posti (postinro, toimipaikka) VALUE ('" + postinumero + "', '"
                        + toimipaikka + "');");
                // Nappaa poikkeukset ja tulostaa ne.
            } catch (DataTruncation liikaapituutta) {
                // jos postinumeron pituus on liian pitkä, niin tulee errori
                Alert constraitAlert = new Alert(AlertType.ERROR);
                constraitAlert.setHeaderText("Jotain meni vikaan");
                constraitAlert.setContentText("Postinumeron pituus ei saa ylittää viittä merkkiä!");
                constraitAlert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                // Yhteys tietokantaan suljetaan
                con.close();
            }
            Stage stage = (Stage) btTallenna.getScene().getWindow();
            stage.close();
        } else {
            Alert constraitAlert = new Alert(AlertType.ERROR);
            constraitAlert.setHeaderText("Jotain meni vikaan");
            constraitAlert.setContentText("Syötä postinumero ja toimipaikka.");
            constraitAlert.showAndWait();
        }
    }

}
