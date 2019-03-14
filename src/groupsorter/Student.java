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
    private String name;
    private String preferredRole;
    private double attendance;
    private boolean inGroup;
    public Student(String n, String role, double att) {
        name = n;
        preferredRole = role;
        attendance = att;
        inGroup = false;
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

    void setAttendance(float att) {
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
