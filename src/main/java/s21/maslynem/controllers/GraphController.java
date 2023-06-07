package s21.maslynem.controllers;

import javafx.fxml.FXML;

public class GraphController {
    private ScreenController screenController;

    public void initScreenController(ScreenController screenController) {
        this.screenController = screenController;
    }

    @FXML
    void onCalculatorWindowClicked() {
        screenController.activate("Calculator");
    }
}
