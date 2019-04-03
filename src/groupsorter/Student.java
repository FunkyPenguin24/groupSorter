/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupsorter;

/**
 * Represents a student that is stored in a class and sorted into groups
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

    /**
     * Represents a student that is stored in a class and sorted into groups
     *
     * @param id The unique identifier of the student
     * @param n The name of the student
     * @param role The preferred role of the student
     * @param att The student's current attendance
     * @param gID The unique identifier of the group the student is part of (-1
     * if no group)
     */
    public Student(int id, String n, String role, double att, int gID) {
        studentID = id;
        name = n;
        preferredRole = role;
        attendance = att;
        inGroup = false;
        groupID = gID;
    }

    /**
     * Sets the student's unique identifier
     *
     * @param id The student's new unique identifier
     */
    public void setStudentID(int id) {
        studentID = id;
    }

    /**
     * Returns the student's unique identifier
     *
     * @return the student's unique identifier
     */
    public int getStudentID() {
        return studentID;
    }

    /**
     * Sets the unique identifier of the group the student belongs to (-1 if no
     * group)
     *
     * @param id The unique identifier of the new group the student belongs to
     * (-1 is no group)
     */
    void setGroupID(int id) {
        if (id != -1) {
            groupID = id;
        }
    }

    /**
     * Gets the unique identifier of the group student belongs to (-1 if no
     * group)
     *
     * @return the unique identifier of the group student belongs to (-1 if no
     * group)
     */
    int getGroupID() {
        return groupID;
    }

    /**
     * Returns whether or not the student is in a group
     *
     * @return whether or not the student is in a group
     */
    public boolean isInGroup() {
        return inGroup;
    }

    /**
     * Sets whether or not the student is in a group
     *
     * @param inGroup If the student is in a group or not
     */
    public void setInGroup(boolean inGroup) {
        this.inGroup = inGroup;
    }

    /**
     * Returns the student's attendance
     *
     * @return the student's attendance
     */
    double getAttendance() {
        return attendance;
    }

    /**
     * Sets the student's new attendance
     *
     * @param att the student's new attendance
     */
    void setAttendance(double att) {
        this.attendance = att;
    }

    /**
     * Returns the student's name
     *
     * @return the student's name
     */
    String getName() {
        return name;
    }

    /**
     * Sets the student's new name
     *
     * @param n The student's new name
     */
    void setName(String n) {
        name = n;
    }

    /**
     * Returns the student's preferred role
     *
     * @return the student's preferred role
     */
    String getPrefRole() {
        return preferredRole;
    }

    /**
     * Sets the student's preferred role
     *
     * @param pr the student's preferred role
     */
    void setPrefRole(String pr) {
        preferredRole = pr;
    }

}
