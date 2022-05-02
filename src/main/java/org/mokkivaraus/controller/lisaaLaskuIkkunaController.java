package org.mokkivaraus.controller;
import java.sql.Connection;
import java.sql.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class lisaaLaskuIkkunaController {

    @FXML
    private Button btPeruuta;

    @FXML
    private Button btTallenna;
    
    @FXML
    private TextField tfLaskuId;

    @FXML
    private TextField tfHinta;

    @FXML
    private TextField tfVarausId;

    @FXML
    void btPeruutaAction(ActionEvent event) {
        Stage stage = (Stage) btPeruuta.getScene().getWindow();
        stage.close();
    }
    

    @FXML
    void btTallennaAction(ActionEvent event) throws SQLException {
        if (tfVarausId.getText() != "" && tfHinta.getText() != ""){
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
            try{
                    java.sql.Statement stmt = con.createStatement();
                    int varaus_id = Integer.parseInt(tfVarausId.getText());
                    double hinta = Double.parseDouble(tfHinta.getText());
                    double alv = hinta - (hinta * 0.9);

                    stmt.executeUpdate("INSERT INTO lasku (varaus_id, summa, alv) VALUES ('" + varaus_id +"','" + hinta + "','" + alv + "');");
            } catch (Exception e){
                System.out.println(e);
            } finally {
                con.close();
            }
            Stage stage = (Stage) btTallenna.getScene().getWindow();
            stage.close();
        }
    }

}
