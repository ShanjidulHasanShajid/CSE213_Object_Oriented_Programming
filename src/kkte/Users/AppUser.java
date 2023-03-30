package kkte.Users;

import java.io.Serializable;

public class AppUser extends Employee implements Serializable{
    private int appUserID;
    private int password;
    private String userType;

    public AppUser(String name, int ID, String dateOfJoining, String bloodGroup, String department, String gender, int password, String userType, int appUserID) {
        this.name = name;
        this.ID = ID;
        this.dateOfJoining = dateOfJoining;
        this.bloodGroup = bloodGroup;
        this.department = department;
        this.gender = gender;
        this.password = password;
        this.userType = userType;
        this.appUserID = appUserID;
    }

    public int getAppUserID() {
        return appUserID;
    }

    public void setAppUserID(int appUserID) {
        this.appUserID = appUserID;
    }

    
    public int getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
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

    public String getGender() {
        return gender;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    public void setGender(String gender) {
        this.gender = gender;
    }

    
}
