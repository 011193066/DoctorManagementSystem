package main.java;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.java.objects.Gender;
import main.java.objects.Patient;
import main.utils.SerialGenerator;
import main.utils.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class FormViewController implements Initializable {

    @FXML
    public ImageView imageView;
    @FXML
    public Label upload;
    @FXML
    public TextField nameField;
    @FXML
    public TextField ageField;
    @FXML
    public ComboBox comboBox;
    @FXML
    public TextField phoneField;
    @FXML
    public DatePicker datePicker;
    @FXML
    public TextArea historyField;
    @FXML
    public Text dr_src;

    @FXML
    public void select() {
        String selected = (String) comboBox.getSelectionModel().getSelectedItem();
        comboBox.setPromptText(selected);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("src/main/img/patient.jpg");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);

        ObservableList<String> list = FXCollections.observableArrayList("Male", "Female");
        comboBox.setItems(list);
    }

    public void uploadPhoto(MouseEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose Image");

        String userDirectory = System.getProperty("user.home") + "\\Pictures";
        File directory = new File(userDirectory);
        chooser.setInitialDirectory(directory);
        File filepath = chooser.showOpenDialog(stage);

        BufferedImage bufferedImage = ImageIO.read(filepath);
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);

        imageView.setImage(image);

        upload.setText("");
    }

    public void onSAVE(MouseEvent event) throws Exception {

        Patient patient = new Patient(
                nameField.getText(),                                // patient name
                Integer.parseInt(ageField.getText()),               // patient age
                phoneField.getText(),                               // phone number of patient
                Gender.getGender(comboBox.getValue().toString()),   // patient gender {Look at main.java.objects.Gender}
                datePicker.getValue(),                              // date value chose
                historyField.getText()                              // patient history and complications
        );


        int serial = getSerial(patient);

        Stage serialNumberWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene completionScreen = new Scene(FXMLLoader.load(getClass().getResource("../fxml/serialNumber.fxml")));

        serialNumberWindow.setScene(completionScreen);
        TextField patientName = (TextField) completionScreen.lookup("#patientNameField");
        TextField serialNum = (TextField) completionScreen.lookup("#serialNumField");
        TextField dr_nameField = (TextField) completionScreen.lookup("#doctorNameField");
        TextField chamberField = (TextField) completionScreen.lookup("#chamberField");

        patientName.setText(patient.name);
        serialNum.setText(String.valueOf(serial));
        dr_nameField.setText(Utils.getButtonSource(dr_src.getText()));
        chamberField.setText(getChamber(dr_nameField.getText()));
    }


    private int getSerial(Patient patient) {
        int serial = Integer.MIN_VALUE;
        try {
            Socket socket = new Socket("127.0.0.1", 8080);

            ObjectOutputStream patientWriter = new ObjectOutputStream(socket.getOutputStream());
            patientWriter.writeObject(patient);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serial = reader.read();

            patientWriter.close();
            reader.close();
            socket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return serial;
    }

    public String getChamber(String docName) {
        File file = new File(Utils.doctorFile(docName));
        String chamber = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String fileIn;
            while ((fileIn = bufferedReader.readLine()) != null) {
                if (fileIn.startsWith("Chamber: ")) {
                    chamber = fileIn.substring(8);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return chamber;
    }
}
