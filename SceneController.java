package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by ralfpopescu on 6/7/17.
 */
public class SceneController {

    Stage primaryStage;
    PayrollController payrollController;
    Scene payrollScene;
    State state;
    EmployeeEditController employeeEditController;

    public SceneController(Stage ps, State s){
        primaryStage = ps;
        state = s;
    }

    public void PayrollScene(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PayrollPage.fxml"));
            loader.setRoot(loader.getRoot());
            Parent root = loader.load();

            PayrollController controller = loader.getController();
            controller.giveSceneController(this);
            controller.setState(state);

            primaryStage.getScene().setRoot(root);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void EmployeeScene(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeEditPage.fxml"));
            loader.setRoot(loader.getRoot());
            Parent root = loader.load();

            EmployeeEditController controller = loader.getController();
            controller.giveSceneController(this);
            controller.giveState(state);
            controller.populateList();

            primaryStage.getScene().setRoot(root);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void PositionScene(){
        try {
            Scene scene = FXMLLoader.load(getClass().getResource("PayrollPage.fxml"));
            primaryStage.setScene(scene);
        } catch (Exception e){

        }
    }

    public void setPayrollController(PayrollController pc, Scene pr){
        payrollController = pc;
        payrollScene = pr;
    }

    public Stage getStage(){
        return primaryStage;
    }

    public void setEmployeeEditController(EmployeeEditController ec){
        employeeEditController = ec;
    }

}
