package com.company;

// Java implementation of  Server side
// It contains two classes : Server and ClientHandler
// Save file as Server.java

import java.io.*;
import java.net.*;

// Server class
public class Server
{
    public static void main(String[] args) throws IOException
    {
        // server is listening on port 5056
        ServerSocket serverSocket = new ServerSocket(5056);

        // running infinite loop for getting
        // client request
        //noinspection InfiniteLoopStatement
        while (true)
        {
            Socket socket = null;

            try
            {
                // socket object to receive incoming client requests
                socket = serverSocket.accept();

                System.out.println("Novo cliente conectado : " + socket);

                // obtaining input and out streams
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                System.out.println("Associando uma nova Thread ao cliente");

                // create a new thread object
                Thread thread = new ClientHandler(socket, dataInputStream, dataOutputStream);

                // Invoking the start() method
                thread.start();

            }
            catch (Exception e){
                assert socket != null;
                socket.close();
                e.printStackTrace();
            }
        }
    }
}