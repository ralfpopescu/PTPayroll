package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;

public class PayrollController {

    SceneController sceneController;
    SheetCreator sheetCreator;
    int test = 0;
    State state;

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
        state.setAlohaFile(file);

    }
    public void HandleUploadWhenIWork(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(sceneController.getStage());
        state.setWhenIWorkFile(file);

    }
    public void HandleAddEditEmployees(){

        sceneController.EmployeeScene();
        test();


    }
    public void HandleAddEditPositions(){

    }
    public void HandleMakePayroll(){
        state.getSheetCreator().makeSheet();
    }

    public void test(){
        test += 1;
        System.out.println(test);
    }
}
