package com.example.finalprojdb;


import java.util.Date;

public class MedicineClass {


        private int M_code;
        private String M_name;
        private Date Expiry_date;
        private double cost;
        private String Effect_side;
        private String Interactions;

        public MedicineClass(int m_code, String m_name, Date expiry_date, double cost, String effect_side, String interactions) {
            M_code = m_code;
            M_name = m_name;
            Expiry_date = expiry_date;
            this.cost = cost;
            Effect_side = effect_side;
            Interactions = interactions;
        }

        public MedicineClass(int m_code, String m_name, double cost, String effect_side, String interactions){

            M_code = m_code;
            M_name = m_name;
            this.cost = cost;
            Effect_side = effect_side;
            Interactions = interactions;

        }

        public int getM_code() {
            return M_code;
        }

        public void setM_code(int m_code) {
            M_code = m_code;
        }

        public String getM_name() {
            return M_name;
        }

        public void setM_name(String m_name) {
            M_name = m_name;
        }

        public Date getExpiry_date() {
            return Expiry_date;
        }

        public void setExpiry_date(Date expiry_date) {
            Expiry_date = expiry_date;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public String getEffect_side() {
            return Effect_side;
        }

        public void setEffect_side(String effect_side) {
            Effect_side = effect_side;
        }

        public String getInteractions() {
            return Interactions;
        }

        public void setInteractions(String interactions) {
            Interactions = interactions;
        }

}
