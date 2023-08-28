package com.app.messagingapp.server;

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
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerController implements Initializable, CommonController {
    @FXML
    private Button b_send;
    @FXML
    private TextField tf_message;
    @FXML
    private VBox vb_message;
    @FXML
    private ScrollPane sp_message;

    private Server server;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            server = new Server(new ServerSocket(1234));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        vb_message.heightProperty().addListener((observableValue, oldVal, newVal) -> sp_message.setVvalue((Double) newVal));

        server.receiveMessage(vb_message);

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

                server.sendMessage(message);
                tf_message.clear();
            }
        });
    }

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

    @Override
    public VBox getVBox() {
        return vb_message;
    }
}