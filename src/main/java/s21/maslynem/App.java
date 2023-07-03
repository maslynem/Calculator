package s21.maslynem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import s21.maslynem.controllers.CalculatorController;
import s21.maslynem.controllers.CreditController;
import s21.maslynem.controllers.GraphController;
import s21.maslynem.controllers.SceneController;
import s21.maslynem.model.HistoryOfModel;

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
        FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("/fxml/settings_window.fxml"));
        FXMLLoader creditLoader = new FXMLLoader(getClass().getResource("/fxml/credit_window.fxml"));

        SceneController sceneController = new SceneController(new Scene(calcPane));

        sceneController.addPane("Calculator", calcPane);
        sceneController.addPane("Graph", graphLoader.load());
        sceneController.addPane("Settings", settingsLoader.load());
        sceneController.addPane("Credit", creditLoader.load());
        sceneController.activate("Calculator");

        CalculatorController calculatorController = calculatorLoader.getController();
        calculatorController.initModel(Paths.get(getPath() + "/history.txt"));
        calculatorController.initSceneController(sceneController);

        GraphController graphController = graphLoader.getController();
        graphController.initScreenController(sceneController);

        CreditController creditController = creditLoader.getController();
        creditController.initScreenController(sceneController);

        stage.setScene(sceneController.getMainScene());
        stage.show();

        stage.setOnCloseRequest(event ->calculatorController.saveHistoryOfModel(Paths.get(getPath() + "/history.txt")));
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
