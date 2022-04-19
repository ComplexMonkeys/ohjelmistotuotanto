package org.mokkivaraus.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.mokkivaraus.Mokinvaraus;
import org.mokkivaraus.Mokki;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class mokitIkkunaController implements Initializable {

    @FXML
    private Button btLisaa;

    @FXML
    private Button btMuokkaa;

    @FXML
    private Button btPaivita;

    @FXML
    private Button btPoista;

    @FXML
    private HBox hbNapit;

    @FXML
    private TitledPane mokitIkkuna;

    @FXML
    private TableView<Mokki> tvmokit;

    @FXML
    private TableColumn<Mokki, Integer> cAlue;

    @FXML
    private TableColumn<Mokki, Integer> cHenkilomaara;

    @FXML
    private TableColumn<Mokki, Integer> cMokkiId;

    @FXML
    private TableColumn<Mokki, String> cMokkiNimi;

    Mokki valittu;

    public void paivitaLista(){
        try {
            tvmokit.getItems().setAll(haeLista());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Painiketta painaessa ohjelma hakee seuraavan ikkunan fxml-tiedoston, avaa
    // uuden ikkunan sen pohjalta ja piilottaa nykyisen ikkunan
    // IOException: Jostakin syystä tiedostoa lisaaMokkiIkkuna.fxml ei löydy.
    // Tarkista tiedostopolut.
    @FXML
    void btLisaaAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Mokinvaraus.class.getResource("lisaaMokkiIkkuna.fxml"));
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btMuokkaAction(ActionEvent event) {
        // https://www.youtube.com/watch?v=V9nDH2iBJSM
        FXMLLoader loader = new FXMLLoader(Mokinvaraus.class.getResource("muokkaaMokkiIkkuna.fxml"));
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        muokkaaMokkiIkkunaController controller = loader.getController();
        controller.initdata(valittu.getMokki_id());

        stage.show();
    }

    @FXML
    void btPaivitaAction(ActionEvent event) {
        paivitaLista();
    }

    @FXML
    public void btPoistaAction(ActionEvent event) throws Exception {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            // Asettaa mokki muuttujaan valitun mökin.
            Mokki mokki = tvmokit.getSelectionModel().getSelectedItem();
            try (// SQL komento joka poistaa valitun mökin.
            Statement stmt = con.createStatement()) {
                stmt.executeUpdate("DELETE FROM mokki WHERE mokki_id = " + mokki.getMokki_id() + ";");
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

    // initialize-metodi, joka suoritetaan kun ikkuna avataan. Alustaa sarakkeet
    // tableview-taulukolle ja hakee tiedot listasta, joka luodaan
    // haeLista-metodissa
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cAlue.setCellValueFactory(new PropertyValueFactory<Mokki, Integer>("alue_id"));
        cHenkilomaara.setCellValueFactory(new PropertyValueFactory<Mokki, Integer>("henkilomaara"));
        cMokkiId.setCellValueFactory(new PropertyValueFactory<Mokki, Integer>("mokki_id"));
        cMokkiNimi.setCellValueFactory(new PropertyValueFactory<Mokki, String>("mokkinimi"));

        try {
            tvmokit.getItems().setAll(haeLista());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tvmokit.setRowFactory(tv -> {
            TableRow<Mokki> row = new TableRow<>();
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

    // haeLista-metodi, joka luo listan olioista näytettäväksi taulukkoon.
    private List<Mokki> haeLista() throws SQLException{
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
        }
        finally{
            // Yhteys tietokantaan suljetaan.
            con.close();
        }
        return lista;

    }
}