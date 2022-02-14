package com.example.finalprojdb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class D3_EditAppointmentController implements Initializable {

    @FXML
    public ChoiceBox ChoiceBox;

    @FXML
    private Button update;

    @FXML
    private Button clear;

    @FXML
    private DatePicker tf_date;

    @FXML
    private TextField tf_id;

    int patientidOne= Doctor3_Controller.appointment.getPatientId();
    Date dateOne = Doctor3_Controller.appointment.getAppointmentDate();
    Time timeOne  = Doctor3_Controller.appointment.getStartTime() ;

    LocalDate date;
    Time time;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println(  " id "+patientidOne );
        System.out.println(  "date"+ dateOne );
        System.out.println(  "time"+ timeOne);

    }

    public void updateTime(ActionEvent actionEvent){
        ObservableList<String> timeList = FXCollections.observableArrayList();
        LocalDate newDate = tf_date.getValue();
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
        for (int i = 0; i < doctorReservedTimes.size(); i++)
            timeList.remove(doctorReservedTimes.get(i));

        // Remove reserved patient times
        for (int j = 0; j < PatientReservedTime.size(); j++)
            timeList.remove(PatientReservedTime.get(j));

        // Add the remaining times to ChoiceBox

        ChoiceBox.setItems(timeList);
    }
    public void update_buttonClicked(ActionEvent actionEvent) {

        date = tf_date.getValue();
        time = Time.valueOf(ChoiceBox.getValue().toString());

        int DoctorID = UserSessionD.getInstance(null).getDoctor().getId();

        String sql = "UPDATE appointment SET A_date = '"+date +"' , StartTime = '"+time+"' WHERE P_ID = '" +patientidOne+"' and A_date ='" +dateOne+"' and StartTime = '"+timeOne+"'" ;
        try {
            DB_Connection.execute(sql);
            clear_ButtonClicked();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear_ButtonClicked() {
        tf_date.setValue(null);
        tf_id.setText(null);
        ChoiceBox.setValue(null);
    }



    public void setTextField(String patientId, LocalDate appointmentDate, Time startTime){

        tf_id.setText(patientId);
        tf_date.setValue(appointmentDate);
        ChoiceBox.setValue(startTime);
    }

    // localDate : date  // for check doctor time
    public static ObservableList<String> getDoctorReservedTimes(LocalDate appointmentDate, int doctorId) {
        ObservableList<String> reservedTimes = FXCollections.observableArrayList();
        try {

            // define and execute the query
            // ResultSet object is a table of data representing a database result set
            String sql = "SELECT * from appointment where D_ID= '" + doctorId + "' and A_date = '" + appointmentDate.toString() + "'";
            ResultSet rs = DB_Connection.select(sql);

            // (rs. next()) means that if the next row is not null (means if it exists)
            while (rs.next()) {
                reservedTimes.add(rs.getString("StartTime"));
            }

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            return reservedTimes;
        }
    }


    // localDate : date  // for check patient time
    public static ObservableList<String> getPatientReservedTimes(LocalDate appointmentDate, int PatientID) {
        ObservableList<String> reservedTimes = FXCollections.observableArrayList();
        try {

            // define and execute the query
            // ResultSet object is a table of data representing a database result set

            String sql = "SELECT * from appointment where P_ID= '" + PatientID + "' and A_date = '" + appointmentDate.toString() + "'";
            ResultSet rs = DB_Connection.select(sql);

            // (rs. next()) means that if the next row is not null (means if it exists)
            while (rs.next()) {
                reservedTimes.add(rs.getString("StartTime"));
            }

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            return reservedTimes;
        }

    }
}

