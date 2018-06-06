package adders;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeController {

    @FXML
    public void goToChose(ActionEvent event) throws IOException {
        Parent chose_parent = FXMLLoader.load(getClass().getResource("chose.fxml"));
        Scene chose_scene = new Scene(chose_parent);
        Stage chose_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        chose_stage.setScene(chose_scene);
        chose_stage.show();
    }
}
