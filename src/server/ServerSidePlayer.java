package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author pedro
 */
public class ServerSidePlayer extends Thread {

    // IO
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    private ServerSidePlayer oponent;
    private char symbol;
    private ServerGame game;

    public ServerSidePlayer(Socket socket, char symbol, ServerGame game) {
        this.socket = socket;
        this.symbol = symbol;
        this.game = game;

        //Inicializa IO
        this.initIO();
    }

    private void initIO() {
        System.out.println("Iniciando IO do player (" + this.symbol + ")");
        try {
            this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.output = new PrintWriter(this.socket.getOutputStream(), true);
            this.output.println("WELCOME " + this.symbol);
            this.output.println("MESSAGE Aguardando oponente...");

            System.out.println("IO do player (" + this.symbol + ") iniciado com sucesso!");
        } catch (IOException e) {
            System.out.println("Um jogador morreu :( " + e);
        }
    }

    @Override
    public void run() {
        try {
            // The thread is only started after everyone connects.
            output.println("MESSAGE Todos jogadores conectados!");

            // Tell the first player that it is his/her turn.
            if (this.symbol == 'X') {
                output.println("MESSAGE Sua vez");
            }

            // Repeatedly get commands from the client and process them.
            while (true) {
                String command = input.readLine();
                System.out.println(command);
                
                
                
                if (command.startsWith("MOVE" )) {
                    int location = Integer.parseInt(command.substring(5));
                    if (this.game.legalMove(location, this)) {
                        this.output.println("VALID_MOVE");
                        //String response = this.game.checkWinner() ? "VICTORY" : this.game.boardFilledUp() ? "TIE" : "";

                        String response;
                        if (this.game.checkWinner()) {
                            this.game.increaseWins(this.symbol);
                            
                            response = "VICTORY ";
                            //response += ":"+this.game.getxVictories();
                            //response += ":"+this.game.getoVictories();
                            //response += ":"+this.game.getTies();

                            this.game.resetBoard();
                        } 
                        else if (this.game.boardFilledUp()) {
                            this.game.increaseTies();
                            
                            response = "TIE ";
                            //response += ":"+this.game.getxVictories();
                            //response += ":"+this.game.getoVictories();
                            //response += ":"+this.game.getTies();

                            this.game.resetBoard();
                        } else {
                            response = "";
                        }

                        output.println(response);
                    } else {
                        output.println("MESSAGE ?");
                    }
                } 
                else if (command.startsWith("SCOREBOARD")) {
                    String response = "SCOREBOARD ";
                    response += ":"+this.game.getxVictories();
                    response += ":"+this.game.getoVictories();
                    response += ":"+this.game.getTies();
                    output.println(response);
                }
                else if (command.startsWith("QUIT")) {
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Um jogador morreu :( " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }

    // Gerencia a movimentação do outro player
    public void otherPlayerMoved(int location) {
        this.output.println("OPPONENT_MOVED " + location);

        String response;
        if (this.game.checkWinner()) {
            response = "DEFEAT ";
            //response += ":"+this.game.getxVictories();
            //response += ":"+this.game.getoVictories();
            //response += ":"+this.game.getTies();
        } else if (this.game.boardFilledUp()) {
            response = "TIE";
        } else {
            response = "";
        }

        //this.game.checkWinner() ? "DEFEAT" : this.game.boardFilledUp() ? "TIE" : ""
        this.output.println(response);
    }

    public ServerSidePlayer getOponent() {
        return oponent;
    }

    public void setOponent(ServerSidePlayer oponent) {
        this.oponent = oponent;
    }

}
