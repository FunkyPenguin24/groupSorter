/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupsorter;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
import javax.swing.JComponent;

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
        initComponent(studentList, new Rectangle(30, listHeight, 200, 350));
        initNameList(studentList);

        JButton addStudentManual = new JButton("Add student"); //creates and sets up the button to manually add a student
        programWindow.add(addStudentManual);
        initComponent(addStudentManual, new Rectangle(30, listHeight + 370, 200, 35));
        initAddStudentManual(addStudentManual);

        JButton addStudentBatch = new JButton("Import students (Database/text file)"); //creates and sets up the button to add students via a csv
        programWindow.add(addStudentBatch);
        initComponent(addStudentBatch, new Rectangle(30, listHeight + 425, 200, 35));
        initAddStudentBatch(addStudentBatch);

        JButton addStudentDB = new JButton("Load saved students"); //creates and sets up the button to add students via a database
        programWindow.add(addStudentDB);
        initComponent(addStudentDB, new Rectangle(30, listHeight + 480, 200, 35));
        initAddStudentDB(addStudentDB);

        JButton sortButton = new JButton("Sort"); //creates and sets up the button to sort students
        programWindow.add(sortButton);
        initComponent(sortButton, new Rectangle(250, listHeight + 60, 150, 35));
        initSortButton(sortButton);

        JButton editStudentButton = new JButton("Edit student"); //creates and sets up the button to edit a selected student
        programWindow.add(editStudentButton);
        initComponent(editStudentButton, new Rectangle(250, listHeight + 115, 150, 35));
        initEditStudentButton(editStudentButton, studentList);

        JButton saveStudentsDB = new JButton("Save loaded students"); //creates and sets up the button to save students in the internal database
        programWindow.add(saveStudentsDB);
        initComponent(saveStudentsDB, new Rectangle(420, listHeight + 480, 200, 35));
        initSaveButtonDB(saveStudentsDB);

        JButton saveToExternalDBButton = new JButton("Export students (database)"); //creates and sets up the button to save students to an external database
        programWindow.add(saveToExternalDBButton);
        initComponent(saveToExternalDBButton, new Rectangle(420, listHeight + 370, 200, 35));
        initExternalDBButton(saveToExternalDBButton);

        JButton saveToExternalCSVButton = new JButton("Export students (text file)");
        programWindow.add(saveToExternalCSVButton);
        initComponent(saveToExternalCSVButton, new Rectangle(420, listHeight + 425, 200, 35));
        initSaveCSVButton(saveToExternalCSVButton);

        groupNumSpinner = new JSpinner();
        programWindow.add(groupNumSpinner);
        initComponent(groupNumSpinner, new Rectangle(305, listHeight, 40, 40));

        groupNumberList = new JList();
        programWindow.add(groupNumberList);
        initComponent(groupNumberList, new Rectangle(420, listHeight, 15, 350));
        initGroupNumberList(groupNumberList);

        groupMemberList = new JList();
        programWindow.add(groupMemberList);
        initComponent(groupMemberList, new Rectangle(435, listHeight, 100, 350));
        initGroupMemberList(groupMemberList);

        programWindow.setVisible(true);
    }

    private void initComponent(JComponent button, Rectangle bounds) {
        button.setVisible(true);
        button.setEnabled(true);
        button.setBounds(bounds);
    }

    void initSaveButtonDB(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveStudentsToDB(cl.getStudentList());
            }
        });
    }

    private void initExternalDBButton(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File chosenFile = selectDataFilePopup("db");
                if (chosenFile != null) {
                    saveStudentsToDB(cl.getStudentList(), chosenFile.getPath());
                }
            }
        });
    }

    private void initSaveCSVButton(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File chosenFile = selectDataFilePopup("text");
                if (chosenFile != null) {
                    saveStudentsToCSV(cl.getStudentList(), chosenFile.getPath());
                }
            }
        });
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

    void initSortButton(JButton sortButton) {
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

    private void initEditStudentButton(JButton button, JList studentList) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (studentList.getSelectedIndex() != -1) {
                    String studentID = studentList.getSelectedValue().toString().substring(0, 8);
                    Student selectedStudent = cl.getStudentByID(Integer.parseInt(studentID));
                    editStudentPopup(selectedStudent);
                }
            }
        });
    }

    private void initNameList(JList list) {
        list.setListData(cl.getNamesForList());
        list.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    private void addStudentToList(int id, String name, String role, double attendance) {
        JList list = studentList;
        String[] nameList = new String[list.getModel().getSize() + 1];
        for (int i = 0; i < list.getModel().getSize(); i++) {
            nameList[i] = (String) list.getModel().getElementAt(i);
        }
        nameList[list.getModel().getSize()] = id + ", " + name + ", " + role + ", " + attendance;
        list.setListData(nameList);
    }

    private void initAddStudentManual(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudentPopup();
            }
        });
    }

    private void initAddStudentBatch(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File chosenFile = selectDataFilePopup("null");
                String fileName = chosenFile.getName();
                if (fileName.substring(fileName.length() - 4, fileName.length()).equals(".txt")) { //if the file is a text file
                    readStudentsFromCSV(chosenFile);
                } else if (fileName.substring(fileName.length() - 3, fileName.length()).equals(".db")) { //if the file is a text file
                    readStudentsFromDB(chosenFile);
                }
            }
        });
    }

    private void initAddStudentDB(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadStudentsFromDB();
            }
        });
    }

    private void initGroupNumberList(JList list) {
        list.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    private void initGroupMemberList(JList list) {
        list.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    private void fillGroupNumberList(JList list) {
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

    private void fillGroupMemberList(JList list) {
        int numOfStudents = cl.getStudentList().size();
        int numOfRows = numOfStudents + (cl.getNumOfGroups());
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
        readStudentsFromDB(databaseFile);
    }

    private void addStudentPopup() {
        JTextField studentIDField = new JTextField(5);
        JTextField studentName = new JTextField(5);
        JTextField studentRole = new JTextField(5);
        JTextField studentAtt = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("ID:"));
        myPanel.add(studentIDField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
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
            createStudent(Integer.parseInt(studentIDField.getText()), studentName.getText(), studentRole.getText(), Double.parseDouble(studentAtt.getText()));
        }
    }

    private void editStudentPopup(Student s) {
        JTextField studentIDField = new JTextField(5);
        studentIDField.setText(s.getStudentID() + "");
        JTextField studentName = new JTextField(5);
        studentName.setText(s.getName());
        JTextField studentRole = new JTextField(5);
        studentRole.setText(s.getPrefRole());
        JTextField studentAtt = new JTextField(5);
        studentAtt.setText(s.getAttendance() + "");

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("ID:"));
        myPanel.add(studentIDField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
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
            updateStudent(s, studentIDField.getText(), studentName.getText(), studentRole.getText(), studentAtt.getText());
            //createStudent(cl.getStudentListSize(), studentName.getText(), studentRole.getText(), Double.parseDouble(studentAtt.getText()));
            //addStudentToList(Integer.parseInt(studentIDField.getText()), studentName.getText(), studentRole.getText(), Double.parseDouble(studentAtt.getText()));
        }
    }

    private void updateStudent(Student s, String id, String name, String role, String attendance) {
        s.setStudentID(Integer.parseInt(id));
        s.setName(name);
        s.setPrefRole(role);
        s.setAttendance(Double.parseDouble(attendance));
        initNameList(studentList);
    }

    private void createClass() {
        cl = new classOfStudents("Nemitari");
    }

    private File selectDataFilePopup(String fileTypeNeeded) {
        JFileChooser fileChooser = new JFileChooser();
        int value = fileChooser.showOpenDialog(groupNumberList);
        File chosenFile;
        if (value == JFileChooser.APPROVE_OPTION) {
            chosenFile = fileChooser.getSelectedFile();
            String fileName = chosenFile.getName();
            if (fileName.substring(fileName.length() - 4, fileName.length()).equals(".txt")) { //if the file is a text file
                if (fileTypeNeeded.equals("text") || fileTypeNeeded.equals("null")) {
                    return chosenFile;
                } else if (fileTypeNeeded.equals("db")) {
                    JOptionPane.showMessageDialog(groupNumberList,
                            "Please pick a suitable file type (.db)");
                }
            } else if (fileName.substring(fileName.length() - 3, fileName.length()).equals(".db")) { //if the file is a database file
                if (fileTypeNeeded.equals("db") || fileTypeNeeded.equals("null")) {
                    return chosenFile;
                } else if (fileTypeNeeded.equals("text")) {
                    JOptionPane.showMessageDialog(groupNumberList,
                            "Please pick a suitable file type (.txt)");
                }
            } else {
                JOptionPane.showMessageDialog(groupNumberList,
                        "Please pick a suitable file type (.txt/.db)");
                return null;
            }
            return null;
        }
        return null;
    }

    private void readStudentsFromDB(File fileToBeRead) {
        DataReader dr = new DataReader(fileToBeRead.getPath());
        ArrayList<Student> readStudents = null;
        try {
            readStudents = dr.loadStudents();
        } catch (SQLException ex) {
            Logger.getLogger(GroupSorter.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < readStudents.size(); i++) {
            createStudent(readStudents.get(i));
        }
        cl.sortStudentsByAttendance();
        System.out.println("Students loaded");
    }

    private void readStudentsFromCSV(File fileToBeRead) { //file given is txt where data is read seperated by commas
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

    private void saveStudentsToDB(ArrayList<Student> studentList) {
        DataWriter dw = new DataWriter("src\\db\\classDatabase.db");
        try {
            dw.saveStudents(studentList);
        } catch (SQLException ex) {
            Logger.getLogger(GroupSorter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveStudentsToDB(ArrayList<Student> studentList, String dbPath) {
        DataWriter dw = new DataWriter(dbPath);
        try {
            dw.saveStudents(studentList);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(groupNumberList,
                    "An error has occured with the database, please check the information provided to ensure it is set up correctly."
                    + " If problems persist, call technical support on XXXX XXX XXXX for further assistance");
            //Logger.getLogger(GroupSorter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveStudentsToCSV(ArrayList<Student> studentList, String filePath) {
        File fileToWrite = new File(filePath);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileToWrite));
            for (int i = 0; i < studentList.size(); i++) {
                String studentLine = "";
                studentLine += studentList.get(i).getStudentID() + ",";
                studentLine += studentList.get(i).getName() + ",";
                studentLine += studentList.get(i).getPrefRole() + ",";
                studentLine += studentList.get(i).getAttendance() + ",";
                System.out.println(studentLine);
                writer.write(studentLine);
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(GroupSorter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void analyseData(ArrayList<String> studentInfo) {
        int studentID = 0;
        String studentName = "";
        String studentRole = "";
        double studentAtt = 0;
        for (int i = 0; i < studentInfo.size(); i++) {
            int value = 0;
            int valueStart = 0;
            String studentInfoLine = studentInfo.get(i);
            for (int j = 0; j < studentInfoLine.length(); j++) {
                if (",".equals(studentInfoLine.substring(j, j + 1))) {
                    switch (value) {
                        case 0:
                            studentID = Integer.parseInt(studentInfoLine.substring(valueStart, j));
                            break;
                        case 1:
                            studentName = studentInfoLine.substring(valueStart, j);
                            break;
                        case 2:
                            studentRole = studentInfoLine.substring(valueStart, j);
                            break;
                        case 3:
                            studentAtt = Double.parseDouble(studentInfoLine.substring(valueStart, j));
                            break;
                    }
                    valueStart = j + 1;
                    value += 1;
                }
            }
            System.out.println("ID: " + studentID + ", Name: " + studentName + ", Role: " + studentRole + ", Attendance: " + studentAtt);
            createStudent(studentID, studentName, studentRole, studentAtt);
        }
        cl.sortStudentsByAttendance();
    }

    private void createStudent(int id, String name, String role, double attendance) {
        Student s = new Student(id, name, role, attendance);
        addStudentToList(id, name, role, attendance);
        cl.addStudent(s);
        cl.sortStudentsByAttendance();
    }

    private void createStudent(Student student) {
        addStudentToList(student.getStudentID(), student.getName(), student.getPrefRole(), student.getAttendance());
        cl.addStudent(student);
        cl.sortStudentsByAttendance();
    }

    private void setNumOfStudentsPerGroup(int n) {
        cl.setStudentsPerGroup(n);
    }

    private void sortStudents() {
        cl.sortStudentsIntoGroups();
        cl.printGroupInfo();
    }

}
