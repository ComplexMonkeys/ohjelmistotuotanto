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
    private Button btPaluu;

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

    @FXML
    void btPaluuAction(){
        
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
