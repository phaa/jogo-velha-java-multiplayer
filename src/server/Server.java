package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class Server {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(5000);
            System.out.println("Iniciando servidor na porta 5000");
            
            ServerGame game = new ServerGame();
            
            while (true) {
                Socket socketX = serverSocket.accept();
                ServerSidePlayer playerX = new ServerSidePlayer(socketX, 'X', game);
                
                Socket socketO = serverSocket.accept();
                ServerSidePlayer playerO = new ServerSidePlayer(socketO, 'O', game);
                
                playerX.setOponent(playerO);
                playerO.setOponent(playerX);
                
                game.setCurrentPlayer(playerX);
                
                playerX.start();
                playerO.start();
            }
        } catch (IOException ex) {
            System.out.println("Um erro ocorreu: " + ex.getStackTrace());
        } 
        finally {
            try {
                serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
