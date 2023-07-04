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
import s21.maslynem.model.calculator.Calculator;
import s21.maslynem.model.creditCalculator.CreditCalculator;
import s21.maslynem.model.graphModel.GraphModel;

import java.io.*;
import java.nio.file.Paths;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader calculatorLoader = new FXMLLoader(getClass().getResource("/fxml/calculator_window.fxml"));
        FXMLLoader graphLoader = new FXMLLoader(getClass().getResource("/fxml/graph_window.fxml"));
        FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("/fxml/settings_window.fxml"));
        FXMLLoader creditLoader = new FXMLLoader(getClass().getResource("/fxml/credit_window.fxml"));

        Pane calcPane = calculatorLoader.load();
        SceneController sceneController = new SceneController(new Scene(calcPane));

        sceneController.addPane("Calculator", calcPane);
        sceneController.addPane("Graph", graphLoader.load());
        sceneController.addPane("Settings", settingsLoader.load());
        sceneController.addPane("Credit", creditLoader.load());
        sceneController.addPane("DifferentiatedCreditList", new Pane());
        sceneController.activate("Calculator");

        CalculatorController calculatorController = calculatorLoader.getController();
        Calculator calculator = new Calculator(Paths.get("history/history.txt").toAbsolutePath());

        calculatorController.initModel(calculator);
        calculatorController.initSceneController(sceneController);

        GraphController graphController = graphLoader.getController();
        GraphModel graphModel = new GraphModel(calculator);
        graphController.initModel(graphModel);
        graphController.initScreenController(sceneController);

        CreditController creditController = creditLoader.getController();
        CreditCalculator creditCalculator = new CreditCalculator();
        creditController.initModel(creditCalculator);
        creditController.initScreenController(sceneController);

        stage.setScene(sceneController.getMainScene());
        stage.show();

        stage.setOnCloseRequest(event ->calculatorController.saveHistoryOfModel(Paths.get("history/history.txt").toAbsolutePath()));
        stage.setResizable(false);

    }
}
