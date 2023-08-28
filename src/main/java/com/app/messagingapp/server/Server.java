package com.app.messagingapp.server;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Server(ServerSocket serverSocket) {
        try {
            this.serverSocket = serverSocket;
            this.socket = serverSocket.accept();
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating the Server!");
            close(socket, bufferedReader, bufferedWriter);
        }
    }

    private void close(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
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

    public void sendMessage(String message){
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message to the Client!");
            close(socket, bufferedReader, bufferedWriter);
        }
    }

    public void receiveMessage(VBox vBox){
        new Thread(() -> {
            while(socket.isConnected()){
                try{
                    String message = bufferedReader.readLine();
                    ServerController.addLabel(message, vBox);
                }catch (IOException e){
                    e.printStackTrace();
                    System.out.println("Error receiving message from the Client!");
                    close(socket, bufferedReader, bufferedWriter);
                    break;
                }
            }
        }).start();
    }
}
