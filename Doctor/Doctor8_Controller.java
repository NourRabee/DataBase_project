package com.example.finalprojdb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Doctor8_Controller implements Initializable {

    @FXML
    public TextField tf_newpass;
    @FXML
    public TextField tf_oldpass;
    @FXML
    public TextField tf_new2;
    @FXML
    public Label message;
    @FXML
    private DoctorMenuController doctorMenuController;


    @FXML
    public void changePassword(ActionEvent actionEvent) {

        DoctorClass doctor = UserSessionD.getInstance(null).getDoctor();

        if (tf_newpass.getText().equals(tf_new2.getText())){    // check if the new password equals the confirmed one
            try {

                String sql1= "SELECT * from doctor where D_ID=" +doctor.getId()+" and D_password="+tf_oldpass.getText(); // get the doctor with logged in id and old pwd value
                System.out.println(sql1);
                ResultSet oldPW = DB_Connection.select(sql1);

                if (oldPW.next()){ // old pass is correct
                    String sql2 ="UPDATE doctor set D_password= '"+tf_newpass.getText()+ "' where D_ID='"+doctor.getId()+"'";
                    System.out.println(sql2);
                    DB_Connection.execute(sql2);
                    message.setStyle("-fx-text-fill: GREEN");
                    message.setText("Password updated");

                }
                else{
                    message.setStyle("-fx-text-fill: RED");
                    message.setText("Old password is wrong");
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        else{
            message.setStyle("-fx-background-color: RED");
            message.setText("New Passwords must match each other!");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
