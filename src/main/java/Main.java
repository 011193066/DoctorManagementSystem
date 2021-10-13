package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../fxml/openView.fxml")), 600, 448);
        stage.setTitle("DoctorManagementSystem");
        stage.setScene(scene);
        stage.show();
    }
}
