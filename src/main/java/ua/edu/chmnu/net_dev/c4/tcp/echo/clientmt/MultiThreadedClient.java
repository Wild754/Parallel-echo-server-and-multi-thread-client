package ua.edu.chmnu.net_dev.c4.tcp.echo.clientmt;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 6710;
    private static final int CLIENT_COUNT = 1100;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(50); // Пул потоків

        for (int i = 0; i < CLIENT_COUNT; i++) {
            final int clientId = i + 1; // Порядковий номер клієнта
            executorService.submit(() -> {
                try (Socket clientSocket = new Socket(SERVER_HOST, SERVER_PORT)) {
                    communicateWithServer(clientSocket, clientId);
                } catch (IOException e) {
                    System.err.println("Client error: " + e.getMessage());
                }
            });
        }

        executorService.shutdown();
    }

    private static void communicateWithServer(Socket clientSocket, int clientId) {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            // Отримання запиту на нік
            String promptNick = reader.readLine();
            writer.println("Client-" + clientId);

            // Генерування випадкового рядка
            String randomString = generateRandomString(10);

            long startTime = System.nanoTime();

            // Відправка на сервер
            writer.println(randomString);

            // Отримання відповіді
            String response = reader.readLine();

            long endTime = System.nanoTime();

            // Обчислення тривалості
            long durationMs = (endTime - startTime) / 1_000_000;

            System.out.printf("Client %d received: %s, Duration: %d ms%n",
                    clientId, response, durationMs);

            // Після отримання відповіді відключаємося від сервера
            clientSocket.close();
            System.out.println("Client " + clientId + " disconnected");

        } catch (IOException e) {
            System.err.println("Error during communication: " + e.getMessage());
        }
    }

    private static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            stringBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }

        return stringBuilder.toString();
    }
}
