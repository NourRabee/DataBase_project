package com.example.finalprojdb;

import java.security.PublicKey;
import java.sql.*;
import java.util.Properties;

public class DB_Connection {

    private static String dbUsername = "root"; //database username
    private static String dbPassword = "Hilove177!"; // database password
    private static String URL = "127.0.0.1"; // server location
    private static String port = "3306";// port that mysql uses
    private static String dbName = "clinic";// database on mysql to connect to
    public static Connection con;

    public DB_Connection(){
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        new DBConn(URL, port, dbName, dbUsername, dbPassword);
       // String SQL;
        con.close();
    }

    public static void close() throws ClassNotFoundException, SQLException {
        con.close();
    }

    public static ResultSet select(String SQL) throws SQLException, ClassNotFoundException {
        System.out.println(SQL);
        DBConn a = new DBConn(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();
        Statement stmt = con.createStatement();
        return stmt.executeQuery(SQL);
    }
    public static void execute(String SQL) throws ClassNotFoundException, SQLException {
        System.out.println(SQL);
        DBConn a = new DBConn(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();
        Statement stmt = con.createStatement();
        // ExecuteUpdate :This method is used for execution of DML statement(INSERT, UPDATE and DELETE)
        stmt.executeUpdate(SQL);
        stmt.close();
        con.close();
    }
}
class DBConn {

    private Connection con;
    private String dbURL;
    private String dbUsername;
    private String dbPassword;
    private String URL;
    private String port;
    private String dbName;

    DBConn(String URL, String port, String dbName, String dbUsername, String dbPassword) {
        this.URL = URL;
        this.port = port;
        this.dbName = dbName;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }
    //A Connection is the session between java application and database.
    public Connection connectDB() throws ClassNotFoundException, SQLException {
        dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?verifyServerCertificate=false";
        Properties p = new Properties();
        p.setProperty("user", dbUsername);
        p.setProperty("password", dbPassword);
        p.setProperty("useSSL", "false");
        p.setProperty("autoReconnect", "true");
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(dbURL, p);
        return con;
    }
}
