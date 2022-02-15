package com.example.finalprojdb;

public class DoctorClass {


    private int id;
    private int password;
    private String name;
    private String specialization;
    private String address;
    private String email;
    private int phone;


    public DoctorClass(int id, int password, String name, String specialization, String address, String email, int phone) {

        this.id = id;
        this.password = password;
        this.name = name;
        this.specialization = specialization;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public DoctorClass(int d_id, String d_name, String specialization) {
        this.id = d_id;
        this.name = d_name;
        this.specialization = specialization;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

}
