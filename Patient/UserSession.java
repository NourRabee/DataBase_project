package com.example.finalprojdb;

public class UserSessionP {

    private static UserSessionP instance;

    private PatientClass patient;

    private UserSessionP(PatientClass patient) {
        this.patient = patient;
    }

    public static UserSessionP getInstance(PatientClass patient) {
        if (instance == null || instance.getPatient()==null) {

            instance = new UserSessionP(patient);
        }
        return instance;
    }

    public PatientClass getPatient() {
        return patient;
    }

    public void cleanUserSession() {
        patient = null;
    }
}
