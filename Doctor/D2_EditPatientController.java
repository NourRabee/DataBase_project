package com.example.finalprojdb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class D2_EditPatientController implements Initializable {

    @FXML
    private ChoiceBox choiceBox;

    @FXML
    private Button clear;

    @FXML
    private TextField tf_choicebox;

    @FXML
    private TextField tf_id;

    @FXML
    private Button update;

    ObservableList <String> fieldsList = FXCollections.observableArrayList("Patient's name",
            "Patient's address", "Patient's phone", "Patient's email", "Patient's blood type", "Patient's birth date","Patient's allergy");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        choiceBox.setItems(fieldsList);

    }
    @FXML
    void clear_ButtonClicked() {

        tf_id.setText(null);
        tf_choicebox.setText(null);

    }
    @FXML
    void update_ButtonClicked(ActionEvent event) throws SQLException, ClassNotFoundException {

        String id = tf_id.getText();

        if(id.isEmpty()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill the id text field!!");
            alert.showAndWait();
        }else{

            if(String.valueOf(choiceBox.getValue())== "Patient's name"){

                String p_name = tf_choicebox.getText();
                String query = "UPDATE patient SET p_name = '"+p_name+"' WHERE P_ID='"+tf_id.getText()+"'";
                DB_Connection.execute(query);

            }
            if(String.valueOf(choiceBox.getValue())== "Patient's address"){

                String p_address = tf_choicebox.getText();
                String query = "UPDATE patient SET address = '"+p_address+"' WHERE P_ID='"+tf_id.getText()+"'";
                DB_Connection.execute(query);

            }
            if(String.valueOf(choiceBox.getValue())== "Patient's blood type"){

                String p_bloodType = tf_choicebox.getText();
                String query = "UPDATE patients_history SET blood_type = '"+p_bloodType+"' WHERE P_ID='"+tf_id.getText()+"'";
                DB_Connection.execute(query);

            }
            if(String.valueOf(choiceBox.getValue())== "Patient's phone"){

                String p_phone = tf_choicebox.getText();
                String query = "UPDATE patient SET phone = '"+p_phone+"' WHERE P_ID='"+tf_id.getText()+"'";
                DB_Connection.execute(query);

            }
            if(String.valueOf(choiceBox.getValue())== "Patient's email"){

                String p_email = tf_choicebox.getText();
                String query = "UPDATE patient SET email = '"+p_email+"' WHERE P_ID='"+tf_id.getText()+"'";
                DB_Connection.execute(query);

            }
            if(String.valueOf(choiceBox.getValue())== "Patient's birth date"){

                LocalDate p_BD = LocalDate.parse(tf_choicebox.getText());
                int age = LocalDate.now().getYear() - p_BD.getYear();

                String query = "UPDATE patient SET birth = '"+p_BD+"' WHERE P_ID='"+tf_id.getText()+"'";
                //Update the age based on the new birthdate
                String query2 ="UPDATE patient SET age = '"+age+"'";
                DB_Connection.execute(query);

            }
            if(String.valueOf(choiceBox.getValue())== "Patient's allergy"){

                String p_allergy = tf_choicebox.getText();
                String query = "UPDATE patients_history SET allergy = '"+p_allergy+"' WHERE P_ID='"+tf_id.getText()+"'";
                DB_Connection.execute(query);
            }
            clear_ButtonClicked();

        }

    }

}
