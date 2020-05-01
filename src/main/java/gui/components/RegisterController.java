package gui.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class RegisterController {
    @FXML
    private Button registerBtn;

    @FXML
    private TextField usernameTF;

    @FXML
    private TextField emailTF;

    @FXML
    private PasswordField passwordTF;

    @FXML
    private AnchorPane mainPane;

    @FXML
    void initialize() {
        setEnabled(true);
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            mainPane.setVisible(true);
            mainPane.setDisable(false);
        } else {
            mainPane.setVisible(false);
            mainPane.setDisable(true);
        }
    }
}
