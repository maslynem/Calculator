package s21.maslynem.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class DataModel implements Serializable {
    private ObservableList<String> history = FXCollections.observableArrayList();

    public ObservableList<String> getHistory() {
        return history;
    }

    public void addNewData(String string) {
        history.add(string);
    }

    public void clearHistory() {
        history.clear();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(new ArrayList<>(history));
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        history = FXCollections.observableArrayList((ArrayList<String>) in.readObject());
    }

    public void tryToLoadDataFromFile(Path path) {
        try (InputStream inputStream = Files.newInputStream(path, StandardOpenOption.CREATE);
             ObjectInputStream is = new ObjectInputStream(inputStream)
        ) {
            DataModel dataModel = (DataModel) is.readObject();
            this.history = dataModel.history;
        } catch (IOException | ClassNotFoundException ignored) {
        }
    }

    public void saveDataToFile(Path path) {
        try (OutputStream fileOutputStream = Files.newOutputStream(path);
             ObjectOutputStream os = new ObjectOutputStream(fileOutputStream)) {
            os.writeObject(this);
        } catch (IOException ignored) {
        }
    }
}
