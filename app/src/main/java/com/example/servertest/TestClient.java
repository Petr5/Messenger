package com.example.servertest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TestClient implements Runnable{
    private Thread thread;
    private ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String message = "hello buddy";
    boolean message_in_que = true;
    public TestClient()
    {
        this.thread = new Thread( this );
        this.thread.setPriority(Thread.NORM_PRIORITY );
        this.thread.start();
    }

    public void send_message(String message){
        this.message = message;
        message_in_que = true;
    }

    @Override
    public void run()
    {
        try
        {
            this.socket = new Socket( "10.0.2.2" , 8000 );
            System.out.println("!!!!!!!" + socket);
        }
        catch( IOException e )
        {
            System.out.println( "failed to create socket" );
            e.printStackTrace();
        }

        System.out.println( "connected" );

        try
        {
            this.dataInputStream = new DataInputStream( new BufferedInputStream( this.socket.getInputStream() ) );
            this.dataOutputStream = new DataOutputStream( new BufferedOutputStream( this.socket.getOutputStream() ) );
        }
        catch ( IOException e )
        {
            System.out.println( "failed to create streams" );
            e.printStackTrace();
        }


        while ( true )
        {

            if (message_in_que){
                System.out.println("start sending message");
                try {
                    this.dataOutputStream.writeUTF(message);
                    this.dataOutputStream.flush();
                } catch (IOException e) {
                    System.out.println( "failed to write data" );
                    e.printStackTrace();
                }
                message_in_que = false;
            }


            try
            {
                String message = this.dataInputStream.readUTF();
                System.out.println("message is " + message);
            }
            catch ( IOException e )
            {
                System.out.println( "failed to read data" );
                e.printStackTrace();
                break;
            }
        }
    }
}