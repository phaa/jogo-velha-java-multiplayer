/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

/**
 *
 * @author pedro
 */
public class ServerGame {
    private int xVictories = 0;
    private int oVictories = 0;
    private int ties = 0;
    private ServerSidePlayer currentPlayer;
    private ServerSidePlayer[] board = {
        null, null, null,
        null, null, null,
        null, null, null
    };
    
    public boolean checkWinner() {
        return
            (board[0] != null && board[0] == board[1] && board[0] == board[2])
          ||(board[3] != null && board[3] == board[4] && board[3] == board[5])
          ||(board[6] != null && board[6] == board[7] && board[6] == board[8])
          ||(board[0] != null && board[0] == board[3] && board[0] == board[6])
          ||(board[1] != null && board[1] == board[4] && board[1] == board[7])
          ||(board[2] != null && board[2] == board[5] && board[2] == board[8])
          ||(board[0] != null && board[0] == board[4] && board[0] == board[8])
          ||(board[2] != null && board[2] == board[4] && board[2] == board[6]);
    }
    
    // no empty squares
    public boolean boardFilledUp() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == null) {
                return false;
            }
        }
        return true;
    }
    
    // thread when player tries a move
    public synchronized boolean legalMove(int location, ServerSidePlayer player) {
        if (player == this.currentPlayer && this.board[location] == null) {
            this.board[location] = this.currentPlayer;
            this.currentPlayer = this.currentPlayer.getOponent();
            this.currentPlayer.otherPlayerMoved(location);
            return true;
        }
        return false;
    }
    
    public ServerSidePlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(ServerSidePlayer currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    
    public void resetBoard() {
        for (int i = 0; i < this.board.length; i++) {
            this.board[i] = null;
        }
        System.out.println("Resetou tabuleiro");
    }

    public void increaseWins(char symbol) {
        if(symbol == 'X') {
            this.xVictories++;
        }
        else if(symbol == 'O') {
            this.oVictories++;
        }
       
        // O jogo vai terminar com o jogador trocado, por isso destrocamos denovo
        this.currentPlayer = this.currentPlayer.getOponent();
        
        System.out.println("Vitórias do x: " + this.xVictories);
        System.out.println("Vitórias do o: " + this.oVictories);
        System.out.println("Empates: " + this.ties);
    }
    
    public void increaseTies() {
        this.ties++;
        System.out.println("Vitórias do x: " + this.xVictories);
        System.out.println("Vitórias do o: " + this.oVictories);
        System.out.println("Empates: " + this.ties);
    }

    public int getxVictories() {
        return xVictories;
    }

    public int getoVictories() {
        return oVictories;
    }

    public int getTies() {
        return ties;
    }
    
    
    
}



/**
    public boolean checkWinner() {
        for (int a = 0; a < 8; a++) {
            String line = "";

            switch (a) {
                case 0:
                    line = this.tiles[0] + this.tiles[1] + this.tiles[2];
                    break;
                case 1:
                    line = this.tiles[3] + this.tiles[4] + this.tiles[5];
                    break;
                case 2:
                    line = this.tiles[6] + this.tiles[7] + this.tiles[8];
                    break;
                case 3:
                    line = this.tiles[0] + this.tiles[3] + this.tiles[6];
                    break;
                case 4:
                    line = this.tiles[1] + this.tiles[4] + this.tiles[7];
                    break;
                case 5:
                    line = this.tiles[2] + this.tiles[5] + this.tiles[8];
                    break;
                case 6:
                    line = this.tiles[0] + this.tiles[4] + this.tiles[8];
                    break;
                case 7:
                    line = this.tiles[2] + this.tiles[4] + this.tiles[6];
                    break;
            }

            if (line.equals("xxx")) {
                System.out.println("x ganhou");
                return true;
            } else if (line.equals("ooo")) {
                System.out.println("o ganhou");
                return true;
            } 
            
            //else if (this.step == 9) { // se ninguém ganhou e já está na jogada n° 9
                //this.winner = "draw";
            //}
        }
        return false;
    }
    */