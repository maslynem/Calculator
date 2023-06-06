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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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
        try (InputStream inputStream = Files.newInputStream(Paths.get(getPath() + "/history.txt"), StandardOpenOption.CREATE);
             ObjectInputStream is = new ObjectInputStream(inputStream)
        ) {
            DataModel dataModel = (DataModel) is.readObject();
            return dataModel == null ? new DataModel() : dataModel;
        } catch (IOException | ClassNotFoundException e) {
            return new DataModel();
        }
    }

    private void saveDataModel(DataModel dataModel) {
        try (OutputStream fileOutputStream = Files.newOutputStream(Paths.get(getPath() + "/history.txt"));
             ObjectOutputStream os = new ObjectOutputStream(fileOutputStream)) {
            System.out.println(getPath() + "/history.txt");
            os.writeObject(dataModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPath() {
        try {
            return new File(MainWindow.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI()).getParent();
        } catch (URISyntaxException ignored) {
            return "";
        }
    }
}
