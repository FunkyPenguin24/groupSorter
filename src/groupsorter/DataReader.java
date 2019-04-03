/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupsorter;

import java.util.ArrayList;
import java.sql.*;

/**
 * Reads student data from a given database and returns it to the system
 *
 * @author niall
 */
public class DataReader {

    private String databasePath = "";

    /**
     * Reads student data from a given database and returns it to the system
     *
     * @param dbPath The path of the database that the object will read
     */
    public DataReader(String dbPath) {
        databasePath = dbPath;
    }

    /**
     * Loads students from the database specified at initialization
     *
     * @return a list of students extracted from a database
     * @throws SQLException
     */
    ArrayList<Student> loadStudents() throws SQLException {
        ArrayList<Student> studentList = new ArrayList<>();
        ResultSet rs = getTable("Students");
        while (rs.next()) {
            Student newStudent = new Student(rs.getInt("studentID"), rs.getString("studentName"), rs.getString("prefRole"), rs.getDouble("attendance"), rs.getInt("groupID")); //creates a new student with the attributes of that in the table
            //System.out.println(newStudent.getStudentID());
            studentList.add(newStudent);
        }
        return studentList;
    }

    /**
     * Returns a table of information taken from a database containing a list of
     * students
     *
     * @param tableName The name of the table to extract the information from
     * @return a table of student information
     * @throws SQLException
     */
    private ResultSet getTable(String tableName) throws SQLException { //the method to return all the data from the table with the name passed to it
        Connection conn = getConnection(); //creates a new connection with the database at the filepath given when the object was instantiated
        String getStatement; //creates a new string to hold the SQL statement that will be executed
        getStatement = "SELECT * FROM " + tableName; //sets the SQL statement to select all the data in the specified table
        Statement stm = conn.createStatement(); //creates a new statement in the connection created above
        return stm.executeQuery(getStatement); //executes the above statement on the database and returns the resulting data
    }

    /**
     * Returns a connection to a database specified at initialization
     *
     * @return a connection to the database
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException { //the method that will return an SQL connection to the database
        return DriverManager.getConnection("jdbc:sqlite:" + databasePath); //returns the connection to the specified database
    }
}
