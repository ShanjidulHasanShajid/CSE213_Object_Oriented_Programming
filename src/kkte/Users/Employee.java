/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkte.Users;

import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;

public abstract class Employee implements Serializable {
    protected String name;
    protected int ID;
    protected String dateOfJoining;
    protected String bloodGroup;
    protected String department;
    protected String gender;
    
}
