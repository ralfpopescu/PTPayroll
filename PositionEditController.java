package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by ralfpopescu on 7/31/17.
 */
public class PositionEditController {
    @FXML ListView<String> PositionList;
    @FXML TextField PositionText;
    @FXML TextField CodeText;

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
    public void HandleAddPosition(){
        String name = PositionText.getCharacters().toString();
        String num = CodeText.getCharacters().toString();
        String line = num + "\t" + name;
        System.out.println(name + num);
        ObservableList<String> items = PositionList.getItems();
        items.add(line);
        PositionList.setItems(items);
        updateTextFile();
    }

    public void HandleDeletePosition(){
        int selectedIdx = PositionList.getSelectionModel().getSelectedIndex();
        PositionList.getItems().remove(selectedIdx);

    }

    public void populateList(){
        FileHandler fileHandler = new FileHandler();
        HashMap<String, String> divisionCodes = fileHandler.getDivisionCodes();
        Set<String> keyList = divisionCodes.keySet();
        Set<String> namePlusKey = new TreeSet<String>();
        for(String name: keyList){
            String code = divisionCodes.get(name);
            String item = code + "\t" + name;
            namePlusKey.add(item);
        }
        ObservableList<String> items = FXCollections.observableArrayList(
                namePlusKey);
        PositionList.setItems(items);
        PositionList.setEditable(true);
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
        ObservableList<String> items = PositionList.getItems();
        fileHandler.writeToPositionFile(items);
    }
}

