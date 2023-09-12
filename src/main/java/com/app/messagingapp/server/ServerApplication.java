package com.app.messagingapp.server;

import com.app.messagingapp.CommonController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The entry point for the server-side of the messaging application.
 * Initializes the GUI by loading the FXML file  and setting up the {@link ServerController}.
 */
public class ServerApplication extends javafx.application.Application {

    /**
     * The main entry point for the server application.
     * Initializes the server application's user interface and controller.
     * @param stage The primary stage for the application.
     * @throws IOException if an error occurs during FXML file loading.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerApplication.class.getResource("/com/app/messagingapp/user-view.fxml"));

        CommonController controller = new ServerController(); // or ClientController
        fxmlLoader.setController(controller);

        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        stage.setTitle("Server");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * The main method, which launches the Server-JavaFX-application.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        launch();
    }
}