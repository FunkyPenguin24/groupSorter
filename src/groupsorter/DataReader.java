/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupsorter;

import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author niall
 */
public class DataReader {

    private String databasePath = "";

    public DataReader(String dbPath) {
        databasePath = dbPath;
    }

    ArrayList<Student> loadStudents() throws SQLException {
        ArrayList<Student> studentList = new ArrayList<>();
        ResultSet rs = getTable("Students");
        while (rs.next()) {
            Student newStudent = new Student(rs.getInt("studentID"), rs.getString("studentName"), rs.getString("prefRole"), rs.getDouble("attendance")); //creates a new student with the attributes of that in the table
            studentList.add(newStudent);
        }
        return studentList;
    }

    private ResultSet getTable(String tableName) throws SQLException { //the method to return all the data from the table with the name passed to it
        Connection conn = getConnection(); //creates a new connection with the database at the filepath given when the object was instantiated
        String getStatement; //creates a new string to hold the SQL statement that will be executed
        getStatement = "SELECT * FROM " + tableName; //sets the SQL statement to select all the data in the specified table
        Statement stm = conn.createStatement(); //creates a new statement in the connection created above
        return stm.executeQuery(getStatement); //executes the above statement on the database and returns the resulting data
    }

    private Connection getConnection() throws SQLException { //the method that will return an SQL connection to the database
        return DriverManager.getConnection("jdbc:sqlite:" + databasePath); //returns the connection to the specified database
    }
}
