package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Created by ralfpopescu on 6/7/17.
 */
public class EmployeeEditController {

    private SceneController sceneController;
    State state;

    public void giveSceneController(SceneController sc){
        sceneController = sc;
    }
    public void giveState(State s){
        state = s;
    }

    public void HandleBackButton(){
        sceneController.PayrollScene();;

    }
    public void HandleAddEmployee(){

    }
}
