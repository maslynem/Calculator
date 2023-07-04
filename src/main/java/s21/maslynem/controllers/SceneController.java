package s21.maslynem.controllers;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;

public class SceneController {
    private HashMap<String, Pane> paneMap = new HashMap<>();
    private HashMap<String, Scene> sceneMap = new HashMap<>();
    private Scene main;

    public SceneController(Scene main) {
        this.main = main;
    }

    public void addPane(String name, Pane pane) {
        paneMap.put(name, pane);
    }

    public void removePane(String name) {
        paneMap.remove(name);
    }

    public Scene getMainScene() {
        return main;
    }

    public Stage getModalityStage(String name) {
        Scene scene = sceneMap.computeIfAbsent(name, value -> new Scene(paneMap.get(name)));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(name);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(main.getWindow());
        stage.setResizable(false);
        return stage;
    }

    public Pane getPaneByName(String name) {
        return paneMap.get(name);
    }

    public void activate(String name) {
        main.setRoot(paneMap.get(name));
    }
}
