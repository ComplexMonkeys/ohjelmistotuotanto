package org.mokkivaraus.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.mokkivaraus.Palvelu;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class lisaaVarausPalveluIkkunaController {

    @FXML
    private Button btPeruuta;

    @FXML
    private Button btTallenna;

    @FXML
    private ListView<Palvelu> listPalvelu;

    @FXML
    private TextField tfAsiakasId;

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
        mokkiId = a;
        aloitusPvm = b;
        lopetusPvm = c;
        listPalvelu.setItems(haeLista());
    }

    public ObservableList<Palvelu> haeLista(){
        ObservableList<Palvelu> palvelut = FXCollections.observableArrayList();

        // TODO: Tee lista hakemalla kaikki mökin kanssa samalla alueella olevat palvelut

        return palvelut;
    }

}
