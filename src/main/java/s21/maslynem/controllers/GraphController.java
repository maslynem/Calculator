package s21.maslynem.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import s21.maslynem.model.graphModel.GraphModel;

import java.net.URL;
import java.util.ResourceBundle;

public class GraphController implements Initializable {
    @FXML
    private ScatterChart<Number, Number> scatterChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private TextField inputField;

    @FXML
    private TextField maxX;

    @FXML
    private TextField maxY;

    @FXML
    private TextField minX;

    @FXML
    private TextField minY;

    private GraphModel graphModel;

    private SceneController sceneController;

    private static final Logger LOGGER = LogManager.getLogger(GraphController.class);

    public void initScreenController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    @FXML
    private void onCalculatorWindowClicked() {
        sceneController.activate("Calculator");
    }

    @FXML
    private void onSettingsClicked() {
        Stage stage = sceneController.getModalityStage("Settings");
        stage.show();
    }
    @FXML
    private void onCreditClicked() {
        sceneController.activate("Credit");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addMinValueListener(minX);
        addMinValueListener(minY);
        addMaxValueListener(maxX);
        addMaxValueListener(maxY);
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);
    }

    public void initModel(GraphModel graphModel) {
        this.graphModel = graphModel;
    }

    @FXML
    private void onEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            int minx = minX.getText().isEmpty() ? -50 : Integer.parseInt(minX.getText());
            int maxx = maxX.getText().isEmpty() ? 50 : Integer.parseInt(maxX.getText());
            int miny = minY.getText().isEmpty() ? -50 : Integer.parseInt(minY.getText());
            int maxy = maxY.getText().isEmpty() ? 50 : Integer.parseInt(maxY.getText());
            xAxis.setLowerBound(minx);
            xAxis.setUpperBound(maxx);
            xAxis.setTickUnit((maxx - minx) / 10.);
            yAxis.setLowerBound(miny);
            yAxis.setUpperBound(maxy);
            yAxis.setTickUnit((maxy - miny) / 10.);

            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            String expression = inputField.getText();
            ObservableList<XYChart.Data<Number, Number>> dataList = graphModel.getGraphData(minx, maxx, inputField.getText());
            if (dataList.isEmpty()) {
                LOGGER.error("Wrong expression: " + expression);
                inputField.setText("Wrong expression: " + expression);
            } else {
                LOGGER.info(expression);
                series.setData(dataList);
                scatterChart.getData().clear();
                scatterChart.getData().add(series);
            }
        }
    }


    private void addMinValueListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.isEmpty() && newValue.equals("-")) {
                return;
            }
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.startsWith("-") ? "-" + newValue.replaceAll("\\D", "") : newValue.replaceAll("\\D", ""));
            }
            if (!textField.getText().isEmpty() && !textField.getText().equals("-")) {
                if (Integer.parseInt(textField.getText()) < -1_000_000) {
                    textField.setText("-1000000");
                } else if (Integer.parseInt(textField.getText()) > 0) {
                    textField.setText("0");
                }
            }
        });
    }

    private void addMaxValueListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("\\D", ""));
            }
            if (!textField.getText().isEmpty()) {
                if (Integer.parseInt(textField.getText()) > 1_000_000) {
                    textField.setText("1000000");
                } else if (Integer.parseInt(textField.getText()) < 0) {
                    textField.setText("0");
                }
            }
        });
    }
}
