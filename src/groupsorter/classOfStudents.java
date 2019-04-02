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
        studentsPerGroup = sPg;
        setNumOfGroups((int) Math.ceil(getStudentListSize()/sPg));
        System.out.println("STUDENTS PER GROUP " + studentsPerGroup);
        System.out.println("TOTAL STUDENT NUMBER " + getStudentListSize());
        System.out.println("NUMBER OF GROUPS NEEDED " + getNumOfGroups());
    }
    
    int getNumOfGroups() {
        return numOfGroups;
    }

    void setNumOfGroups(int n) {
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
            groupList.add(new Group(i + ""));
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
    }

    void removeStudentFromGroup(Student s, Group g) {
        if (g.getStudentList().contains(s)) {
            g.removeStudent(s);
            s.setInGroup(false);
        }
    }

    void sortStudentsIntoGroups() {
        boolean noGroupNeeds; //if no group needs what role the student has
        double studentNum = getStudentListSize();
        double maxGroupSize = getStudentsPerGroup();
        System.out.println("AVERAGE NUMBER OF STUDENTS PER GROUP IS " + maxGroupSize);
        for (int i = 0; i < studentNum; i++) { //loop through each student
            noGroupNeeds = false;
            for (int j = 0; j < numOfGroups; j++) { //loop through each group
                if (canStudentGoToGroup(studentList.get(i), groupList.get(j), maxGroupSize, noGroupNeeds)) {
                    addStudentToGroup(studentList.get(i), groupList.get(j));
                } else {
                    //System.out.println(studentList.get(i).getName() + " can't join team " + groupList.get(j).getGroupName());
                }
                if (j == numOfGroups - 1 && !studentList.get(i).isInGroup()) {
                    noGroupNeeds = true;
                    j = -1; //sets j to -1 because for loops increment at the end of the loop, meaning j will be reset to its initial 0 when the loop starts again
                }
            }
        }
    }
        
    boolean canStudentGoToGroup(Student s, Group g, double maxGroupSize, boolean noGroupNeeds) {
        if (noGroupNeeds)
            return (!s.isInGroup() && g.getStudentListSize() < maxGroupSize);
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
        for (int i = 0; i < getStudentList().size(); i++)
            if (!getStudentList().get(i).isInGroup()){
                studentNames[j] = getStudentList().get(i).getName() + ", " + getStudentList().get(i).getPrefRole() + ", " + getStudentList().get(i).getAttendance();
                j++;
            }
        return studentNames;
    }
        
    void printGroupInfo() {
        for (int i = 0; i < getGroupListSize(); i++) {
            Group group = getGroupList().get(i);
            System.out.println("Group " + group.getGroupName());
            for (int j = 0; j < group.getStudentListSize(); j++) {
                Student s = group.getStudentList().get(j);
                System.out.println(s.getName() + ": " + s.getPrefRole() + ", " + s.getAttendance());
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
