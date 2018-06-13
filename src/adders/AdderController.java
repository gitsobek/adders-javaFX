package adders;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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

    public static Adder createAdderInstance(int type, int first, int second, int residues) {
        if(type == 1) return new Adder(4, first, second, residues);
        else if(type == 2) return new Adder(8, first, second, residues);
        else System.out.println("Error: Niedostępna opcja, wyłączam program!");
        return null;
    }

    @FXML
    public void calculateSum(ActionEvent event) throws IOException {

        if(variableA.getText().trim().equals("") || variableB.getText().trim().equals("") || variableM.getText().trim().equals("")) {
            showAlert("Musisz podać wartości początkowe!");
        } else {
            first = Integer.parseInt(variableA.getText());
            second = Integer.parseInt(variableB.getText());
            residues = Integer.parseInt(variableM.getText());

            if(type == 1) {
                if(first > 16) showAlert("Pierwsza wartość jest spoza zakresu!");
                if(second > 16) showAlert("Druga wartość jest spoza zakresu!");
            }
            if(type == 2) {
                if(first > 32) showAlert("Pierwsza wartość jest spoza zakresu!");
                if(second > 32) showAlert("Druga wartość jest spoza zakresu!");
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
        }
        else {
            errorlabel.setVisible(true);
            printresult.setText("");
            inputA.setText("");
            inputB.setText("");
            System.out.println("Adder: ERROR SIGNAL!");
        }
    }

    @FXML
    public void closeProgram(ActionEvent event) {
        Stage stage = (Stage) closebutton.getScene().getWindow();
        stage.close();
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void showAlert(String msg) {
        Alert alertInput = new Alert(Alert.AlertType.WARNING);
        alertInput.setTitle("Adder: Warning");
        alertInput.setHeaderText(null);
        alertInput.setContentText(msg);
        alertInput.showAndWait();
    }
}
