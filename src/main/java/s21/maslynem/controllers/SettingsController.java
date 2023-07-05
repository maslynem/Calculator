package s21.maslynem.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    private RadioButton day;

    @FXML
    private RadioButton hour;

    @FXML
    private RadioButton month;

    private RadioButton lastSelected;

    private static final Logger LOGGER = LogManager.getLogger(SettingsController.class);

    @FXML
    void onChanged(MouseEvent event) {
            RadioButton radioButton = (RadioButton) event.getSource();
            if (radioButton.equals(hour) && !lastSelected.equals(hour)) {
                rewriteLog4j2Fxml("0 0 * * * ?");
                lastSelected = hour;
            } else if (radioButton.equals(day) && !lastSelected.equals(day)) {
                rewriteLog4j2Fxml("0 0 0 * * ?");
                lastSelected = day;
            } else if (radioButton.equals(month) && !lastSelected.equals(month)) {
                rewriteLog4j2Fxml("0 0 0 1 * ?");
                lastSelected = month;
            }
            Configurator.reconfigure();
    }

    private void rewriteLog4j2Fxml(String newCron) {
        String changeStr = String.format("                <CronTriggeringPolicy schedule=\"%s\"/>", newCron);
        String path = Objects.requireNonNull(getClass().getResource("/log4j2.xml")).getPath();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(path))));
             FileWriter fileWriter = new FileWriter(path)) {
            String strLine;
            while ((strLine = br.readLine()) != null) {
                if (strLine.contains("CronTriggeringPolicy")) {
                    sb.append(changeStr).append("\n");
                } else {
                    sb.append(strLine).append("\n");
                }
            }
            fileWriter.write(sb.toString());
        } catch (IOException exception) {
            LOGGER.error(exception.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lastSelected = hour;
    }
}
