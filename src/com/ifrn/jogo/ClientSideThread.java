package com.ifrn.jogo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author pedro
 */
public class ClientSideThread extends Thread {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public ClientSideThread() throws IOException {
        this.socket = new Socket("localhost", 5000);
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = new PrintWriter(this.socket.getOutputStream(), true);
    }
    
    @Override
    public void run() {
        try {
            /**
             * O loop principal da thread cliente, ou seja, o que o cliente fará
             * quando estiver conectado com o servidor.
             */
            String data;
            while(true) {
                data = input.readLine();
                // ele lê a resposta do servidor e imprime
                System.out.println("Resposta (ClientThread): " + data);
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
