package ru.ob11to.inputoutput.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    private static final Logger LOG = LoggerFactory.getLogger(EchoServer.class);

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                try (OutputStream output = socket.getOutputStream();
                     BufferedReader input = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()))) {
                    output.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                    for (String string = input.readLine(); string != null && !string.isEmpty(); string = input.readLine()) {
                        if (string.startsWith("GET") && string.toLowerCase().contains("exit")) {
                            server.close();
                        } else if (string.startsWith("GET") && string.toLowerCase().contains("hello")) {
                            output.write("Hello, dear friend.".getBytes());
                        } else if (string.startsWith("GET")) {
                            output.write("What?".getBytes());
                        }
                        System.out.println(string);
                    }
                    output.flush();
                }
            }
        } catch (IOException e) {
            LOG.info(e.getMessage());
        }
    }
}