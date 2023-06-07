package s21.maslynem.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import s21.maslynem.model.Calculator;
import s21.maslynem.model.GraphModel;

import java.net.URL;
import java.util.ResourceBundle;

public class GraphController implements Initializable {
    @FXML
    private LineChart<Number, Number> lineChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxix;

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

    private ScreenController screenController;

    public void initScreenController(ScreenController screenController) {
        this.screenController = screenController;
    }

    @FXML
    private void onCalculatorWindowClicked() {
        screenController.activate("Calculator");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addMinValueListener(minX);
        addMinValueListener(minY);
        addMaxValueListener(maxX);
        addMaxValueListener(maxY);
        xAxis.setAutoRanging(false);
        yAxix.setAutoRanging(false);
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
            yAxix.setLowerBound(miny);
            yAxix.setUpperBound(maxy);
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            ObservableList<XYChart.Data<Number, Number>> datas = GraphModel.getGraphData(minx,maxx,inputField.getText());
            series.setData(datas);
            lineChart.getData().clear();
            lineChart.getData().add(series);
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
