package org.mokkivaraus.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.mokkivaraus.Mokinvaraus;
import org.mokkivaraus.Posti;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class postiIkkunaController implements Initializable{

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
    private TableColumn<Posti, String> cPostinro;

    @FXML
    private TableColumn<Posti, String> cToimipaikka;

    @FXML
    private HBox hbNapit;

    @FXML
    private TableView<Posti> tvPosti;

    Posti valittu;

    
    /** 
     * Metodi avaa ikkunan postitoimipaikan lisäämiselle lisaaPostiIkkuna.fxml-tiedostosta. Ikkunan sulkeutuessa päivittää tvPosti-taulukon haeLista()-metodin mukaisesti.
     * 
     * @param event Lisää-napin painaminen
     * @throws SQLException haeLista()-metodin virhe
     * @throws IOException lisaaPostiIkkuna.fxml-tiedosta ei ole löytynyt, tarkista että tiedosto löytyy oikeasta paikasta
     */
    @FXML
    void btLisaaAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Mokinvaraus.class.getResource("lisaaPostiIkkuna.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Lisää");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHiding(sulku -> {
                try {
                    paivitaLista();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * Hakee muokkaaPostiIkkuna.fxml-tiedoston ja avaa sen pohjalta uuden ikkunan postitoimialueen muokkaamiselle luomalla uuden kontrollerin ja 
     * viemällä sille valitun Posti-olion arvot initdata()-metodin avulla. Uudelle avatulle ikkunalle asetetaan myös sulkeutumisen kuuntelija, mikä päivittää
     * tvPosti-taulun tuoreimpiin arvoihin.
     * 
     * @param event Muokkaa-painikkeen painaminen
     */
    @FXML
    void btMuokkaAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Mokinvaraus.class.getResource("muokkaaPostiIkkuna.fxml"));
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        muokkaaPostiIkkunaController controller = loader.getController();
        controller.initdata(valittu.getPostinro(), valittu.getToimipaikka());
        stage.setTitle("Muokkaa");

        stage.show();
        stage.setOnHiding(sulku -> {
            try {
                paivitaLista();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    
    /** 
     * Kutsuu paivitaLista()-metodia.
     * 
     * @param event Päivitä-painikkeen painaminen.
     */
    @FXML
    void btPaivitaAction(ActionEvent event) {
        paivitaLista();
    }

    
    /** 
     * Avaa päävalikon alkuIkkuna.fxml-tiedoston pohjalta ja sulkee nykyisen ikkunan.
     * 
     * @param event Paluu-painikkeen painaminen.
     */
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

    
    /** 
     * Ottaa yhteyden tietokantaan ja poistaa sieltä taulukosta valittuna olevan Posti-olion. Painiketta ei voi painaa jollei taulukosta ole jotakin valittuna.
     * Poiston tapahduttua päivittää listan paivitaLista()-metodia kutsumalla.
     * 
     * @param event Poista-painikkeen painaminen.
     * @throws SQLException Tietokantaan ei saada yhteyttä. Tarkista osoita, käyttäjänimi ja salasana.
     */
    @FXML
    void btPoistaAction(ActionEvent event) throws SQLException{
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            // Asettaa mokki muuttujaan valitun mökin.
            Posti posti = tvPosti.getSelectionModel().getSelectedItem();
            try (// SQL komento joka poistaa valitun mökin.
            Statement stmt = con.createStatement()) {
                stmt.executeUpdate("DELETE FROM posti WHERE postinro = " + posti.getPostinro() + ";");
            }
            con.close();
            // Päivittää listan poiston tapahduttua.
            paivitaLista();
        }
        // Nappaa SQL poikkeukset ja tulostaa ne.
        catch (SQLIntegrityConstraintViolationException integrityE){
            Alert constraitAlert = new Alert(AlertType.ERROR);
            constraitAlert.setHeaderText("Postitoimipaikkaa ei voida poistaa!");
            constraitAlert.setContentText("Toimipaikkaa ei voida poistaa, koska sille on asetettu mökkejä tai asiakkaita.");
            constraitAlert.showAndWait();
        }
        finally {
            con.close();
        }
    }

    /**
     * Kutsuu haeLista()-metodia hakeakseen listan ja asettaa listan sisällön tvPosti-taulukkon arvoiksi.
     */
    public void paivitaLista(){
        try {
            tvPosti.getItems().setAll(haeLista());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }	

    
    /** 
     * Initialize-metodi. Alustaa tvPosti-taulukon sarakkeiden arvotyypit ja asettaa taulukkoon haeLista()-metodista saamansa listan sisällön.
     * Määrittää myös hiiren kuuntelijan, joka asettaa klikatun rivin olion valittu-muuttuujaan.
     * 
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cPostinro.setCellValueFactory(new PropertyValueFactory<Posti, String>("postinro"));
        cToimipaikka.setCellValueFactory(new PropertyValueFactory<Posti, String>("toimipaikka"));
    
        try {
            tvPosti.getItems().setAll(haeLista());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        tvPosti.setRowFactory(tv -> {
            TableRow<Posti> row = new TableRow<>();
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

    
    /** 
     * Ottaa yhteyden tietokantaan ja hakee sieltä koko posti-taulun sisällön palautettavaan listaan.
     * 
     * @return List<Posti> Lista tietokantaan tallennetuista postitoimialueista.
     * @throws SQLException Tietokantaan ei saada yhteyttä. Tarkista osoita, käyttäjänimi ja salasana.
     */
    public List<Posti> haeLista() throws SQLException{
        List<Posti> lista = new ArrayList<>();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try{
                java.sql.Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM posti");
                while(rs.next()) {
                    Posti tempposti = new Posti(rs.getString(1),rs.getString(2));
                    lista.add(tempposti);
                }
                con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        finally{
            con.close();
        }
        return lista;
    }

}
