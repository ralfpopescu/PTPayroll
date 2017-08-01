package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;

public class PayrollController {

    SceneController sceneController;
    SheetCreator sheetCreator;
    int test = 0;
    State state;
    @FXML private TextField AlohaText;
    @FXML private TextField WhenIWorkText;

    public PayrollController(){

    }

    public void giveSceneController(SceneController sc){
        sceneController = sc;
        System.out.println("sup");
    }

    public void setState(State s){
        state = s;
    }

    public void HandleUploadAloha(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(sceneController.getStage());

        if(file != null) {
            FileHandler fh = new FileHandler();
            fh.csvToXLSX(file);
            state.setAlohaFile(file);
            AlohaText.setText(file.getName());
        }

    }
    public void HandleUploadWhenIWork(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(sceneController.getStage());
        if(file != null) {
            state.setWhenIWorkFile(file);
            WhenIWorkText.setText(file.getName());
        }

    }
    public void HandleAddEditEmployees(){

        sceneController.EmployeeScene();
        test();


    }
    public void HandleAddEditPositions(){
        sceneController.PositionScene();

    }
    public void HandleMakePayroll(){
        state.getSheetCreator().makeSheet();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Success!");
        alert.setContentText("Payroll made.");

        alert.showAndWait();
    }

    public void test(){
        test += 1;
        System.out.println(test);
    }
}
