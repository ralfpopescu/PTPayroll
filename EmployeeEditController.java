package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.fxml.FXML;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.TextField;
import java.util.HashMap;

/**
 * Created by ralfpopescu on 6/7/17.
 */
public class EmployeeEditController {

    @FXML ListView<String> EmployeeList;
    @FXML TextField NameText;
    @FXML TextField KeyText;

    private SceneController sceneController;
    State state;

    public void giveSceneController(SceneController sc){
        sceneController = sc;
    }
    public void giveState(State s){
        state = s;
    }

    public void HandleBackButton(){
        sceneController.PayrollScene();

    }
    public void HandleAddEmployee(){
        String name = NameText.getCharacters().toString();
        String num = KeyText.getCharacters().toString();
        System.out.println(name + num);
    }

    public void HandleDeleteEmployee(){
        int selectedIdx = EmployeeList.getSelectionModel().getSelectedIndex();
        EmployeeList.getItems().remove(selectedIdx);
    }

    public void populateList(){
        FileHandler fileHandler = new FileHandler();
        HashMap<String, Integer> empKeys = fileHandler.getEmpKeys();
        ObservableList<String> items =FXCollections.observableArrayList (
                empKeys.keySet());
        EmployeeList.setItems(items);
        EmployeeList.setEditable(true);
    }
}
