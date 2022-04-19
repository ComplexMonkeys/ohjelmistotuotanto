package org.mokkivaraus.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.mokkivaraus.Mokinvaraus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class lisaaMokkiIkkunaController {
    
    @FXML
    private Button btPeruuta;

    @FXML
    private Button btTallenna;

    @FXML
    private TextField tfAlue;

    @FXML
    private TextField tfHenkilomaara;

    @FXML
    private TextField tfHinta;

    @FXML
    private TextField tfKuvaus;

    @FXML
    private TextField tfNimi;

    @FXML
    private TextField tfOsoite;

    @FXML
    private TextField tfPostinumero;

    @FXML
    private TextField tfVarustelu;

    @FXML
    void btPeruutaAction(ActionEvent event) {
        Stage stage = (Stage) btPeruuta.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btTallennaAction(ActionEvent event) throws Exception {
        if (tfAlue.getText() != "" && tfHenkilomaara.getText() != "" && tfHinta.getText() != ""
                && tfKuvaus.getText() != "" && tfNimi.getText() != "" && tfOsoite.getText() != ""
                && tfPostinumero.getText() != "" && tfVarustelu.getText() != "") {

            Connection con = DriverManager.getConnection(
                    // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
                    "jdbc:mysql://localhost:3306/vn", "employee", "password");
            try {
                Statement stmt = con.createStatement();
                // Haetaan tiedot tekstikentistä ja muutetaan oikeisiin muotoihin.
                int alueenid = Integer.parseInt(tfAlue.getText());
                String postinro = tfPostinumero.getText();
                String nimi = tfNimi.getText();
                String osoite = tfOsoite.getText();
                Double hinta = Double.parseDouble(tfHinta.getText());
                String kuvaus = tfKuvaus.getText();
                int maara = Integer.parseInt(tfHenkilomaara.getText());
                String varustelu = tfVarustelu.getText();

                // Määrittää SQL komennon ja lähettää sen tietokannalle.
                stmt.executeUpdate(
                        "INSERT INTO mokki (alue_id, postinro, mokkinimi, katuosoite, hinta, kuvaus, henkilomaara, varustelu) VALUES ('"
                                + alueenid + "','" + postinro + "','" + nimi + "','" + osoite + "','" + hinta + "','"
                                + kuvaus + "','" + maara + "','" + varustelu + "');");
                // TODO: Lisää tähän tvmokit listan päivitys jos mahdollista?
                
                // Nappaa poikkeukset ja tulostaa ne.
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                // Yhteys tietokantaan suljetaan.
                con.close();
            }
            Stage stage = (Stage) btTallenna.getScene().getWindow();
            stage.close();
        }
    }

}
