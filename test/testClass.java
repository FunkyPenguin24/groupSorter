/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import groupsorter.*;
import java.io.File;
import java.io.FileNotFoundException;
import org.junit.*;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Eldram
 */
public class testClass {
//    public static void main(String[] args) {
//        testClass t = new testClass();
//        t.testOpenFile();
//    }    
    
    @Rule
    public ExpectedException ex = ExpectedException.none();
    
    @Test
    public void throwsNullPointerException() {
        ex.expect(NullPointerException.class);
        GroupSorter classBeingTested = new GroupSorter();
        classBeingTested.readStudentsFromCSV(new File("src\\files\\studentInfo.txt"));
    }
    
    @Test
    public void testStudentNum() {
        ex.expect(NullPointerException.class);
        GroupSorter classBeingTested = new GroupSorter();
        classBeingTested.setNumOfStudentsPerGroup(10);
    }
    
}
