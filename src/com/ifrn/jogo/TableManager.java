package com.ifrn.jogo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class TableManager extends Thread {

    private String tiles[] = {"", "", "", "", "", "", "", "", ""};
    private CustomButton btnTiles[];
    private int step = 0;
    private String winner = null;
    private String currentSymbol;

    private Socket socket;
    private BufferedReader socketInput;
    private PrintWriter socketOutput;

    public TableManager(String startSymbol, CustomButton[] btnTiles) {
        this.currentSymbol = startSymbol;
        this.btnTiles = btnTiles;
        try {
            this.socket = new Socket("localhost", 5000);
            this.socketInput = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.socketOutput = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException ex) {
            System.out.println("Não foi possível conectar");
        }
    }

    @Override
    public void run() {
        try {
            String[] data;
            String symbol;
            int position;
            int step;
            
            while (true) {
                data = this.socketInput.readLine().split(":");
                
                symbol = data[0];
                position = Integer.parseInt(data[1]);
                step = Integer.parseInt(data[2]);
                
                this.tiles[position] = symbol;
                this.step = step;
                
                this.btnTiles[position].setType(symbol);
                this.btnTiles[position].repaint();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                socketInput.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean nextTurn(int position) {
        if (this.winner != null) {
            //ignora qualquer clique
            return false;
        }

        System.out.println(this.step);

        if (this.tiles[position].equals("")) {
            //this.tiles[position] = this.currentSymbol;
            //this.btnTiles[position].setType(this.currentSymbol);
            //this.step++;
            
            System.out.println("Colocou " + this.currentSymbol);
            this.socketOutput.println(this.currentSymbol+":"+position+":"+this.step);

            this.checkWinner();
            this.flipSymbol();
        }
        return true;
    }

    private void checkWinner() {
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
                this.winner = "x";
                System.out.println("x ganhou");
                return;
            } else if (line.equals("ooo")) {
                System.out.println("o ganhou");
                this.winner = "o";
                return;
            } else if (this.step == 9) { // se ninguém ganhou e já está na jogada n° 9
                this.winner = "draw";
            }
        }
    }

    public String getCurrentSymbol() {
        return this.currentSymbol;
    }

    public void flipSymbol() {
        if (this.currentSymbol.equals("o")) {
            this.currentSymbol = "x";
        } else if (this.currentSymbol.equals("x")) {
            this.currentSymbol = "o";
        }
    }

    public String getWinner() {
        return this.winner;
    }

    public boolean hasWinner() {
        return this.winner != null;
    }
    
    public void repaintTiles() {
        
    }
}
