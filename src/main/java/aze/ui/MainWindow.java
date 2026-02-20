package aze.ui;

import aze.Aze;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Aze aze;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image azeImage = new Image(this.getClass().getResourceAsStream("/images/DaAze.png"));

    /**
     * Initializes the main window.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(
                DialogBox.getAzeDialog(" Hello! I'm Aze\n What can I do for you?", azeImage)
        );
    }

    /** Injects the Aze instance */
    public void setAze(Aze a) {
        aze = a;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Aze's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = aze.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getAzeDialog(response, azeImage)
        );
        userInput.clear();
        if (aze.isExit()) {
            Platform.exit();
        }
    }
}
