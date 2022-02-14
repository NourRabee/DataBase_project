package com.example.finalprojdb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;

public class D2_DeletePatientController {

    @FXML
    private Button clear;

    @FXML
    private Button delete;

    @FXML
    private TextField tf_id;

    ResultSet rs;

    @FXML
    void clear_ButtonClicked() {

        tf_id.setText(null);

    }

    @FXML
    void delete_ButtonClicked(ActionEvent event) throws SQLException, ClassNotFoundException {

        rs = DB_Connection.select("SELECT P_ID FROM patient WHERE P_ID= '"+tf_id.getText()+"'");

        String id = tf_id.getText();

        if(id.isEmpty()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();

        }else if (rs.next()){

            getQuery();
            clear_ButtonClicked();


        }else{

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("This Patient does not exist!!");
            alert.showAndWait();

        }

    }
    void getQuery() throws SQLException, ClassNotFoundException {


        String query1 = "DELETE FROM patients_history WHERE P_ID= '"+tf_id.getText()+"'";
        String query2 = "DELETE FROM patient WHERE P_ID= '"+tf_id.getText()+"'";

        DB_Connection.execute(query2);
        DB_Connection.execute(query1);

    }

}
