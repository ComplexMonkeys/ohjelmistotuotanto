package org.mokkivaraus.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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

    
    /** 
     * Peruuta painikkeen toiminnallisuus.
     */
    @FXML
    void btPeruutaAction() {
        Stage stage = (Stage) btPeruuta.getScene().getWindow();
        stage.close();
    }

    
    /** 
     * Tallenna painikkeen toiminnallisuus, tallentaa tietokantaan palvelun uudet tiedot
     * @throws Exception Heitää poikkeuksen, jos jokin kentistä on tyhjä.
     */
    @FXML
    void btTallennaAction() throws SQLException {
        //luodaan tietokantaan yhteys
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try{
            //luodaan statement sql-lausetta varten
            java.sql.Statement stmt = con.createStatement();
            //haetaan textfieldien arvot ja parsetetaan ne jos eivät ole merkkijonoja
            int palvelunid = Integer.parseInt(labelId.getText());
            int alue_id = Integer.parseInt(tfAlueId.getText());
            double alv = Double.parseDouble(tfAlv.getText());
            double hinta = Double.parseDouble(tfHinta.getText());
            String kuvaus = tfKuvaus.getText();
            String nimi = tfNimi.getText();
            int tyyppi = Integer.parseInt(tfTyyppi.getText());

            //sql-lause joka lisää palvelupöytään uuden alkion textfieldin arvoilla
            stmt.executeUpdate("UPDATE palvelu set palvelu_id = "+ palvelunid + ", alue_id = "+alue_id+", nimi = '"+nimi+"', alv = "+ alv +" , hinta = "+ hinta +" , tyyppi = "+ tyyppi +" , kuvaus = '"+kuvaus+"' WHERE palvelu_id = "+palvelunid+" ;");
        }
        //poikkeuksenhallinnan käsittelyä jos annettu alue_id ei ole olemassa
        catch(Exception e){
            e.printStackTrace();
            //alertin määrittely
            Alert constraitAlert = new Alert(AlertType.ERROR);
            //määritellään alertin sisältö ja otsikko
            constraitAlert.setHeaderText("Jotain meni vikaan");
            constraitAlert.setContentText("Tarkista, että alue_id on olemassa");
            constraitAlert.showAndWait();
        }
        finally {
            con.close();
        }
        Stage stage = (Stage) btTallenna.getScene().getWindow();
        stage.close();
    }
    
    /** 
     * Initdata metodi välittää edelliset arvot ikkunaan, jotta niitä voidaan muokata
     * @param palvelu_id palvelun id
     * @param alue_id alue id
     * @param alv palvelun alv
     * @param hinta palvelun hinta
     * @param kuvaus palvelun kuvaus
     * @param nimi palvelun nimi
     * @param tyyppi palvelun tyyppi
     */
    public void initdata(int palvelu_id, int alue_id, double alv, double hinta, String kuvaus, String nimi, int tyyppi) {
        labelId.setText(Integer.toString(palvelu_id));
        tfAlueId.setText(Integer.toString(alue_id));
        tfAlv.setText(Double.toString(alv));
        tfHinta.setText(Double.toString(hinta));
        tfKuvaus.setText(kuvaus);
        tfNimi.setText(nimi);
        tfTyyppi.setText(Integer.toString(tyyppi));
    }
}
