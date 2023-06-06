package s21.maslynem.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class DataModel implements Serializable {
    private ObservableList<String> history = FXCollections.observableArrayList();

    public ObservableList<String> getHistory() {
        return history;
    }

    public void addNewData(String string) {
        history.add(string);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(new ArrayList<>(history));
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        history = FXCollections.observableArrayList((ArrayList<String>)in.readObject());
    }

}
