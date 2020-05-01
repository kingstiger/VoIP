package gui.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField usernameTF;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordTF;

    @FXML
    void initialize() {
        setEnabled(false);
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

