create database clinic;
use clinic;

#drop database clinic;

create table Patients_history(
                                 P_ID int,
                                 blood_type varchar(4),
                                 allergy varchar(50),
                                 primary key(P_ID)
);


create table Patient(
                        P_ID int,
                        P_password int,
                        P_name varchar(25),
                        age int,
                        address varchar(100),
                        email varchar(75),
                        birth date,
                        phone int,
                        foreign key (P_ID) references Patients_history(P_ID),
                        primary key(P_ID)
);

create table Doctor(
                       D_ID int,
                       D_password int,
                       D_name varchar(25),
                       specialization varchar(150),
                       address varchar(100),
                       email varchar(75),
                       phone int,
                       primary key(D_ID)
);

create table Appointment(
                            P_ID int,
                            D_ID int,
                            A_date date,
                            StartTime time,
                            EndTime time,
                            foreign key (P_ID) references Patient(P_ID),
                            foreign key (D_ID) references Doctor(D_ID),
                            primary key(P_ID, D_ID, A_date, StartTime)
);
create table Diagnosis(
                          P_ID int,
                          D_ID int,
                          Diagnosis_Time time,
                          Diagnosis_date date,
                          diagnose varchar(30),
                          treatment varchar(30),
                          Complaints varchar(30),
                          foreign key (P_ID) references Patient(P_ID),
                          foreign key (D_ID) references Doctor(D_ID),
                          primary key(P_ID, D_ID, Diagnosis_date, Diagnosis_Time)
);

create table LAB_Test(
                         Test_code int,
                         T_name varchar(20),
                         Requirements varchar(50),
                         cost real,
                         primary key(Test_code)
);
create table LAB_Request(
                            P_ID int,
                            Test_code int,
                            LAB_date date,
                            StartTime time,
                            cost real,
                            Date_Of_Payment date,
                            result varchar(100),
                            foreign key (P_ID) references Diagnosis(P_ID),
                            foreign key (Test_code) references LAB_Test(Test_code),
                            primary key(P_ID, StartTime, LAB_date)
);
create table Transfer(
                         P_ID int,
                         PresentTime time,
                         Presentdate date,
                         trans_date date,
                         trans_time time,
                         hospital varchar(30),
                         examination_type varchar(40),
                         foreign key (P_ID) references Diagnosis(P_ID),
                         primary key(P_ID, trans_date, trans_time)
);

# week entity
create table Medicine(
                         M_code int,
                         M_name varchar(20),
                         Expiry_date date,
                         cost real,
                         Effect_side varchar(70),
                         Interactions varchar(70),
                         primary key (M_code)
);
create table Prescription(
                             P_ID int not null ,
                             Med_code int,
                             Dosage varchar(20),
                             Prescription_date date not null ,
                             Prescription_Time time,
    # default 0 , default 1 when deleted record
                             deleted boolean default 0,
                             foreign key (Med_code) references Medicine(M_code),
                             foreign key (P_ID) references Diagnosis(P_ID),
                             primary key(P_ID, Med_code, Prescription_date, Prescription_Time)
);
