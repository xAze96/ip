package aze;

import java.io.IOException;

import aze.ui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Aze using FXML.
 */
public class Main extends Application {

    private Aze aze = new Aze("data/tasks.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Aze");

            // Claude Sonnet 4.5 - Set minimum window size and modify MainWindow.fxml to make the window resizable
            stage.setMinHeight(400);
            stage.setMinWidth(300);

            fxmlLoader.<MainWindow>getController().setAze(aze);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

