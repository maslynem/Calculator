package s21.maslynem.model.graphModel;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import s21.maslynem.model.calculator.Calculator;


class GraphModelTest {
    private final GraphModel graphModel = new GraphModel(new Calculator());

    @Test
    void GraphModel_GetGraphDataFromSinX_SizeShouldBe10000() {
        ObservableList<XYChart.Data<Number, Number>> graphData = graphModel.getGraphData(-30, 30, "sin(x)");
        Assertions.assertEquals(10000, graphData.size());
    }

    @Test
    void GraphModel_GetGraphDataFromSqrtX_SizeShouldBe5000() {
        ObservableList<XYChart.Data<Number, Number>> graphData = graphModel.getGraphData(-30, 30, "sqrt(x)");
        Assertions.assertEquals(5000, graphData.size());
    }

    @Test
    void GraphModel_GetGraphDataFromIncorrectData_SizeShouldBe0() {
        ObservableList<XYChart.Data<Number, Number>> graphData = graphModel.getGraphData(-30, 30, "ERROR");
        Assertions.assertEquals(0, graphData.size());
    }

}