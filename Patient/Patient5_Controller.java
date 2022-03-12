package com.example.finalprojdb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Patient5_Controller implements Initializable {

    @FXML
    private Button changeButton;

    @FXML
    private Label result;

    @FXML
    private TextField tf_new2;

    @FXML
    private TextField tf_newpass;

    @FXML
    private TextField tf_oldpass;


    @FXML
    public void resetPW_ButtonClicked(ActionEvent actionEvent) {

       PatientClass patient = UserSessionP.getInstance(null).getPatient();

        if (tf_newpass.getText().equals(tf_new2.getText())){    // check if the new password equals the confirmed one
            try {

                String sql1= "SELECT * from patient where P_ID=" +patient.getId()+" and P_password="+tf_oldpass.getText(); // get the patient with logged in id and old pwd value
                System.out.println(sql1);
                ResultSet oldPW = DB_Connection.select(sql1);

                if (oldPW.next()){ // old pass is correct
                    String sql2 ="UPDATE patient set P_password= '"+tf_newpass.getText()+ "' where P_ID='"+patient.getId()+"'";
                    System.out.println(sql2);
                    DB_Connection.execute(sql2);
                    result.setStyle("-fx-text-fill: GREEN");
                    result.setText("Password updated");

                }
                else{
                    result.setStyle("-fx-text-fill: RED");
                    result.setText("Old password is wrong");
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        else{
            result.setStyle("-fx-background-color: RED");
            result.setText("Your new password and confirm password do not match!");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
