package com.example.finalprojdb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class PatientMenuController {

    @FXML
    public void profile_buttonClicked() throws IOException {

        Main m = new Main();
        m.changeScene("Patient1_Profile.fxml");
    }
    @FXML
    public void clinicD_buttonClicked() throws IOException {

        Main m = new Main();
        m.changeScene("Patient2_clinicD.fxml");
    }

    @FXML
    public void reservation_buttonClicked() throws IOException {

        Main m = new Main();
        m.changeScene("Patient3_Appointments.fxml");
    }

    @FXML
    public void patientsHistory_buttonClicked() throws IOException {


        Main m = new Main();
        m.changeScene("Patient4_patientsHistory.fxml");
    }
    @FXML
    public void editPW_buttonClicked() throws IOException {

        Main m = new Main();
        m.changeScene("Patient5_resetPW.fxml");
    }
    @FXML
    public void contactInfo_buttonClicked() {
    }
}
