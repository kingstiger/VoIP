package com.gui.components;

import com.rest_providers.UserProviderImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {
    @Autowired
    private MainController mainController;

    @Autowired
    private UserProviderImpl userProvider;

    @FXML
    private Button registerBtn;

    @FXML
    private TextField usernameTF;

    @FXML
    private Button loginBtn;

    @FXML
    private CheckBox rememberMeCB;

    @FXML
    private PasswordField passwordTF;

    @FXML
    private AnchorPane mainPane;

    @FXML
    void login(ActionEvent event) {
        // TODO: 2020-05-04
    }

    @FXML
    void register(ActionEvent event) {
        mainController.switchToRegister();
    }
}

