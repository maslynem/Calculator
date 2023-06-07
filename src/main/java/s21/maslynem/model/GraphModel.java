package s21.maslynem.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class GraphModel {
    private GraphModel() {}

    public static ObservableList<XYChart.Data<Number, Number>> getGraphData(int minX, int maxX, String expression) {
        ObservableList<XYChart.Data<Number, Number>> data = FXCollections.observableArrayList();
        double h = (Math.abs(minX) + Math.abs(maxX)) / 1000.;
        for (double i = minX; i <= maxX; i += h) {
            data.add(new XYChart.Data<>(i, Calculator.calculate(expression.replaceAll("x", "("+i+")"))));
        }
        return data;
    }
}
