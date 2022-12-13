package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class ServerSideThread extends Thread {
    private Socket socket;
    private ArrayList<ServerSideThread> friendlyThreads;
    private BufferedReader input;
    private PrintWriter output;

    public ServerSideThread(Socket socket, ArrayList<ServerSideThread> threads) {
        this.socket = socket;
        this.friendlyThreads = threads;
    }
    
    @Override
    public void run() {
        try {
            /**
             * O loop principal da thread servidor, ou seja, o que o servidor fará
             * quando receber a requisição de algum cliente.
             */
            
            // Inicializa o IO
            this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.output = new PrintWriter(this.socket.getOutputStream(), true);
            
            String data;
            while(true) {
                data = input.readLine();
                
                // se o cliente quiser desconectar
                if(data.equalsIgnoreCase("exit")) {
                    break;
                }
                
                // broadcast para todos os clientes 
                this.broadcast(data);
                
                // output.println("Servidor responde" + data);
                System.out.println("Servidor responde = " + data);
            }
        } catch(IOException ex) {
            Logger.getLogger(ServerSideThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void broadcast(String data) {     
        for(ServerSideThread thread : this.friendlyThreads) {
            thread.getOutputWriter().println(data);
        }
    }
    
    public PrintWriter getOutputWriter() {
        return this.output;
    }
}
