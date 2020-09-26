package com.company;

// Java implementation for a client
// Save file as Client.java

import java.io.*;
import java.net.*;
import java.util.Scanner;

// Client class
public class Client
{
    public static void main(String[] args) throws IOException
    {
        try
        {
            Scanner scanner = new Scanner(System.in);

            // getting localhost ip
            InetAddress ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5056
            Socket socket = new Socket(ip, 5056);

            // obtaining input and out streams
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            String response;
            // the following loop performs the exchange of
            // information between client and client handler
            while (true)
            {
                response = dataInputStream.readUTF();
                System.out.println(response);

                String toSend = scanner.nextLine();
                dataOutputStream.writeUTF(toSend);

                // If client sends exit,close this connection
                // and then break from the while loop
                if(toSend.equals("Sair"))
                {
                    System.out.println("Fechando esta conexão : " + socket);
                    socket.close();
                    System.out.println("Conexão fechada");
                    break;
                }

            }

            // closing resources
            scanner.close();
            dataInputStream.close();
            dataOutputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}