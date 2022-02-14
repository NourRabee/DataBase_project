package com.example.finalprojdb;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DoctorMenuController {

    public void profile_buttonClicked(ActionEvent actionEvent) throws IOException {
        Main m = new Main();
        m.changeScene("Doctor1_Profile.fxml");
    }

    public void patientsHistory_buttonClicked(ActionEvent actionEvent) throws IOException {

        Main m = new Main();
        m.changeScene("Doctor4_patientsHistory.fxml");
    }

    public void editPW_buttonClicked(ActionEvent actionEvent) throws IOException {

        Main m = new Main();
        m.changeScene("Doctor8_resetPW.fxml");
    }


    public void reservation_buttonClicked(ActionEvent actionEvent) throws IOException {

        Main m = new Main();
        m.changeScene("Doctor3_appointments.fxml");
    }

    public void patientsPrescription_buttonClicked(ActionEvent actionEvent) throws IOException {

        Main m = new Main();
        m.changeScene("Doctor5_prescription.fxml");
    }

    public void patientsLabRequest_buttonClicked(ActionEvent actionEvent) throws IOException {

        Main m = new Main();
        m.changeScene("Doctor6_transferToLab.fxml");
    }

    public void patientsTransfer_buttonClicked(ActionEvent actionEvent) throws IOException {

        Main m = new Main();
        m.changeScene("Doctor7_ExTransfer.fxml");

    }

    public void clinicP_buttonClicked(ActionEvent actionEvent) throws IOException {

        Main m = new Main();
        m.changeScene("Doctor2_clinicPatients.fxml");

    }

}
