package com.example.finalprojdb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Patient1_Controller implements Initializable {

    @FXML
    private Label L_name;
    @FXML
    private Label L_address;
    @FXML
    private Label L_email;
    @FXML
    private Label L_allergy;
    @FXML
    private Label L_phone;
    @FXML
    private Label L_blood;
    @FXML
    private Label L_birth;

    public void setProfile(PatientClass patient) {

        UserSessionP.getInstance(patient);
        L_name.setText(patient.getPname());
        L_address.setText(patient.getAddress());
        L_email.setText(patient.getEmail());
        L_phone.setText(String.valueOf(patient.getPhone()));
        L_blood.setText(patient.getBloodType());
        L_birth.setText(String.valueOf(patient.getBirth()));
        L_allergy.setText(patient.getAllergy());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

         //if patient logged in display profile data
         if (UserSessionP.getInstance(null).getPatient()!=null)
            setProfile(UserSessionP.getInstance(null).getPatient());

    }

    public void signOut_buttonClicked(ActionEvent actionEvent) throws IOException {

        Main m = new Main();
        m.changeScene("LoginPatient.fxml");
    }

}
