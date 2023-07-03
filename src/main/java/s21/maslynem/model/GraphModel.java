package s21.maslynem.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class GraphModel {
    private Calculator calculator;

     public GraphModel() {
         calculator = new Calculator();
    }

    public ObservableList<XYChart.Data<Number, Number>> getGraphData(int minX, int maxX, String expression) {
        ObservableList<XYChart.Data<Number, Number>> data = FXCollections.observableArrayList();
        double h = (maxX - minX) / 10000.;
        for (double i = minX; i <= maxX; i += h) {
            try {
                double value = calculator.calculate(expression.replace("x", "(" + i + ")"), false);
                data.add(new XYChart.Data<>(i, value));
            } catch (Exception ignored) {
            }
        }
        return data;
    }
}
