package gui.components;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class MainController {

    @FXML
    private VBox callPane;

    @FXML
    private VBox registerPane;

    @FXML
    private VBox loginPane;

    @FXML
    private LoginController loginController;

    @FXML
    private RegisterController registerController;

    @FXML
    private CallPageController callPageController;

    @FXML
    void initialize() {
        callPane.setVisible(false);
        loginPane.setVisible(false);
        registerPane.setVisible(true);
    }
}
