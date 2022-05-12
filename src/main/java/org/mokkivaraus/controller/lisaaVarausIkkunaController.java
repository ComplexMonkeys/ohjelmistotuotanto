package org.mokkivaraus.controller;

import org.mokkivaraus.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.*;

public class lisaaVarausIkkunaController implements Initializable {

    @FXML
    private Button btPaluu;

    @FXML
    private Button btVaraus;

    @FXML
    private Button btPaivita;

    @FXML
    private TableView<Mokki> tvMokit;

    @FXML
    private TableColumn<Mokki, String> cAlue;

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
    DateTimeFormatter mysqlFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    
    /** 
     * Hakee alustavat tiedot edellisestä ikkunasta ja suoritetaan 
     * @param url kutsunut resurssi
     * @param rb resurssista tulevat asiat
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Asetetaan listan soluille niiden piirteet.
        cAlue.setCellValueFactory(new PropertyValueFactory<Mokki, String>("alueNimi"));
        cHenkilomaara.setCellValueFactory(new PropertyValueFactory<Mokki, Integer>("henkilomaara"));
        cMokkiId.setCellValueFactory(new PropertyValueFactory<Mokki, Integer>("mokki_id"));
        cMokkiNimi.setCellValueFactory(new PropertyValueFactory<Mokki, String>("mokkinimi"));
        // Asetetaan mökkien tiedot listaan.
        try {
            tvMokit.getItems().setAll(haeMokkiLista());
        // Nappaa SQL poikkeukset ja tulostaa niistä stacktracet.
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Kun riviä klikataan, siinä oleva mökki asetetaan "valittu" muuttujaan.
        tvMokit.setRowFactory(tv -> {
            TableRow<Mokki> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    valittu = row.getItem();
                }
            });
            return row;
        });
    }
    
    /** 
     * Paluu painikkeen toiminnallisuus. Palataan edelliseen ikkunaan.
     */
    @FXML
    void btPaluuAction() {
        Stage stage = (Stage) btPaluu.getScene().getWindow();
        stage.close();
    }

    
    /** 
     * Varaus painikkeen toiminnallisuus. Avaa ikkunan jossa varaukselle lisätään palvelut.
     * @throws Exception
     */
    @FXML
    void btVarausAction() throws Exception {
        // Asetetaan tarvittavia muuttujia.
        int mokkiId = valittu.getMokki_id();
        LocalDate aloitusPvm = dpAloitus.getValue();
        LocalDate lopetusPvm = dpLopetus.getValue();
        DateTimeFormatter mysqlFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        // Pyöritellään päivämäärät sopivaan muotoon.
        LocalDateTime aloitusPvmFormat = aloitusPvm.atTime(12, 0, 0);
        aloitusPvmFormat.format(mysqlFormat);
        LocalDateTime lopetusPvmFormat = lopetusPvm.atTime(12, 0, 0);
        lopetusPvmFormat.format(mysqlFormat);
        // Lataa uuden ikkunan FXML tiedoston.
        FXMLLoader loader = new FXMLLoader(Mokinvaraus.class.getResource("lisaaVarausPalveluIkkuna.fxml"));
        Stage stage = new Stage();
        // Yrittää aloittaa uuden tason.
        try {
            stage.setScene(new Scene(loader.load()));
        // Nappaa IO poikkeukset ja tulostaa niiden stacktracet.
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ladataan seuraavan ikkunan contolleri ja avataan uusi ikkuna.
        lisaaVarausPalveluIkkunaController controller = loader.getController();
        controller.initdata(mokkiId, aloitusPvmFormat, lopetusPvmFormat);
        stage.setTitle("Tee varaus");

        stage.show();
    }
    
    /** 
     * @return Lista tietyn aikavälin sisällä olevien mökkien id:istä
     * @throws SQLException Heittää poikkeuksen mikäli SQL komennollle annetut päivämäärät eivät ole oikeassa muodossa.
     */
    private List<Integer> haeSuodatettuLista() throws SQLException{
        // Pyöritellään datepickereiden tiedot oikeisiin muotoihin
        LocalDate date = dpAloitus.getValue();
        LocalDateTime startFormatted = date.atTime(12, 0, 0);
        startFormatted.format(mysqlFormat);
        LocalDate dateEnd = dpLopetus.getValue();
        LocalDateTime endFormatted = dateEnd.atTime(12, 0, 0);
        endFormatted.format(mysqlFormat);
        // Lista johon sijoitetaan mokkien id:t
        List<Integer> lista = new ArrayList<>();
        // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            Statement stmt = con.createStatement();
            // Määrittää ja suorittaa SQL komennon, jolla saadaan tietyn aikavälin sisällä olevat varaukset
            ResultSet rs = stmt.executeQuery("SELECT * FROM varaus WHERE (varattu_alkupvm BETWEEN '" + startFormatted + "' AND '" + endFormatted + "') OR (varattu_loppupvm BETWEEN '" + startFormatted + "' AND '" + endFormatted + "')OR varattu_alkupvm < '"+ startFormatted +"' AND varattu_loppupvm > '"+ endFormatted +"' ;");
            // Lisää kaikki taulukossa olevien alkioiden tiedot listaan.
            while (rs.next()) {
                Varaus tempvaraus = new Varaus(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5),
                rs.getString(6), rs.getString(7));
                // Lisätään varauksien mökki id:t listaan.
                lista.add(tempvaraus.getMokkiId());
            }
            // Nappaa poikkeukset ja tulostaa ne.
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            // Yhteys tietokantaan suljetaan.
            con.close();
        }
        return lista;

    }


/** 
 * Metodi jolla rakennetaan SQL komento rajatun mökkilistan hakemista varten.
 * @return String vaadittu SQL komento.
 */
String sqlKomento(){
    String stringOut = "";
    try {
        // Luodaan uusi stringbuilder
        StringBuilder sqlString = new StringBuilder();
        sqlString.append("SELECT * FROM mokki where");
        for (int i = 0; i < (haeSuodatettuLista().size()); i++){
         sqlString.append(" mokki_id !=" + haeSuodatettuLista().get(i));
        if(i != haeSuodatettuLista().size()-1){
            sqlString.append(" and");
        }
        }
        sqlString.append(";");
        stringOut = sqlString.toString();
        // Napataan SQL poikkeukset ja tulostetaan niiden stacktrace
     } catch (SQLException e) {
         e.printStackTrace();
     }
    return stringOut;
}


    
    /** 
     * @return List<Mokki>
     * @throws SQLException
     */
    private List<Mokki> haeRajattuMokkiLista() throws SQLException {
        List<Mokki> lista = new ArrayList<>();
        // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            Statement stmt = con.createStatement();
            // Määrittää SQL komennon ja lähettää sen tietokannalle.
            ResultSet rs = stmt.executeQuery(sqlKomento());
            // Lisää kaikki taulukossa olevien alkioiden tiedot listaan.
            while (rs.next()) {
                Mokki tempmokki = new Mokki(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getDouble(6), rs.getString(7), rs.getInt(8), rs.getString(9));
                lista.add(tempmokki);
            }
            // Nappaa poikkeukset ja tulostaa ne.
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Yhteys tietokantaan suljetaan.
            con.close();
        }
        return lista;

    }

    /**
     * Metodi jolla päivitetään listan näkymä riippuen siitä onko datepickereitä käytetty.
     */
    public void paivitaLista() {
        try {
            if (!haeSuodatettuLista().isEmpty()){
                try {
                    tvMokit.getItems().setAll(haeRajattuMokkiLista());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    tvMokit.getItems().setAll(haeMokkiLista());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }


    
    /** 
     * Päivitä napin toiminnallisuus. Päivittää listan sen nykyiseen tilaan
     */
    @FXML
    void btPaivitaAction() {
        paivitaLista();
    }

    
    /** 
     * @return List<Mokki>, lista mökeistä listaan asettamista varten.
     * @throws SQLException Heittää poikkeuksen mikäöi tietokannan mokki osio on tyhjä, tai SQL komento on virheellinen.
     */
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
}
