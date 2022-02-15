package com.example.finalprojdb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;


public class Doctor7_Controller implements Initializable {

    @FXML
    private ChoiceBox<String> choiceBoxTime;

    @FXML
    private DatePicker tf_Date;

    @FXML
    private TextField tf_ExaminationType;

    @FXML
    private TextField tf_hospital;

    @FXML
    private TextField tf_pid;

    ObservableList<ExTransferClass> list = FXCollections.observableArrayList();
    int DoctorID = UserSessionD.getInstance(null).getDoctor().getId();
    

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //force user to pick dates from the current day,"Disable all the dates before the current date"
        tf_Date.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });

        tf_Date.valueProperty().addListener((observable, oldDate, newDate)-> {
            ObservableList<String> timeList = FXCollections.observableArrayList();
            String[] hours = {"08", "09", "10", "11", "12", "13", "14", "15", "16", "17"};
            String[] minutes = {"00:00"};
            for (int i = 0; i < hours.length; i++)
                for (int j = 0; j < minutes.length; j++)
                    timeList.add(hours[i] + ":" + minutes[j]); // fill the ObservableList with clinic time


            // Add the remaining times to ChoiceBox
            choiceBoxTime.setItems(timeList);
        });

    }


    public void insert_buttonClicked() throws SQLException, ClassNotFoundException {

        ResultSet rs1 = DB_Connection.select("SELECT P_ID FROM appointment WHERE D_ID = '" + DoctorID + "' AND P_ID = '" + tf_pid.getText() + "'");

        String pID = tf_pid.getText();
        String diagnose = tf_ExaminationType.getText();
        String hospitalName = tf_hospital.getText();


        if (pID.isEmpty() || diagnose.isEmpty()  || hospitalName.isEmpty() || tf_Date.getValue() == null || choiceBoxTime.getValue() == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();

        }else{

            if (!rs1.next()) {

                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setHeaderText(null);
                alert2.setContentText("Make sure that it is your patient!");
                alert2.showAndWait();
                tf_pid.setText(null);

            } else {

                getQuery();
                clear_buttonClicked();

            }
        }

    }

    public void clear_buttonClicked() {

        tf_Date.setValue(null);
        tf_ExaminationType.setText(null);
        tf_hospital.setText(null);
        tf_pid.setText(null);
        choiceBoxTime.setValue(null);

    }
    public void getQuery() throws SQLException, ClassNotFoundException {

        String query1 = "INSERT INTO transfer (P_ID, PresentTime, Presentdate, trans_date, trans_time, hospital, examination_type) " +
                "VALUES ('"+tf_pid.getText()+"', '"+LocalTime.now()+"', '"+LocalDate.now()+"', '"+ tf_Date.getValue() +"', '"
                + choiceBoxTime.getValue()+"', '"+ tf_hospital.getText()+"', '"+tf_ExaminationType.getText()+"')";

        String query2 = "INSERT INTO diagnosis ( P_ID, D_ID, Diagnosis_Time, Diagnosis_date, diagnose, treatment) " +
                "VALUES('"+tf_pid.getText()+"', '"+DoctorID+"', '"+LocalTime.now()+"', '"+LocalDate.now()+"', 'External transfer','--')";

        DB_Connection.execute(query1);
        DB_Connection.execute(query2);


    }



}
