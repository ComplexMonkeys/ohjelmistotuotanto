package org.mokkivaraus.controller;

import org.mokkivaraus.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

public class varausIkkunaController implements Initializable {

    @FXML
    private Button btHaku;

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
    private TableColumn<Varaus, String> cAloitusPvm;

    @FXML
    private TableColumn<Varaus, Integer> cMokkiId;

    @FXML
    private TableColumn<Varaus, String> cPaatosPvm;

    @FXML
    private TableColumn<Varaus, Integer> cVarausId;

    @FXML
    private DatePicker dpAlku;

    @FXML
    private DatePicker dpLoppu;

    @FXML
    private HBox hbNapit;

    @FXML
    private TableView<Varaus> tvVaraus;

    @FXML
    private TextField tfAlue;

    DateTimeFormatter mysqlFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
    Varaus valittu;
    Varaus tulostus;

    public void paivitaVarauslista() {
        try {
            tvVaraus.getItems().setAll(haeVarauslista());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void paivitaRajattuLista() {
        String teksti = tfAlue.getText();
        if (!teksti.isBlank()) {
            try {
                tvVaraus.getItems().setAll(haeRajattuIdLista());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                ArrayList<Varaus> arrayList = haeRajattuVarausLista();
                if (!arrayList.isEmpty()) {
                    try {
                        tvVaraus.getItems().setAll(arrayList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        tvVaraus.getItems().clear();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btHakuAction(ActionEvent event) {
        try {
            paivitaRajattuLista();
        } catch (NullPointerException e) {
            System.out.println("Valitse päivämäärät haulle!");
        }
    }

    @FXML
    void btLisaaAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Mokinvaraus.class.getResource("lisaaVarausIkkuna.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Lisää varaus");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHiding(sulku -> {
                try {
                    paivitaVarauslista();
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
        FXMLLoader loader = new FXMLLoader(Mokinvaraus.class.getResource("muokkaaVarausIkkuna.fxml"));
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        muokkaaVarausIkkunaController controller = loader.getController();
        controller.initdata(valittu.getVarausId(), valittu.getVarattuPvm(), valittu.getMokkiId(),
                valittu.getAsiakasId());
        stage.setTitle("Muokkaa varausta");

        stage.show();
        stage.setOnHiding(sulku -> {
            try {
                paivitaVarauslista();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void btPaivitaAction(ActionEvent event) {
        paivitaVarauslista();
    }

    @FXML
    void btPaluuAction(ActionEvent event) {
        Stage stage = (Stage) btPaluu.getScene().getWindow();
        stage.close();
        Parent root;
        try {
            root = FXMLLoader.load(Mokinvaraus.class.getResource("alkuIkkuna.fxml"));
            Stage stage2 = new Stage();
            stage2.setTitle("Aloitus");
            stage2.setScene(new Scene(root));
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btPoistaAction(ActionEvent event) throws Exception {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            // Asettaa varaus muuttujaan valitun varauksen.
            Varaus varaus = tvVaraus.getSelectionModel().getSelectedItem();
            try (
                    // SQL komento joka poistaa valitun mökin.
                    Statement stmt2 = con.createStatement()) {
                stmt2.executeUpdate("DELETE FROM varauksen_palvelut WHERE varaus_id = " + varaus.getVarausId() + ";");
            }
            Statement stmt2 = con.createStatement();
            {
                stmt2.executeUpdate("DELETE FROM lasku WHERE varaus_id = " + varaus.getVarausId() + ";");
            }
            Statement stmt = con.createStatement();
            {
                stmt.executeUpdate("DELETE FROM varaus WHERE varaus_id = " + varaus.getVarausId() + ";");
            }
        }
        // Nappaa SQL poikkeukset ja tulostaa ne.
        catch (SQLException e) {
            System.out.print(e);
        } finally {
            // Suljetaan yhteys tietokantaan.
            con.close();
            // Päivittää listan poiston tapahduttua.
            paivitaVarauslista();
        }
    }

    private ArrayList<Integer> haeIdLista() throws SQLException {
        ArrayList<Integer> IdList = new ArrayList<>();
        String valittuAlue = tfAlue.getText();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery("SELECT * FROM alue WHERE nimi = '" + valittuAlue + "';");
            while (rs2.next()) {
                int valittuId = rs2.getInt(1);
                Statement stmt3 = con.createStatement();
                ResultSet rs3 = stmt3.executeQuery("SELECT * FROM mokki WHERE alue_id = '" + valittuId + "'");
                while (rs3.next()) {
                    IdList.add(rs3.getInt(1));
                }
            }
        } catch (SQLException sE) {
            sE.printStackTrace();
        }
        return IdList;
    }

    private ArrayList<Varaus> haeRajattuIdLista() throws SQLException {
        ArrayList<Varaus> lista = new ArrayList<>();
        // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        if (dpAlku.getValue() == null && dpLoppu.getValue() == null) {
            try {
                Statement stmt = con.createStatement();
                // Määrittää SQL komennon ja lähettää sen tietokannalle.
                for (int i = 0; i < haeIdLista().size(); i++) {
                    ResultSet rs = stmt.executeQuery(
                            "SELECT * FROM varaus WHERE mokki_mokki_id = '" + haeIdLista().get(i) + "' ;");
                    // Lisää kaikki taulukossa olevien alkioiden tiedot listaan.
                    while (rs.next()) {
                        Varaus tempvaraus = new Varaus(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
                                rs.getString(5),
                                rs.getString(6), rs.getString(7));
                        lista.add(tempvaraus);
                    }
                }
            }
            // Nappaa poikkeukset ja tulostaa ne.
            catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Yhteys tietokantaan suljetaan.
                con.close();
            }
        } else {
            LocalDate date = dpAlku.getValue();
            LocalDateTime startFormatted = date.atTime(12, 0, 0);
            startFormatted.format(mysqlFormat);
            LocalDate dateEnd = dpLoppu.getValue();
            LocalDateTime endFormatted = dateEnd.atTime(12, 0, 0);
            endFormatted.format(mysqlFormat);
            try {
                Statement stmt = con.createStatement();
                // Määrittää SQL komennon ja lähettää sen tietokannalle.
                for (int i = 0; i < haeIdLista().size() - 1; i++) {
                    ResultSet rs = stmt.executeQuery("SELECT * FROM varaus WHERE (varattu_alkupvm BETWEEN '"
                            + startFormatted + "' AND '" + endFormatted + "') OR (varattu_loppupvm BETWEEN '"
                            + startFormatted + "' AND '" + endFormatted + "')OR (varattu_alkupvm < '" + startFormatted
                            + "' AND varattu_loppupvm > '" + endFormatted + "') AND mokki_mokki_id = '"
                            + haeIdLista().get(i) + "' ;");
                    // Lisää kaikki taulukossa olevien alkioiden tiedot listaan.
                    while (rs.next()) {
                        Varaus tempvaraus = new Varaus(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
                                rs.getString(5),
                                rs.getString(6), rs.getString(7));
                        lista.add(tempvaraus);
                    }
                }
                // Nappaa poikkeukset ja tulostaa ne.
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Yhteys tietokantaan suljetaan.
                con.close();
            }
        }
        return lista;
    }

    private ArrayList<Varaus> haeRajattuVarausLista() throws SQLException {
        LocalDate date = dpAlku.getValue();
        LocalDateTime startFormatted = date.atTime(12, 0, 0);
        startFormatted.format(mysqlFormat);
        LocalDate dateEnd = dpLoppu.getValue();
        LocalDateTime endFormatted = dateEnd.atTime(12, 0, 0);
        endFormatted.format(mysqlFormat);
        ArrayList<Varaus> lista = new ArrayList<>();
        // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            Statement stmt = con.createStatement();
            // Määrittää SQL komennon ja lähettää sen tietokannalle.
            ResultSet rs = stmt.executeQuery("SELECT * FROM varaus WHERE (varattu_alkupvm BETWEEN '" + startFormatted
                    + "' AND '" + endFormatted + "') OR (varattu_loppupvm BETWEEN '" + startFormatted + "' AND '"
                    + endFormatted + "')OR (varattu_alkupvm < '" + startFormatted + "' AND varattu_loppupvm > '"
                    + endFormatted + "') ;");
            // Lisää kaikki taulukossa olevien alkioiden tiedot listaan.
            while (rs.next()) {
                Varaus tempvaraus = new Varaus(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
                        rs.getString(5),
                        rs.getString(6), rs.getString(7));
                lista.add(tempvaraus);
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

    public List<Varaus> haeVarauslista() throws SQLException {
        List<Varaus> lista = new ArrayList<>();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            java.sql.Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM varaus");
            while (rs.next()) {
                Varaus tempalue = new Varaus(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7));
                lista.add(tempalue);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            con.close();
        }
        return lista;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cMokkiId.setCellValueFactory(new PropertyValueFactory<Varaus, Integer>("mokkiId"));
        cVarausId.setCellValueFactory(new PropertyValueFactory<Varaus, Integer>("VarausId"));
        cPaatosPvm.setCellValueFactory(new PropertyValueFactory<Varaus, String>("VarattuLoppu"));
        cAloitusPvm.setCellValueFactory(new PropertyValueFactory<Varaus, String>("VarattuAlku"));

        try {
            tvVaraus.getItems().setAll(haeVarauslista());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tvVaraus.setRowFactory(tv -> {
            TableRow<Varaus> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {

                    valittu = row.getItem();
                    btMuokkaa.setDisable(false);
                    btPoista.setDisable(false);
                }

                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    tulostus = row.getItem();
                    Dialog<String> dialog = new Dialog<String>();
                    dialog.setTitle("Varaus");
                    ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
                    dialog.setContentText(tulostus.toString());
                    dialog.getDialogPane().getButtonTypes().add(type);
                    dialog.showAndWait();
                }
            });
            return row;
        });
    }

}