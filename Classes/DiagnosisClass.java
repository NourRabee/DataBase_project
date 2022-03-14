package com.example.finalprojdb;


import java.sql.Time;
import java.util.Date;

public class DiagnosisClass {

    private PatientClass patient;
    private DoctorClass doctor;
    private Time Diagnosis_Time;
    private Date Diagnosis_date;
    private String diagnose;
    private String treatment;
    private String Complaints;

    public DiagnosisClass(PatientClass patient, DoctorClass doctor, Time diagnosis_Time, Date diagnosis_date, String diagnose, String treatment, String complaints) {
        this.patient = patient;
        this.doctor = doctor;
        Diagnosis_Time = diagnosis_Time;
        Diagnosis_date = diagnosis_date;
        this.diagnose = diagnose;
        this.treatment = treatment;
        Complaints = complaints;
    }

    public PatientClass getPatient() {
        return patient;
    }

    public void setPatient(PatientClass patient) {
        this.patient = patient;
    }

    public int getPatientId() {
        return patient.getId();
    }

    public String getPatientName() {
        return patient.getPname();
    }

    public DoctorClass getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorClass doctor) {
        this.doctor = doctor;
    }

    public int getDoctortId() {
        return doctor.getId();
    }

    public String getDoctorName() {
        return doctor.getName();
    }

    public String getDoctorSpecialization() {
        return doctor.getSpecialization();
    }

    public Time getDiagnosis_Time() {
        return Diagnosis_Time;
    }

    public void setDiagnosis_Time(Time diagnosis_Time) {
        Diagnosis_Time = diagnosis_Time;
    }

    public Date getDiagnosis_date() {
        return Diagnosis_date;
    }

    public void setDiagnosis_date(Date diagnosis_date) {
        Diagnosis_date = diagnosis_date;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getComplaints() {
        return Complaints;
    }

    public void setComplaints(String complaints) {
        Complaints = complaints;
    }
}
