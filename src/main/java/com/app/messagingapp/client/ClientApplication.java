package com.app.messagingapp.client;

import com.app.messagingapp.CommonController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The entry point for the client-side of the messaging application.
 * Initializes the GUI by loading the FXML file and setting up the {@link ClientController}.
 */
public class ClientApplication extends javafx.application.Application {

    /**
     * The main entry point for the client application.
     * Initializes the client application's user interface and controller.
     * @param stage The primary stage for the application.
     * @throws IOException if an error occurs during FXML file loading.
     */
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

    /**
     * The main method, which launches the Client-JavaFX-application.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        launch();
    }
}