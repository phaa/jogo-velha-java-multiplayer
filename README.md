# Distributed Tic-Tac-Toe Game

## Objective

This project was developed for the Distributed Systems course of the Internet Systems Technology program at IFRN. The objective is to create a multiplayer Tic-Tac-Toe game where two players can compete against each other through a network connection using web sockets.

## Features

* **Multiplayer Game:** Allows two players to play against each other over the network.
* **Graphical Interface:** Intuitive interface for player interaction.
* **Network Communication:** Uses sockets for communication between players.
* **Scoreboard:** Keeps track of wins, losses, and draws.
* **New Game Option:** Allows players to play again after a match ends.
* **Game Rules:** Implements the classic Tic-Tac-Toe rules.
* **Game Tips:** Menu with tips and strategies for players.

## Technologies Used

* **Java:** Main programming language.
* **Swing:** Library for creating the graphical interface.
* **Sockets:** For network communication.

## How to Run

1.  **Prerequisites:**
    * Java Development Kit (JDK) installed.

2.  **Execution:**
    * Compile the Java files of the project.
    * Run the main class (`GameUI`). The game will start and wait for another player to connect.

## How to Play

1.  **Start the Game:** Execute the `GameUI.java` file.
2.  **Connect:** Wait until another player connects.
3.  **Play:**
    * Players take turns marking a square on the board (X or O).
    * The goal is to get three of their marks in a row, column, or diagonal.
    * The game ends when a player wins or a draw occurs.
4.  **New Game:** At the end of the match, players can choose to play again.

## Code Structure

The project code is organized as follows:

* `GameUI.java`: Main class containing the game logic and graphical interface.
* `CustomButton.java`: Class representing the buttons on the game board.
* `MainWindow.java`: Class representing the main window of the game.
* `com.ifrn.assets`: Package containing the image files used in the interface.

## Additional Information

* The game was developed as part of the evaluation for the Distributed Systems course at IFRN.
* The code includes comments for easier understanding.

## Developers

[Pedro Henrique Amorim de Azevedo](https://github.com/seu-usuario)
[José Ramos Chacon](https://github.com/JoseChacon01)
