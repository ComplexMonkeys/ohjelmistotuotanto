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
import javafx.scene.control.Alert.AlertType;
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

    public void paivitaAluelista(){
        try {
            tvAlue.getItems().setAll(haeAluelista());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }	

    @FXML
    void btLisaaAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Mokinvaraus.class.getResource("lisaaAlueIkkuna.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Lisää alue");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHiding(sulku -> {
                try {
                    paivitaAluelista();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btMuokkaAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Mokinvaraus.class.getResource("muokkaaAlueIkkuna.fxml"));
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        muokkaaAlueIkkunaController controller = loader.getController();
        controller.initdata(valittu.getAlue_id());
        stage.setTitle("Muokkaa aluetta");

        stage.show();
        stage.setOnHiding(sulku -> {
            try {
                paivitaAluelista();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void btPaivitaAction(ActionEvent event) {
        paivitaAluelista();
    }

    @FXML
    void btPoistaAction(ActionEvent event) throws Exception{
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            // Asettaa mokki muuttujaan valitun mökin.
            Alue alue = tvAlue.getSelectionModel().getSelectedItem();
            try (// SQL komento joka poistaa valitun mökin.
            Statement stmt = con.createStatement()) {
                stmt.executeUpdate("DELETE FROM alue WHERE alue_id = " + alue.getAlue_id() + ";");
            }
            con.close();
            // Päivittää listan poiston tapahduttua.
            paivitaAluelista();
        }
        // Nappaa SQL poikkeukset ja tulostaa ne.
        catch (SQLIntegrityConstraintViolationException integrityE){
            Alert constraitAlert = new Alert(AlertType.ERROR);
            constraitAlert.setHeaderText("Aluetta ei voida poistaa!");
            constraitAlert.setContentText("Aluetta ei voida poistaa, koska sille on asetettu mökkejä.");
            constraitAlert.showAndWait();
        }
        finally {
            con.close();
        }
    }

    @FXML
    void btPaluuAction(){
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

    @Override
        public void initialize(URL url, ResourceBundle rb) {
            cAlueId.setCellValueFactory(new PropertyValueFactory<Alue, Integer>("alue_id"));
            cAlueNimi.setCellValueFactory(new PropertyValueFactory<Alue, String>("nimi"));
    
            try {
                tvAlue.getItems().setAll(haeAluelista());
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
            tvAlue.setRowFactory(tv -> {
                TableRow<Alue> row = new TableRow<>();
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

    public List<Alue> haeAluelista() throws SQLException{
        List<Alue> lista = new ArrayList<>();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try{
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
        finally{
            con.close();
        }
        return lista;
    }

}
