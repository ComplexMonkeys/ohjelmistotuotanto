package org.mokkivaraus.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.mokkivaraus.Mokinvaraus;
import org.mokkivaraus.Varaus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class varausIkkunaController {

    @FXML
    private Button btPaluu;

    @FXML
    private Button btVaraus;

    @FXML
    private Button btPaivita;

    @FXML
    private TableColumn<Varaus, Integer> cAlue;

    @FXML
    private TableColumn<Varaus, Integer> cHenkilomaara;

    @FXML
    private TableColumn<Varaus, Integer> cMokkiId;

    @FXML
    private TableColumn<Varaus, String> cMokkiNimi;

    @FXML
    private DatePicker dpAloitus;

    @FXML
    private DatePicker dpLopetus;

    @FXML
    private TableView<Varaus> tvVaraus;

    public void paivitaLista(){
        try {
            tvVaraus.getItems().setAll(haeVarausLista());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btPaluuAction(ActionEvent event) {
        Stage stage = (Stage) btPaluu.getScene().getWindow();
        stage.close();
        Parent root;
        try {
            root = FXMLLoader.load(Mokinvaraus.class.getResource("alkuIkkuna.fxml"));
            Stage stage2 = new Stage();
            stage2.setTitle("alkuikkuna");
            stage2.setScene(new Scene(root));
            stage2.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btVarausAction(ActionEvent event) {

    }

    @FXML
    void btPaivitaAction(ActionEvent event) {
        paivitaLista();
    }

    @FXML
    void dpAloitusAction(ActionEvent event) {

    }

    @FXML
    void dpLopetusAction(ActionEvent event) {

    }
// haeLista-metodi, joka luo listan olioista näytettäväksi taulukkoon.
private List<Varaus> haeVarausLista() throws SQLException{
    List<Varaus> lista = new ArrayList<>();
    // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
    try {
        Statement stmt = con.createStatement();
        // Määrittää SQL komennon ja lähettää sen tietokannalle.
        ResultSet rs = stmt.executeQuery("select * from varaus");
        // Lisää kaikki taulukossa olevien alkioiden tiedot listaan.
        while (rs.next()) {
            Varaus tempvaraus = new Varaus(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                    rs.getString(4), rs.getString(5), rs.getString(6));
            lista.add(tempvaraus);
        }
        // Nappaa poikkeukset ja tulostaa ne.
    } catch (Exception e) {
        System.out.println(e);
    }
    finally{
        // Yhteys tietokantaan suljetaan.
        con.close();
    }
    return lista;

}
}
