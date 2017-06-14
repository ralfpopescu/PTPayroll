package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FileHandler fileHandler = new FileHandler();
        fileHandler.csvToXLSX();

        State state = new State();
        state.setEmpKeys(fileHandler.getEmpKeys());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("PayrollPage.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Park Tavern Payroll");
        Scene payroll = new Scene(root, 700, 600);
        primaryStage.setScene(payroll);
        primaryStage.show();

        SceneController sceneController = new SceneController(primaryStage, state);

        PayrollController payrollController = loader.getController();
        payrollController.giveSceneController(sceneController);
        payrollController.setState(state);
        sceneController.setPayrollController(payrollController, payroll);


        //fileHandler.alphabetizeEmployees();

//        SheetCreator sheetCreator = new SheetCreator();
//        sheetCreator.makeSheet();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
