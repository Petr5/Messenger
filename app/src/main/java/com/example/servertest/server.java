package com.example.servertest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

public class server implements Runnable
{
    private Thread thread;
    private ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String message = "this is message from server";
    boolean message_in_que = true;
    private ServerWriter writer;
    public void send_message(String message){
        writer.send_message(message);
    }
    @Override
    public void run()
    {
        // create a server socket
        try {
            this.serverSocket = new ServerSocket( 8080 );
            System.out.println("!!!!!!!" + serverSocket);
        }
        catch ( IOException e ) {
            System.out.println( "failed to start server socket" );
            e.printStackTrace();
        }

        // wait for a connection
        System.out.println( "waiting for connections..." );
        try {this.socket = serverSocket.accept();}
        catch ( IOException e )
        {System.out.println( "failed to accept" );e.printStackTrace();}

        System.out.println( "client connected" );

        try {
            this.dataInputStream = new DataInputStream( new BufferedInputStream( this.socket.getInputStream() ) );
            this.dataOutputStream = new DataOutputStream( new BufferedOutputStream( this.socket.getOutputStream() ) );
        }
        catch ( IOException e ) {
            System.out.println( "failed to create streams" );
            e.printStackTrace();
        }

        this.writer = new ServerWriter();
        writer.set_outputStream(this.get_OutputStream());
        Thread writer_thread = new Thread(writer);
        writer_thread.start();

        System.out.println("try accept message");
        while(true){
            try
            {
                String message = this.dataInputStream.readUTF();
                System.out.println("message is " + message);
            }
            catch ( IOException e )
            {
                e.printStackTrace();
                break;
            }
        }


        System.out.println( "server thread stopped" );
    }
    public DataOutputStream get_OutputStream (){
        return this.dataOutputStream;
    }
}
