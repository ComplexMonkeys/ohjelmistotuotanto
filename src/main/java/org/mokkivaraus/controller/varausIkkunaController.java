package org.mokkivaraus.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.mokkivaraus.*;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class varausIkkunaController implements Initializable {

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
    private HBox hbNapit;

    @FXML
    private TableView<Varaus> tvVaraus;

    Varaus valittu;

    public void paivitaVarauslista() {
        try {
            tvVaraus.getItems().setAll(haeVarauslista());
        } catch (SQLException e) {
            e.printStackTrace();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btMuokkaAction(ActionEvent event) {

    }

    @FXML
    void btPaivitaAction(ActionEvent event) {

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
    void btPoistaAction(ActionEvent event) {

    }

    public List<Varaus> haeVarauslista() throws SQLException {
        List<Varaus> lista = new ArrayList<>();

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/vn", "employee", "password");
            java.sql.Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM varaus");

            while (rs.next()) {
                Varaus tempalue = new Varaus(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7));
                lista.add(tempalue);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return lista;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cMokkiId.setCellValueFactory(new PropertyValueFactory<Varaus, Integer>("mokkiId"));
        cVarausId.setCellValueFactory(new PropertyValueFactory<Varaus, Integer>("VarausId"));
        cPaatosPvm.setCellValueFactory(new PropertyValueFactory<Varaus, String>("VarattuAlku"));
        cAloitusPvm.setCellValueFactory(new PropertyValueFactory<Varaus, String>("VarattuLoppu"));

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
            });
            return row;
        });
    }

}