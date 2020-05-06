package com.gui.components;

import com.models.LoginForm;
import com.rest_providers.UserProviderImpl;
import com.utils.IpUtils;
import com.utils.PasswordUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.UnknownHostException;

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
    void login(ActionEvent event) throws
                                  UnknownHostException {
        LoginForm loginForm = LoginForm
                .builder()
                .username(usernameTF.getText())
                .password(PasswordUtils.getPasswordHash(passwordTF.getText()))
                .IPAddress(IpUtils.getLocalIpAddr())
                .build();

        new Thread(() -> {
            Platform.runLater(() -> {
                try {
                    userProvider.login(loginForm);
                    mainController.switchToCall();
                } catch (Exception e) {
                    e.printStackTrace();
                    controllers.AlertController.showAlert("Failed to login!",
                                                          null,
                                                          "Register or activate your account!");
                }
            });

        }).start();
    }

    @FXML
    void register(ActionEvent event) {
        mainController.switchToRegister();
    }
}

