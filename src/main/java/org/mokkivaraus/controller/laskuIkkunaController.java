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
import java.util.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
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

    public void paivitaLista() {
        try {
            tvLaskut.getItems().setAll(haeLista());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btLisaaAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Mokinvaraus.class.getResource("lisaaLaskuIkkuna.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Lisää asiakas");
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

    @FXML
    void btMuokkaAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Mokinvaraus.class.getResource("muokkaaLaskuIkkuna.fxml"));
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        muokkaaLaskuIkkunaController controller = loader.getController();
        controller.initdata(valittu.getLasku_id(),valittu.getSumma(),valittu.getVaraus_id());
        stage.setTitle("Muokkaa laskutusta");

        stage.show();
        stage.setOnHiding(sulku -> {
            try {
                paivitaLista();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void btPaivitaAction(ActionEvent event) {
        paivitaLista();
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
                    System.out.println(tulostus);
                    
                    // Luo alert-dialogin muokatuilla painikkeilla
                    ButtonType btPrintti = new ButtonType("Tulosta", ButtonBar.ButtonData.OK_DONE);
                    ButtonType btSposti = new ButtonType("Sähköposti", ButtonBar.ButtonData.CANCEL_CLOSE);
                    Alert alert = new Alert(AlertType.WARNING,
                            tulostus.toString(),
                            btPrintti,
                            btSposti);
                    alert.setTitle("Lähetä lasku");
                    alert.setHeaderText("Miten haluat lähettää laskun?");
                    alert.setResizable(false);
                    Optional<ButtonType> result = alert.showAndWait();
                    // TODO: Jostakin syystä valitsee säpon vaikka painaisi ruksia...
                    if (result.orElse(btPrintti) == btSposti) {
                        System.out.println("Säpo");
                        spostilahettaja();
                        // TODO: Lähetä lasku s-postilla
                    } else if (result.orElse(btSposti) == btPrintti){
                        System.out.println("Printti");
                        tulostus();
                    }
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
    public void tulostus(){
        VBox vboxi = new VBox(10);
        Label tulostuslbl = new Label("Lasku ID: "+ valittu.getLasku_id());
        Label tulostuslbl2 = new Label("Varaus ID: "+ valittu.getVaraus_id());
        Label tulostuslbl3 = new Label("Varauksen summa on: "+ valittu.getSumma());
        vboxi.getChildren().addAll(tulostuslbl,tulostuslbl2,tulostuslbl3);
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


    public void spostilahettaja() {
        String host = "villagenewbies03@gmail.com";
        
        final String user = "villagenewbies03@gmail.com";

        final String password = "ohjelmistotuotanto";

        // Tähän sähköposti, johon viesti lähetetään..
        String to = "";

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
            message.setText("Hyvä asiakas, ohessa on pyydetty sähköpostilasku: " +  "Lasku ID: "+ valittu.getLasku_id() +
             " Varaus ID: "+ valittu.getVaraus_id() + " Varauksen summa on: "+ valittu.getSumma() 
             + "Eräpäivä 15.6.2022" + "tilinumero FI13 1194 2948 2394 59");

            Transport.send(message);
            System.out.println("viesti lähetetty onnistuneesti!");

        } catch (MessagingException e){
}

}
}