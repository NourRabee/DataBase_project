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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class D3_DeleteAppointmentController implements Initializable {

    // connecting Scene builder tools

    @FXML
    private ChoiceBox ChoiceBox;

    @FXML
    private DatePicker tf_date;

    @FXML
    private TextField tf_id;

    ResultSet rs;

    // get doctor id from UserSessionD class
    int doctorId=UserSessionD.getInstance(null).getDoctor().getId();

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

            // check if P_ID is not empty
            if(!tf_id.getText().isEmpty()) {
                ObservableList reservedTimes = getReservedTimes(newDate, doctorId, Integer.parseInt(tf_id.getText()));

                ChoiceBox.setItems(reservedTimes);
            }
            // else if P_ID is empty
            else{
                ObservableList reservedTimes2 = getReservedTimes2(newDate, doctorId);

            }

        });
    }

    public void Delete_buttonClicked(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        rs = DB_Connection.select("SELECT P_ID FROM appointment WHERE P_ID = '"+ tf_id.getText()+"'");

            // if all values is not empty
            if (!tf_id.getText().isEmpty() && tf_date.getValue() != null && ChoiceBox.getValue() != null) {

                String sql = "";
                sql = "DELETE FROM appointment WHERE P_ID= '" + tf_id.getText() + "' and A_date = '" + java.sql.Date.valueOf(tf_date.getValue()) + "' and StartTime = '" + java.sql.Time.valueOf(ChoiceBox.getValue().toString()) + "' and D_ID ='" + doctorId + "'";
                try {
                    if(rs.next()) {
                        DB_Connection.execute(sql);
                        clear_ButtonClicked();
                    }
                    else{
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setHeaderText(null);
                        alert2.setContentText("This patient does not exist!");
                        alert2.showAndWait();
                        clear_ButtonClicked();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }// else if we delete all the appointment in selected date
            else if (tf_id.getText().isEmpty() && tf_date.getValue() != null && ChoiceBox.getValue() == null) {

                String sql = "DELETE FROM appointment WHERE A_date = '" + java.sql.Date.valueOf(tf_date.getValue()) + "' and D_ID ='" + doctorId + "'";
                try {
                        DB_Connection.execute(sql);
                        clear_ButtonClicked();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //else if we delete all the appointments for a specific patient
            else if (!tf_id.getText().isEmpty() && tf_date.getValue() == null && ChoiceBox.getValue() == null) {
                String sql = "DELETE FROM appointment WHERE P_ID ='" + tf_id.getText() + "'";
                try {
                    if(rs.next()) {
                        DB_Connection.execute(sql);
                        clear_ButtonClicked();
                    }else{
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setHeaderText(null);
                        alert2.setContentText("This patient does not exist!");
                        alert2.showAndWait();
                        clear_ButtonClicked();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    // if we want to delete an appointment according to P_ID, Date, and Time
    public static ObservableList<String> getReservedTimes(LocalDate appointmentDate, int doctorId, int patient_id) {
        ObservableList<String> reservedTimes = FXCollections.observableArrayList();
        try {

            // define and execute the query
            // ResultSet object is a table of data representing a database result set

            String sql = "SELECT * from appointment where P_ID = '"+patient_id+"' and D_ID= '"+doctorId+"' and A_date = '"+appointmentDate.toString()+"'";
            ResultSet rs = DB_Connection.select(sql);

            while (rs.next()){
                // (rs. next()) means that if the next row is not null (means if it exists)
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

    // if we want to delete an appointment according Date
    public static ObservableList<String> getReservedTimes2(LocalDate appointmentDate, int doctorId) {
        ObservableList<String> reservedTimes = FXCollections.observableArrayList();
        try {

            // define and execute the query
            // ResultSet object is a table of data representing a database result set
            String sql = "SELECT * from appointment where D_ID= '"+doctorId+"' and A_date = '"+appointmentDate.toString()+"'";
            ResultSet rs = DB_Connection.select(sql);

            // (rs. next()) means that if the next row is not null (means if it exists)
            while (rs.next()){
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
    public void clear_ButtonClicked() {
        ChoiceBox.setValue(null);
        tf_date.setValue(null);
        tf_id.setText(null);
    }

}
