/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkte.Users;

import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;

public class Executive extends Employee implements payableWage, Serializable{
    private float salary;
    private SimpleStringProperty overtimeHours;
    private static final float OVERTIME_RATE = 500.0f;
    private static final float LEAVE_PENALTY = 500.0f;
    private SimpleStringProperty leavesTaken;
    //private float raise;
    private SimpleStringProperty bonusPercentage;
    private SimpleStringProperty advanceWithdrawn;
    private float finalSalary;
    
    /*
    SimpleStringProperty name,
    int ID,
    String dateOfJoining,
    String bloodGroup,
    String department,
    */
    public Executive(){
        this.ID = 0;
        this.name =  "Malala";
        this.salary = 0.0f;
        this.overtimeHours = new SimpleStringProperty ("0.0");
        this.leavesTaken = new SimpleStringProperty("0");
        this.bonusPercentage = new SimpleStringProperty("0.0");
        this.bloodGroup = "A+";
        this.dateOfJoining = "";
        this.department = "";
        this.gender = "";
        this.advanceWithdrawn = new SimpleStringProperty("0.0");
    }
    
    public Executive(
            int ID,
            String name,
            String dateOfJoining,
            String bloodGroup,
            String department,
            float salary, 
            String overtimeHours, 
            String leavesTaken, 
            String bonusPercentage,
            String gender,
            String advanceWithdrawn,
            Float finalSalary) {
        this.ID = ID;
        this.name =  name;
        this.salary = salary;
        this.overtimeHours = new SimpleStringProperty (overtimeHours);
        this.leavesTaken = new SimpleStringProperty(leavesTaken);
        this.bonusPercentage = new SimpleStringProperty(bonusPercentage);
        this.bloodGroup = bloodGroup;
        this.dateOfJoining = dateOfJoining;
        this.department = department;
        this.gender = gender;
        this.advanceWithdrawn = new SimpleStringProperty(advanceWithdrawn);
        this.finalSalary = finalSalary;
    }
    public Executive(
            int ID,
            String name,
            String dateOfJoining,
            String bloodGroup,
            String department,
            float salary, 
            String overtimeHours, 
            String leavesTaken, 
            String bonusPercentage,
            String gender,
            String advanceWithdrawn) {
        this.ID = ID;
        this.name =  name;
        this.salary = salary;
        this.overtimeHours = new SimpleStringProperty (overtimeHours);
        this.leavesTaken = new SimpleStringProperty(leavesTaken);
        this.bonusPercentage = new SimpleStringProperty(bonusPercentage);
        this.bloodGroup = bloodGroup;
        this.dateOfJoining = dateOfJoining;
        this.department = department;
        this.gender = gender;
        this.advanceWithdrawn = new SimpleStringProperty(advanceWithdrawn);
    }
    public String getGender(){
        return gender;
    }
    public float getSalary() {
        return salary;
    }

    public String getOvertimeHours() {
        return overtimeHours.get();
    }

    public static float getOVERTIME_RATE() {
        return OVERTIME_RATE;
    }

    public static float getLEAVE_PENALTY() {
        return LEAVE_PENALTY;
    }

    public String getLeavesTaken() {
        return leavesTaken.get();
    }

    public String getBonusPercentage() {
        return bonusPercentage.get();
    }

    public void setBonusPercentage(String bonusPercentage) {
        this.bonusPercentage = new SimpleStringProperty(bonusPercentage) ;
    }

   
    public String getAdvanceWithdrawn(){
        return advanceWithdrawn.get();
    }
    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public String getDateOfJoining() {
        return dateOfJoining;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getDepartment() {
        return department;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public void setOvertimeHours(String overtimeHours) {
        this.overtimeHours = new SimpleStringProperty (overtimeHours);
    }

    public void setLeavesTaken(String leavesTaken) {
        this.leavesTaken = new SimpleStringProperty (leavesTaken);
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setDateOfJoining(String dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    public void setGender(String gender){
        this.gender = gender;
        
    }
    public void setAdvanceWithdrawn(String advanceWithdrwn){
        this.advanceWithdrawn = new SimpleStringProperty (advanceWithdrwn);
    }
    public void setFinalSalary(){
        
        this.finalSalary = payableWage();
    }
    public float getFinalSalary(){
        return finalSalary;
    }
    
    @Override
    public float payableWage() {
        float wage;
        wage = salary + 
                (Float.parseFloat(overtimeHours.get())*OVERTIME_RATE) - 
                (Float.parseFloat(leavesTaken.get())*LEAVE_PENALTY)- Float.parseFloat(advanceWithdrawn.get()) 
                +(salary*(Float.parseFloat(bonusPercentage.get())/100.0f)   );
        return wage;
    }
}
