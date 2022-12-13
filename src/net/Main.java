package net;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author pedro
 */
public class Main {

    public static void main(String[] args) {
        ArrayList<ServerSideThread> threadList = new ArrayList();
        
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Iniciando servidor na porta 5000");
            
            while (true) {
                Socket socket = serverSocket.accept();
                ServerSideThread serverThread = new ServerSideThread(socket, threadList);
                
                /**
                 * Adiciona a thread à lista de threads para que as proximas
                 * threads possam ter uma referência dessa.
                 */
                threadList.add(serverThread);
                serverThread.start();
            }
        } catch (Exception ex) {
            System.out.println("Um erro ocorreu: " + ex.getStackTrace());
        }
    }

}
