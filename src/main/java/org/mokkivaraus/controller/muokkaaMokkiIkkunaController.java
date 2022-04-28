package org.mokkivaraus.controller;


import org.mokkivaraus.Mokki;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.*;

public class muokkaaMokkiIkkunaController {

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
    private TextField tfKuvaus; // TODO: Katso saisiko tekstin wrappaamaan

    @FXML
    private TextField tfNimi;

    @FXML
    private TextField tfOsoite;

    @FXML
    private TextField tfPostinumero;

    @FXML
    private TextField tfVarustelu;

    @FXML
    private Label labelId;

    Mokki mokki;

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
            int mokkinumero = Integer.parseInt(labelId.getText());
            
    
            // Määrittää SQL komennon ja lähettää sen tietokannalle.
            stmt.executeUpdate("UPDATE mokki set mokkinimi = '"+ nimi + "', katuosoite = '"+osoite+"', kuvaus = '"+kuvaus+"', henkilomaara = "+ maara +" , hinta = "+ hinta +" , varustelu = '"+varustelu+"' WHERE mokki_id = '"+mokkinumero+"' ;");
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

    public void initdata(int mokki_id, int alueenid, String kuvaus, String varustelu, String nimi, String postinro, String osoite, int henkilomaara, double hinta) {
        labelId.setText(Integer.toString(mokki_id));
        tfNimi.setText(nimi);
        tfAlue.setText(Integer.toString(alueenid));
        tfVarustelu.setText(varustelu);
        tfOsoite.setText(osoite);
        tfKuvaus.setText(kuvaus);
        tfPostinumero.setText(postinro);
        tfHinta.setText(Double.toString(hinta));
        tfHenkilomaara.setText(Integer.toString(henkilomaara));
    }

}
