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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;


/**
 * Controller-luokka mokitIkkuna.fxml-tiedostossa määritellylle ikkunalle. Painikkeiden ja taulukon metodit löytyvät täältä.
 */
public class mokitIkkunaController implements Initializable {

    @FXML
    private Button btLisaa;

    @FXML
    private Button btHae;

    @FXML
    private Button btMuokkaa;

    @FXML
    private Button btPaivita;

    @FXML
    private Button btPoista;

    @FXML
    private Button btPaluu;

    @FXML
    private HBox hbNapit;

    @FXML
    private TitledPane mokitIkkuna;

    @FXML
    private TableView<Mokki> tvmokit;

    @FXML
    private TableColumn<Mokki, String> cAlue;

    @FXML
    private TableColumn<Mokki, Integer> cHenkilomaara;

    @FXML
    private TableColumn<Mokki, Integer> cMokkiId;

    @FXML
    private TableColumn<Mokki, String> cMokkiNimi;

    @FXML
    private TextField tfAlue;

    /**
     * Valittu mökki, muokkaa- ja poista-napit kykenevät olemaan aktiivisia vasta kuin jokin mökki on valittuna. Saa arvonsa kun taulukon riviä klikataan hiirellä. Kuuntelija löytyy initialize()-metodista.
     */
    Mokki valittu;

    /**
     * Tulostettava mökki. Saa arvonsa tuplaklikatusta taulukon oliosta. Kuuntelija löytyy initialize()-metodista.
     */
    Mokki tulostus;

    /**
     * Metodi päivittää tvmokit-taulukon haeLista()-metodilla.
     * @throws SQLException haeLista()-metodin virhe
     */
    public void paivitaLista(){
        try {
            tvmokit.getItems().setAll(haeLista());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * Metodi avaa ikkunan mökin lisäämiselle lisaaMokkiIkkuna.fxml-tiedostosta. Ikkunan sulkeutuessa päivittää tvmokit-taulukon haeLista()-metodin mukaisesti.
     * 
     * @param event Lisää-napin painaminen
     * @throws SQLException haeLista()-metodin virhe
     * @throws IOException lisaaMokkiIkkuna.fxml-tiedosta ei ole löytynyt, tarkista että tiedosto löytyy oikeasta paikasta
     */
    @FXML
    void btLisaaAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Mokinvaraus.class.getResource("lisaaMokkiIkkuna.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Lisää mökki");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHiding(sulku -> {
                try {
                    tvmokit.getItems().setAll(haeLista());
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    
    /** 
     * Hakee muokkaaMokkiIkkuna.fxml-tiedoston ja avaa sen pohjalta uuden ikkunan mökin muokkaamiselle luomalla uuden kontrollerin ja 
     * viemällä sille valitun Mokki-olion arvot initdata()-metodin avulla. Uudelle avatulle ikkunalle asetetaan myös sulkeutumisen kuuntelija, mikä päivittää
     * tvmokit-taulun tuoreimpiin arvoihin.
     * 
     * @param event Muokkaa-painikkeen painaminen
     * @throws SQLException haeLista()-metodin virhe
     */
    @FXML
    void btMuokkaAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Mokinvaraus.class.getResource("muokkaaMokkiIkkuna.fxml"));
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        muokkaaMokkiIkkunaController controller = loader.getController();
        controller.initdata(valittu.getMokki_id(), valittu.getAlue_id(), valittu.getKuvaus(), valittu.getVarustelu(), 
        valittu.getMokkinimi(), valittu.getPostinro(), valittu.getKatuosoite(), valittu.getHenkilomaara(), valittu.getHinta());
        stage.setTitle("Muokkaa mökkiä");

        stage.show();

        stage.setOnHiding(sulku -> {
            try {
                tvmokit.getItems().setAll(haeLista());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    
    /** 
     * Kutsuu paivitaLista()-metodia.
     * 
     * @param event Päivitä-painikkeen painaminen
     */
    @FXML
    void btPaivitaAction(ActionEvent event) {
        paivitaLista();
    }

    
    /** 
     * Ottaa yhteyden tietokantaan ja poistaa sieltä valittuna olevan mökin.
     * 
     * @param event Poista-painikkeen painaminen
     * @throws SQLException Tietokantaan ei saa yhteyttä. Osoite, käyttäjänimi tai salasana ovat mitä todennäköisimmin väärin.
     */
    @FXML
    public void btPoistaAction(ActionEvent event) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            // Asettaa mokki muuttujaan valitun mökin.
            Mokki mokki = tvmokit.getSelectionModel().getSelectedItem();
            try (
                // SQL komento joka poistaa valitun mökin.
            Statement stmt = con.createStatement()) {
                stmt.executeUpdate("DELETE FROM mokki WHERE mokki_id = " + mokki.getMokki_id() + ";");
            }
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

    /**
     * Avaa päävalikon alkuIkkuna.fxml-tioedostosta ja sulkee nykyisen ikkunan.
     * 
     * @param event Paluu-painikkeen painaminen
     * @throws IOException Tiedostoa alkuIkkuna.fxml ei löydy, tarkista tiedoston sijainti.
     */
    @FXML
    public void btPaluuAction(ActionEvent event){
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

    
    /** 
     * Initialize-metodi, joka suoritetaan kun ikkuna avataan. Alustaa sarakkeet tableview-taulukolle ja hakee tiedot listasta, joka luodaan
     * haeLista-metodissa
     * 
     * Metodi myös asettaa kuuntelijat hiiren klikkauksille. Jos taulukon riviä klikataan, asetetaan se valittu-muuttujaan Mokki-olioksi ja
     * jos riviä tuplaklikataan, se asettaa rivin olion tulostus-muuttujaan ja näyttää Olion tiedot avattavassa dialogissa.
     * 
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cAlue.setCellValueFactory(new PropertyValueFactory<Mokki, String>("alueNimi"));
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
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    tulostus = row.getItem();
                    Dialog<String> dialog = new Dialog<String>();
                    dialog.setTitle("Mökki");
                    ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
                    dialog.setContentText(tulostus.toString());
                    dialog.getDialogPane().getButtonTypes().add(type);
                    dialog.showAndWait();
                }

                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {

                    valittu = row.getItem();
                    btMuokkaa.setDisable(false);
                    btPoista.setDisable(false);
                }
            });
            return row;
        });
    }

    
    /** 
     * Luo yhteyden tietokantaan ja hakee tietokannasta listan sinne tallennetuista mökeistä.
     * 
     * @return List<Mokki> Lista tietokantaan tallennetuista mökeistä.
     * @throws SQLException Tietokantaan ei saada yhteyttä. Osoite, käyttäjänimi tai salasana on väärin.
     */
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

    
    /** 
     * Tarkistaa onko tfAlue-tekstikentässä tekstiä. Jos hakukenttään ollaan kirjoitettu jotakin, metodi kutsuu haeSuodatettuLista()-metodia ja asettaa listan tvmokit-taulukkoon.
     * Jos tekstikentässä ei ole tekstiä, päivitetään taulukko paivitaLista()-metodin mukaisesti.
     * 
     * @param event Hae-painikkeen painaminen.
     * @throws SQLException haeSuodatettuLista()-metodin virhe.
     */
    @FXML
    void btHaeAction(ActionEvent event) {
        if(tfAlue.getText() != ""){
            try {
                tvmokit.getItems().setAll(haeSuodatettuLista());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            paivitaLista();
        }
    }

    
    /** 
     * Ottaa yhteyden tietokantaan ja hakee sieltä listan mökeistä jotka sijaitsevat tfAlue-tekstikentässä määritetyllä alueella. Jos tulostettu lista on tyhjä, haku on mitä luultavimmin epäonnistunut.
     * @return List<Mokki> Suodatettu lista tietyn alueen mökeistä.
     * @throws SQLException Yhteys tietokantaan epäonnistunut. Tarkista Osoite, käyttäjänimi tai salasana.
     */
    private List<Mokki> haeSuodatettuLista() throws SQLException{
        List<Mokki> lista = new ArrayList<>();
        // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            Statement stmt = con.createStatement();
            // Määrittää SQL komennon ja lähettää sen tietokannalle.
            ResultSet rs = stmt.executeQuery("SELECT * FROM mokki WHERE alue_id IN (SELECT alue_id FROM alue WHERE nimi = '" + tfAlue.getText() + "');");
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