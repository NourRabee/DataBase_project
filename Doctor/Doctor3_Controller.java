package com.example.finalprojdb;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Doctor3_Controller implements Initializable {

    // connecting Scene builder tools
    @FXML
    public TableView<AppointmentsClass> appointmentTable;
    @FXML
    public TableColumn<AppointmentsClass,Integer> idcol;
    @FXML
    public TableColumn<AppointmentsClass,String > namecol;
    @FXML
    public TableColumn<AppointmentsClass, Date> datecol;
    @FXML
    public TableColumn<AppointmentsClass, Time> timecol;
    @FXML
    public TableColumn<AppointmentsClass, String> editCol;
    @FXML
    public TextField search_tf;

    // define the observable list
    ObservableList<AppointmentsClass> list = FXCollections.observableArrayList();

    // get doctor id from UserSession class
    int doctorID = UserSessionD.getInstance(null).getDoctor().getId();

    static AppointmentsClass appointment;

    public static AppointmentsClass getAppointment() {
        return appointment;
    }

    public static void setAppointment(AppointmentsClass appointment) {
        Doctor3_Controller.appointment = appointment;
    }

    // To control the Scene builder tools
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // defining the contents of the Scene builder columns from classes

        idcol.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        datecol.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        timecol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        // editCol.setCellValueFactory(new PropertyValueFactory<Appointment, FontAwesomeIconView>("EDIT"));

        try {

            // define and execute the query
            // ResultSet object is a table of data representing a database result set

            ResultSet rs = DB_Connection.select("SELECT * FROM appointment a, patient p, patients_history ph, doctor d where  a.P_ID=p.P_ID" +
                    " and a.P_ID=ph.P_ID and a.D_ID = d.D_ID and a.D_ID='"+doctorID+"'");

            // (rs. next()) means that if the next row is not null (means if it exists)
            while (rs.next()) {
                // sending DataBase columns as constructors to tables classes

                list.add(new AppointmentsClass(new PatientClass(rs.getInt("P_ID"),
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

            //add cell of button edit
            Callback<TableColumn<AppointmentsClass, String>, TableCell<AppointmentsClass, String>> cellFoctory = (TableColumn<AppointmentsClass, String> param) -> {
                // make cell containing buttons
                final TableCell<AppointmentsClass, String> cell = new TableCell<AppointmentsClass, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        //that cell created only on non-empty rows
                        if (empty) {
                            setGraphic(null);
                            setText(null);

                        } else {


                            FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);


                            editIcon.setStyle(
                                    " -fx-cursor: hand ;"
                                            + "-glyph-size:28px;"
                                            + "-fx-fill:#00E676;"
                            );
                            setGraphic(editIcon);
                            editIcon.setOnMouseClicked((MouseEvent event) -> {

                                appointment = appointmentTable.getSelectionModel().getSelectedItem();
                                FXMLLoader loader = new FXMLLoader ();
                                loader.setLocation(getClass().getResource("D3_EditAppointment.fxml"));
                                try {
                                    loader.load();
                                } catch (IOException ex) {
                                    System.out.println(ex.getMessage());
                                }

                                D3_EditAppointmentController updateAppointmentController = loader.getController();

                                updateAppointmentController.setTextField(String.valueOf(appointment.getPatient().getId()), LocalDate.parse(appointment.getAppointmentDate().toString()),appointment.getStartTime());
                                updateAppointmentController.updateTime(null);
                                Parent parent = loader.getRoot();

                                Stage stage = new Stage();
                                stage.setScene(new Scene(parent));
                                stage.initStyle(StageStyle.UTILITY);
                                stage.show();


                            });


                            setText(null);

                        }
                    }

                };

                return cell;
            };
            editCol.setCellFactory(cellFoctory);
            // To fill the table view
            appointmentTable.setItems(list);

            search(appointmentTable, search_tf, list);

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    public void add_new_appointment(MouseEvent mouseEvent) throws IOException {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("D3_AddAppointment.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Doctor2_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        search(appointmentTable, search_tf, list);

    }

    public void delete(MouseEvent mouseEvent) throws IOException {

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

        search(appointmentTable, search_tf, list);

    }


    public void update(MouseEvent mouseEvent) {
    }

    public static void search(TableView<AppointmentsClass> table, TextField search_tf, ObservableList<AppointmentsClass> list){

        //wrap the observationalList "list" in a FilteredList (initially display all data)
        FilteredList<AppointmentsClass> filterList = new FilteredList<>(list, b -> true);

        //set the filter predict whenever the text field changes.
        search_tf.textProperty().addListener((observable, newValue, oldValue) -> {
            filterList.setPredicate(appointment -> {

                //if the tf_search is empty, display all the patients

                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {

                    return true;

                }
                String searchKeyword = newValue.toLowerCase();

                if (String.valueOf(appointment.getPatientId()).indexOf(searchKeyword) > -1) {

                    return true; //means we find match in patient ID

                } else if (appointment.getPatientName().toLowerCase().indexOf(searchKeyword) > -1) {

                    return true;

                } else if (appointment.getStartTime().toString().indexOf(searchKeyword) > -1) {

                    return true;

                } else if (appointment.getAppointmentDate().toString().indexOf(searchKeyword) > -1) {

                    return true;
                } else
                    return false;// no match found

            });

        });
        SortedList<AppointmentsClass> sortedData = new SortedList<>(filterList);

        //Bind sorted result with table view "patientTable"
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        //Apply filtered and sorted data to the table View.setItems(sortedData);

        table.setItems(sortedData);
    }


    public void refresh(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        // Clear the list to fill it with new data
        list.clear();

        // define and execute the query
        // ResultSet object is a table of data representing a database result set
        ResultSet rs = DB_Connection.select("SELECT * FROM appointment a, patient p, patients_history ph, doctor d where  a.P_ID=p.P_ID" +
                " and a.P_ID=ph.P_ID and a.D_ID = d.D_ID and a.D_ID='"+doctorID+"'");

        // (rs. next()) means that if the next row is not null (means if it exists)
        while (rs.next()) {
            // sending DataBase columns as constructors to tables classes
            list.add(new AppointmentsClass(new PatientClass(rs.getInt("P_ID"),
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
        appointmentTable.setItems(list);

        search(appointmentTable, search_tf, list);

    }

}
