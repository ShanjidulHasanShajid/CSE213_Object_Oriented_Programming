
package kkte;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class UserHomePageForHRController implements Initializable {

    @FXML    private TextField employeeNameTextField;
    @FXML    private ComboBox departmentComboBox;
    @FXML    private TextField employeeStartingSalaryTextField;
    @FXML    private TextArea outputTextArea;
    @FXML    private ComboBox bloodGroupComboBox;
    @FXML    private ComboBox dateDayComboBox;
    @FXML    private ComboBox dateMonthComboBox;
    @FXML    private ComboBox dateYearComboBox;
    @FXML    private ComboBox genderComboBox;
    @FXML    private Label employeeIDLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        departmentComboBox.getItems().addAll("Finance","Marketing","Sales","IT", "HR","Accounts", "Research","Production");
        bloodGroupComboBox.getItems().addAll("A+","B+","AB+","O+", "A-","B-","AB-","O-");
        for (int i = 1 ; i <= 31 ; i++){
            String s = Integer.toString(i);
            dateDayComboBox.getItems().add(s);
        }
        dateMonthComboBox.getItems().addAll("Jan","Feb","Mar","Apr","May", "June","July","Aug","Sept","Oct","Nov","Dec");
        for (int i = 2000 ; i <= 2030; i++){
            String s = Integer.toString(i);
            dateYearComboBox.getItems().add(s);
        }
        dateYearComboBox.setValue("2021");
        genderComboBox.getItems().addAll("Male", "Female", "Others");
        employeeIDLabel.setText(Integer.toString(getAutomatedID()));
    }  
    
    
    public int getAutomatedID(){
        File ff = null;
        int empID = 0;
        FileInputStream fis = null;
        DataInputStream dis = null;
        try {
        ff = new File("EmployeeIDGenerator.bin");
        fis = new FileInputStream(ff);
        dis = new DataInputStream(fis);
        empID =  dis.readInt();
        } 
        catch (Exception ex) {} 
        finally 
        {
            try 
            {
                if(dis != null) dis.close();
            } 
            catch (Exception ex) {}
        }
        return empID;
    }
    
    public void updateAutomatedID()
    {
        File ff = null;
        FileOutputStream fos = null;
        DataOutputStream dos = null;
        int oldEmpId = getAutomatedID();
        try {
            ff = new File("EmployeeIDGenerator.bin");
            fos = new FileOutputStream(ff);
            dos = new DataOutputStream(fos);
            int newEmpID = oldEmpId + 1;
            dos.writeInt(newEmpID);       
        } 
        catch (Exception ex) {} 
        finally{
            try{
                if(dos != null) dos.close();
            } catch (Exception ex) {}
        }
    }

    @FXML
    private void employeeNameTextFieldOnClick(MouseEvent event) {
        employeeNameTextField.clear();
    }

    @FXML
    private void employeeStartingSalaryTextFieldOnClick(MouseEvent event) {
        employeeStartingSalaryTextField.clear();
    }

    @FXML
    private void LogOutButtonOnClick(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Scene sc = new Scene(parent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(sc);
        window.show();    
    }
    
    @FXML
    private void calculateSalaryOnClick(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("checkRollHR.fxml"));
        Scene sc = new Scene(parent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(sc);
        window.show();
    }
    
    

    @FXML
    private void saveDataOnClick(ActionEvent event) 
    {
        
        File f = null;
        FileOutputStream fos = null;
        
        DataOutputStream dos = null;
        
        try {
            f = new File("Employee.bin");
            if(f.exists()) fos = new FileOutputStream(f,true);
            else fos = new FileOutputStream(f);
            dos = new DataOutputStream(fos);
            
            dos.writeInt(getAutomatedID());
            updateAutomatedID();
            dos.writeUTF(employeeNameTextField.getText());
            dos.writeUTF(genderComboBox.getValue().toString());
            dos.writeUTF(departmentComboBox.getValue().toString());
            dos.writeFloat(Float.parseFloat(employeeStartingSalaryTextField.getText()));
            String date = "";
            date+= dateDayComboBox.getValue().toString() + " " + dateMonthComboBox.getValue().toString()
                    +", "+ dateYearComboBox.getValue().toString();
            dos.writeUTF(date);
            dos.writeUTF(bloodGroupComboBox.getValue().toString());
            employeeStartingSalaryTextField.clear();
            employeeNameTextField.clear();
            
            Alert ar = new Alert(AlertType.INFORMATION);
            ar.setTitle("Information");
            ar.setContentText("Data Saved Successfully");
            ar.setHeaderText("Saved!");
            ar.showAndWait();
            employeeIDLabel.setText(Integer.toString(getAutomatedID()));
            
        } 
        catch (Exception ex) {} 
        finally 
        {
            try 
            {
                if(dos != null) dos.close();
            } catch (Exception ex) {}
        }
    }

    @FXML
    private void loadDataOnCLick(ActionEvent event) {
        outputTextArea.setText("");
        File f = null;
        FileInputStream fis = null;
        DataInputStream dis = null;
        String str="";
        try {
            f = new File("Employee.bin");
            if(!f.exists()){
                outputTextArea.setText("Please Add Employee First!");
            }
            else{
                
                fis = new FileInputStream(f);
                dis = new DataInputStream(fis);
                while(true){
                    str+= "Id : "+Integer.toString(dis.readInt())
                        +" ; Name : "+dis.readUTF()
                        +" ; Gender : "+dis.readUTF()
                        +" ; Department : "+dis.readUTF()
                        +" ; \nSalary : "+Float.toString(dis.readFloat())
                        +" ; Date of Joining : "+dis.readUTF()
                        +" ; Blood Group : "+ dis.readUTF()+"\n\n";
                }
            }
        } 
        catch (Exception ex) {
            outputTextArea.setText(str);  
        } 
        finally 
        {
            try {
                if(dis != null) dis.close();
            } 
            catch (Exception ex) {}
        }
    }


    
}
