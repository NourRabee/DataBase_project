package com.example.finalprojdb;

import java.sql.Time;
import java.util.Date;

public class LabRequestClass {

    private PatientClass patient;
    private LabTestClass lab_test;
    private Date LAB_date;
    private Time StartTime;
    private double cost;
    private Date Date_Of_Payment;
    private String result;

    public LabRequestClass(PatientClass patient, LabTestClass lab_test, Date LAB_date, Time startTime, double cost, Date date_Of_Payment, String result) {
        this.patient = patient;
        this.lab_test = lab_test;
        this.LAB_date = LAB_date;
        StartTime = startTime;
        this.cost = cost;
        Date_Of_Payment = date_Of_Payment;
        this.result = result;
    }

    public PatientClass getPatient() {return patient;}

    public void setPatient(PatientClass patient) {this.patient = patient;}

    public String getPatientName() {return patient.getPname();}

    public int getPatientID() {return patient.getId();}

    public LabTestClass getLab_test() {return lab_test;}

    public void setLab_test(LabTestClass lab_test) {this.lab_test = lab_test;}

    public String getTestName() {
        return lab_test.getT_name();
    }

    public double getTestcost() {return lab_test.getCost();}

    public String getTestRequirements() {
        return lab_test.getRequirements();
    }

    public Date getLAB_date() {
        return LAB_date;
    }

    public void setLAB_date(Date LAB_date) {
        this.LAB_date = LAB_date;
    }

    public Time getStartTime() {
        return StartTime;
    }

    public void setStartTime(Time startTime) {
        StartTime = startTime;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Date getDate_Of_Payment() {
        return Date_Of_Payment;
    }

    public void setDate_Of_Payment(Date date_Of_Payment) {
        Date_Of_Payment = date_Of_Payment;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
