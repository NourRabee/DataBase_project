package com.example.finalprojdb;

public class LabTestClass {

        private int Test_code;
        private String T_name;
        private String Requirements;
        private double cost;

        public LabTestClass(int test_code, String t_name, String requirements, double cost) {
            this.Test_code = test_code;
            this.T_name = t_name;
            this.Requirements = requirements;
            this.cost = cost;
        }

        public int getTest_code() {
            return Test_code;
        }

        public void setTest_code(int test_code) {
            Test_code = test_code;
        }

        public String getT_name() {
            return T_name;
        }

        public void setT_name(String t_name) {
            T_name = t_name;
        }

        public String getRequirements() {
            return Requirements;
        }

        public void setRequirements(String requirements) {
            Requirements = requirements;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }
    }
