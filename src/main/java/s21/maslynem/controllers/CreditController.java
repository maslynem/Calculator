package s21.maslynem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class CreditController implements Initializable {

    @FXML
    private TextField allSum;

    @FXML
    private RadioButton annuity;

    @FXML
    private Button calcButton;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private Spinner<Integer> creditTerm;

    @FXML
    private ToggleGroup creditType;

    @FXML
    private RadioButton differentiated;

    @FXML
    private TextField monthlyPayment;

    @FXML
    private TextField percentages;

    @FXML
    private Spinner<Double> rate;

    @FXML
    private Button showButton;

    @FXML
    private Spinner<Integer> sumSpinner;

    private SceneController sceneController;

    public void initScreenController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    @FXML
    void onCalculatorWindowClicked() {
        sceneController.activate("Calculator");
    }

    @FXML
    void onGraphClicked() {
        sceneController.activate("Graph");
    }
    @FXML
    void onSettingsClicked() {
        Stage stage = sceneController.getModalityStage("Settings");
        stage.show();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sumSpinner.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                long value = Long.parseLong(newValue);
                if (value > 1_000_000_000) {
                    sumSpinner.getEditor().setText("1000000000");
                } else if (value < 1000) {
                    sumSpinner.getEditor().setText("1000");
                }
            } catch (NumberFormatException exception) {
                sumSpinner.getEditor().setText(oldValue);
            }
        });

        creditTerm.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int value = Integer.parseInt(newValue);
                if (value > 100) {
                    creditTerm.getEditor().setText("100");
                } else if (value < 1) {
                    creditTerm.getEditor().setText("1");
                }
            } catch (NumberFormatException exception) {
                creditTerm.getEditor().setText(oldValue);
            }
        });

        rate.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double value = Double.parseDouble(newValue);
                if (value >= 100 || value <= 0.1) {
                    rate.getEditor().setText(oldValue);
                }
            } catch (NumberFormatException exception) {
                rate.getEditor().setText(oldValue);
            }
        });

        choiceBox.getItems().addAll("Лет" , "Мес.");
        choiceBox.setValue("Лет");

    }
}