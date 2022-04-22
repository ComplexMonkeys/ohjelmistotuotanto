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
import javafx.stage.*;

public class asiakasIkkunaController implements Initializable{

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
    private TableColumn<Asiakas, Integer> cAsiakasId;

    @FXML
    private TableColumn<Asiakas, String> cAsiakasNimi;

    @FXML
    private TableView<Asiakas> tvAsiakas;

    Asiakas valittu;

    
    public void paivitaLista(){
        try {
            tvAsiakas.getItems().setAll(haeLista());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btLisaaAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Mokinvaraus.class.getResource("lisaaAsiakasIkkuna.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Lisää asiakas");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHiding(sulku -> {
                try {
                    tvAsiakas.getItems().setAll(haeLista());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btMuokkaaAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Mokinvaraus.class.getResource("muokkaaAsiakasIkkuna.fxml"));
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        muokkaaAsiakasIkkunaController controller = loader.getController();
        controller.initdata(valittu.getAsiakas_id());
        stage.setTitle("Muokkaa asiakasta");

        stage.show();
        stage.setOnHiding(sulku -> {
            try {
                tvAsiakas.getItems().setAll(haeLista());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void btPaivitaAction(ActionEvent event) {
        paivitaLista();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cAsiakasId.setCellValueFactory(new PropertyValueFactory<Asiakas, Integer>("asiakas_id"));
        cAsiakasNimi.setCellValueFactory(new PropertyValueFactory<Asiakas, String>("nimi"));
        try {
            tvAsiakas.getItems().setAll(haeLista());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tvAsiakas.setRowFactory(tv -> {
            TableRow<Asiakas> row = new TableRow<>();
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
    void btPoistaAction(ActionEvent event) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            // Asettaa mokki muuttujaan valitun mökin.
            Asiakas asiakas = tvAsiakas.getSelectionModel().getSelectedItem();
            try (// SQL komento joka poistaa valitun mökin.
            Statement stmt = con.createStatement()) {
                stmt.executeUpdate("DELETE FROM asiakas WHERE asiakas_id = " + asiakas.getAsiakas_id() + ";");
            }
            con.close();
            // Päivittää listan poiston tapahduttua.
            paivitaLista();
        }
        // Nappaa SQL poikkeukset ja tulostaa ne.
        catch (SQLException e) {
            System.out.print(e);
        }
        finally {
            con.close();
        }
    }

    private List<Asiakas> haeLista() throws SQLException{
        List<Asiakas> lista = new ArrayList<>();
        // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            Statement stmt = con.createStatement();
            // Määrittää SQL komennon ja lähettää sen tietokannalle.
            ResultSet rs = stmt.executeQuery("select * from asiakas");
            // Lisää kaikki taulukossa olevien alkioiden tiedot listaan.
            while (rs.next()) {
                Asiakas tempasiakas = new Asiakas(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                lista.add(tempasiakas);
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
