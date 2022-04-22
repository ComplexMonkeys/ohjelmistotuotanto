package org.mokkivaraus.controller;
import org.mokkivaraus.Mokinvaraus;
import org.mokkivaraus.Mokki;

import java.io.*;
import java.sql.*;
import java.util.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;

public class varausIkkunaController{

    @FXML
    private Button btPaluu;

    @FXML
    private Button btVaraus;

    @FXML
    private Button btPaivita;

    @FXML
    private TableView<Mokki> tvMokit;

    @FXML
    private TableColumn<Mokki, Integer> cAlue;

    @FXML
    private TableColumn<Mokki, Integer> cHenkilomaara;

    @FXML
    private TableColumn<Mokki, Integer> cMokkiId;

    @FXML
    private TableColumn<Mokki, String> cMokkiNimi;

    @FXML
    private DatePicker dpAloitus;

    @FXML
    private DatePicker dpLopetus;

    Mokki valittu;
            
    public void paivitaLista() {
        cAlue.setCellValueFactory(new PropertyValueFactory<Mokki, Integer>("alue_id"));
        cHenkilomaara.setCellValueFactory(new PropertyValueFactory<Mokki, Integer>("henkilomaara"));
        cMokkiId.setCellValueFactory(new PropertyValueFactory<Mokki, Integer>("mokki_id"));
        cMokkiNimi.setCellValueFactory(new PropertyValueFactory<Mokki, String>("mokkinimi"));
        try {
            tvMokit.getItems().setAll(haeMokkiLista());
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btVarausAction(ActionEvent event) {
        TextInputDialog td = new TextInputDialog("Anna asiakas-id!");
        td.showAndWait();
        int asiakasId = Integer.parseInt(td.getEditor().getText());
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

    private List<Mokki> haeMokkiLista() throws SQLException {
        List<Mokki> lista = new ArrayList<>();
        // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            Statement stmt = con.createStatement();
            // Määrittää SQL komennon ja lähettää sen tietokannalle.
            ResultSet rs = stmt.executeQuery("select * from mokki");
            // Lisää kaikki taulukossa olevien alkioiden tiedot listaan.
            while (rs.next()) {
                Mokki tempmokki = new Mokki(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getDouble(6), rs.getString(7), rs.getInt(8), rs.getString(9));
                lista.add(tempmokki);
            }
            // Nappaa poikkeukset ja tulostaa ne.
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            // Yhteys tietokantaan suljetaan.
            con.close();
        }
        return lista;

    }

    /** 
    // haeLista-metodi, joka luo listan olioista näytettäväksi taulukkoon.
    private List<Varaus> haeVarausLista() throws SQLException {
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
        } finally {
            // Yhteys tietokantaan suljetaan.
            con.close();
        }
        return lista;
        

    }*/
}
