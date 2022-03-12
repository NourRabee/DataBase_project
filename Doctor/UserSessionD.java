package com.example.finalprojdb;

public class UserSessionD {


    private static UserSessionD instance;

    private DoctorClass doctor;

    private UserSessionD(DoctorClass doctor) {
        this.doctor = doctor;
    }

    public static UserSessionD getInstance(DoctorClass doctor) {
        if (instance == null || instance.getDoctor()==null) {
            instance = new UserSessionD(doctor);
        }
        return instance;
    }

    public DoctorClass getDoctor() {
        return doctor;
    }

    public void cleanUserSession() {
        doctor = null;
    }
}
