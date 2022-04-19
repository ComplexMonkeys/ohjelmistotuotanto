package org.mokkivaraus.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.mokkivaraus.Alue;
import org.mokkivaraus.Mokinvaraus;

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
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class alueIkkunaController implements Initializable{

    @FXML
    private TitledPane alueIkkuna;

    @FXML
    private Button btLisaa;

    @FXML
    private Button btMuokkaa;

    @FXML
    private Button btPaivita;

    @FXML
    private Button btPoista;

    @FXML
    private TableColumn<Alue, Integer> cAlueId;

    @FXML
    private TableColumn<Alue, String> cAlueNimi;

    @FXML
    private HBox hbNapit;

    @FXML
    private TableView<Alue> tvAlue;

    Alue valittu;

    @FXML
    void btLisaaAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Mokinvaraus.class.getResource("lisaaAlueIkkuna.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Lisää alue");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
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
    void btPoistaAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        cAlueId.setCellValueFactory(new PropertyValueFactory<Alue, Integer>("alue_id"));
        cAlueNimi.setCellValueFactory(new PropertyValueFactory<Alue, String>("nimi"));
        

        tvAlue.getItems().setAll(haeLista());

        tvAlue.setRowFactory(tv -> {
            TableRow<Alue> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton() == MouseButton.PRIMARY){
        
                    valittu = row.getItem();
                    btMuokkaa.setDisable(false);
                    btPoista.setDisable(false);
                }
            });
            return row;
        });
    }

    public List<Alue> haeLista(){
        List<Alue> lista = new ArrayList<>();
        
        try{
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/vn", "employee", "password");
                java.sql.Statement stmt = con.createStatement();
                
                ResultSet rs = stmt.executeQuery("SELECT * FROM alue");
            
                while(rs.next()) {
                    Alue tempalue = new Alue(rs.getInt(1),rs.getString(2));
                    lista.add(tempalue);
                }
                con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return lista;
    }

}
