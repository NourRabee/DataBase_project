package com.example.finalprojdb;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Patient3_Controller implements Initializable {

    @FXML
    private TableColumn<AppointmentsClass, Date> Datecol;

    @FXML
    private TableColumn<AppointmentsClass, Time> TimeCol;

    @FXML
    private TableView<AppointmentsClass> appointmentTable;

    @FXML
    private TableColumn<AppointmentsClass, Integer> idcol;

    @FXML
    private TableColumn<AppointmentsClass, String> namecol;

    @FXML
    private TableColumn<AppointmentsClass, String> specialityCol;

    ObservableList<AppointmentsClass> TableList = FXCollections.observableArrayList();
    int patientID = UserSessionP.getInstance(null).getPatient().getId();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idcol.setCellValueFactory(new PropertyValueFactory<>("DoctortId"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("DoctorName"));
        specialityCol.setCellValueFactory(new PropertyValueFactory<>("DoctorSpecialization"));
        Datecol.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        TimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));

        try {

            // define and execute the query
            // ResultSet object is a table of data representing a database result set

            ResultSet rs = DB_Connection.select("SELECT * FROM appointment a, patient p, patients_history ph, doctor d where  a.P_ID=p.P_ID" +
                    " and a.P_ID=ph.P_ID and a.D_ID = d.D_ID and a.P_ID='"+patientID+"'");

            // (rs. next()) means that if the next row is not null (means if it exists)
            while (rs.next()) {
                // sending DataBase columns as constructors to tables classes

                TableList.add(new AppointmentsClass(new PatientClass(rs.getInt("P_ID"),
                        rs.getString("P_name"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getDate("birth").toLocalDate(),
                        rs.getInt("phone"),
                        rs.getString("blood_type"),
                        rs.getString("allergy")),
                        new DoctorClass(rs.getInt("D_ID"),
                                0,
                                rs.getString("D_name"),
                                rs.getString("specialization"),
                                rs.getString("address"),
                                rs.getString("email"),
                                rs.getInt("phone")),
                        rs.getDate("A_date"),
                        rs.getTime("StartTime"),
                        rs.getTime("EndTime")));

            }
            // To fill the table view
            appointmentTable.setItems(TableList);

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    @FXML
    void add_new_appointment() throws IOException {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("P3_AddAppointment.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Doctor2_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void delete() throws IOException {

        try{
            Parent parent = FXMLLoader.load(getClass().getResource("D3_DeleteAppointment.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Doctor2_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void refresh(MouseEvent event) throws SQLException, ClassNotFoundException {

        TableList.clear();

        ResultSet rs = DB_Connection.select("SELECT * FROM appointment a, patient p, patients_history ph, doctor d where  a.P_ID=p.P_ID" +
                " and a.P_ID=ph.P_ID and a.D_ID = d.D_ID and a.P_ID='" + patientID + "'");

        // (rs. next()) means that if the next row is not null (means if it exists)
        while (rs.next()) {
            // sending DataBase columns as constructors to tables classes

            TableList.add(new AppointmentsClass(new PatientClass(rs.getInt("P_ID"),
                    rs.getString("P_name"),
                    rs.getString("address"),
                    rs.getString("email"),
                    rs.getDate("birth").toLocalDate(),
                    rs.getInt("phone"),
                    rs.getString("blood_type"),
                    rs.getString("allergy")),
                    new DoctorClass(rs.getInt("D_ID"),
                            0,
                            rs.getString("D_name"),
                            rs.getString("specialization"),
                            rs.getString("address"),
                            rs.getString("email"),
                            rs.getInt("phone")),
                    rs.getDate("A_date"),
                    rs.getTime("StartTime"),
                    rs.getTime("EndTime")));

        }
        // To fill the table view
        appointmentTable.setItems(TableList);

    }


}
