/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupsorter;

import java.util.ArrayList;

/**
 *
 * @author niall
 */
public class Group {

    private Student leader;
    private int groupID;
    private ArrayList<Student> studentList;

    /**
     * A group that holds a number of students
     *
     * @param id The unique identifier of the group
     */
    public Group(int id) {
        groupID = id;
        studentList = new ArrayList<>();
    }

    /**
     * Returns the list of students contained in the group
     *
     * @return the list of students contained in the group
     */
    ArrayList<Student> getStudentList() {
        return studentList;
    }

    /**
     * Sets the unique identifier of the group
     *
     * @param id The new unique identifier of the group
     */
    void setGroupID(int id) {
        groupID = id;
    }

    /**
     * Returns the unique identifier of the group
     *
     * @return the unique identifier of the group
     */
    int getGroupID() {
        return groupID;
    }

    /**
     * Returns the amount of students in the group
     *
     * @return the number of students in the group
     */
    int getStudentListSize() {
        return studentList.size();
    }

    /**
     * Adds a given student to the group
     *
     * @param s The student to be added to the group
     */
    void addStudent(Student s) {
        studentList.add(s);
    }

    /**
     * Removes a given student from the group
     *
     * @param s The student to be removed from the group
     */
    void removeStudent(Student s) {
        s.setInGroup(false);
        studentList.remove(s);
    }

    /**
     * Clears all the students from the group
     */
    void clearStudents() {
        for (int i = 0; i < studentList.size(); i++) {
            studentList.get(i).setInGroup(false);
            studentList.get(i).setGroupID(-1);
        }
    }

    /**
     * Returns true if the group needs the role of a given student
     *
     * @param s The student trying to be added to the group
     * @return whether the group needs the role of the given student
     */
    boolean needsStudent(Student s) {
        String[] teamRoles = getTeamRoles();
        for (int i = 0; i < teamRoles.length; i++) {
            if (s.getPrefRole().equals(teamRoles[i])) {
                //System.out.println("Team " + getGroupName() + " does not need " + s.getName());
                return false;
            }
        }
        return true;
    }

    /**
     * Returns an array of the roles occupied by the students in the group
     *
     * @return an array of the group roles
     */
    String[] getTeamRoles() {
        String[] teamRoles = new String[getStudentListSize()];
        for (int i = 0; i < getStudentListSize(); i++) {
            teamRoles[i] = getStudentList().get(i).getPrefRole();
        }
        return teamRoles;
    }

}
