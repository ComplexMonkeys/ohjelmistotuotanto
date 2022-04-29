package org.mokkivaraus.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.mokkivaraus.Palvelu;
import org.mokkivaraus.Varaus;

import java.sql.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class lisaaVarausPalveluIkkunaController {

    @FXML
    private Button btPeruuta;

    @FXML
    private Button btTallenna;

    @FXML
    private ListView<String> listPalvelu;

    @FXML
    private Spinner<Integer> spLkm;

    @FXML
    private TextField tfAsiakasId;
    private int currentValue;

    int mokkiId;
    LocalDateTime aloitusPvm;
    LocalDateTime lopetusPvm;
    
    @FXML
    void btPeruutaAction(ActionEvent event) {
    }

    @FXML
    void btTallennaAction(ActionEvent event) throws Exception{
        int asiakasId = Integer.parseInt(tfAsiakasId.getText());
        DateTimeFormatter mysqlFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime dateTimeEnd = LocalDateTime.now().plusDays(7);
        

        Connection con = DriverManager.getConnection(
                    // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
                    "jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            Statement stmt = con.createStatement();
            // Määrittää SQL komennon ja lähettää sen tietokannalle.
            stmt.executeUpdate(
                    "INSERT INTO varaus (asiakas_id, mokki_mokki_id, varattu_pvm, vahvistus_pvm, varattu_alkupvm, varattu_loppupvm) VALUES ('"
                            + asiakasId + "','" + mokkiId + "','" + dateTime.format(mysqlFormat) + "','" + dateTimeEnd.format(mysqlFormat) + "','" + aloitusPvm + "','"
                            + lopetusPvm + "');");
                
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

    public void initdata(int a, LocalDateTime b, LocalDateTime c){
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);
        valueFactory.setValue(0);
        spLkm.setValueFactory(valueFactory);
        currentValue = spLkm.getValue();
        spLkm.valueProperty().addListener(new ChangeListener<Integer>() {
            public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2){
                currentValue = spLkm.getValue();
                tfAsiakasId.setText(Integer.toString(currentValue));
            }
        });
        mokkiId = a;
        aloitusPvm = b;
        lopetusPvm = c;
        try {
            listPalvelu.setItems(haeLista());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public ObservableList<String> haeLista() throws SQLException{
        ObservableList<String> palvelut = FXCollections.observableArrayList();
        int alueId = 0;

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try{
            Statement stmt = con.createStatement();
            ResultSet set = stmt.executeQuery("SELECT alue_id FROM mokki WHERE mokki_id = '" + mokkiId + "' ;");
            if (set.next()){
                alueId = set.getInt(1);
            }

            ResultSet set2 = stmt.executeQuery("SELECT * FROM palvelu WHERE alue_id = '" + alueId + "' ;");
            while (set2.next()){
                Palvelu tempPalvelu = new Palvelu(set2.getInt(1), set2.getInt(2), set2.getString(3), set2.getInt(4), set2.getString(5), set2.getDouble(6), set2.getDouble(7));
                palvelut.add(tempPalvelu.getNimi());
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally{
            con.close();
        }

        return palvelut;
    }

}
