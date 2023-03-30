/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkte;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import kkte.Users.Executive;

/**
 * FXML Controller class
 *
 * @author shaji
 */
public class CalculateSalaryPageForHRController implements Initializable {

    @FXML    private TableView<Executive> tableView;
    @FXML    private TableColumn<Executive, Integer> tableViewEmpID;
    @FXML    private TableColumn<Executive, String> tableViewEmpName;
    @FXML    private TableColumn<Executive, String> tableViewEmpDept;
    @FXML    private TableColumn<Executive, Float> tableViewempSalary;
    @FXML    private ComboBox selectMonthComboBox;
    @FXML    private ComboBox selectYearComboBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectMonthComboBox.getItems().addAll("Jan","Feb","Mar","Apr","May", "June","July","Aug","Sept","Oct","Nov","Dec");
        for (int i = 2000 ; i <= 2030; i++){
            String s = Integer.toString(i);
            selectYearComboBox.getItems().add(s);
        }
        selectYearComboBox.setValue("2021");
        
        tableViewEmpID.setCellValueFactory(new PropertyValueFactory<Executive,Integer>("ID"));
        tableViewEmpName.setCellValueFactory(new PropertyValueFactory<Executive, String>("name"));
        tableViewEmpDept.setCellValueFactory(new PropertyValueFactory<Executive, String>("department"));
        tableViewempSalary.setCellValueFactory(new PropertyValueFactory<Executive, Float>("finalSalary"));
        
    }    

    @FXML
    private void goToCheckRollManagementButtonOnClick(ActionEvent event) throws IOException {
        Parent parent;
        parent = FXMLLoader.load(getClass().getResource("checkRollHR.fxml"));
        Scene sc = new Scene(parent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(sc);
        window.show();
    }

    @FXML
    private void logOutButtonOnClick(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Scene sc = new Scene(parent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(sc);
        window.show();        
    }
    //ObservableList<Executive> people = FXCollections.observableArrayList(); 
    @FXML
    private void loadDataButtonOnClick(ActionEvent event) {
        if (selectMonthComboBox.getSelectionModel().isEmpty() || selectYearComboBox.getSelectionModel().isEmpty() ){
            Alert alrt = new Alert(AlertType.ERROR);
            alrt.setContentText("Please Select both month and year!");
            alrt.showAndWait();   
        }
        else {
            File f = null;
            FileInputStream fis = null;
            DataInputStream dis = null;
            String filename = selectMonthComboBox.getValue().toString()+selectYearComboBox.getValue().toString()+"SalaryRecord.bin";
            try{
                f = new File (filename);
                if (!f.exists()) {
                    Alert alrt = new Alert(AlertType.WARNING);
                    alrt.setContentText("No database found for entered Month and Year!");
                    alrt.showAndWait();
                }
                else 
                {
                    fis = new FileInputStream(filename);
                    dis = new DataInputStream(fis);
                    while(true)
                    {
                        int idStr = dis.readInt(); 
                        String nameStr = dis.readUTF();
                        String deptSTR = dis.readUTF();
                        float sal = dis.readFloat();
                        String leavestakenStr = Float.toString(dis.readFloat());
                        String overtimeHStr = Float.toString(dis.readFloat());
                        String bonusPercentStr = Float.toString(dis.readFloat());
                        String bgStr = dis.readUTF();
                        String doJStr = dis.readUTF();
                        String genderStr = dis.readUTF();
                        String advancesStr = dis.readUTF();
                        Float finalSal = dis.readFloat();
                        Executive emp = new Executive(idStr,nameStr,doJStr,bgStr,deptSTR,sal,overtimeHStr,
                                leavestakenStr,bonusPercentStr,genderStr, advancesStr, finalSal);
                        tableView.getItems().add(emp);
                    }
                }
                
            }
            catch(Exception ex){
                
            }
        }
    }

    @FXML
    private void clearTableOnCLick(ActionEvent event) {
        tableView.getItems().clear();
    }
    
}
