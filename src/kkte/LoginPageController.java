/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkte;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import kkte.Users.AppUser;

/**
 * FXML Controller class
 *
 * @author shaji
 */
public class LoginPageController implements Initializable {

    @FXML    private TextField idTextField;
    @FXML    private TextField passwordTextField;
    @FXML    private ComboBox userTypeComboBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      userTypeComboBox.getItems().addAll("HR Manager","IT Admin","Field Training Officer","Estate Manager");
    }    

    @FXML
    private void userIDOnClick(MouseEvent event) {
        idTextField.clear();
    }

    @FXML
    private void passwordOnclick(MouseEvent event) {
        passwordTextField.clear();
    }

    @FXML
    private void helpButtonOnClick(ActionEvent event) {
        
    }

    @FXML
    private void loginButtonOnClick(ActionEvent event) {
        File f = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            f = new File("AppUsers.bin");
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            AppUser appUser;
            while (true)
            {
                appUser = (AppUser)ois.readObject();
                System.out.println("loop");
                int enteredUserID = Integer.parseInt(idTextField.getText());
                
                //System.out.println("UserID: " + appUser.getAppUserID());
                int enteredUserPass = Integer.parseInt(passwordTextField.getText());
                System.out.println("enteredUserPASS: " +enteredUserPass);
                System.out.println("UserPass: " + appUser.getPassword());
                String selectedType = userTypeComboBox.getValue().toString();
                if ((enteredUserID == appUser.getAppUserID()) && (enteredUserPass == appUser.getPassword()) && (selectedType.equals(appUser.getUserType()))){
                    
                    
                    if ("IT Admin".equals(appUser.getUserType()))
                    {
                        Parent parent = FXMLLoader.load(getClass().getResource("ITadminstrator.fxml"));
                        Scene sc = new Scene(parent);
                        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                        window.setScene(sc);
                        window.show(); 
                    }
                    if ("HR Manager".equals(appUser.getUserType()))
                    {
                        Parent parent = FXMLLoader.load(getClass().getResource("userHomePageForHR.fxml"));
                        Scene sc = new Scene(parent);
                        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                        window.setScene(sc);
                        window.show(); 
                    }
                    break;
                }
            }
                
        }
        catch (Exception ex){
            Alert newAlert = new Alert(AlertType.WARNING);
            newAlert.setContentText("Wrong Credentials. Try Again");
            newAlert.showAndWait();
        }
    }
    
}
