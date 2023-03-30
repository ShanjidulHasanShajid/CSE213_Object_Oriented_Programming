
package kkte;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import kkte.Users.AppUser;
import kkte.Users.Executive;



public class ITadminstratorController implements Initializable {

    @FXML    private Label showUserIDLabel;
    @FXML    private Label showPasswordLabel;
    @FXML    private TextField employeeIDTextField;
    @FXML    private Label showUserNameLabelForAdiing;
    @FXML    private ComboBox selectUserTypeComboBox;
    @FXML    private TableView<AppUser> tableView;
    @FXML    private TableColumn<AppUser, Integer> tableViewUserID;
    @FXML    private TableColumn<AppUser, Integer> tableViewEmpID;
    @FXML    private TableColumn<AppUser, String> tableViewUserName;
    @FXML    private TableColumn<AppUser, String> tableViewDept;
    @FXML    private TableColumn<AppUser, String> tableViewUserType;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showUserIDLabel.setText(Integer.toString(getAutomatedID()));
        showPasswordLabel.setText(Integer.toString(getAutomatedPassword()));
        selectUserTypeComboBox.getItems().addAll("HR Manager","IT Admin","Field Training Officer","Estate Manager");
        
        tableViewUserID.setCellValueFactory(new PropertyValueFactory<AppUser,Integer>("appUserID"));
        tableViewEmpID.setCellValueFactory(new PropertyValueFactory <AppUser,Integer>("ID"));
        tableViewUserName.setCellValueFactory(new PropertyValueFactory<AppUser, String>("name"));
        tableViewDept.setCellValueFactory(new PropertyValueFactory <AppUser, String>("department"));
        tableViewUserType.setCellValueFactory(new PropertyValueFactory <AppUser, String>("userType"));
        tableView.setItems(getPeople());
        
    }
    
    ObservableList<AppUser> people = FXCollections.observableArrayList();
    public ObservableList<AppUser> getPeople(){
        File f = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        AppUser au;
        try{
            f = new File("AppUsers.bin");
            fis = new FileInputStream (f);
            ois = new ObjectInputStream (fis);
            while (true){
                au = (AppUser)ois.readObject();
                people.add(au);
            }
        }
        catch(Exception ex){}
        
        return people;
    }
    
    
    public int getAutomatedID(){
        File ff = null;
        int empID = 0;
        FileInputStream fis = null;
        DataInputStream dis = null;
        try {
        ff = new File("UserIDGenerator.bin");
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
            ff = new File("UserIDGenerator.bin");
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
    public int getAutomatedPassword(){
        File ff = null;
        int empPassword = 0;
        FileInputStream fis = null;
        DataInputStream dis = null;
        try {
        ff = new File("PasswordGenerator.bin");
        fis = new FileInputStream(ff);
        dis = new DataInputStream(fis);
        empPassword =  dis.readInt();
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
        return empPassword;
    }
    public void updateAutomatedPassword()
    {
        File ff = null;
        FileOutputStream fos = null;
        DataOutputStream dos = null;
        int oldEmpPassword = getAutomatedPassword();
        try {
            ff = new File("PasswordGenerator.bin");
            fos = new FileOutputStream(ff);
            dos = new DataOutputStream(fos);
            int newEmpPassword = oldEmpPassword + 1;
            dos.writeInt(newEmpPassword);       
        } 
        catch (Exception ex) {} 
        finally{
            try{
                if(dos != null) dos.close();
            } catch (Exception ex) {}
        }
    }
    
    public AppUser ex;
    public String getEmployeeName(int inputId)
    {
        File read = null;
        FileInputStream fis = null;
        DataInputStream dis = null;
        try
        {
            read = new File ("Employee.bin");
            fis = new FileInputStream(read);
            dis = new DataInputStream(fis);
            while (true)
            {
                int id;
                id = dis.readInt();
                String name = dis.readUTF();
                String gend = dis.readUTF();
                String dept = dis.readUTF();
                float sal = dis.readFloat();
                String doj = dis.readUTF();
                String BG = dis.readUTF();
                if (inputId == id){
                    //AppUser(String name, int ID, String dateOfJoining, 
                    //String bloodGroup, String department, String gender, int password, String userType)
                    ex = new AppUser(name,id,doj,BG,dept,gend,getAutomatedPassword(),selectUserTypeComboBox.getValue().toString(),getAutomatedID());
                    return name;
                }
            }
        }
        catch (Exception e){}
        finally{
                try 
                {
                    if(dis != null) dis.close();
                } 
                catch (Exception ex) {}
            }
        return "null";
    }

    @FXML
    private void addUserButtonOnClick(ActionEvent event) {
        File w = null;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        //System.out.println("Error1");
       
        try
        {
            w = new File("AppUsers.bin");
            if(w.exists())
            {

                fos = new FileOutputStream (w,true);
                oos = new AppendableObjectOutputStream(fos);
            }
            else {
                fos = new FileOutputStream (w);
                oos = new ObjectOutputStream(fos);
            }
            
            oos.writeObject(ex);
            updateAutomatedID();
            updateAutomatedPassword();
            showUserIDLabel.setText(Integer.toString(getAutomatedID()));
            showPasswordLabel.setText(Integer.toString(getAutomatedPassword()));
            employeeIDTextField.clear();
            tableView.getItems().add(ex);
            Alert newAlert = new Alert(AlertType.CONFIRMATION);
            newAlert.setContentText("New User Added Successfully");
            newAlert.showAndWait();

        }
        catch(Exception ex){
        }
        finally{
            try 
            {
                if(oos != null) oos.close();
            } 
            catch (Exception ex) {}
        }
        
        
    }

    @FXML
    private void employeeIDtextFieldOnClick(MouseEvent event) {
        employeeIDTextField.clear();
    }
    

 



    @FXML
    private void logOutButtonOnClick(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Scene sc;
        sc = new Scene(parent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(sc);
        window.show();
    }

    @FXML
    private void searchEmployeeButtonOnCLick(ActionEvent event) {
        if (selectUserTypeComboBox.getSelectionModel().isEmpty()){
            Alert newAlert = new Alert(AlertType.WARNING);
            newAlert.setContentText("Please Select User Type Before Searching For User");
            newAlert.showAndWait();
        }
        else if (getEmployeeName(Integer.parseInt(employeeIDTextField.getText())) == "null" ){
            Alert newAlert = new Alert(AlertType.WARNING);
            newAlert.setContentText("Employee ID not Found");
            newAlert.showAndWait();
        }
        else {
             showUserIDLabel.setText(Integer.toString(getAutomatedID()));
             showUserNameLabelForAdiing.setText(getEmployeeName(Integer.parseInt(employeeIDTextField.getText())));
             showPasswordLabel.setText(Integer.toString(getAutomatedPassword()));
             
             
        }
    }
    
}
