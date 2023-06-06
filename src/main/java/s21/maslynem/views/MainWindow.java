package s21.maslynem.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import s21.maslynem.controllers.MainWindowController;
import s21.maslynem.model.DataModel;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class MainWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main_window.fxml"));
        Parent rootNode = loader.load();
        MainWindowController mainWindowController = loader.getController();

        DataModel dataModel = loadDataModel();
        mainWindowController.initModel(dataModel);

        stage.setOnCloseRequest(event -> saveDataModel(dataModel));

        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.show();

    }

    private DataModel loadDataModel() {
        try (FileInputStream fileInputStream = new FileInputStream(Paths.get(getClass().getResource("/history.txt").toURI()).toFile());
             ObjectInputStream is = new ObjectInputStream(fileInputStream)
        ) {
            DataModel dataModel = (DataModel) is.readObject();
            return dataModel == null ? new DataModel() : dataModel;
        } catch (IOException | URISyntaxException | ClassNotFoundException e) {
            return new DataModel();
        }
    }

    private void saveDataModel(DataModel dataModel) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(Paths.get(getClass().getResource("/history.txt").toURI()).toFile());
             ObjectOutputStream is = new ObjectOutputStream(fileOutputStream)
        ) {
            is.writeObject(dataModel);
        } catch (IOException | URISyntaxException ignored) {
        }
    }
}
