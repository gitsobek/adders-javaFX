package adders;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;

public class ChoseController {

    private Stage stage;

    @FXML
    private Button button;

    @FXML
    private RadioButton first;

    @FXML
    private RadioButton second;

    @FXML
    public void goToAdder(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adder.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AdderController controller = loader.getController();
        if(first.isSelected()) controller.setType(1);
        else if(second.isSelected()) controller.setType(2);

        stage.setScene(scene);
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void returnFour(ActionEvent event) {
        if(first.isSelected()) button.setDisable(false);
    }

    @FXML
    public void returnEight(ActionEvent event) {
        if(second.isSelected()) button.setDisable(false);
    }

}
