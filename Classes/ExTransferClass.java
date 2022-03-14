package com.example.finalprojdb;

import java.sql.Time;
import java.util.Date;

public class ExTransferClass {

        private PatientClass patient;
        private Time PresentTime;
        private Date Presentdate;
        private Date trans_date;
        private Time trans_time;
        private String hospital;
        private String examination_type;

        public ExTransferClass(PatientClass patient, Time presentTime, Date presentdate, Date trans_date, Time trans_time, String hospital, String examination_type) {
            this.patient = patient;
            PresentTime = presentTime;
            Presentdate = presentdate;
            this.trans_date = trans_date;
            this.trans_time = trans_time;
            this.hospital = hospital;
            this.examination_type = examination_type;
        }

        public PatientClass getPatient() {
            return patient;
        }

        public void setPatient(PatientClass patient) {
            this.patient = patient;
        }

        public Time getPresentTime() {
            return PresentTime;
        }

        public void setPresentTime(Time presentTime) {
            PresentTime = presentTime;
        }

        public Date getPresentdate() {
            return Presentdate;
        }

        public void setPresentdate(Date presentdate) {
            Presentdate = presentdate;
        }

        public Date getTrans_date() {
            return trans_date;
        }

        public void setTrans_date(Date trans_date) {
            this.trans_date = trans_date;
        }

        public Time getTrans_time() {
            return trans_time;
        }

        public void setTrans_time(Time trans_time) {
            this.trans_time = trans_time;
        }

        public String getHospital() {
            return hospital;
        }

        public void setHospital(String hospital) {
            this.hospital = hospital;
        }

        public String getExamination_type() {
            return examination_type;
        }

        public void setExamination_type(String examination_type) {
            this.examination_type = examination_type;
        }
    }

