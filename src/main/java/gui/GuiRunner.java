package gui;

import gui.components.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;

public class GuiRunner extends Application {

    private static Logger logger = Logger.getLogger(GuiRunner.class);
    private Image icon = new Image("file:///icons/phone.jpg");
    private MainController component;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws
                                          IOException {
        URL mainFxmlFileUrl = GuiRunner.class.getClassLoader()
                                             .getResource("fxml_files/main/main.fxml");

        FXMLLoader loader = new FXMLLoader(mainFxmlFileUrl);
        Parent root = loader.load();

        component = loader.getController();

        primaryStage.setTitle("Logs checker");
        primaryStage.getIcons()
                    .add(icon);
        primaryStage.setScene(new Scene(root, 800, 800));

        primaryStage.show();
    }

}
