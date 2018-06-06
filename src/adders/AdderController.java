package adders;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AdderController {

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
    private Label printresult;

    @FXML
    private Label errorlabel;

    public static Adder createAdderInstance(int type, int first, int second, int residues) {
        if(type == 1) return new Adder(4, first, second, residues);
        else if(type == 2) return new Adder(8, first, second, residues);
        else System.out.println("Error: Niedostępna opcja, wyłączam program!");
        return null;
    }

    @FXML
    public void calculateSum(ActionEvent event) throws IOException {

        int first = Integer.parseInt(variableA.getText());
        int second = Integer.parseInt(variableB.getText());
        int residues = Integer.parseInt(variableM.getText());

        Adder adder = createAdderInstance(type, first, second, residues);
        Adder moduloAdder = createAdderInstance(type, first % residues, second % residues, residues);

        int LHS = adder.runAdder();
        int RHS = moduloAdder.runAdder();

        printresult.setVisible(false);
        errorlabel.setVisible(false);

        if(LHS == RHS) {
            adder.printResult();
            printresult.setText(adder.printResultForController());
            printresult.setVisible(true);
        }
        else {
            errorlabel.setVisible(true);
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
}
