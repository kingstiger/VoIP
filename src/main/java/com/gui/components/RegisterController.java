package com.gui.components;

import com.models.UserShortDAO;
import com.rest_providers.UserProviderImpl;
import com.utils.IpUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.UnknownHostException;

@Controller
public class RegisterController {
    @Autowired
    private UserProviderImpl userProvider;

    @FXML
    private Button registerBtn;

    @FXML
    private TextField usernameTF;

    @FXML
    private TextField emailTF;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordTF;

    @FXML
    private AnchorPane mainPane;

    @FXML
    void initialize() {

    }

    @FXML
    void register(ActionEvent event) throws
                                     UnknownHostException {
        UserShortDAO userShortDAO = UserShortDAO
                .builder()
                .username(usernameTF.getText())
                .email(emailTF.getText())
                .IPAddress(IpUtils.getLocalIpAddr())
                .build();

        System.out.println(userShortDAO);
        userProvider.register(userShortDAO);
    }

    @FXML
    void login(ActionEvent event) {

    }
}
