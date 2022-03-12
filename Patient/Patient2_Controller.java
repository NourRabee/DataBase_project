package com.example.finalprojdb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Patient2_Controller implements Initializable {

    @FXML
    private TableView<DoctorClass> clinicDTable;

    @FXML
    private TableColumn<DoctorClass, Integer> doctorIDCol;

    @FXML
    private TableColumn<DoctorClass, String> doctorNameCol;

    @FXML
    private TableColumn<DoctorClass, String> specializationCol;

    @FXML
    private TextField tf_search;

    ObservableList<DoctorClass> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try{

            ResultSet rs = DB_Connection.select("SELECT D_ID, D_name, specialization FROM doctor");

            while (rs.next()){

                list.add(new DoctorClass(rs.getInt("D_ID"),
                        rs.getString("D_name"),
                        rs.getString("specialization")));

            }
            doctorIDCol.setCellValueFactory(new PropertyValueFactory<DoctorClass, Integer>("id"));
            doctorNameCol.setCellValueFactory(new PropertyValueFactory<DoctorClass, String >("name"));
            specializationCol.setCellValueFactory(new PropertyValueFactory<DoctorClass, String>("specialization"));

            clinicDTable.setItems(list);

            //wrap the observationalList "list" in a FilteredList (initially display all data)
            FilteredList<DoctorClass> filterList = new FilteredList<>(list, b -> true);

            //set the filter predict whenever the text field changes.
            tf_search.textProperty().addListener((observable, newValue, oldValue) -> {
                filterList.setPredicate(doctor -> {

                    //if the tf_search is empty, display all the patients

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {

                        return true;

                    }
                    String searchKeyword = newValue.toLowerCase();

                    if (String.valueOf(doctor.getId()).indexOf(searchKeyword) > -1) {

                        return true; //means we find match in doctor ID

                    } else if (doctor.getName().toLowerCase().indexOf(searchKeyword) > -1) {

                        return true;

                    } else if (doctor.getSpecialization().toLowerCase().indexOf(searchKeyword) > -1) {

                        return true;
                    } else
                        return false;// no match found

                });

            });
            SortedList<DoctorClass> sortedData = new SortedList<>(filterList);

            //Bind sorted result with table view "patientTable"
            sortedData.comparatorProperty().bind(clinicDTable.comparatorProperty());

            //Apply filtered and sorted data to the table View.setItems(sortedData);

            clinicDTable.setItems(sortedData);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
