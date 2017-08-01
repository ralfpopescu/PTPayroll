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
        ObservableList<String> items = PositionList.getItems();
        items.add(line);
        PositionList.setItems(items);
        updateTextFile();
    }

    public void HandleDeletePosition(){
        int selectedIdx = PositionList.getSelectionModel().getSelectedIndex();
        PositionList.getItems().remove(selectedIdx);
        updateTextFile();

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


    public void updateTextFile(){
        FileHandler fileHandler = new FileHandler();
        ListView<String> emptyList = new ListView<String>();
        ObservableList<String> items = PositionList.getItems();
        ObservableList<String> modifiedItems = emptyList.getItems();

        System.out.println("ay" + modifiedItems);

        for(int j = 0; j < items.size(); j++){
            String item = items.get(j);
            String[] split = item.split("\\s");
            String modifiedItem = "";
            for (int i = 1; i < split.length; i++){
                modifiedItem += split[i];
                if(split.length > 2 && i < split.length - 1){
                    modifiedItem += " ";
                }
            }
            modifiedItem += "," + split[0];
            modifiedItems.add(modifiedItem);

        }

        fileHandler.writeToPositionFile(modifiedItems);
    }
}

