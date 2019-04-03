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
public class DataWriter {

    private String databasePath = "";

    public DataWriter(String dbPath) {
        databasePath = dbPath;
    }

    void saveStudents(ArrayList<Student> studentList) throws SQLException {
        Connection conn = getConnection();
        String setStatement, studentName, prefRole;
        double attendance;
        int studentID;
        for (int i = 0; i < studentList.size(); i++) {
            Student currentStudent = studentList.get(i);
            studentName = currentStudent.getName();
            prefRole = currentStudent.getPrefRole();
            attendance = currentStudent.getAttendance();
            studentID = currentStudent.getStudentID();
            if (!studentExists(studentID, conn)) {
                setStatement = "INSERT INTO Students(studentID, studentName, prefRole, attendance) VALUES ('"
                        + studentID + "', '"
                        + studentName + "', '"
                        + prefRole + "', '"
                        + attendance + "')";
            } else {
                setStatement = "UPDATE Students SET "
                        + "studentName = '" + studentName + "', "
                        + "prefRole = '" + prefRole + "', "
                        + "attendance = '" + attendance + "' WHERE "
                        + "studentID = '" + studentID + "';";
            }
            Statement stm = conn.createStatement();
            stm.execute(setStatement);
        }
    }
    
    private boolean studentExists(int studentID, Connection conn) throws SQLException { //the method that checks if a team exists or not
        String setStatement = "SELECT * FROM Students WHERE studentID = '" + studentID + "'"; //creates the SQL statement to get the data of the team that has the same name as the given name
        Statement stm = conn.createStatement(); //creates the statement with the given connection
        ResultSet rs = stm.executeQuery(setStatement); //executes the statement above and returns the resulting set of data        
        if (rs.isClosed()) { //if the results set is closed already, there is no data in it so the team with the given name must not exist
            return false; //return that the team does not exist
        } else {
            rs.close(); //close the results set
            return true; //return that the team does exist
        }
    }

    private Connection getConnection() throws SQLException { //gets and returns a connection to the database at the file path specified when the object was instantiated
        return DriverManager.getConnection("jdbc:sqlite:" + databasePath); //returns the connection to the specified database
    }
        
}
