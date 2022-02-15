package com.example.finalprojdb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class Doctor5_Controller implements Initializable {

    @FXML
    private TableView<MedicineClass> writePrescriptionTable;

    @FXML
    private TableColumn<MedicineClass, Integer> MCodeCol;

    @FXML
    private TableColumn<MedicineClass, String> MnameCol;

    @FXML
    private TableColumn<MedicineClass, Double> costCol;

    @FXML
    private TableColumn<MedicineClass, String> effectSideCol;

    @FXML
    private TableColumn<MedicineClass, String> interactionsCol;

    @FXML
    private TextField tf_MedicineCode;

    @FXML
    private TextField tf_pid;

    @FXML
    private TextField tf_dosage;

    ObservableList<MedicineClass> list = FXCollections.observableArrayList();

    int DoctorID = UserSessionD.getInstance(null).getDoctor().getId();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {

            ResultSet rs = DB_Connection.select("SELECT * FROM medicine");

            while (rs.next()) {

                list.add(new MedicineClass(rs.getInt("M_code"),
                        rs.getString("M_name"),
                        rs.getDouble("cost"),
                        rs.getString("Effect_side"),
                        rs.getString("Interactions")));

            }

            MCodeCol.setCellValueFactory(new PropertyValueFactory<>("M_code"));
            MnameCol.setCellValueFactory(new PropertyValueFactory<>("M_name"));
            costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
            interactionsCol.setCellValueFactory(new PropertyValueFactory<>("Interactions"));
            effectSideCol.setCellValueFactory(new PropertyValueFactory<>("Effect_side"));

            writePrescriptionTable.setItems(list);

        } catch (SQLException | ClassNotFoundException e ) {
            e.printStackTrace();
        }
    }

    @FXML
    public void insert_ButtonClicked() throws SQLException, ClassNotFoundException {


        ResultSet rs1 = DB_Connection.select("SELECT P_ID FROM appointment WHERE D_ID = '" + DoctorID + "' AND P_ID = '" + tf_pid.getText() + "'");
        ResultSet rs2 = DB_Connection.select("SELECT M_code FROM medicine WHERE M_code = '" + tf_MedicineCode.getText() + "'");


        String code = tf_MedicineCode.getText();
        String p_id = tf_pid.getText();
        String dosage = tf_dosage.getText();


        if (code.isEmpty() || p_id.isEmpty()  || dosage.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();

        } else {

            if (!rs1.next()) {

                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setHeaderText(null);
                alert2.setContentText("Make sure that it is your patient!");
                alert2.showAndWait();
                tf_pid.setText(null);

            } else {
                if (!rs2.next()) {

                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setHeaderText(null);
                    alert2.setContentText("Make sure of the medicine code!");
                    alert2.showAndWait();
                    tf_MedicineCode.setText(null);

                } else {
                    getQuery();
                    clear_ButtonClicked();
                }
            }

        }
    }

        @FXML
        public void clear_ButtonClicked () {


            tf_MedicineCode.setText(null);
            tf_pid.setText(null);
            tf_dosage.setText(null);

        }

        public void getQuery () throws SQLException, ClassNotFoundException {

            ResultSet rs = DB_Connection.select("SELECT M_name FROM medicine WHERE M_code = '"+tf_MedicineCode.getText() + "'");

            ObservableList<String> medicine_name = FXCollections.observableArrayList();

            while (rs.next()){

                medicine_name.add(rs.getString("M_name"));
            }
            String nameM= medicine_name.get(0);

            String query1 = "INSERT INTO prescription (P_ID, Med_code, Dosage, Prescription_date, Prescription_Time) " +
                "VALUES ('"+tf_pid.getText()+"', '"+tf_MedicineCode.getText()+"', '"+tf_dosage.getText()+"', '"+ LocalDate.now() +"', '"+ LocalTime.now() +"')";

            String query2 = "INSERT INTO diagnosis ( P_ID, D_ID, Diagnosis_Time, Diagnosis_date, diagnose, treatment) " +
                    "VALUES('"+tf_pid.getText()+"', '"+DoctorID+"', '"+LocalTime.now()+"', '"+LocalDate.now()+"', 'Prescription','"+nameM+"')";

            DB_Connection.execute(query1);
            DB_Connection.execute(query2);


            }
    }
