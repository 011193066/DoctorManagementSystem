package main.java;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.utils.Utils;


public class Controller {
    public void onclick(MouseEvent event) throws Exception {
        String dr = ((Node) event.getSource()).getId();
        Utils.setButtonSource(dr);
        Stage mainWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainWindow.setScene(new Scene(FXMLLoader.load(getClass().getResource("../fxml/doctorProfile.fxml"))));
    }
}
