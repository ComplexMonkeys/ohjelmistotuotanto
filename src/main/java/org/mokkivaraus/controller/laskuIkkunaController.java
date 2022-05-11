package org.mokkivaraus.controller;

import org.mokkivaraus.*;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.*;

public class laskuIkkunaController implements Initializable {

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
    private TableColumn<Lasku, Integer> cLaskuId;

    @FXML
    private TableColumn<Lasku, Double> cSumma;

    @FXML
    private TableColumn<Lasku, Integer> cVarausId;

    @FXML
    private HBox hbNapit;

    @FXML
    private TableView <Lasku> tvLaskut;

    Lasku valittu;
    Lasku tulostus;

    /** 
     * metodi joka päivittää listan, jossa näkyy laskut
     */

    public void paivitaLista() {
        try {
            tvLaskut.getItems().setAll(haeLista());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * Painike, joka laittaa näkyviin lisää laskuikkunan
     * heittää errorin, jos tulee ongelmia. 
     */
    @FXML
    void btLisaaAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Mokinvaraus.class.getResource("lisaaLaskuIkkuna.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Lisää lasku");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHiding(sulku -> {
                try {
                    paivitaLista();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * Painike avaa ikkunan, jossa voidaan muokata laskuja.
     */
    @FXML
    void btMuokkaAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Mokinvaraus.class.getResource("muokkaaLaskuIkkuna.fxml"));
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    /** 
     * tässä välitetään dataa controlleriin, jotta saamme nykyiset arvot näkymään kentissä
     */
        muokkaaLaskuIkkunaController controller = loader.getController();
        controller.initdata(valittu.getLasku_id(),valittu.getSumma(),valittu.getVaraus_id());
        stage.setTitle("Muokkaa laskutusta");

        stage.show();
        stage.setOnHiding(sulku -> {
            try {
                paivitaLista();
                    /** 
                    * jos tulee ongelmia niin heittää errorin.
                    */
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    
    /** 
     * painike, joka päivitää listan
     */
    @FXML
    void btPaivitaAction(ActionEvent event) {
        paivitaLista();
    }

    
    /** 
     * nappi, josta pääsee takaisin alkuikkunaan
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * nappi, joka poistaa valitun laskun 
     */
    @FXML
    void btPoistaAction(ActionEvent event)  throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            // Asettaa mokki muuttujaan valitun mökin.
            Lasku lasku = tvLaskut.getSelectionModel().getSelectedItem();
            try (// SQL komento joka poistaa valitun mökin.
                    Statement stmt = con.createStatement()) {
                stmt.executeUpdate("DELETE FROM lasku WHERE lasku_id = " + lasku.getLasku_id() + ";");
            }
            con.close();
            // Päivittää listan poiston tapahduttua.
            paivitaLista();
        }
        // Nappaa SQL poikkeukset ja tulostaa ne.
        catch (SQLException e) {
            System.out.print(e);
        } finally {
            con.close();
        }
    }

    
    /** 
     * metodi, joka suorittuu, kun ikkuna avataan
     * asetetaan taulukoihin arvot mitä sinne laitetaan
     * sitten asetetaan toiminto, että voidaan valita yksi laskuista
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cLaskuId.setCellValueFactory(new PropertyValueFactory<Lasku, Integer>("lasku_id"));
        cSumma.setCellValueFactory(new PropertyValueFactory<Lasku, Double> ("summa"));
        cVarausId.setCellValueFactory(new PropertyValueFactory<Lasku, Integer>("varaus_id"));
        try {
            tvLaskut.getItems().setAll(haeLista());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tvLaskut.setRowFactory(tv -> {
            TableRow<Lasku> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    tulostus = row.getItem();

                    Stage dialogi = new Stage();
                    dialogi.setTitle("Lähetä lasku");
                    Scene scene = new Scene(luoPane(), 300, 200);
                    dialogi.setScene(scene);
                    dialogi.show();
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
/*
* Tehdään lista, jossa haetaan laskut tietokannasta.
*/
    private List<Lasku> haeLista() throws SQLException {
        List<Lasku> lista = new ArrayList<>();
        // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            Statement stmt = con.createStatement();
            // Määrittää SQL komennon ja lähettää sen tietokannalle.
            ResultSet rs = stmt.executeQuery("select * from lasku");
            // Lisää kaikki taulukossa olevien alkioiden tiedot listaan.
            while (rs.next()) {
                Lasku templlasku = new Lasku(rs.getInt(1),rs.getInt(2),rs.getDouble(3), rs.getDouble(4));
                lista.add(templlasku);
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
/*
* saadaan laskuun eräpäivä
*/
    LocalDate dateTimeEnd = LocalDate.now().plusDays(7);

/*
* metodi, jolla voimme tehdä paperilaskun
*/
    public void tulostus(){
        VBox vboxi = new VBox(10);
        Text teksti = new Text("Hyvä asiakas, ohessa on pyydetty sähköpostilasku: " +  "\n Lasku ID: "+ valittu.getLasku_id() +
        "\n Varaus ID: "+ valittu.getVaraus_id() + "\n Varauksen summa on: "+ valittu.getSumma() 
        + "\n Eräpäivä " + dateTimeEnd + " \n tilinumero FI13 1194 2948 2394 59 ");
        vboxi.getChildren().addAll(teksti);
        vboxi.setPrefSize(400,250);
        javafx.print.Printer defaultprinter = javafx.print.Printer.getDefaultPrinter();
        if (defaultprinter != null){
            String tulostinNimi = defaultprinter.getName();
            System.out.println("Tulostimen nimi on: " + tulostinNimi);
        } else {
            System.out.println("Tulostimia ei löydetty");
        }
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        Printer printer = printerJob.getPrinter();
        // Create the Page Layout of the Printer
        printerJob.printPage(printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT,Printer.MarginType.EQUAL), vboxi);
        printerJob.endJob();
    }


/*
* metodi, jolla lähetämme sähköpostiin laskun
*/
    public void spostilahettaja() throws SQLException{
        String etunimi = "";
        String to = "";
            // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
            try {
                Statement stmt = con.createStatement();
                // Määrittää SQL komennon ja lähettää sen tietokannalle.
                ResultSet rs = stmt.executeQuery("select email, etunimi from asiakas where asiakas_id = (select asiakas_id from varaus where varaus_id = " + tulostus.getVaraus_id() + " );");
                // Lisää kaikki taulukossa olevien alkioiden tiedot listaan.
                while (rs.next()) {
                    to = rs.getString(1);
                    etunimi = rs.getString(2);
                }
                // Nappaa poikkeukset ja tulostaa ne.
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                // Yhteys tietokantaan suljetaan.
                con.close();
            }

            
        String host = "villagenewbies03@gmail.com";
        
        final String user = "villagenewbies03@gmail.com";

        final String password = "ohjelmistotuotanto";




        Properties props = new Properties();
        props.put("mail.smtp.ssl.trust", "*" );
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port","587");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(props,new jakarta.mail.Authenticator() {
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
              return new jakarta.mail.PasswordAuthentication(user,password);
                    }
                });
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject("Mökinvaraus lasku");
            message.setText("Hei, "+ etunimi + " ohessa on pyytämäsi sähköpostilasku: " +  "Lasku ID: "+ valittu.getLasku_id() +
             "\n Varaus ID: "+ valittu.getVaraus_id() + "\n Varauksen summa on: "+ valittu.getSumma() 
             + "\n Eräpäivä " + dateTimeEnd + " \n tilinumero FI13 1194 2948 2394 59 ");

            Transport.send(message);
            System.out.println("viesti lähetetty onnistuneesti!");

        } catch (MessagingException e){

        }

    }

    /**
     * Rakentaa uuden ikkunan, missä näyttää lähetettävän laskun ja kaksi painiketta, joista toisesta lasku tulostetaan paperille ja toisesta lähettää laskun säshköpostilla.
     * Kun jompaa kumpaa painiketta painaa, ikkuna sulkee itsensä.
     * @return Broderpane-olio, missä näytetään laskun tiedot ja painikkeet laskun lähettämiselle/tulostamiselle
     */
    public BorderPane luoPane(){
        // Luo alert-dialogin muokatuilla painikkeilla
        Button btSposti = new Button("Sposti");
        Button btTulosta = new Button("Tulosta");
        btSposti.setOnAction(s -> {
            try {
                spostilahettaja();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) btSposti.getScene().getWindow();
            stage.close();
        });
        btTulosta.setOnAction(t -> {
            tulostus();
            Stage stage = (Stage) btTulosta.getScene().getWindow();
            stage.close();
        });
        BorderPane pane = new BorderPane();

        HBox boxi = new HBox(10);
        boxi.getChildren().addAll(btSposti, btTulosta);
        boxi.setAlignment(Pos.CENTER_RIGHT);

        Text lasku = new Text(tulostus.toString());
        pane.setBottom(boxi);
        pane.setCenter(lasku);
        BorderPane.setMargin(boxi, new Insets(5, 5, 5, 5));
        BorderPane.setMargin(lasku, new Insets(5, 5, 5, 5));

        return pane;
    }
}