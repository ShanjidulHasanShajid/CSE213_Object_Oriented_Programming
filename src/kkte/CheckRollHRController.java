
package kkte;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import kkte.Users.Executive;

/**
 * FXML Controller class
 *
 * @author shaji
 */
public class CheckRollHRController implements Initializable {

    @FXML    private TableView<Executive> tableView;
    @FXML    private TableColumn<Executive, Integer> tableViewID;
    @FXML    private TableColumn<Executive, String> tableViewName;
    @FXML    private TableColumn<Executive, String> tableViewDept;
    @FXML    private TableColumn<Executive, Float> tableViewIDSalary;
    @FXML    private TableColumn<Executive, String> tableViewOvertimeHours;
    @FXML    private TableColumn<Executive, String> tableViewLeavestaken;
    @FXML    private TableColumn<Executive, String> tableViewBonus;
    @FXML    private ComboBox selectMonthComboBox;
    @FXML    private TableColumn<Executive, String> tableViewAdvanceWithdrawn;
    @FXML    private ComboBox selectYearComboBox;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableViewID.setCellValueFactory(new PropertyValueFactory <Executive, Integer>("ID"));
        tableViewName.setCellValueFactory(new PropertyValueFactory <Executive, String>("name"));
        tableViewDept.setCellValueFactory(new PropertyValueFactory <Executive, String>("department"));
        tableViewIDSalary.setCellValueFactory(new PropertyValueFactory <Executive, Float>("salary"));
        tableViewOvertimeHours.setCellValueFactory(new PropertyValueFactory <Executive, String>("overtimeHours"));
        tableViewLeavestaken.setCellValueFactory(new PropertyValueFactory <Executive, String>("leavesTaken"));
        tableViewBonus.setCellValueFactory(new PropertyValueFactory <Executive, String>("bonusPercentage"));
        tableViewAdvanceWithdrawn.setCellValueFactory(new PropertyValueFactory <Executive, String>("advanceWithdrawn"));
        
        tableView.setEditable(true);
        
        tableViewOvertimeHours.setCellFactory(TextFieldTableCell.forTableColumn());
        tableViewBonus.setCellFactory(TextFieldTableCell.forTableColumn());
        tableViewLeavestaken.setCellFactory(TextFieldTableCell.forTableColumn());
        tableViewAdvanceWithdrawn.setCellFactory(TextFieldTableCell.forTableColumn());
        tableView.setItems(getPeople());
        
        selectMonthComboBox.getItems().addAll("Jan","Feb","Mar","Apr","May", "June","July","Aug","Sept","Oct","Nov","Dec");
        
        for (int i = 2000 ; i <= 2030; i++){
            String s = Integer.toString(i);
            selectYearComboBox.getItems().add(s);
        }
        
    }    
    ObservableList<Executive> people = FXCollections.observableArrayList();
    public ObservableList<Executive> getPeople(){
        File f = null;
        FileInputStream fis = null;
        DataInputStream dis = null;
        try 
        {
            f = new File("Employee.bin");
            fis = new FileInputStream(f);
            dis = new DataInputStream(fis);
            while(true)
            {
                int idStr = dis.readInt(); 
                String nameStr = dis.readUTF();
                String genderStr = dis.readUTF();
                String deptSTR = dis.readUTF();
                float sal = dis.readFloat();
                String doJStr = dis.readUTF();
                String bgStr = dis.readUTF();
                String overtimeHStr = "0.0";
                String leavestakenStr = "0";
                String bonusPercentStr = "0.0";
                String advancesStr = "0.0";
                //Float finalSal = 0.0f;
                people.add(new Executive (idStr,nameStr,doJStr,bgStr,deptSTR,sal,overtimeHStr,leavestakenStr,bonusPercentStr,genderStr, advancesStr));
            }
        } 
        catch (Exception ex) {  
        } 
        finally 
        {
            try {
                if(dis != null) dis.close();
            } 
            catch (Exception ex) {}
        }
        return people;
    } 

    @FXML
    private void goToPreviousScreenOnClick(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("userHomePageForHR.fxml"));
        Scene sc;
        sc = new Scene(parent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(sc);
        window.show();
    }
    
    @FXML
    private void saveDataOnClick(ActionEvent event) {
        if (selectYearComboBox.getSelectionModel().isEmpty() || selectMonthComboBox.getSelectionModel().isEmpty()){
            Alert ar = new Alert(Alert.AlertType.WARNING);
            ar.setTitle("Warning!");
            ar.setContentText("Select Month and Year first");
            ar.setHeaderText("Waring!");
            ar.showAndWait();
        }
        else {
            File f = null;
            FileOutputStream fos = null;
            DataOutputStream dos = null;
            String filename = selectMonthComboBox.getValue().toString()+selectYearComboBox.getValue().toString()+"SalaryRecord.bin";

            try {
                f = new File(filename);
                if(f.exists()) fos = new FileOutputStream(f);
                else fos = new FileOutputStream(f);
                dos = new DataOutputStream(fos);
                for (Executive userData : people){
                    //dos.writeUTF(selectMonthComboBox.getValue().toString());
                    //dos.writeUTF(selectYearComboBox.getValue().toString());
                    dos.writeInt(userData.getID());
                    dos.writeUTF(userData.getName());
                    dos.writeUTF(userData.getDepartment());
                    dos.writeFloat(userData.getSalary());
                    dos.writeInt(Integer.parseInt(userData.getLeavesTaken()));
                    dos.writeFloat(Float.parseFloat(userData.getOvertimeHours()));
                    dos.writeFloat(Float.parseFloat(userData.getBonusPercentage()));
                    dos.writeUTF(userData.getBloodGroup());
                    dos.writeUTF(userData.getDateOfJoining());
                    dos.writeUTF(userData.getGender());
                    dos.writeUTF(userData.getAdvanceWithdrawn());
                    userData.setFinalSalary();
                    float final_sal = userData.getFinalSalary();
                    dos.writeFloat(final_sal);
                }            
                Alert ar = new Alert(Alert.AlertType.INFORMATION);
                ar.setTitle("Information");
                ar.setContentText("Data Saved Successfully");
                ar.setHeaderText("Saved!");
                ar.showAndWait();

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
        
        
    }

    @FXML
    private void calculateSalaryButtonOnClick(ActionEvent event) throws IOException {
        Parent secondSceneForHR = FXMLLoader.load(getClass().getResource("calculateSalaryPageForHR.fxml"));
        Scene sc = new Scene(secondSceneForHR);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(sc);
        window.show();
    }

    @FXML
    private void overtimeHoursCellEditAction(CellEditEvent event) {
        Executive personSelected = tableView.getSelectionModel().getSelectedItem();
        personSelected.setOvertimeHours(event.getNewValue().toString());
    }

    @FXML
    private void leavesTakenCellEditAction(CellEditEvent event) {
        Executive personSelected = tableView.getSelectionModel().getSelectedItem();
        personSelected.setLeavesTaken(event.getNewValue().toString());
    }

    @FXML
    private void bonusCellEditAction(CellEditEvent event) {
        Executive personSelected = tableView.getSelectionModel().getSelectedItem();
        personSelected.setBonusPercentage(event.getNewValue().toString());
    }

    @FXML
    private void advanceWithdrawnCellEditAction(CellEditEvent event) {
        Executive personSelected = tableView.getSelectionModel().getSelectedItem();
        personSelected.setAdvanceWithdrawn(event.getNewValue().toString());
    }

    @FXML
    private void logOutButtonOnAction(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Scene sc = new Scene(parent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(sc);
        window.show();        
    }
    
}
