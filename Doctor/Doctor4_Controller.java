package com.example.finalprojdb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.scene.control.TableView;

public class Doctor4_Controller implements Initializable {

    @FXML
    private TableView<LabRequestClass> LabResultTable;

    @FXML
    private TableView<DiagnosisClass> TableView;

    @FXML
    private TableColumn<DiagnosisClass, Date> colDate;

    @FXML
    private TableColumn<LabRequestClass, Date> colDate1;

    @FXML
    private TableColumn<DiagnosisClass, String> colDiagnosis;

    @FXML
    private TableColumn<DiagnosisClass, Integer> colDoctorId;

    @FXML
    private TableColumn<DiagnosisClass, String> colDoctorName;

    @FXML
    private TableColumn<LabRequestClass, String> colPatientname1;

    @FXML
    private TableColumn<LabRequestClass, String> colLabTestName;

    @FXML
    private TableColumn<LabRequestClass, String> colLabTestResult;

    @FXML
    private TableColumn<DiagnosisClass, Integer> colPatientId;

    @FXML
    private TableColumn<DiagnosisClass, String> colSpecialty;

    @FXML
    private TableColumn<LabRequestClass, Integer> colPatientid1;

    @FXML
    private TableColumn<DiagnosisClass, Time> colTime;

    @FXML
    private TableColumn<LabRequestClass, Time> colTime1;

    @FXML
    private TableColumn<DiagnosisClass, String> colTreatment;

    @FXML
    private TextField tf_search;

    @FXML
    private TextField tf_search1;


    ObservableList<DiagnosisClass> list = FXCollections.observableArrayList();
    ObservableList<LabRequestClass> list2 = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colDoctorId.setCellValueFactory(new PropertyValueFactory<>("DoctortId"));
        colDoctorName.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        colSpecialty.setCellValueFactory(new PropertyValueFactory<>("doctorSpecialization"));
        colDiagnosis.setCellValueFactory(new PropertyValueFactory<>("diagnose"));
        colTreatment.setCellValueFactory(new PropertyValueFactory<>("treatment"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("Diagnosis_Time"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Diagnosis_date"));

        colPatientid1.setCellValueFactory(new PropertyValueFactory<>("PatientID"));
        colPatientname1.setCellValueFactory(new PropertyValueFactory<>("PatientName"));
        colDate1.setCellValueFactory(new PropertyValueFactory<>("LAB_date"));
        colTime1.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
        colLabTestName.setCellValueFactory(new PropertyValueFactory<>("TestName"));
        colLabTestResult.setCellValueFactory(new PropertyValueFactory<>("Result"));

        try {
            ResultSet rs = DB_Connection.select("SELECT * FROM diagnosis, doctor, patient, " +
                    "patients_history where patients_history.P_ID = Patient.P_ID and Patient.P_ID =" +
                    " diagnosis.P_ID and doctor.D_ID = diagnosis.D_ID");

            while (rs.next()) {
                list.add(new DiagnosisClass(new PatientClass(rs.getInt("P_ID"),
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
                        rs.getTime("Diagnosis_Time"),
                        rs.getDate("Diagnosis_date"),
                        rs.getString("diagnose"),
                        rs.getString("treatment"),
                        rs.getString("Complaints")));
            }
            TableView.setItems(list);


            ResultSet rs2 = DB_Connection.select("SELECT * FROM lab_test, lab_request," +
                    " patient, patients_history where Patients_history.P_ID = patient.P_ID and " +
                    "patient.P_ID = lab_request.P_ID and lab_request.Test_code = lab_test.Test_code");

            while (rs2.next()) {
                list2.add(new LabRequestClass(new PatientClass(rs2.getInt("P_ID"),
                        rs2.getString("P_name"),
                        rs2.getString("address"),
                        rs2.getString("email"),
                        rs2.getDate("birth").toLocalDate(),
                        rs2.getInt("phone"),
                        rs2.getString("blood_type"),
                        rs2.getString("allergy")),
                        new LabTestClass(rs2.getInt("Test_code"),
                                rs2.getString("T_name"),
                                rs2.getString("Requirements"),
                                rs2.getDouble("cost")),
                        rs2.getDate("LAB_date"),
                        rs2.getTime("StartTime"),
                        rs2.getDouble("cost"),
                        rs2.getDate("Date_Of_Payment"),
                        rs2.getString("result")));
            }

            LabResultTable.setItems(list2);

            search(TableView, tf_search1, list);
            search2(LabResultTable, tf_search, list2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void search(TableView<DiagnosisClass> table, TextField search_tf, ObservableList<DiagnosisClass> list){

        //wrap the observationalList "list" in a FilteredList (initially display all data)
        FilteredList<DiagnosisClass> filterList = new FilteredList<>(list, b -> true);

        //set the filter predict whenever the text field changes.
        search_tf.textProperty().addListener((observable, newValue, oldValue) -> {
            filterList.setPredicate(diagnosis ->  {

                //if the tf_search is empty, display all the patients

                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {

                    return true;

                }
                String searchKeyword = newValue.toLowerCase();

                if (String.valueOf(diagnosis.getPatientId()).indexOf(searchKeyword) > -1) {

                    return true; //means we find match in patient ID

                } else if (String.valueOf(diagnosis.getDoctortId()).contains(searchKeyword)) {

                    return true;

                }
                else if (diagnosis.getDoctorName().toLowerCase().indexOf(searchKeyword) > -1) {

                    return true;

                }
                else if (diagnosis.getDoctorSpecialization().toLowerCase().indexOf(searchKeyword) > -1) {

                    return true;

                } else if (diagnosis.getDiagnosis_date().toString().indexOf(searchKeyword) > -1) {

                    return true;
                }
                else if (diagnosis.getDiagnosis_Time().toString().indexOf(searchKeyword) > -1) {

                    return true;
                }
                else if (diagnosis.getDiagnose().toLowerCase().indexOf(searchKeyword) > -1) {

                    return true;
                }
                else if (diagnosis.getTreatment().toLowerCase().indexOf(searchKeyword) > -1) {

                    return true;
                }
                else
                    return false;// no match found

            });

        });
        SortedList<DiagnosisClass> sortedData = new SortedList<>(filterList);

        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);
    }


    public static void search2(TableView<LabRequestClass> table, TextField search_tf, ObservableList<LabRequestClass> list){

        //wrap the observationalList "list" in a FilteredList (initially display all data)
        FilteredList<LabRequestClass> filterList = new FilteredList<>(list, b -> true);

        //set the filter predict whenever the text field changes.
        search_tf.textProperty().addListener((observable, newValue, oldValue) -> {
            filterList.setPredicate(lab_request ->  {

                //if the tf_search is empty, display all the patients

                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {

                    return true;

                }
                String searchKeyword = newValue.toLowerCase();

                if (String.valueOf(lab_request.getPatientID()).indexOf(searchKeyword) > -1) {

                    return true; //means we find match in patient ID
                }
                else if (lab_request.getPatientName().toLowerCase().indexOf(searchKeyword) > -1) {

                    return true;

                }
                else if (lab_request.getLAB_date().toString().indexOf(searchKeyword) > -1) {

                    return true;
                }
                else if (lab_request.getStartTime().toString().indexOf(searchKeyword) > -1) {

                    return true;
                }
                else if (lab_request.getTestName().toLowerCase().indexOf(searchKeyword) > -1) {

                    return true;
                }
                else
                    return false;// no match found

            });

        });
        SortedList<LabRequestClass> sortedData = new SortedList<>(filterList);

        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);
    }



}
