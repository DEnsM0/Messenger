package com.app.messagingapp.server;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Manages the exchange of messages with a connected Client.
 */
public class Server {
    /**
     * The server socket for accepting client connections.
     */
    private ServerSocket serverSocket;
    /**
     * The socket to exchange messages with the Client.
     */
    private Socket socket;
    /**
     * The BufferedReader to accept messages from the InputStream of the {@link #socket}
     */
    private BufferedReader bufferedReader;
    /**
     * The BufferedWriter to send messages via the InputStream of the {@link #socket}
     */
    private BufferedWriter bufferedWriter;

    /**
     * Creates an instance of the Server class.
     * @param serverSocket The server socket for accepting client connections.
     */
    public Server(ServerSocket serverSocket) {
        try {
            this.serverSocket = serverSocket;
            this.socket = serverSocket.accept();
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating the Server!");
            close();
        }
    }

    /**
     * Closes connection between the Server and the Client.
     * Calls close() method on {@link #bufferedReader},{@link #bufferedWriter},{@link #socket}
     * @throws RuntimeException if an error appears while closing
     */
    private void close(){
        try{
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends a message to the Client.
     * Uses {@link #bufferedWriter} to pass a message to the OutputStream of the {@link #socket}.
     * @param message A message string to be sent to the Client.
     */
    public void sendMessage(String message){
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message to the Client!");
            close();
        }
    }

    /**
     * Receives a message from the connected Client and displays it in the GUI.
     * Uses {@link #bufferedReader} to accept/read a message from the InputStream of the {@link #socket}.
     * Uses a separate Thread to display a message.
     * @param vBox A vBox to display a message in the GUI
     */
    public void receiveMessage(VBox vBox){
        new Thread(() -> {
            while(socket.isConnected()){
                try{
                    String message = bufferedReader.readLine();
                    ServerController.addLabel(message, vBox);
                }catch (IOException e){
                    e.printStackTrace();
                    System.out.println("Error receiving message from the Client!");
                    close();
                    break;
                }
            }
        }).start();
    }
}
