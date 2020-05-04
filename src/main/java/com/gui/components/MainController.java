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
        makePanelsVisible(false, true, false);
    }

    public void switchToLogin() {
        makePanelsVisible(false, true, false);
    }

    public void switchToRegister() {
        makePanelsVisible(false, false, true);
    }

    public void switchToCall() {
        makePanelsVisible(true, false, false);
    }

    private void makePanelsVisible(boolean callPage, boolean loginPage, boolean registerPage) {
        callPane.setVisible(callPage);
        loginPane.setVisible(loginPage);
        registerPane.setVisible(registerPage);
    }

}
