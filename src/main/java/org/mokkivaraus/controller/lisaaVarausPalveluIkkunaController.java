package org.mokkivaraus.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.mokkivaraus.Palvelu;
import org.mokkivaraus.VarauksenPalvelut;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class lisaaVarausPalveluIkkunaController {

    @FXML
    private Button btPeruuta;

    @FXML
    private Button btTallenna;

    @FXML
    private Button btPaivita;

    @FXML
    private ListView<Palvelu> listPalvelu;

    @FXML
    private Spinner<Integer> spLkm;

    @FXML
    private TextField tfAsiakasId;
    private String selected;

    ObservableList<VarauksenPalvelut> palveluLista = FXCollections.observableArrayList();
    int mokkiId;
    LocalDateTime aloitusPvm;
    LocalDateTime lopetusPvm;

    
    /** 
     * Painike takaisin edelliseen näkymään palaamiselle.
     */
    @FXML
    void btPeruutaAction() {

        Stage stage = (Stage) btPeruuta.getScene().getWindow();
        stage.close();
    }

    
    /** 
     * Luo varauksen tietokantaan listalla valituilla palveluiden määrillä ja osoitetulla asiakas id:llä.
     * @throws Exception Heittää poikkeuksen, jos sql komento on viallinen.
     */
    @FXML
    void btTallennaAction() throws Exception {
        VarauksenPalvelut varauksenPalvelut = new VarauksenPalvelut(Integer.parseInt(selected), spLkm.getValue());
        try {
            palveluLista.add(varauksenPalvelut);
        } catch (Exception e) {
            System.out.println(e);
        }

        // Määrittelee tarpeellisia muuttujia.
        int asiakasId = Integer.parseInt(tfAsiakasId.getText());
        DateTimeFormatter mysqlFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime dateTimeEnd = LocalDateTime.now().plusDays(7);

        // Luodaan yhteys tietokantaan.
        Connection con = DriverManager.getConnection(
                // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
                "jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            // Luo statementin SQL komentoa varten.
            Statement stmt = con.createStatement();
            // Määrittää SQL komennon jolla luodaan uusi varaus annetuilla tiedoilla.
            stmt.executeUpdate(
                    "INSERT INTO varaus (asiakas_id, mokki_mokki_id, varattu_pvm, vahvistus_pvm, varattu_alkupvm, varattu_loppupvm) VALUES ('"
                            + asiakasId + "','" + mokkiId + "','" + dateTime.format(mysqlFormat) + "','"
                            + dateTimeEnd.format(mysqlFormat) + "','" + aloitusPvm + "','"
                            + lopetusPvm + "');");

            // Alustaa lauseen toista SQL komentoa varten.
            Statement stmt2 = con.createStatement();
            // Määrittelee SQL komennon jolla saadaan uusimman varauksen id.
            ResultSet rs = stmt2.executeQuery("SELECT MAX(varaus_id)FROM varaus;");

            // Valitsee uusimman varauksen id:n muuttujaan.
            if (rs.next()) {
                // Asettaa yllä saadun varauksen id:n jokaiseen valittuun palveluun.
                int varauksen_id = rs.getInt(1);
                for (int i = 0; i < (palveluLista.size()); i++) {
                    VarauksenPalvelut valittuVaraus = palveluLista.get(i);
                    valittuVaraus.setVarausId(varauksen_id);
                }
                // Luo palvelulistan pohjalta uudelle varaukselle varauksen palvelut.
                for (int i = 0; i < (palveluLista.size()); i++) {
                    try {
                        // Suorittaa vaaditun SQL komennon.
                        VarauksenPalvelut valittuVaraus = palveluLista.get(i);
                        stmt.executeUpdate("INSERT INTO varauksen_palvelut (varaus_id, palvelu_id, lkm) VALUES ('"
                                + valittuVaraus.getVarausId() + "','" + valittuVaraus.getPalveluId() + "','"
                                + valittuVaraus.getLkm() + "');");

                    // Nappaa poikkeukset ja tulostaa stacktracen.
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // Alustaa SQL lauseen kolmatta komentoa varten.
                Statement stmt3 = con.createStatement();
                // Määrittelee SQL komennon jolla kokonaishinta haetaan tietokannasta.
                ResultSet rs2 = stmt3.executeQuery("select sum(T1.hinta + T2.hinta * t3.lkm) from (select hinta from mokki WHERE mokki_id = (SELECT mokki_mokki_id FROM varaus WHERE varaus_id = " + varauksen_id + ") ) as T1 ,(select hinta from palvelu where palvelu_id = (SELECT palvelu_id FROM varauksen_palvelut WHERE varaus_id = " + varauksen_id + ")) as T2, (select lkm from varauksen_palvelut WHERE varaus_id = " + varauksen_id + ") as t3;");
              
                if (rs2.next()) {
                    // Määritellään tarvittavat muuttujat.
                    double summa = rs2.getDouble(1);
                    double alv = summa - (summa * 0.9);
                    // Määrittelee ja suorittaa SQL komennon.
                stmt3.executeUpdate("INSERT INTO lasku (varaus_id, summa, alv) VALUES ('"
                + varauksen_id + "','" + summa + "','" + alv + "');");
            }
            // Nappaa poikkeukset ja tulostaa ne.
        } } catch (Exception e) {
            System.out.println(e);
        }finally {
            // Yhteys tietokantaan suljetaan.
            con.close();
        }
        Stage stage = (Stage) btTallenna.getScene().getWindow();
        stage.close();

    }

    
    /** 
     * Painike tallentaa valitulle palvelulle spinnerin osoittaman määrän.
     */
    @FXML
    void btPaivitaAction() {
        VarauksenPalvelut varauksenPalvelut = new VarauksenPalvelut(Integer.parseInt(selected), spLkm.getValue());
        try {
            // Lisää listalle palvelun spinnerin osoittamilla arvoilla.
            palveluLista.add(varauksenPalvelut);
            System.out.println("Palveluiden lukumäärä tallennettu");
        // Nappaa poikkeukset ja tulostaa stacktracen niistä.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * Hakee alustavat tiedot.
     * @param a Integer, valitun mökin id.
     * @param b LocalDateTime, valittu varauksen alkupäivä.
     * @param c LocalDateTime, valittu varauksen loppupäivä.
     */
    public void initdata(int a, LocalDateTime b, LocalDateTime c) {
        listPalvelu.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) throws NullPointerException {
                try {
                selected = Integer.toString(listPalvelu.getSelectionModel().getSelectedItem().getPalvelu_id());
                } catch (NullPointerException e) {
                    // Hiljentää NullPointerExceptionin
                    //e.printStackTrace();
            }
            }});
        // Määrittelee spinnerin ominaisuudet kuten pienimmän ja suurimman arvon.
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99);
        // Spinnerin oletusarvo.
        valueFactory.setValue(0);
        spLkm.setValueFactory(valueFactory);
        // Asettaa alustavat tiedot muuttujiin.
        mokkiId = a;
        aloitusPvm = b;
        lopetusPvm = c;

        // Yrittää asettaa kaikki palvelut listaan
        try {
            listPalvelu.setItems(haeLista());
        // Nappaa SQL poikkeukset ja tulostaa niiden stacktracet. 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * @return ObservableList<Palvelu>
     * @throws SQLException Heittää SQL errorin, jos valittua mökki id:tä tai alue id:tä ei löydy.
     */
    public ObservableList<Palvelu> haeLista() throws SQLException {
        ObservableList<Palvelu> palvelut = FXCollections.observableArrayList();
        int alueId = 0;

        // Luodaan yhteys tietokantaan.
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            // Alustetaan SQL lause komentoa varten.
            Statement stmt = con.createStatement();

            // Määritellään SQL komento joka etsii mökin tietyllä id:llä saadakseen siitä alue id:n
            ResultSet set = stmt.executeQuery("SELECT alue_id FROM mokki WHERE mokki_id = '" + mokkiId + "' ;");
            if (set.next()) {
                alueId = set.getInt(1);
            }

            // Etsitään kaikki palvelut tietyllä alue id:llä
            ResultSet set2 = stmt.executeQuery("SELECT * FROM palvelu WHERE alue_id = '" + alueId + "' ;");
            // Lisätään kaikki palvelut listaan.
            while (set2.next()) {
                Palvelu tempPalvelu = new Palvelu(set2.getInt(1), set2.getInt(2), set2.getString(3), set2.getInt(4),
                        set2.getString(5), set2.getDouble(6), set2.getDouble(7));
                palvelut.add(tempPalvelu);
            }
            // Nappaa kaikki poikkeukset ja tulostaa stacktracen niistä
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Yhteys tietokantaan suljetaan.
            con.close();
        }

        return palvelut;
    }

}
