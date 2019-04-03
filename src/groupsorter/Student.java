/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupsorter;

/**
 *
 * @author niall
 */
public class Student {

    private int studentID;
    private String name;
    private String preferredRole;
    private double attendance;
    private boolean inGroup;
    private int groupID;

    public Student(int id, String n, String role, double att, int gID) {
        studentID = id;
        name = n;
        preferredRole = role;
        attendance = att;
        inGroup = false;
        groupID = gID;
    }

    public void setStudentID(int id) {
        studentID = id;
    }

    public int getStudentID() {
        return studentID;
    }

    void setGroupID(int id) {
        if (id != -1)
            groupID = id;
    }

    int getGroupID() {
        return groupID;
    }

    public boolean isInGroup() {
        return inGroup;
    }

    public void setInGroup(boolean inGroup) {
        this.inGroup = inGroup;
    }

    double getAttendance() {
        return attendance;
    }

    void setAttendance(double att) {
        this.attendance = att;
    }

    String getName() {
        return name;
    }

    void setName(String n) {
        name = n;
    }

    String getPrefRole() {
        return preferredRole;
    }

    void setPrefRole(String pr) {
        preferredRole = pr;
    }

}
