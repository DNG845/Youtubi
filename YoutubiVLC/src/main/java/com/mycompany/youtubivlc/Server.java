/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.youtubivlc;

/**
 *
 * @author danny
 */
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        final int PORT = 8000;
        final String Content = 
"C:\\Users\\danny\\OneDrive\\Documentos\\NetBeansProjects\\Musica-Videos-Doc";

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor en el puerto: " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Coneccion realizada desde: " + 
                        clientSocket.getInetAddress());

                Thread clientThread = new Thread(() -> ClientRequest
        (clientSocket, Content));
                clientThread.start();
            }
        } catch (IOException e) {
        }
    }

    private static void ClientRequest(Socket clientSocket, String contentDirectory) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader
        (clientSocket.getInputStream()));
            String request = reader.readLine();

            if (request.startsWith("GET /")) {
                String[] parts = request.split(" ");
                String filePath = parts[1].substring(1);
                File file = new File(contentDirectory, filePath);

                if (file.exists() && file.isFile()) {
                    sendFileContent(clientSocket, file);
                } else {
                    sendResponse(clientSocket, "Archivo no encontrado");
                }
            } else {
                sendResponse(clientSocket, "Solicitud erronea");
            }

            clientSocket.close();
        } catch (IOException e) {
        }
    }

    private static void sendFileContent(Socket clientSocket, File file) 
            throws IOException {
        try (BufferedOutputStream out = new BufferedOutputStream
        (clientSocket.getOutputStream())) {
            if (file.getName().endsWith(".txt") || 
                    file.getName().endsWith(".mp3") || 
                    file.getName().endsWith(".mp4") || 
                    file.getName().endsWith(".jpg") || 
                    file.getName().endsWith(".png")) {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            } else {

                sendResponse(clientSocket, "Archivo no encontrado");
            }

            out.flush();
        }
    }

     private static void sendResponse(Socket clientSocket, String message) throws IOException {
        clientSocket.getOutputStream().write(message.getBytes());
    }
}
