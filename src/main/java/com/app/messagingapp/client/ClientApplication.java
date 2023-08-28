package com.app.messagingapp.client;

import com.app.messagingapp.CommonController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApplication extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("/com/app/messagingapp/user-view.fxml"));

        CommonController controller = new ClientController(); // or ClientController
        fxmlLoader.setController(controller);

        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        stage.setTitle("Client");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}