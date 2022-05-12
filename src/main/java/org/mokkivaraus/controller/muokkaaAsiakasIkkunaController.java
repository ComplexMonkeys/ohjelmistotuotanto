package org.mokkivaraus.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.mokkivaraus.Asiakas;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;



public class muokkaaAsiakasIkkunaController {

    Asiakas asiakas; 

    @FXML
    private Button btPeruuta;

    @FXML
    private Button btTallenna;

    @FXML
    private Label labelId;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfEtunimi;

    @FXML
    private TextField tfOsoite;

    @FXML
    private TextField tfPostiNro;

    @FXML
    private TextField tfPuhNro;

    @FXML
    private TextField tfSukunimi;

    
    /** 
     * Toiminnallisuus peruuta napille. Sulkee nykyisen ikkunan.
     */
    @FXML
    void btPeruutaAction() {
    Stage stage = (Stage) btPeruuta.getScene().getWindow();
    stage.close();
    }

    
    /** 
     * Päiviä painikkeen toiminnallisuus. Päivittää asiakkaan tiedot tietokannassa.
     * @throws Exception Mikäli jotain tietoa ei anneta, tulee ongelmia.
     */
    @FXML
    void btTallennaAction() throws Exception { 
        if (tfEmail.getText() != "" && tfEtunimi.getText() != "" && tfSukunimi.getText() != ""
        && tfOsoite.getText() != "" && tfPostiNro.getText() != "" && tfPuhNro.getText() != "") {
    Connection con = DriverManager.getConnection(
            // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
            "jdbc:mysql://localhost:3306/vn", "employee", "password");
    try {
        Statement stmt = con.createStatement();
        // Haetaan tiedot tekstikentistä ja muutetaan oikeisiin muotoihin.
        String email = tfEmail.getText();
        String etunimi = tfEtunimi.getText();
        String sukunimi = tfSukunimi.getText();
        String osoite = tfOsoite.getText();
        String postinro = tfPostiNro.getText();
        String puhelin = tfPuhNro.getText();
        int asiakasnum = Integer.parseInt(labelId.getText());
        

        // Määrittää SQL komennon ja lähettää sen tietokannalle.
        stmt.executeUpdate("UPDATE asiakas set email = '"+ email + "', lahiosoite = '"+osoite+"', postinro = '"+postinro+"', etunimi = '"+ etunimi +"' , sukunimi = '"+ sukunimi +"' , puhelinnro = '"+puhelin+"' WHERE asiakas_id = "+asiakasnum+" ;");
    } catch (Exception e) {
        System.out.println(e);
        Alert constraitAlert = new Alert(AlertType.ERROR);
        constraitAlert.setHeaderText("Jotain meni vikaan");
        constraitAlert.setContentText("Tarkista, että postinro on olemassa");
        constraitAlert.showAndWait();
    } finally {
        // Yhteys tietokantaan suljetaan.
        con.close();
    }
    Stage stage = (Stage) btTallenna.getScene().getWindow();
    stage.close();
}
}
    
    /** 
     * Saa halutut kentät edellisestä ikkunasta ja asettaa ne oikeisiin kenttiin.
     * @param Asiakas_id Asiakkaan id
     * @param Email Asiakkaan sähköpostiosoite
     * @param Etunimi Asiakkaan etunimi
     * @param Sukunimi Asiakkaan sukunimi
     * @param Lahiosoite Asiakkaan osoite
     * @param Postinro Asiakkaan postinumero
     * @param Puhelinnro Asiakkaan puhelinnumero 
     */
    public void initdata(int Asiakas_id, String Email, String Etunimi, String Sukunimi, String Lahiosoite, String Postinro, String Puhelinnro) {
    labelId.setText(Integer.toString(Asiakas_id));
    tfEmail.setText(Email);
    tfEtunimi.setText(Etunimi);
    tfSukunimi.setText(Sukunimi);
    tfOsoite.setText(Lahiosoite);
    tfPostiNro.setText(Postinro);
    tfPuhNro.setText(Puhelinnro);
    }
}
