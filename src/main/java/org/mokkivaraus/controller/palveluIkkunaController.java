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
import javafx.scene.control.cell.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class palveluIkkunaController {

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
    private TableColumn<Palvelu, Integer> cAlue;

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

    @FXML
    void btLisaaAction(ActionEvent event) {

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
            stage2.setTitle("alkuikkuna");
            stage2.setScene(new Scene(root));
            stage2.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

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

                //puuttuu listan paivitus
             }
             catch (SQLException e){
                 System.out.println(e);
             }
             finally {
                 con.close();
             }
    }

}
