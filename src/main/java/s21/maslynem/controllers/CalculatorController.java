package s21.maslynem.controllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import s21.maslynem.model.Calculator;
import s21.maslynem.model.DataModel;
import s21.maslynem.model.WrongExpressionException;

import java.net.URL;
import java.util.EmptyStackException;
import java.util.ResourceBundle;

public class CalculatorController implements Initializable {
    @FXML
    private TextField inputField;

    @FXML
    private TableView<String> historyTable;

    @FXML
    private TableColumn<String, String> history;

    private DataModel dataModel;

    private SceneController sceneController;

    private static final Logger LOGGER = LogManager.getLogger(CalculatorController.class);

    @FXML
    private void onOperandClicked(MouseEvent event) {
        Button button = (Button) event.getSource();
        String operand = button.getText();
        if (operand.equals("mod")) {
            operand = "%";
        } else if (operand.equals("√")) {
            operand += "(";
        }
        inputField.setText(inputField.getText() + operand);
    }

    @FXML
    void onRefreshClicked() {
        inputField.setText("");
    }

    @FXML
    void onCalculateClicked() {
        double result;
        String inputText = inputField.getText();
        if (!inputText.isEmpty()) {
            try {
                result = Calculator.calculate(inputText);
                dataModel.addNewData(inputText + "=" + result);
                LOGGER.info(inputText + "=" + result);
                inputField.setText(String.valueOf(result));
            } catch (WrongExpressionException | EmptyStackException exception) {
                inputField.setText(exception.getMessage());
                LOGGER.error(inputText + " : " + exception.getMessage());
            }
        }
    }

    @FXML
    void onHistoryClicked(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            TablePosition<?, ?> pos = historyTable.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = historyTable.getItems().get(index);
            selected = selected.substring(0, selected.indexOf("="));
            inputField.setText(selected);
        }
    }

    @FXML
    void onSettingsClicked() {
        Stage stage = sceneController.getModalityStage("Settings");
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        history.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
    }

    public void initModel(DataModel dataModel) {
        this.dataModel = dataModel;
        historyTable.setItems(dataModel.getHistory());
    }

    public void initScreenController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    @FXML
    void onClearHistoryClicked() {
        dataModel.clearHistory();
    }

    @FXML
    void onGraphWindowClicked() {
        sceneController.activate("Graph");
    }
    @FXML
    void onCreditWindowClicked() {
        sceneController.activate("Credit");
    }
}
