/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupsorter;

import java.util.ArrayList;

/**
 * A class that holds a list of students that can be sorted into a list of
 * groups
 *
 * @author niall
 */
public class classOfStudents {

    private int numOfGroups;
    private int studentsPerGroup;
    private String lecturerName;
    private ArrayList<Group> groupList;
    private ArrayList<Student> studentList;

    /**
     *
     * @param l The name of the lecturer assigned to the class
     */
    public classOfStudents(String l) {
        lecturerName = l;
        groupList = new ArrayList<>();
        studentList = new ArrayList<>();
    }

    /**
     * Returns the amount of students per group set for a certain class of
     * students
     *
     * @return Students per group
     */
    public int getStudentsPerGroup() {
        return studentsPerGroup;
    }

    /**
     * Sets the amount of students per group to a given values for a certain
     * class of students
     *
     * @param sPg Students per group
     */
    public void setStudentsPerGroup(int sPg) {
        double numberOfStudents = getStudentListSize();
        double studentsPerGroup = sPg;
        this.studentsPerGroup = sPg;
        double numOfGroups = (numberOfStudents / studentsPerGroup);
        setNumOfGroups((int) Math.ceil(numOfGroups));
        System.out.println("STUDENTS PER GROUP " + studentsPerGroup);
        System.out.println("TOTAL STUDENT NUMBER " + getStudentListSize());
        System.out.println("NUMBER OF GROUPS NEEDED " + getNumOfGroups());
    }

    /**
     * Returns the numer of groups in a class of students
     *
     * @return the number of groups in a class of students
     */
    int getNumOfGroups() {
        return numOfGroups;
    }

    /**
     * Sets the number of groups in a class of students to a given value
     *
     * @param n Number of groups
     */
    void setNumOfGroups(int n) {
        //System.out.println("num of groups: " + n);
        numOfGroups = n;
        createGroups(n);
    }

    /**
     * Returns the name of the class' lecturer
     *
     * @return the name of the class' lecturer
     */
    String getLecturerName() {
        return lecturerName;
    }

    /**
     * Sets the name of the lecturer to a given value
     *
     * @param n The name of the new lecturer
     */
    void setLecturerName(String n) {
        lecturerName = n;
    }

    /**
     * Returns the number of students in the class
     *
     * @return the number of students in the class
     */
    int getStudentListSize() {
        return studentList.size();
    }

    /**
     * Returns the number of groups in the class
     *
     * @return the number of groups in the class
     */
    int getGroupListSize() {
        return groupList.size();
    }

    /**
     * Returns the list of students for the class
     *
     * @return the list of students for the class
     */
    ArrayList<Student> getStudentList() {
        return studentList;
    }

    /**
     * Returns the list of groups for the class
     *
     * @return the list of groups for the class
     */
    ArrayList<Group> getGroupList() {
        return groupList;
    }

    /**
     * Adds a given student to the list of students for the class
     *
     * @param s Student to be added to the class
     */
    void addStudent(Student s) {
        studentList.add(s);
        if (s.getGroupID() != -1) { //if the student has a group
            if (getGroupByID(s.getGroupID()) != null) { //if that group is already created

            } else { //if the group doesn't exist
                groupList.add(new Group(s.getGroupID())); //creates the group
                numOfGroups++;
            }
            getGroupByID(s.getGroupID()).addStudent(s); //adds the student to the group
        }
    }

    /**
     * Removes a given student from the class
     *
     * @param s Student to be removed from the class
     */
    void removeStudent(Student s) {
        studentList.remove(s);
    }

    /**
     * Creates a given number of groups
     *
     * @param n Number of groups to be created
     */
    void createGroups(int n) {
        numOfGroups = n;
        clearGroups();
        for (int i = 0; i < n; i++) {
            groupList.add(new Group(i + 1));
        }
    }

    /**
     * Clears all of the groups in the class
     */
    void clearGroups() {
        for (int i = groupList.size() - 1; i >= 0; i--) {
            groupList.get(i).clearStudents();
            groupList.remove(i);
        }
    }

    /**
     * Clears all of the students in the class
     */
    void clearStudents() {
        for (int i = studentList.size() - 1; i >= 0; i--) {
            studentList.remove(i);
        }
    }

    /**
     * Adds a given student to a given group
     *
     * @param s The student to be added to the given group
     * @param g The group that the given student will be added to
     */
    void addStudentToGroup(Student s, Group g) {
        //System.out.println(s.getName() + " was added to group " + g.getGroupName());
        g.addStudent(s);
        s.setInGroup(true);
        s.setGroupID(g.getGroupID());
    }

    /**
     * Removes a given student from a given group
     *
     * @param s The student to be removed from the given group
     * @param g The group that the given student will be removed from
     */
    void removeStudentFromGroup(Student s, Group g) {
        if (g.getStudentList().contains(s)) {
            g.removeStudent(s);
            s.setInGroup(false);
            s.setGroupID(-1);
        }
    }

    /**
     * Sorts the list of students in the class into different groups according
     * to preferred roles and attendance
     */
    void sortStudentsIntoGroups() {
        boolean noGroupNeeds; //if no group needs what role the student has
        double studentNum = getStudentListSize();
        double maxGroupSize = getStudentsPerGroup();
        System.out.println("max group size: " + maxGroupSize);
        System.out.println("number of students: " + studentNum);
        System.out.println("AVERAGE NUMBER OF STUDENTS PER GROUP IS " + maxGroupSize);
        for (int i = 0; i < studentNum; i++) { //loop through each student
            if (studentList.get(i).getGroupID() != -1) {
                if (getGroupByID(studentList.get(i).getGroupID()) == null) {
                    createGroup(studentList.get(i).getGroupID());
                }
                addStudentToGroup(studentList.get(i), getGroupByID(studentList.get(i).getGroupID()));
            } else {
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
    }

    /**
     * Creates a group with a given id
     *
     * @param id The unique identifier of the group
     */
    private void createGroup(int id) {
        Group newGroup = new Group(id);
        groupList.add(newGroup);
    }

    /**
     * Returns the group that is associated with a given identifier
     *
     * @return The group that is associated with a given identifier
     * @param id The unique identifier of the group
     */
    private Group getGroupByID(int id) {
        Group g = null;
        for (int i = 0; i < groupList.size(); i++) {
            if (groupList.get(i).getGroupID() == id) {
                return groupList.get(i);
            }
        }
        return g;
    }

    /**
     * Returns true if the given student can be placed into a given group
     *
     * @return if the given student can be placed into a given group
     * @param s The student being added to a group
     * @param g The group the student is being added to
     * @param maxGroupSize The maximum number of students per group
     * @param noGroupNeeds Whether or no other group needs this student and
     * their role
     */
    boolean canStudentGoToGroup(Student s, Group g, double maxGroupSize, boolean noGroupNeeds) {
        if (noGroupNeeds) //if no group needs the student
        {
            return (!s.isInGroup() && g.getStudentListSize() < maxGroupSize); //returns true is the student is not in a group and the number of students in the group isn't the max number
        } else {
            return (g.needsStudent(s) && !s.isInGroup() && g.getStudentListSize() < maxGroupSize);
        }
    }

    /**
     * Returns an array containing the names of the students in the class
     *
     * @return an array of all the student names in the class
     */
    String[] getNamesArray() {
        String[] studentNames = new String[getStudentList().size()];
        for (int i = 0; i < getStudentList().size(); i++) {
            studentNames[i] = getStudentList().get(i).getName();
        }
        return studentNames;
    }

    /**
     * Returns an array containing the information of the students in the class
     *
     * @return an array of all the student information in the class
     */
    String[] getNamesForList() {
        int studentsWithoutGroups = 0;
        for (int i = 0; i < getStudentList().size(); i++) {
            if (!getStudentList().get(i).isInGroup()) {
                studentsWithoutGroups++;
            }
        }
        String[] studentNames = new String[studentsWithoutGroups];
        int j = 0;
        for (int i = 0; i < getStudentList().size(); i++) {
            Student s = getStudentList().get(i);
            if (!s.isInGroup()) {
                studentNames[j] = s.getStudentID() + ", " + s.getName() + ", " + s.getPrefRole() + ", " + s.getAttendance();
                j++;
            }
        }
        return studentNames;
    }

    /**
     * Returns a student that corresponds to a given identifier
     *
     * @return a student that matches a given identifier
     */
    Student getStudentByID(int id) {
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getStudentID() == id) {
                return studentList.get(i);
            }
        }
        return null;
    }

    /**
     * Sorts the list of students into descending order by attendance, so that
     * the highest attending students are at the front of the list
     */
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
        for (int i = 0; i < studentList.size(); i++) {
            System.out.println(studentList.get(i).getAttendance());
        }
    }
}
