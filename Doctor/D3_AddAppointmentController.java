package com.example.finalprojdb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class D3_AddAppointmentController implements Initializable {

    // connecting Scene builder tools
    @FXML
    private ChoiceBox ChoiceBox; // time

    @FXML
    private Button Save;

    @FXML
    private Button clear;

    @FXML
    private DatePicker tf_date; // date

    @FXML
    private TextField tf_id; // Patient ID


    // To control the Scene builder tools
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //force user to pick dates from the current day,"Disable all the dates before the current date"
        tf_date.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });

        // event listener ,, as sensors
        // observable, oldDate, newDate : the Observablelist we will fill it , The old date,  and the new date
        tf_date.valueProperty().addListener((observable, oldDate, newDate)-> {

            ObservableList<String> timeList = FXCollections.observableArrayList();

            String[] hours = {"08", "09", "10", "11", "12", "13", "14", "15", "16", "17"};
            String[] minutes = {"00:00", "15:00", "30:00", "45:00"};
            for (int i = 0; i < hours.length; i++)
                for (int j = 0; j < minutes.length; j++)
                    timeList.add(hours[i] + ":" + minutes[j]); // fill the ObservableList with clinic time

            // Get object from type Doctor and from UserSession class
            DoctorClass doctor = UserSessionD.getInstance(null).getDoctor();

            // ObservableList for booked doctor appointments
            ObservableList<String> doctorReservedTimes = getDoctorReservedTimes(newDate, doctor.getId());

            // ObservableList for booked patient appointments
            ObservableList<String> PatientReservedTime = getPatientReservedTimes(newDate, Integer.parseInt(tf_id.getText()));

            // Remove reserved doctor times
            for (int i = 0; i<doctorReservedTimes.size() ; i++)
                timeList.remove(doctorReservedTimes.get(i));

            // Remove reserved patient times
            for (int j = 0 ; j<PatientReservedTime.size() ; j++)
                timeList.remove(PatientReservedTime.get(j));

            // Add the remaining times to ChoiceBox
            ChoiceBox.setItems(timeList);
        });
    }
    @FXML
    void Save_buttonClicked(ActionEvent event)  throws SQLException, ClassNotFoundException {

        // get doctor id from UserSession class
        int doctorId = UserSessionD.getInstance(null).getDoctor().getId();
        ResultSet rs = DB_Connection.select("SELECT P_ID FROM appointment WHERE P_ID = '"+tf_id.getText()+"'"+"AND D_ID = '"+doctorId+"'");

        // to check the values from new appointment.FXML
        if ( tf_id.getText().isEmpty() || tf_date.getValue()== null ||  ChoiceBox.getValue()==null ) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();

        }else if(!rs.next()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("This Patient does not have any appointments with you!!");
            alert.showAndWait();

        } else {
            String query = "INSERT into appointment (P_ID, D_ID, A_date, StartTime) Values ('" + Integer.parseInt(tf_id.getText()) + "','" + doctorId + "','" + java.sql.Date.valueOf(tf_date.getValue()) + "','" + Time.valueOf(ChoiceBox.getValue().toString()) + "') ";
            DB_Connection.execute(query);
            clear_ButtonClicked();
        }
    }

    @FXML
    void clear_ButtonClicked() {
        ChoiceBox.setValue(null);
        tf_date.setValue(null);
        tf_id.setText(null);
    }

    // localDate : date  // for check doctor time
    public static ObservableList<String> getDoctorReservedTimes(LocalDate appointmentDate, int doctorId) {
        ObservableList<String> reservedTimes = FXCollections.observableArrayList();
        try {

            // define and execute the query
            // ResultSet object is a table of data representing a database result set
            String sql = "SELECT * from appointment where D_ID= '"+doctorId+"' and A_date = '"+appointmentDate.toString()+"'";
            ResultSet rs = DB_Connection.select(sql);

            // (rs. next()) means that if the next row is not null (means if it exists)
            while (rs.next()) {
                reservedTimes.add(rs.getString("StartTime"));
            }

        }
        catch (SQLException e){
            System.out.println(e);
        }
        finally {
            return reservedTimes;
        }
    }


    // localDate : date  // for check patient time
    public static ObservableList<String> getPatientReservedTimes(LocalDate appointmentDate, int PatientID) {
        ObservableList<String> reservedTimes = FXCollections.observableArrayList();
        try {

            // define and execute the query
            // ResultSet object is a table of data representing a database result set

            String sql = "SELECT * from appointment where P_ID= '"+PatientID+"' and A_date = '"+appointmentDate.toString()+"'";
            ResultSet rs = DB_Connection.select(sql);

            // (rs. next()) means that if the next row is not null (means if it exists)
            while (rs.next()) {
                reservedTimes.add(rs.getString("StartTime"));
            }

        }
        catch (SQLException e){
            System.out.println(e);
        }
        finally {
            return reservedTimes;
        }
    }

}
