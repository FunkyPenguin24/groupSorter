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
public class classOfStudents {
    
    private int numOfGroups;
    private int studentsPerGroup;
    private String lecturerName;
    private ArrayList<Group> groupList;
    private ArrayList<Student> studentList;

    public classOfStudents(String l) {
        lecturerName = l;
        groupList = new ArrayList<>();
        studentList = new ArrayList<>();
    }

    public int getStudentsPerGroup() {
        return studentsPerGroup;
    }

    public void setStudentsPerGroup(int sPg) {
        double numberOfStudents = getStudentListSize();
        double studentsPerGroup = sPg;
        this.studentsPerGroup = sPg;
        double numOfGroups = (numberOfStudents/studentsPerGroup);
        setNumOfGroups((int) Math.ceil(numOfGroups));
        System.out.println("STUDENTS PER GROUP " + studentsPerGroup);
        System.out.println("TOTAL STUDENT NUMBER " + getStudentListSize());
        System.out.println("NUMBER OF GROUPS NEEDED " + getNumOfGroups());
    }
    
    int getNumOfGroups() {
        return numOfGroups;
    }

    void setNumOfGroups(int n) {
        //System.out.println("num of groups: " + n);
        numOfGroups = n;
        createGroups(n);
    }
        
    String getLecturerName() {
        return lecturerName;
    }
    
    void setLecturerName(String n) {
        lecturerName = n;
    }

    int getStudentListSize() {
        return studentList.size();
    }
    
    int getGroupListSize() {
        return groupList.size();
    }
    
    ArrayList<Student> getStudentList() {
        return studentList;
    }

    ArrayList<Group> getGroupList() {
        return groupList;
    }

    void addStudent(Student s) {
        studentList.add(s);
    }

    void removeStudent(Student s) {
        studentList.remove(s);
    }

    void createGroups(int n) {
        numOfGroups = n;
        clearGroups();
        for (int i = 0; i < n; i++)
            groupList.add(new Group(i + 1));
    }
    
    void clearGroups() {
        for (int i = 0; i < groupList.size(); i++) {
            groupList.get(i).clearStudents();
            groupList.remove(i);
        }
    }
    
    void addStudentToGroup(Student s, Group g) {
        //System.out.println(s.getName() + " was added to group " + g.getGroupName());
        g.addStudent(s);
        s.setInGroup(true);
        s.setGroupID(g.getGroupID());
    }

    void removeStudentFromGroup(Student s, Group g) {
        if (g.getStudentList().contains(s)) {
            g.removeStudent(s);
            s.setInGroup(false);
            s.setGroupID(-1);
        }
    }

    void sortStudentsIntoGroups() {
        boolean noGroupNeeds; //if no group needs what role the student has
        double studentNum = getStudentListSize();
        double maxGroupSize = getStudentsPerGroup();
        System.out.println("max group size: " + maxGroupSize);
        System.out.println("number of students: " + studentNum);
        System.out.println("AVERAGE NUMBER OF STUDENTS PER GROUP IS " + maxGroupSize);
        for (int i = 0; i < studentNum; i++) { //loop through each student
            noGroupNeeds = false;
            for (int j = 0; j < numOfGroups; j++) { //loop through each group
                if (canStudentGoToGroup(studentList.get(i), groupList.get(j), maxGroupSize, noGroupNeeds)) {
                    addStudentToGroup(studentList.get(i), groupList.get(j));
                } else {
                    System.out.println(studentList.get(i).getName() + " can't join team " + groupList.get(j).getGroupID());
                }
                if (j == numOfGroups - 1 && !studentList.get(i).isInGroup()) {
                    noGroupNeeds = true;
                    j = -1; //sets j to -1 because for loops increment at the end of the loop, meaning j will be reset to its initial 0 when the loop starts again
                }
            }
        }
    }
        
    boolean canStudentGoToGroup(Student s, Group g, double maxGroupSize, boolean noGroupNeeds) {
        if (noGroupNeeds) //if no group needs the student
            return (!s.isInGroup() && g.getStudentListSize() < maxGroupSize); //returns true is the student is not in a group and the number of students in the group isn't the max number
        else
            return (g.needsStudent(s) && !s.isInGroup() && g.getStudentListSize() < maxGroupSize);            
    }
    
    String[] getNamesArray() {
        String[] studentNames = new String[getStudentList().size()];
        for (int i = 0; i < getStudentList().size(); i++) {
            studentNames[i] = getStudentList().get(i).getName();
        }
        return studentNames;
    }
    
    String[] getNamesForList() {
        int studentsWithoutGroups = 0;
        for (int i = 0; i < getStudentList().size(); i++)
            if (!getStudentList().get(i).isInGroup())
                studentsWithoutGroups++;
        String[] studentNames = new String[studentsWithoutGroups];
        int j = 0;
        for (int i = 0; i < getStudentList().size(); i++) {
            Student s = getStudentList().get(i);
            if (!s.isInGroup()){
                studentNames[j] = s.getStudentID() + ", " + s.getName() + ", " + s.getPrefRole() + ", " + s.getAttendance();
                j++;
            }
        }
        return studentNames;
    }
    
    Student getStudentByID(int id) {
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getStudentID() == id)
                return studentList.get(i);
        }
        return null;
    }
    
    void printGroupInfo() {
        for (int i = 0; i < getGroupListSize(); i++) {
            Group group = getGroupList().get(i);
            System.out.println("Group " + group.getGroupID());
            for (int j = 0; j < group.getStudentListSize(); j++) {
                Student s = group.getStudentList().get(j);
                System.out.println(s.getStudentID() + " " + s.getName() + ": " + s.getPrefRole() + ", " + s.getAttendance());
            }
            System.out.println();
        }
    }
    
    void sortStudentsByAttendance() {
        boolean swap; //creates the swap variable
        do {
            swap = false; //sets the swap variable to false at the start of each loop
            for (int i = 0; i < studentList.size(); i++) { //loops through the list of students
                if (i != studentList.size() - 1) { //if the selected student isn't at the end of the list (no one to switch with)
                    if (studentList.get(i).getAttendance() < studentList.get(i + 1).getAttendance()) { //if the next student in the list has a lower attendance than the current student
                        Student tempStudent = studentList.get(i + 1); //switch the students and set the swap variable to true, looping the program again
                        studentList.set(i + 1, studentList.get(i)); //this makes sure all the students with higher attendances are at the top of the list
                        studentList.set(i, tempStudent);
                        swap = true;
                    }
                }
            }
        } while (swap); //loops until there are no swaps in a loop
        for (int i = 0; i < studentList.size(); i++)
            System.out.println(studentList.get(i).getAttendance());
    }
    
}
