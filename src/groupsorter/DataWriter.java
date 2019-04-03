/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupsorter;

import java.util.ArrayList;
import java.sql.*;

/**
 * Writes student data to a given database
 *
 * @author niall
 */
public class DataWriter {

    private String databasePath = "";

    /**
     * Writes student data to a given database
     *
     * @param dbPath The path of the database the object will write to
     */
    public DataWriter(String dbPath) {
        databasePath = dbPath;
    }

    /**
     * Saves a given list of students to the database specified upon
     * initialization
     *
     * @param studentList The list of students to be saved
     * @throws SQLException
     */
    void saveStudents(ArrayList<Student> studentList) throws SQLException {
        clearTable("Students");
        Connection conn = getConnection();
        String setStatement, studentName, prefRole;
        double attendance;
        int studentID, groupID;
        for (int i = 0; i < studentList.size(); i++) {
            Student currentStudent = studentList.get(i);
            studentName = currentStudent.getName();
            prefRole = currentStudent.getPrefRole();
            attendance = currentStudent.getAttendance();
            studentID = currentStudent.getStudentID();
            groupID = currentStudent.getGroupID();
            setStatement = "INSERT INTO Students(studentID, studentName, prefRole, attendance, groupID) VALUES ('"
                    + studentID + "', '"
                    + studentName + "', '"
                    + prefRole + "', '"
                    + attendance + "', '"
                    + groupID + "')";
            Statement stm = conn.createStatement();
            stm.execute(setStatement);
        }
    }

    /**
     * Clears a table in the database with a given name - used when overwriting
     * class data
     *
     * @param tableName The name of the table to be cleared
     * @throws SQLException
     */
    private void clearTable(String tableName) throws SQLException {
        Connection conn = getConnection();
        String setStatement = "DELETE FROM " + tableName;
        Statement stm = conn.createStatement();
        stm.execute(setStatement);
    }

    /**
     * Returns a connection with the given database
     *
     * @return a connection with the given database
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException { //gets and returns a connection to the database at the file path specified when the object was instantiated
        return DriverManager.getConnection("jdbc:sqlite:" + databasePath); //returns the connection to the specified database
    }

}
