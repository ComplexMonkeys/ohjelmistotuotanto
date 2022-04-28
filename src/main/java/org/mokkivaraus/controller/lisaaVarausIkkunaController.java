package org.mokkivaraus.controller;
import org.mokkivaraus.Mokinvaraus;
import org.mokkivaraus.Mokki;

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

public class lisaaVarausIkkunaController implements Initializable{

    @FXML
    private Button btPaluu;

    @FXML
    private Button btVaraus;

    @FXML
    private Button btPaivita;

    @FXML
    private TableView<Mokki> tvMokit;

    @FXML
    private TableColumn<Mokki, Integer> cAlue;

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
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cAlue.setCellValueFactory(new PropertyValueFactory<Mokki, Integer>("alue_id"));
        cHenkilomaara.setCellValueFactory(new PropertyValueFactory<Mokki, Integer>("henkilomaara"));
        cMokkiId.setCellValueFactory(new PropertyValueFactory<Mokki, Integer>("mokki_id"));
        cMokkiNimi.setCellValueFactory(new PropertyValueFactory<Mokki, String>("mokkinimi"));

        try {
            tvMokit.getItems().setAll(haeMokkiLista());
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
    public void paivitaLista() {
        try {
            tvMokit.getItems().setAll(haeMokkiLista());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btPaluuAction(ActionEvent event) {
        Stage stage = (Stage) btPaluu.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btVarausAction(ActionEvent event) throws Exception {
        int mokkiId = valittu.getMokki_id();
        LocalDate aloitusPvm = dpAloitus.getValue();
        LocalDate lopetusPvm = dpLopetus.getValue();
        DateTimeFormatter mysqlFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        LocalDateTime aloitusPvmFormat = aloitusPvm.atTime(12,0,0);
        aloitusPvmFormat.format(mysqlFormat);
        LocalDateTime lopetusPvmFormat = lopetusPvm.atTime(12,0,0);
        lopetusPvmFormat.format(mysqlFormat);

        FXMLLoader loader = new FXMLLoader(Mokinvaraus.class.getResource("lisaaVarausPalveluIkkuna.fxml"));
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        lisaaVarausPalveluIkkunaController controller = loader.getController();
        controller.initdata(mokkiId, aloitusPvmFormat, lopetusPvmFormat);
        stage.setTitle("Tee varaus");

        stage.show();
    }  

    @FXML
    void btPaivitaAction(ActionEvent event) {
        paivitaLista();
    }

    @FXML
    void dpAloitusAction(ActionEvent event) {

    }

    @FXML
    void dpLopetusAction(ActionEvent event) {

    }

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
