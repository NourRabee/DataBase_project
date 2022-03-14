package com.example.finalprojdb;

import java.time.LocalDate;


public class PatientClass {

    private int id;
    private String pname;
    private int age;
    private String email;
    private int phone;
    private LocalDate birth;
    private String address;
    private String bloodType;
    private String allergy;



    public PatientClass(int id, String pname, String address, String email, LocalDate birth, int phone,String bloodType,String allergy) {
        this.id = id;
        this.pname = pname;
        this.age = LocalDate.now().getYear() - birth.getYear();
        this.email = email;
        this.phone = phone;
        this.birth = birth;
        this.address = address;
        this.bloodType=bloodType==null?"":bloodType;
        this.allergy=allergy==null?"":allergy;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
