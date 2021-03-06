package adders;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class AdderController {

    private Adder adder;
    private Adder moduloAdder;

    private int first;
    private int second;
    private int residues;
    private int type;

    @FXML
    private Button countbutton;

    @FXML
    private Button closebutton;

    @FXML
    private Button loadfilebutton;

    @FXML
    private TextField variableA;

    @FXML
    private TextField variableB;

    @FXML
    private TextField variableM;

    @FXML
    private TextField variableE1;

    @FXML
    private Label printresult;

    @FXML
    private Label errorlabel;

    @FXML
    private Label inputA;

    @FXML
    private Label inputB;

    @FXML
    public void calculateSum(ActionEvent event) throws IOException {

        File f = new File("src/resources/outputs.txt");
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        if(variableA.getText().trim().equals("") || variableB.getText().trim().equals("") || variableM.getText().trim().equals("")) {
            showAlert("Musisz podać wartości początkowe!");
        }
        else if(variableA.getText().matches("[a-zA-Z]+") || variableB.getText().matches("[a-zA-Z]+") || variableM.getText().matches("[a-zA-Z]+")) {
            showAlert("Pola przyjmują tylko liczby!");
        }
        else {
            first = Math.abs(Integer.parseInt(variableA.getText()));
            second = Math.abs(Integer.parseInt(variableB.getText()));
            residues = Math.abs(Integer.parseInt(variableM.getText()));

            if(type == 1) {
                if(first > 15 || first < 0) {
                    showAlert("Pierwsza wartość jest spoza zakresu!");
                    showErrorSignal();
                }
                if(second > 15 || second < 0) {
                    showAlert("Druga wartość jest spoza zakresu!");
                    showErrorSignal();
                }
            }
            else if(type == 2) {
                if(first > 255 || first < 0) {
                    showAlert("Pierwsza wartość jest spoza zakresu!");
                    showErrorSignal();
                }
                if(second > 255 || second < 0) {
                    showAlert("Druga wartość jest spoza zakresu!");
                    showErrorSignal();
                }
            }
        }

        if(!variableE1.getText().isEmpty()) {
            int err = Integer.parseInt(variableE1.getText());
            adder = createAdderInstance(type, first, second, err);
            moduloAdder = createAdderInstance(type, first % residues, second % residues, residues);
        } else {
            adder = createAdderInstance(type, first, second, residues);
            moduloAdder = createAdderInstance(type, first % residues, second % residues, residues);
        }

        int LHS = adder.runAdder();
        int RHS = moduloAdder.runAdder();

        printresult.setVisible(false);
        errorlabel.setVisible(false);
        inputA.setVisible(false);
        inputB.setVisible(false);

        if(LHS == RHS) {
            adder.printResult();
            printresult.setText(adder.printResultForController());
            printresult.setVisible(true);
            inputA.setText(adder.printInputA());
            inputA.setVisible(true);
            inputB.setText(adder.printInputB());
            inputB.setVisible(true);
            if(f.exists() && !f.isDirectory()) {
                Files.write(Paths.get("src/resources/outputs.txt"), Arrays.asList("Raport: " + adder.printFullResultForController() + "\n"), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            } else {
                Files.write(Paths.get("src/resources/outputs.txt"), Arrays.asList(timeStamp +  System.lineSeparator() + "Raport: " + adder.printFullResultForController() + "\n"), StandardCharsets.UTF_8);
            }
        }
        else {
            showErrorSignal();
        }
    }

    @FXML
    public void closeProgram(ActionEvent event) {
        Stage stage = (Stage) closebutton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void getInputsFromFile(ActionEvent event) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("src/resources/inputs.txt")));
        String[] splitValues = content.split("\n");
        String first = splitValues[0];
        String second = splitValues[1];
        String residues = splitValues[2];
        variableA.appendText(first);
        variableB.appendText(second);
        variableM.appendText(residues);
    }

    public static Adder createAdderInstance(int type, int first, int second, int residues) {
        if(type == 1) return new Adder(4, first, second, residues);
        else if(type == 2) return new Adder(8, first, second, residues);
        else System.out.println("Error: Niedostępna opcja, wyłączam program!");
        return null;
    }

    public void showAlert(String msg) {
        Alert alertInput = new Alert(Alert.AlertType.WARNING);
        alertInput.setTitle("Adder: Warning");
        alertInput.setHeaderText(null);
        alertInput.setContentText(msg);
        alertInput.showAndWait();
    }

    public void showErrorSignal() {
        errorlabel.setVisible(true);
        printresult.setText("");
        inputA.setText("");
        inputB.setText("");
        System.out.println("Adder: ERROR SIGNAL!");
    }

    public void setType(int type) {
        this.type = type;
    }
}
