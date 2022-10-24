package com.example.servertest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TestClientWriter implements Runnable{
    private Thread thread;
    private ServerSocket serverSocket;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private String message = "hello buddy";
    boolean message_in_que = true;



    public void send_message(String message){
        this.message = message;
        message_in_que = true;
    }
    public void set_outputStream(DataOutputStream dataOutputStream){
        this.dataOutputStream = dataOutputStream;
    }
    @Override
    public void run() {
        System.out.println("start sending message");
        while (true) {
            if (message_in_que) {
                try {
                    this.dataOutputStream.writeUTF(message);
                    this.dataOutputStream.flush();
                } catch (IOException e) {
                    System.out.println("failed to write data");
                    e.printStackTrace();
                }
                message_in_que = false;
            }

        }
    }
}