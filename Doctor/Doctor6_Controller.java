package com.example.finalprojdb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class Doctor6_Controller implements Initializable {

    @FXML
    private Label L_testName;

    @FXML
    private TableView<LabTestClass> LabRequestTableView;

    @FXML
    private Button clear1_button;

    @FXML
    private Button clear2_button;

    @FXML
    private Button save1_button;

    @FXML
    private Button save2_button;

    @FXML
    private TableColumn<LabTestClass, Integer> testCodeCol;

    @FXML
    private TableColumn<LabTestClass, Double> testCostCol;

    @FXML
    private TableColumn<LabTestClass, String> testNameCol;

    @FXML
    private TableColumn<LabTestClass, String> testReqCol;

    @FXML
    private DatePicker tf_Date;

    @FXML
    private DatePicker tf_dateOfPayment;

    @FXML
    private TextField tf_pid1;

    @FXML
    private TextField tf_pid2;

    @FXML
    private TextField tf_result;

    @FXML
    private TextField tf_testCode;

    @FXML
    private Tab write_Request;

    ObservableList<LabTestClass> labList = FXCollections.observableArrayList();
    int DoctorID = UserSessionD.getInstance(null).getDoctor().getId();
    String id ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        testCodeCol.setCellValueFactory(new PropertyValueFactory<>("test_code"));
        testNameCol.setCellValueFactory(new PropertyValueFactory<>("t_name"));
        testReqCol.setCellValueFactory(new PropertyValueFactory<>("requirements"));
        testCostCol.setCellValueFactory(new PropertyValueFactory<>("cost"));

        try {
            ResultSet rs = DB_Connection.select("SELECT * FROM Lab_test");

            while (rs.next()) {
                labList.add(new LabTestClass(rs.getInt("Test_code"),
                        rs.getString("T_name"),
                        rs.getString("Requirements"),
                        rs.getDouble("cost")));

            }
            LabRequestTableView.setItems(labList);

        } catch (Exception e) {
            System.out.println(e);
        }

        tf_pid2.textProperty().addListener((observable1, oldID, newID) -> {

            id = tf_pid2.getText();

            try {
                ResultSet rs = DB_Connection.select("SELECT * FROM lab_request where P_ID = '"+id+"'");

                if(rs.next())
                {
                    tf_Date.valueProperty().addListener((observable2, oldDate, newDate) -> {
                        try {
                            ResultSet rs2 = DB_Connection.select("SELECT * FROM lab_request where P_ID = '"+id+"' and LAB_date = '"+tf_Date.getValue()+"'");

                            if(rs2.next())
                            {
                                ObservableList<String> test_name = FXCollections.observableArrayList();

                                ResultSet rs3 = DB_Connection.select("SELECT lab_test.T_name FROM lab_request, lab_test where lab_request.Test_code = lab_test.Test_code and lab_request.P_ID = '"+id+"' and lab_request.LAB_date = '"+tf_Date.getValue()+"'");
                                while (rs3.next()){
                                    test_name.add(rs3.getString("T_name"));
                                }
                                L_testName.setText(String.valueOf(test_name.get(0)));
                                test_name.clear();

                                //force user to pick dates from the current day,"Disable all the dates before the current date"
                                tf_dateOfPayment.setDayCellFactory(picker -> new DateCell() {
                                    public void updateItem(LocalDate date, boolean empty) {
                                        super.updateItem(date, empty);
                                        LocalDate today = tf_Date.getValue();

                                        setDisable(empty || date.compareTo(today) < 0 );
                                    }
                                });
                            }else{

                                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                                alert2.setHeaderText(null);
                                String error = "This patient has not had any lab tests done on date "+tf_Date.getValue();
                                alert2.setContentText(error);
                                alert2.showAndWait();
                                L_testName.setText(null);
                                tf_Date.setValue(null);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    });
                }else if(id.length() == 7 && !rs.next()){

                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setHeaderText(null);
                    alert2.setContentText("Pay Attention! this patient is not in the lab request!");
                    alert2.showAndWait();
                    clear2_buttonClicked();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }

    @FXML
    void Save1_buttonClicked(ActionEvent event) throws SQLException, ClassNotFoundException {

        // To check the entered code and the presence of a prior reservation
        ResultSet rs1 = DB_Connection.select("SELECT P_ID FROM appointment WHERE D_ID = '" + DoctorID + "' AND P_ID = '" + tf_pid1.getText() + "'");
        ResultSet rs2 = DB_Connection.select("SELECT Test_code FROM lab_test WHERE Test_code = '" + tf_testCode.getText() + "'");

        if (tf_pid1.getText().isEmpty() || tf_testCode.getText().isEmpty()) {

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
                tf_pid1.setText(null);

            } else {
                if (!rs2.next()) {

                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setHeaderText(null);
                    alert2.setContentText("Make sure of the test code!");
                    alert2.showAndWait();
                    tf_testCode.setText(null);

                } else {
                    getQuery1();
                    clear1_buttonClicked();
                }
            }

        }
    }


    @FXML
    void Save2_buttonClicked(ActionEvent event) {


        if (tf_pid2.getText().isEmpty() || tf_Date.getValue()  == null || tf_dateOfPayment.getValue()  == null || tf_result.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();

        }
        else {
            try {
                getQuery2();
                clear2_buttonClicked();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    void clear1_buttonClicked() {
        tf_pid1.setText("");
        tf_testCode.setText("");
    }

    @FXML
    void clear2_buttonClicked() {

        tf_dateOfPayment.setValue(null);
        tf_result.setText("");
    }


    public void getQuery1 () throws SQLException, ClassNotFoundException {

        ObservableList<String> test_name = FXCollections.observableArrayList();
        ObservableList<Integer> cost = FXCollections.observableArrayList();

        ResultSet rs = DB_Connection.select("SELECT T_name, cost FROM lab_test where Test_code = '"+tf_testCode.getText()+"'");
        while (rs.next()){
            test_name.add(rs.getString("T_name"));
            cost.add(rs.getInt("cost"));
        }
        System.out.println(test_name);
        System.out.println(cost);
        String Lab_request= "LAb Request: "+test_name.get(0);

        String query1 = "INSERT INTO diagnosis ( P_ID, D_ID, Diagnosis_Time, Diagnosis_date, diagnose, treatment) " +
                "VALUES('"+tf_pid1.getText()+"', '"+DoctorID+"', '"+LocalTime.now()+"', '"+LocalDate.now()+"', '"+Lab_request+"','-')";

        String query2 = "INSERT INTO lab_request (P_ID, Test_code, LAB_date, StartTime, cost) " +
                "VALUES('"+tf_pid1.getText()+"', '"+tf_testCode.getText()+"', '"+LocalDate.now()+"', '"+LocalTime.now()+"' ,'"+cost.get(0)+"')";

        DB_Connection.execute(query1);
        DB_Connection.execute(query2);

        test_name.clear();
        cost.clear();
    }

    public void getQuery2 () throws SQLException, ClassNotFoundException {

        try {
            String query2 = "UPDATE lab_request SET result = '" + tf_result.getText() + "', Date_Of_Payment = '" + tf_dateOfPayment.getValue() + "' WHERE P_ID = '" + tf_pid2.getText() + "' and LAB_date = '" + tf_Date.getValue() + "'";
            DB_Connection.execute(query2);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

}
