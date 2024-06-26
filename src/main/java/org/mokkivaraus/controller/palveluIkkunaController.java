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

public class palveluIkkunaController implements Initializable {

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
    private TableColumn<Palvelu, String> cAlue;

    @FXML
    private TableColumn<Palvelu, Double> cHinta;

    @FXML
    private TableColumn<Palvelu, Integer> cPalveluId;

    @FXML
    private TableColumn<Palvelu, String> cPalveluNimi;

    @FXML
    private HBox hbNapit;

    @FXML
    private TableView<Palvelu> tvPalvelut;

    Palvelu valittu;
    Palvelu tulostus;

   /** 
     * metodi, joka päivittää palvelulistan
     */

    public void paivitaPalvelulista(){
        try{
            tvPalvelut.getItems().setAll(haePalvelulista());
        }catch(SQLException e){
                e.printStackTrace();
            }
        
    }

    
    /** 
     * metodi, joka avaa lisää palveluikkunan
     */
    @FXML
    void btLisaaAction(ActionEvent event) {
        Parent root;
        try{
            root = FXMLLoader.load(Mokinvaraus.class.getResource("lisaaPalveluIkkuna.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Lisää palvelu");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHiding(sulku -> {
                try {
                    tvPalvelut.getItems().setAll(haePalvelulista());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            /** 
            * Mikäli ei löydä tiedostoa, niin heittää errorin.
            */
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    
    /** 
    Tämä metodi avaa muokkaa palveluita ikkunan, jossa voi yllätys yllätys, muokata palveluita. :D
     */
    @FXML
    void btMuokkaAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Mokinvaraus.class.getResource("muokkaaPalveluIkkuna.fxml"));
        Stage stage = new Stage();
        try{
            stage.setScene(new Scene(loader.load()));
        } catch(IOException e) {
            e.printStackTrace();
        }
        muokkaaPalveluIkkunaController controller = loader.getController();
        controller.initdata(valittu.getPalvelu_id(), valittu.getAlue_id(), valittu.getAlv(), valittu.getHinta(), valittu.getKuvaus(), valittu.getNimi(), valittu.getTyyppi());
        stage.setTitle("Muokkaa palvelua");
        stage.show();
        stage.setOnHiding(sulku -> {
            try {
                tvPalvelut.getItems().setAll(haePalvelulista());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    
    /** 
     * nappi, joka päivittää palvelulistan
     */
    @FXML
    void btPaivitaAction(ActionEvent event) {
        paivitaPalvelulista();
    }

    
    /** 
     * nappi, joka vie takaisin alkuikkunaan
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
            stage.setOnHiding(sulku -> {
                try {
                    paivitaPalvelulista();
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
     * nappi, joka poistaa valitun palvelun.
     * jos tulee sql-syntaksi ongelma, niin heittää errorin
     */
    @FXML
    void btPoistaAction(ActionEvent event) throws Exception {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            Palvelu palvelu = tvPalvelut.getSelectionModel().getSelectedItem();
            try(
                Statement stmt = con.createStatement()) {
                    stmt.executeUpdate("DELETE FROM palvelu WHERE palvelu_id = " + palvelu.getPalvelu_id());
                }
                con.close();
             }
             catch (SQLException e){
                 System.out.println(e);
             }
             finally {
                 con.close();
             }
    }
    
    /** 
     * metodi, joka suoritetaan heti, kun ikkuna avataan, ja systeemi, jolla valitaan palvelu, joka voidaan esimerkiksi muokata tai poistaa.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cAlue.setCellValueFactory(new PropertyValueFactory<Palvelu, String>("alueNimi"));
        cHinta.setCellValueFactory(new PropertyValueFactory<Palvelu, Double>("hinta"));
        cPalveluId.setCellValueFactory(new PropertyValueFactory<Palvelu, Integer>("palvelu_id"));
        cPalveluNimi.setCellValueFactory(new PropertyValueFactory<Palvelu, String>("nimi"));

        try {
            tvPalvelut.getItems().setAll(haePalvelulista());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tvPalvelut.setRowFactory(tv -> {
            TableRow<Palvelu> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    tulostus = row.getItem();
                    System.out.println(tulostus);
                    Dialog<String> dialog = new Dialog<String>();
                    dialog.setTitle("Palvelu");
                    ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
                    dialog.setContentText(tulostus.tulostus());
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
     * metodi, joka hakee tietokannasta arvot taulukkoon.
     */
    private List<Palvelu> haePalvelulista() throws SQLException{
        List<Palvelu> lista = new ArrayList<>();
        // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            Statement stmt = con.createStatement();
            // Määrittää SQL komennon ja lähettää sen tietokannalle.
            ResultSet rs = stmt.executeQuery("select * from palvelu");
            // Lisää kaikki taulukossa olevien alkioiden tiedot listaan.
            while (rs.next()) {
                Palvelu tempPalvelu = new Palvelu(rs.getInt(1),rs.getInt(2),rs.getString(3),
                rs.getInt(4),rs.getString(5),rs.getDouble(6),rs.getDouble(7));
                lista.add(tempPalvelu);
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
