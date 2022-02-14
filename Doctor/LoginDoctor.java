package com.example.finalprojdb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class LoginDoctor {

    @FXML
    private Label check;

    @FXML
    private Button submitButton;

    @FXML
    private TextField tf_ID;

    @FXML
    private PasswordField tf_PW;

    String username;
    String d_password;
    ResultSet rs1;
    private static Stage stg;

    @FXML
    public void submit(ActionEvent actionEvent) {

        String username = tf_ID.getText();
        String d_password = tf_PW.getText();

        try {
            rs1 = DB_Connection.select("SELECT * FROM doctor WHERE D_ID =" + Integer.parseInt(username)+" and D_password="+ Integer.parseInt(d_password));


            if (!rs1.next()) { // comparing sql query with the user input

                check.setText("Login Failed!");
            } else {

                DoctorClass doctor=new DoctorClass(rs1.getInt("D_ID"),0,rs1.getString("D_name"),rs1.getString("specialization"),rs1.getString("address"),
                        rs1.getString("email"),rs1.getInt("phone"));

                check.setText("Login has done successfully!");

                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Doctor1_Profile.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Doctor1_Controller dc =fxmlLoader.<Doctor1_Controller>getController();
                dc.setProfile(doctor);
                stage.setScene(scene);

                Main m = new Main();
                m.changeScene("Doctor1_Profile.fxml");

                }

            } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    public void BackButtonClick() throws IOException {

        Main m = new Main();
        m.changeScene("MainView.fxml");

    }
}

