package s21.maslynem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import s21.maslynem.controllers.CalculatorController;
import s21.maslynem.controllers.GraphController;
import s21.maslynem.controllers.ScreenController;
import s21.maslynem.model.DataModel;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader calculatorLoader = new FXMLLoader(getClass().getResource("/fxml/calculator_window.fxml"));
        Pane calcPane = calculatorLoader.load();
        FXMLLoader graphLoader = new FXMLLoader(getClass().getResource("/fxml/graph_window.fxml"));

        ScreenController screenController = new ScreenController(new Scene(calcPane));

        screenController.addScreen("Calculator", calcPane);
        screenController.addScreen("Graph", graphLoader.load());
        screenController.activate("Calculator");

        CalculatorController calculatorController = calculatorLoader.getController();
        DataModel dataModel = new DataModel();
        dataModel.tryToLoadDataFromFile(Paths.get(getPath() + "/history.txt"));
        calculatorController.initModel(dataModel);
        calculatorController.initScreenController(screenController);

        GraphController graphController = graphLoader.getController();
        graphController.initScreenController(screenController);



        stage.setScene(screenController.getScene());
        stage.show();

        stage.setOnCloseRequest(event -> dataModel.saveDataToFile(Paths.get(getPath() + "/history.txt")));
        stage.setResizable(false);

    }

    private String getPath() {
        try {
            return new File(App.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI()).getParent();
        } catch (URISyntaxException ignored) {
            return "";
        }
    }
}
