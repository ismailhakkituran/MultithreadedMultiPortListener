package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiPortListener {

    public static void main(String[] args) {
        // Start a thread for each port
        new Thread(new PortListener(5001, new SubscriberProcessor())).start();
        new Thread(new PortListener(6001, new DistributionProcessor())).start();
        new Thread(new PortListener(7001, new ConfigurationProcessor())).start();
        new Thread(new PortListener(8001, new CapacityProcessor())).start();
    }

    // Runnable class to handle port listening
    static class PortListener implements Runnable {
        private final int port;
        private final Processor processor;

        public PortListener(int port, Processor processor) {
            this.port = port;
            this.processor = processor;
        }

        @Override
        public void run() {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Listening on port: " + port);
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Connection established on port: " + port);

                    // Handle client communication in another thread
                    new Thread(new ClientHandler(clientSocket, port, processor)).start();
                }
            } catch (Exception e) {
                System.err.println("Error on port " + port + ": " + e.getMessage());
            }
        }
    }

    // Runnable class to handle client communication
    static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private final int port;
        private final Processor processor;

        public ClientHandler(Socket clientSocket, int port, Processor processor) {
            this.clientSocket = clientSocket;
            this.port = port;
            this.processor = processor;
        }

        @Override
        public void run() {
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                out.println("You are connected to port " + port + ". Please send your data.");
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Port " + port + " received: " + inputLine);
                    processor.process(inputLine);
                    out.println("Data processed: " + inputLine);
                }
            } catch (Exception e) {
                System.err.println("Error handling client on port " + port + ": " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (Exception e) {
                    System.err.println("Error closing client socket: " + e.getMessage());
                }
            }
        }
    }

    interface Processor {
        void process(String data);
    }

    static class SubscriberProcessor implements Processor {
        @Override
        public void process(String data) {
            System.out.println("Processing subscriber data on port 5001: " + data);
            // Add your logic here
        }
    }
    static class DistributionProcessor implements Processor {
        @Override
        public void process(String data) {
            System.out.println("Processing data distribution on port 6001: " + data);
            // Add your logic here
        }
    }

    static class ConfigurationProcessor implements Processor {
        @Override
        public void process(String data) {
            System.out.println("Processing configuration properties on port 7001: " + data);
            // Add your logic here
        }
    }

    static class CapacityProcessor implements Processor {
        @Override
        public void process(String data) {
            System.out.println("Processing capacity demands on port 8001: " + data);
            // Add your logic here
        }
    }
}
