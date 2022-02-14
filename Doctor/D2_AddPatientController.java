package com.example.finalprojdb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class D2_AddPatientController{

        @FXML
        private DatePicker tf_BD;

        @FXML
        private TextField tf_Email;

        @FXML
        private TextField tf_address;

        @FXML
        private TextField tf_allergy;

        @FXML
        private TextField tf_bloodType;

        @FXML
        private TextField tf_id;

        @FXML
        private TextField tf_name;

        @FXML
        private TextField tf_phoneNum;

        ResultSet rs;


    @FXML
    public void Save_buttonClicked(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        rs = DB_Connection.select("SELECT P_ID FROM patient where P_ID = '"+tf_id.getText()+"'");

        String id = tf_id.getText();
        String name = tf_name.getText();
        String email = tf_Email.getText();
        String address = tf_address.getText();
        String birth_Date = String.valueOf(tf_BD.getValue());
        String allergy = tf_allergy.getText();
        String blood_type = tf_bloodType.getText();
        String phone_number = tf_phoneNum.getText();

        if (name.isEmpty() || id.isEmpty() || address.isEmpty() || email.isEmpty() || birth_Date.isEmpty() || allergy.isEmpty() || blood_type.isEmpty() ||phone_number.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();

        }else if(rs.next()){

                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setHeaderText(null);
                alert2.setContentText("This patient already exists!");
                alert2.showAndWait();
                clean_ButtonClicked();

        } else {
            getQuery();
            clean_ButtonClicked();
        }

    }

    @FXML
    private void clean_ButtonClicked() {
        tf_address.setText(null);
        tf_BD.setValue(null);
        tf_allergy.setText(null);
        tf_bloodType.setText(null);
        tf_name.setText(null);
        tf_phoneNum.setText(null);
        tf_Email.setText(null);
        tf_id.setText(null);
    }

    private void getQuery() throws SQLException, ClassNotFoundException {

        int age =(int) ChronoUnit.YEARS.between(tf_BD.getValue(), LocalDate.now());

        String query1 = "INSERT INTO patients_history( blood_type, allergy , P_ID) VALUES ('" + tf_bloodType.getText() + "','" + tf_allergy.getText() + "','" + tf_id.getText() +"')";

        String query2 = "INSERT INTO patient( P_name, P_password, birth, address, email," +
                " phone, P_ID, age) VALUES ('" + tf_name.getText() + "','"+tf_id.getText() + "','"+
                String.valueOf(tf_BD.getValue()) + "','" + tf_address.getText() + "','" + tf_Email.getText() + "','" + tf_phoneNum.getText() +"','" + tf_id.getText() + "','" + age +"')";
        System.out.println(query2);

        DB_Connection.execute(query1);
        DB_Connection.execute(query2);

    }


}

