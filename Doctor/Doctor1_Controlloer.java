package com.example.finalprojdb;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;



public class Doctor1_Controller implements Initializable {

    @FXML
    public Label message;

    @FXML
    private Label L_name;

    @FXML
    private Label L_Specialization;

    @FXML
    private TextField tf_address;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_phone;

    int ID;

    public void setProfile(DoctorClass doctor) {

        UserSessionD.getInstance(doctor);
        L_name.setText(doctor.getName());
        tf_address.setText(doctor.getAddress());
        tf_email.setText(doctor.getEmail());
        tf_phone.setText(String.valueOf(doctor.getPhone()));
        L_Specialization.setText(doctor.getSpecialization());

    }

    @FXML
    void Update_ButtonClicked(ActionEvent event)  {
        try {

            String newAddress = tf_address.getText();
            String newEmail = tf_email.getText();
            int newPhone = Integer.parseInt(tf_phone.getText());

            ResultSet currentAddress = DB_Connection.select("SELECT address FROM doctor WHERE address = '" + newAddress +"' and D_ID = "+ ID );
            ResultSet currentEmail = DB_Connection.select("SELECT email FROM doctor WHERE email = '" + newEmail +"' and D_ID = "+ ID);
            ResultSet currentPhone = DB_Connection.select("SELECT phone FROM doctor WHERE phone = '" + newPhone +"' and D_ID = "+ ID );

            if(!currentAddress.next() || !currentEmail.next() || !currentPhone.next()){

                DoctorClass doctor= UserSessionD.getInstance(null).getDoctor();
                String sql = ("update doctor set address= '" + newAddress + "', email= '" + newEmail + "', phone= '" + newPhone + "' where D_ID="+doctor.getId());
                System.out.println("Before update");
                System.out.println(sql);
                DB_Connection.execute(sql);


                System.out.println("Update executed");
                doctor.setAddress(tf_address.getText());
                doctor.setEmail(tf_email.getText());
                doctor.setPhone(Integer.parseInt(tf_phone.getText()));
                setProfile(doctor);
                message.setText("Profile Updated");
                message.setStyle("-fx-text-fill: GREEN");


            }else{

                message.setText("Error updating profile!! Some fields you trying to update still has the same value!");
                message.setStyle("-fx-text-fill: RED");

            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void signout_ButtonClicked(ActionEvent actionEvent) throws IOException {

        Main m = new Main();
        m.changeScene("LoginDoctor.fxml");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        // if doctor logged in display profile data
        if (UserSessionD.getInstance(null).getDoctor()!=null)
            setProfile(UserSessionD.getInstance(null).getDoctor());
    }

}
