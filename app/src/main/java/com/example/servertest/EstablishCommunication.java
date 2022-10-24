package com.example.servertest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class EstablishCommunication implements Runnable{
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;


    @Override
    public void run() {
//        if (socket.is)
        try {
            this.socket = new Socket("10.0.2.2", 8000);
            System.out.println("!!!!!!!" + socket);
        } catch (IOException e) {
            System.out.println("failed to create socket");
            e.printStackTrace();
        }

        System.out.println("connected");
        try {
            this.dataInputStream = new DataInputStream(new BufferedInputStream(this.socket.getInputStream()));
            this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
        } catch (IOException e) {
            System.out.println("failed to create streams");
            e.printStackTrace();
        }
    }
    public DataOutputStream get_OutputStream (){
        return this.dataOutputStream;
    }
}
