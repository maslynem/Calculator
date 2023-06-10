package s21.maslynem.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class GraphModel {
    private GraphModel() {
    }

    public static ObservableList<XYChart.Data<Number, Number>> getGraphData(int minX, int maxX, String expression) {
        ObservableList<XYChart.Data<Number, Number>> data = FXCollections.observableArrayList();
        double h = (maxX - minX) / 10000.;
        for (double i = minX; i <= maxX; i += h) {
            try {
                double value = Calculator.calculate(expression.replaceAll("x", "(" + i + ")"));
                data.add(new XYChart.Data<>(i, value));
            } catch (Exception ignored) {
            }
        }
        return data;
    }
}
