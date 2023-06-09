package s21.maslynem.controllers;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class SceneController {
    private HashMap<String, Pane> paneMap = new HashMap<>();
    private HashMap<String, Scene> sceneMap = new HashMap<>();
    private Scene main;

    public SceneController(Scene main) {
        this.main = main;
    }

    public void addPane(String name, Pane pane){
        paneMap.put(name, pane);
    }

    public void removePane(String name){
        paneMap.remove(name);
    }

    public Scene getMainScene() {
        return main;
    }

    public Scene getModalityScene(String name) {
        Scene scene = sceneMap.get(name);
        if (scene == null) {
            scene = new Scene(paneMap.get(name));
            sceneMap.put(name, scene);
        }
        return scene;
    }

    public Pane getPaneByName(String name) {
        return paneMap.get(name);
    }

    public void activate(String name){
        main.setRoot( paneMap.get(name) );
    }
}
