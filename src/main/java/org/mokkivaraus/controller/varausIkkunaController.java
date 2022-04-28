package org.mokkivaraus.controller;

import org.mokkivaraus.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class varausIkkunaController implements Initializable {

    @FXML
    private Button btLisaa;

    @FXML
    private Button btMuokkaa;

    @FXML
    private Button btPaivita;

    @FXML
    private Button btPaluu;

    @FXML
    private Button btPoista;

    @FXML
    private TableColumn<Varaus, String> cAloitusPvm;

    @FXML
    private TableColumn<Varaus, Integer> cMokkiId;

    @FXML
    private TableColumn<Varaus, String> cPaatosPvm;

    @FXML
    private TableColumn<Varaus, Integer> cVarausId;

    @FXML
    private HBox hbNapit;

    @FXML
    private TableView<Varaus> tvVaraus;

    Varaus valittu;

    public void paivitaVarauslista() {
        try {
            tvVaraus.getItems().setAll(haeVarauslista());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btLisaaAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Mokinvaraus.class.getResource("lisaaVarausIkkuna.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Lisää varaus");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHiding(sulku -> {
                try {
                    paivitaVarauslista();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btMuokkaAction(ActionEvent event) {

    }

    @FXML
    void btPaivitaAction(ActionEvent event) {
        paivitaVarauslista();
    }

    @FXML
    void btPaluuAction(ActionEvent event) {
        Stage stage = (Stage) btPaluu.getScene().getWindow();
        stage.close();
        Parent root;
        try {
            root = FXMLLoader.load(Mokinvaraus.class.getResource("alkuIkkuna.fxml"));
            Stage stage2 = new Stage();
            stage2.setTitle("Aloitus");
            stage2.setScene(new Scene(root));
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btPoistaAction(ActionEvent event) throws Exception {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            // Asettaa varaus muuttujaan valitun varauksen.
            Varaus varaus = tvVaraus.getSelectionModel().getSelectedItem();
            try (
                    // SQL komento joka poistaa valitun mökin.
                    Statement stmt = con.createStatement()) {
                stmt.executeUpdate("DELETE FROM varaus WHERE varaus_id = " + varaus.getVarausId() + ";");
            }
        }
        // Nappaa SQL poikkeukset ja tulostaa ne.
        catch (SQLException e) {
            System.out.print(e);
        } finally {
            // Suljetaan yhteys tietokantaan.
            con.close();
            // Päivittää listan poiston tapahduttua.
            paivitaVarauslista();
        }
    }

    public List<Varaus> haeVarauslista() throws SQLException {
        List<Varaus> lista = new ArrayList<>();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            java.sql.Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM varaus");
            while (rs.next()) {
                Varaus tempalue = new Varaus(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7));
                lista.add(tempalue);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            con.close();
        }
        return lista;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cMokkiId.setCellValueFactory(new PropertyValueFactory<Varaus, Integer>("mokkiId"));
        cVarausId.setCellValueFactory(new PropertyValueFactory<Varaus, Integer>("VarausId"));
        cPaatosPvm.setCellValueFactory(new PropertyValueFactory<Varaus, String>("VarattuAlku"));
        cAloitusPvm.setCellValueFactory(new PropertyValueFactory<Varaus, String>("VarattuLoppu"));

        try {
            tvVaraus.getItems().setAll(haeVarauslista());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tvVaraus.setRowFactory(tv -> {
            TableRow<Varaus> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {

                    valittu = row.getItem();
                    btMuokkaa.setDisable(false);
                    btPoista.setDisable(false);
                }
            });
            return row;
        });
    }

}