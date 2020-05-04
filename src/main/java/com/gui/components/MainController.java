package com.gui.components;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Controller;

@Controller
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
        callPane.setVisible(true);
        loginPane.setVisible(false);
        registerPane.setVisible(false);
    }
}
