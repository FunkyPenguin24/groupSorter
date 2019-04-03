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

    public Group(int n) {
        groupID = n;
        studentList = new ArrayList<>();
    }

    ArrayList<Student> getStudentList() {
        return studentList;
    }

    void setGroupID(int id) {
        groupID = id;
    }

    int getGroupID() {
        return groupID;
    }

    int getStudentListSize() {
        return studentList.size();
    }

    void addStudent(Student s) {
        studentList.add(s);
    }

    void removeStudent(Student s) {
        s.setInGroup(false);
        studentList.remove(s);
    }

    void setLeader(Student s) {
        leader = s;
    }

    Student getLeader() {
        return leader;
    }

    void clearStudents() {
        for (int i = 0; i < studentList.size(); i++) {
            studentList.get(i).setInGroup(false);
            studentList.get(i).setGroupID(-1);
        }
    }

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

    String[] getTeamRoles() {
        String[] teamRoles = new String[getStudentListSize()];
        for (int i = 0; i < getStudentListSize(); i++) {
            teamRoles[i] = getStudentList().get(i).getPrefRole();
        }
        return teamRoles;
    }

}
