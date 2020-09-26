package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

// ClientHandler class
class ClientHandler extends Thread
{
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    final Socket socket;


    // Constructor
    public ClientHandler(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream)
    {
        this.socket = socket;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public void run()
    {
        String received;
        String toReturn;
        String homeText = "Qual operação deseja realizar?[ + | - | * | / ]..\n"+
                "Digite Sair para encerrar a conexão.";

        // Ask user what he wants
        try {
            dataOutputStream.writeUTF(homeText);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true)
        {

            try {
                // receive the answer from client
                received = dataInputStream.readUTF().trim();

                if(received.equals("Sair"))
                {
                    System.out.println("Cliente " + this.socket + " enviou sair...");
                    System.out.println("Encerrando a conexão...");
                    this.socket.close();
                    System.out.println("Conexão encerrada.");
                    break;
                }

                // write on output stream based on the
                // answer from the client
                switch (received) {

                    case "+" :
                        toReturn = plus();
                        break;

                    case "-" :
                        toReturn = minus();
                        break;

                    case "*" :
                        toReturn = mult();
                        break;

                    case "/" :
                        toReturn = div();
                        break;

                    default:
                        toReturn = "Entrada inválida";
                        break;
                }

                toReturn = toReturn + "\n" + homeText;

                dataOutputStream.writeUTF(toReturn);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try
        {
            // closing resources
            this.dataInputStream.close();
            this.dataOutputStream.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private int readFirst() {
        String unsanitezed;
        try {
            do {
                dataOutputStream.writeUTF("Digite o primeiro elemento:");
                unsanitezed = dataInputStream.readUTF().trim();
            } while (!unsanitezed.matches("^[0-9]+$"));
                return Integer.parseInt(unsanitezed);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return 0;
    }

    private int readSecond() {
        String unsanitezed;
        try {
            do {
                dataOutputStream.writeUTF("Digite o segundo elemento:");
                unsanitezed = dataInputStream.readUTF().trim();
            } while (!unsanitezed.matches("^[0-9]+$"));
            return Integer.parseInt(unsanitezed);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String plus() {
        int total = readFirst() + readSecond();
        return "O total é: " + total;
    }

    private String minus() {
        int total = readFirst() - readSecond();
        return "O total é: " + total;
    }

    private String mult() {
        int total = readFirst() * readSecond();
        return "O total é: " + total;
    }

    private String div() {
        int num1 = readFirst();
        int num2 = readSecond();
        if (num2 == 0 ) {
            return "Não pode dividir número por 0";
        }
        int total = num1 / num2;
        return "O total é: " + total;
    }
}