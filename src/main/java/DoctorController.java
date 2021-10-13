package main.java;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.utils.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class DoctorController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField chamberField;
    @FXML
    private TextField counselingField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField docTypeField;
    @FXML
    private Button fillUpButton;

    private String dr_file;

    @FXML
    public void initialize() throws Exception {
        dr_file = Utils.getDBFile();
        BufferedReader reader = new BufferedReader(new FileReader(dr_file));
        ArrayList<String> list = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] arrays = line.split(":");
            list.addAll(Arrays.asList(arrays));
        }

        nameField.setText(list.get(1));
        chamberField.setText(list.get(3));
        counselingField.setText(list.get(5));
        docTypeField.setText(list.get(7));
        phoneField.setText(list.get(9));
    }

    public void onForm(MouseEvent event) throws Exception {
        Stage mainWindows = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainWindows.setScene(new Scene(FXMLLoader.load(getClass().getResource("../fxml/formView.fxml"))));
        ((Text) mainWindows.getScene().lookup("#dr_src")).setText(dr_file);
    }
}
