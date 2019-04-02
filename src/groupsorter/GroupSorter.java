/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupsorter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import java.sql.*;

/**
 *
 * @author niall
 */
public class GroupSorter {

    classOfStudents cl;
    JList groupNumberList;
    JList groupMemberList;
    JList studentList;
    int listHeight = 45;
    int labelHeight = 30;
    JSpinner groupNumSpinner;
    ArrayList<String> studentNames;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GroupSorter gs = new GroupSorter();
        gs.startProgram();
    }

    void startProgram() {
        studentNames = new ArrayList<>();
        createClass();
        createWindow();
    }

    // <editor-fold defaultstate="collapsed" desc="Window code">
    void createWindow() {
        window programWindow = new window();
        programWindow.setSize(650, 750);

        initLabels(programWindow);

        studentList = new JList(); //creates and initialises a list that contains all the students in the class
        programWindow.add(studentList);
        initNameList(studentList);

        JButton addStudentManual = new JButton("Add student");
        programWindow.add(addStudentManual);
        initAddStudentManual(addStudentManual);

        JButton addStudentBatch = new JButton("Add students (external file)");
        programWindow.add(addStudentBatch);
        initAddStudentBatch(addStudentBatch);
        
        JButton addStudentDB = new JButton("Add students (internal database)");
        programWindow.add(addStudentDB);
        initAddStudentDB(addStudentDB);
        
        JButton sortButton = new JButton("Sort");
        programWindow.add(sortButton);
        initSortButton(sortButton);

        groupNumSpinner = new JSpinner();
        programWindow.add(groupNumSpinner);
        initGroupNumSpinner(groupNumSpinner);

        groupNumberList = new JList();
        programWindow.add(groupNumberList);
        initGroupNumberList(groupNumberList);

        groupMemberList = new JList();
        programWindow.add(groupMemberList);
        initGroupMemberList(groupMemberList);

        programWindow.setVisible(true);
    }
    
    void initSaveButton(window programWindow) {
        //create save button here
    }
    
    void initLabels(window programWindow) {
        JLabel studentLabel = new JLabel("Students in class");
        studentLabel.setVisible(true);
        studentLabel.setEnabled(true);
        studentLabel.setBounds(30, labelHeight, 200, 10);
        programWindow.add(studentLabel);

        JLabel groupNumberLabel = new JLabel("N");
        groupNumberLabel.setVisible(true);
        groupNumberLabel.setEnabled(true);
        groupNumberLabel.setBounds(420, labelHeight, 15, 10);
        programWindow.add(groupNumberLabel);

        JLabel groupMemberLabel = new JLabel("Student name");
        groupMemberLabel.setVisible(true);
        groupMemberLabel.setEnabled(true);
        groupMemberLabel.setBounds(435, labelHeight, 100, 10);
        programWindow.add(groupMemberLabel);

    }

    void initGroupNumSpinner(JSpinner spinner) {
        spinner.setVisible(true);
        spinner.setEnabled(true);
        spinner.setBounds(305, listHeight, 40, 40);
    }

    void initSortButton(JButton sortButton) {
        sortButton.setVisible(true);
        sortButton.setEnabled(true);
        sortButton.setBounds(250, listHeight + 60, 150, 35);
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setNumOfStudentsPerGroup((int) groupNumSpinner.getValue());
                sortStudents();
                fillGroupNumberList(groupNumberList);
                fillGroupMemberList(groupMemberList);
            }
        });
    }

    void initNameList(JList list) {
        list.setListData(cl.getNamesForList());
        list.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        list.setVisible(true);
        list.setEnabled(true);
        list.setBounds(30, listHeight, 200, 350);
    }

    void addStudentToList(String name, String role, double attendance) {
        JList list = studentList;
        String[] nameList = new String[list.getModel().getSize() + 1];
        for (int i = 0; i < list.getModel().getSize(); i++) {
            nameList[i] = (String) list.getModel().getElementAt(i);
        }
        nameList[list.getModel().getSize()] = name + ", " + role + ", " + attendance;
        list.setListData(nameList);
    }

    void initAddStudentManual(JButton button) {
        button.setVisible(true);
        button.setEnabled(true);
        button.setBounds(30, listHeight + 370, 200, 35);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudentPopup();
            }
        });
    }

    void initAddStudentBatch(JButton button) {
        button.setVisible(true);
        button.setEnabled(true);
        button.setBounds(30, listHeight + 425, 200, 35);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectDataFilePopup();
            }
        });
    }
    
    void initAddStudentDB(JButton button) {
        button.setVisible(true);
        button.setEnabled(true);
        button.setBounds(30, listHeight + 480, 200, 35);
        button.addActionListener(new  ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e){
               loadStudentsFromDB();
           }
        });
    }
    
    void initGroupNumberList(JList list) {
        list.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        list.setVisible(true);
        list.setEnabled(true);
        list.setBounds(420, listHeight, 15, 350);
    }

    void initGroupMemberList(JList list) {
        list.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        list.setVisible(true);
        list.setEnabled(true);
        list.setBounds(435, listHeight, 100, 350);
    }

    void fillGroupNumberList(JList list) {
        int numOfStudents = cl.getStudentList().size();
        int numOfRows = numOfStudents + (cl.getNumOfGroups());
        int groupNum = 0;
        int rowNum = 0;
        //System.out.println("INITIAL ROWNUM VAR " + rowNum);
        boolean done = false;
        String[] listData = new String[numOfRows];
        do { //loops until the program is finished with the process
            for (int j = 0; j < cl.getGroupList().get(groupNum).getStudentListSize(); j++) { //loops through for every student in a group
                listData[rowNum] = groupNum + ""; //puts the group number next to the student
                rowNum++; //moves onto the next student's row
            }
            groupNum++; //moves onto the next group
            System.out.println(rowNum);
            listData[rowNum] = " ";
            rowNum++;
            //System.out.println(rowNum);
            if (groupNum == cl.getGroupListSize()) //if all the groups are done, end the loop
            {
                done = true;
            }
        } while (!done);

        list.setListData(listData);
    }

    void fillGroupMemberList(JList list) {
        int numOfStudents = cl.getStudentList().size();
        int numOfRows = numOfStudents + (cl.getNumOfGroups());
//        System.out.println("NUMBER OF ROWS " + numOfRows);
//        System.out.println("NUMBER OF STUDENTS " + numOfStudents);
//        System.out.println("NUMBER OF GROUPS " + cl.getNumOfGroups());
        int groupNum = 0;
        int rowNum = 0;
        boolean done = false;
        String[] listData = new String[numOfRows];
        do {
            for (int j = 0; j < cl.getGroupList().get(groupNum).getStudentListSize(); j++) { //loop through every student in a group
                listData[rowNum] = cl.getGroupList().get(groupNum).getStudentList().get(j).getName(); //puts the students' names next to their group numer
                rowNum++;
            }
            groupNum++;
            listData[rowNum] = " ";
            rowNum++;
            if (groupNum == cl.getGroupListSize()) {
                done = true;
            }
        } while (!done);

        list.setListData(listData);
    }

    // </editor-fold>
    
    private void loadStudentsFromDB() {
        File databaseFile = new File("src\\db\\classDatabase.db");
        try {
            readStudentsFromDB(databaseFile);
        } catch (SQLException ex) {
            Logger.getLogger(GroupSorter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void addStudentPopup() {
        JTextField studentName = new JTextField(5);
        JTextField studentRole = new JTextField(5);
        JTextField studentAtt = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Name:"));
        myPanel.add(studentName);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Role:"));
        myPanel.add(studentRole);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Attendance:"));
        myPanel.add(studentAtt);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter the student's details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            System.out.println("name: " + studentName.getText());
            System.out.println("role: " + studentRole.getText());
            System.out.println("attendance: " + studentAtt.getText());
            createStudent(cl.getStudentListSize(), studentName.getText(), studentRole.getText(), Double.parseDouble(studentAtt.getText()));
            addStudentToList(studentName.getText(), studentRole.getText(), Double.parseDouble(studentAtt.getText()));
        }
    }

    void createClass() {
        cl = new classOfStudents("Nemitari");
//        createStudent("Niall", "Programming", 89.24);
//        createStudent("Jordan", "Programming", 69.21);
//        createStudent("Nick", "Documentation", 95.00);
//        createStudent("Liam", "Programming", 75.15);
//        createStudent("Jacob", "Documentation", 71.00);
//        createStudent("Lee", "Programming", 10.02);
//        createStudent("Steven", "Documentation", 72.15);
//        createStudent("Kevin", "Documentation", 47.54);
    }

    void selectDataFilePopup() {
        JFileChooser fileChooser = new JFileChooser();
        int value = fileChooser.showOpenDialog(groupNumberList);
        if (value == JFileChooser.APPROVE_OPTION) {
            File chosenFile = fileChooser.getSelectedFile();
            String fileName = chosenFile.getName();
            if (fileName.substring(fileName.length() - 4, fileName.length()).equals(".txt")) { //if the file is a text file
                readStudentsFromCSV(chosenFile);
            } else if (fileName.substring(fileName.length() - 3, fileName.length()).equals(".db")) { //if the file is a database file
                try {
                    readStudentsFromDB(chosenFile);
                } catch (SQLException ex) {
                    Logger.getLogger(GroupSorter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void readStudentsFromDB(File fileToBeRead) throws SQLException {
        DataReader dr = new DataReader(fileToBeRead.getPath());
        ArrayList<Student> readStudents = dr.loadStudents();
        for (int i = 0; i < readStudents.size(); i++)
            createStudent(readStudents.get(i));
        cl.sortStudentsByAttendance();
        System.out.println("Students loaded");
    }

    void readStudentsFromCSV(File fileToBeRead) { //file given is txt where data is read seperated by commas
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileToBeRead));
            ArrayList<String> studentInfo = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                studentInfo.add(line);
            }
            analyseData(studentInfo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GroupSorter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GroupSorter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void analyseData(ArrayList<String> studentInfo) {
        int id = 0;
        String studentName = "";
        String studentRole = "";
        double studentAtt = 0;
        for (int i = 0; i < studentInfo.size(); i++) {
            int value = 0;
            int valueStart = 0;
            //System.out.println(studentInfo.get(i));
            String studentInfoLine = studentInfo.get(i);
            for (int j = 0; j < studentInfoLine.length(); j++) {
                if (",".equals(studentInfoLine.substring(j, j + 1))) {
                    switch (value) {
                        case 0:
                            studentName = studentInfoLine.substring(valueStart, j);
                            break;
                        case 1:
                            studentRole = studentInfoLine.substring(valueStart, j);
                            break;
                        case 2:
                            studentAtt = Double.parseDouble(studentInfoLine.substring(valueStart, j));
                            break;
                    }
                    valueStart = j + 1;
                    value += 1;
                }
            }
            System.out.println("Name: " + studentName + ", Role: " + studentRole + ", Attendance: " + studentAtt);
            createStudent(id, studentName, studentRole, studentAtt);
            id++;
        }
        cl.sortStudentsByAttendance();
    }

    void createStudent(int id, String name, String role, double attendance) {
        Student s = new Student(id, name, role, attendance);
        addStudentToList(name, role, attendance);
        cl.addStudent(s);
        cl.sortStudentsByAttendance();
    }
    
    private void createStudent(Student student) {
        addStudentToList(student.getName(), student.getPrefRole(), student.getAttendance());
        cl.addStudent(student);
        cl.sortStudentsByAttendance();
    }

    void setNumOfStudentsPerGroup(int n) {
        cl.setStudentsPerGroup(n);
    }

    void sortStudents() {
        cl.sortStudentsIntoGroups();
        cl.printGroupInfo();
    }

}
