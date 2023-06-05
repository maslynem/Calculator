package s21.maslynem.controllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import s21.maslynem.model.Calculator;
import s21.maslynem.model.WrongExpressionException;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML
    private TextField inputField;

    @FXML
    private TableView<String> historyTable;

    @FXML
    private TableColumn<String, String> history;

    private final ObservableList<String> strings = FXCollections.observableArrayList();
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
        try {
            result = Calculator.calculate(inputField.getText());
            strings.add(inputField.getText() + "=" + result);
            inputField.setText(String.valueOf(result));
        } catch (WrongExpressionException exception) {
            inputField.setText("Wrong Expression");
        }
    }

    @FXML
    void onHistoryClicked(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
            TablePosition<?,?> pos = historyTable.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = historyTable.getItems().get(index);
            selected = selected.substring(0, selected.indexOf("="));
            inputField.setText(selected);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        history.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        historyTable.setItems(strings);

    }
}
