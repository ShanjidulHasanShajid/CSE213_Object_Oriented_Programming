/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkte;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import kkte.Users.AppUser;

/**
 * FXML Controller class
 *
 * @author shaji
 */
public class ITshowController implements Initializable {

    @FXML
    private TextArea textArea;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buttonClick(ActionEvent event) {
        textArea.setText("");
        File f = null;
        FileInputStream fis = null;      
        ObjectInputStream ois = null;
        
        try {
            f = new File("AppUsers.bin");
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            AppUser emp;
            try{
                textArea.setText("");
                while(true){
                    
                    emp = (AppUser)ois.readObject();
                    
                    //emp.submitReport();
                    //System.out.println(emp.toString());
                    textArea.appendText(emp.getName());
                    textArea.appendText("\n");
                    textArea.appendText(emp.getUserType());
                    textArea.appendText("\n");
                    textArea.appendText(Integer.toString(emp.getAppUserID()));
                    textArea.appendText("\n");
                    textArea.appendText(Integer.toString(emp.getPassword()));
                    textArea.appendText("\n");
                }
            }//end of nested try
            catch(Exception e){
                //
            }//nested catch     
            textArea.appendText("All objects are loaded successfully...\n");            
        } catch (IOException ex) { } 
        finally {
            try {
                if(ois != null) ois.close();
            } catch (IOException ex) { }
        }       
    }
    
}
