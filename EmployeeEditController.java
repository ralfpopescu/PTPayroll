package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.fxml.FXML;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.TextField;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

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
        Set<String> keyList = empKeys.keySet();
        Set<String> namePlusKey = new TreeSet<String>();
        for(String name: keyList){
            Integer key = empKeys.get(name);
            String item = name + tabSpace(name) + key;
            namePlusKey.add(item);
        }
        ObservableList<String> items =FXCollections.observableArrayList(
                namePlusKey);
        EmployeeList.setItems(items);
        EmployeeList.setEditable(true);
    }

    public String tabSpace(String name){
        if(name.length() < 16) {
            return "\t \t \t \t";
        } else if(name.length() < 22){
            return "\t \t \t";
        } else if(name.length() < 28){
            return "\t \t";
        } else {
            return "\t";
        }
    }


    public void updateTextFile(){
        FileHandler fileHandler = new FileHandler();
    }
}
