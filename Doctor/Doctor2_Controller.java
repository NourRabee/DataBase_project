package com.example.finalprojdb;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Doctor2_Controller implements Initializable {

    @FXML
    public TableView<PatientClass> patientTable;
    @FXML
    public TableColumn<PatientClass, Integer> idcol;
    @FXML
    public TableColumn<PatientClass, String> namecol;
    @FXML
    public TableColumn<PatientClass, Integer> phonecol;
    @FXML
    public TableColumn<PatientClass, String> emailcol;
    @FXML
    public TableColumn<PatientClass, String> addcol;
    @FXML
    public TableColumn<PatientClass, LocalDate> birthcol;
    @FXML
    public TableColumn<PatientClass, Integer> agecol;
    @FXML
    public TableColumn<PatientClass, String> bloodcol;
    @FXML
    public TableColumn<PatientClass, String> allergycol;
    @FXML
    public TextField search_tf;

    ObservableList<PatientClass> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ResultSet rs = DB_Connection.select("SELECT * FROM patient left outer join" +
                    " patients_history on patient.P_ID=patients_history.P_ID");

            while (rs.next()) {
                list.add(new PatientClass(rs.getInt("patient.P_ID"),
                        rs.getString("P_name"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getDate("birth").toLocalDate(),
                        rs.getInt("phone"),
                        rs.getString("blood_type"),
                        rs.getString("allergy")));

            }

            idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
            namecol.setCellValueFactory(new PropertyValueFactory<>("pname"));
            phonecol.setCellValueFactory(new PropertyValueFactory<>("phone"));
            birthcol.setCellValueFactory(new PropertyValueFactory<>("birth"));
            agecol.setCellValueFactory(new PropertyValueFactory<>("age"));
            addcol.setCellValueFactory(new PropertyValueFactory<>("address"));
            emailcol.setCellValueFactory(new PropertyValueFactory<>("email"));
            bloodcol.setCellValueFactory(new PropertyValueFactory<>("bloodType"));
            allergycol.setCellValueFactory(new PropertyValueFactory<>("allergy"));

            patientTable.setItems(list);

            search(patientTable, search_tf, list);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void insert_MouseClicked() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("D2_AddPatient.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Doctor2_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        search(patientTable, search_tf, list);

    }

    public void delete_MouseClicked() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("D2_deletePatient.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Doctor2_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        search(patientTable, search_tf, list);
    }

    public void edit_MouseClicked() {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("D2_editPatient.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Doctor2_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        search(patientTable, search_tf, list);
    }

    public void refresh_MouseClicked() {

        list.clear();
        try {
            ResultSet rs = DB_Connection.select("SELECT * FROM patient left outer join" +
                    " patients_history on patient.P_ID=patients_history.P_ID");

            while (rs.next()) {
                list.add(new PatientClass(rs.getInt("patient.P_ID"),
                        rs.getString("P_name"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getDate("birth").toLocalDate(),
                        rs.getInt("phone"),
                        rs.getString("blood_type"),
                        rs.getString("allergy")));

            }
            patientTable.setItems(list);

            search(patientTable, search_tf, list);

        } catch (Exception e) {

        }
    }


    public void search(TableView<PatientClass> table, TextField tf_search, ObservableList<PatientClass> list){


        //wrap the observationalList "list" in a FilteredList (initially display all data)
        FilteredList<PatientClass> filterList = new FilteredList<>(list, b -> true);

        //set the filter predict whenever the text field changes.
        tf_search.textProperty().addListener((observable, newValue, oldValue) -> {
            filterList.setPredicate(patient -> {

                //if the tf_search is empty, display all the patients

                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {

                    return true;

                }
                String searchKeyword = newValue.toLowerCase();

                if (patient.getPname().toLowerCase().indexOf(searchKeyword) > -1) {

                    return true; //means we find match in patient name

                } else if ((String.valueOf(patient.getId())).indexOf(searchKeyword) > -1) {

                    return true;

                } else if (patient.getAddress().toLowerCase().indexOf(searchKeyword) > -1) {

                    return true;
                } else if (patient.getEmail().toLowerCase().indexOf(searchKeyword) > -1) {

                    return true;
                } else if (patient.getBloodType().toLowerCase().indexOf(searchKeyword) > -1) {

                    return true;
                } else if ((patient.getBirth().toString()).indexOf(searchKeyword) > -1) {


                    return true;
                } else if (patient.getAllergy().toLowerCase().indexOf(searchKeyword) > -1) {

                    return true;
                } else if (String.valueOf(patient.getPhone()).indexOf(searchKeyword) > -1) {

                    return true;
                } else
                    return false;// no match found

            });

        });
        SortedList<PatientClass> sortedData = new SortedList<>(filterList);

        //Bind sorted result with table view "patientTable"
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        //Apply filtered and sorted data to the table View.setItems(sortedData);

        table.setItems(sortedData);

    }
}
