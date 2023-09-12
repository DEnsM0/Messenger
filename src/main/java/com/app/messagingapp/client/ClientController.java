package com.app.messagingapp.client;

import com.app.messagingapp.CommonController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The `ClientController` class is responsible for managing the client-side GUI and communication with the server.
 */
public class ClientController implements Initializable, CommonController {

    /**
     * The Send-button.
     */
    @FXML
    private Button b_send;

    /**
     * The TextField where the user enters messages to send.
     */
    @FXML
    private TextField tf_message;

    /**
     * A message box. Used to display all messages in chat.
     */
    @FXML
    private VBox vb_message;

    /**
     * The ScrollPane for the field with messages.
     */
    @FXML
    private ScrollPane sp_message;

    /**
     * The instance od Client class.
     */
    private Client client;

    /**
     * Initializes the Client.
     * Adds a Listener to the {@link #vb_message}.
     * Sets an EventHandler to the {@link #b_send}. A new message will be displayed once sent.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     * @throws RuntimeException if the creation of the Client is failed.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            client = new Client(new Socket("localhost", 1234));
            System.out.println("Connected to server");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        vb_message.heightProperty().addListener((observableValue, oldVal, newVal) -> sp_message.setVvalue((Double) newVal));

        client.receiveMessage(vb_message);

        b_send.setOnAction(actionEvent -> {
            String message = tf_message.getText();
            if(!message.isEmpty()){
                HBox hBox = new HBox();

                hBox.setAlignment(Pos.CENTER_RIGHT);
                hBox.setPadding(new Insets(5));

                TextFlow textFlow = new TextFlow(new Text(message));

                textFlow.setStyle("-fx-background-color: rgb(31,138, 255);" +
                        "-fx-background-radius: 20px;");

                textFlow.setPadding(new Insets(5));

                hBox.getChildren().add(textFlow);
                vb_message.getChildren().add(hBox);

                client.sendMessage(message);
                tf_message.clear();
            }
        });
    }

    /**
     * Displays a received message label to the GUI.
     * @param message a string with a received message from the Server.
     * @param vBox a vBox to display a message in the GUI
     */
    public static void addLabel(String message, VBox vBox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5));

        TextFlow textFlow = new TextFlow(new Text(message));

        textFlow.setStyle(
                "-fx-background-color: rgb(67,204,71);" +
                        "-fx-background-radius: 20px;");

        textFlow.setPadding(new Insets(5));
        hBox.getChildren().add(textFlow);

        Platform.runLater(() -> vBox.getChildren().add(hBox));
    }

    /**
     * Gets the VBox used for displaying messages.
     * @return the VBox used for displaying messages.
     */
    @Override
    public VBox getVBox() {
        return vb_message;
    }
}