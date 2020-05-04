package com.gui.components;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class CallPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    void initialize() {
        assert x1 != null : "fx:id=\"x1\" was not injected: check your FXML file 'call_page.fxml'.";
        assert x2 != null : "fx:id=\"x2\" was not injected: check your FXML file 'call_page.fxml'.";

    }
}
