# Jogo da Velha Distribuído

## Objetivo

Este projeto foi desenvolvido para a disciplina de Sistemas Distribuídos do curso de Tecnologia em Sistemas para Internet do IFRN. O objetivo é criar um Jogo da Velha multiplayer, onde dois jogadores podem se enfrentar através de uma conexão de rede usando web sockets.

## Funcionalidades

* **Jogo Multiplayer:** Permite que dois jogadores joguem entre si através da rede.
* **Interface Gráfica:** Interface intuitiva para interação dos jogadores.
* **Comunicação em Rede:** Utiliza sockets para comunicação entre os jogadores.
* **Placar:** Mantém o registro de vitórias, derrotas e empates.
* **Opção de Novo Jogo:** Permite que os jogadores joguem novamente após o término de uma partida.
* **Regras do Jogo:** Implementa as regras clássicas do Jogo da Velha.
* **Dicas de Jogo:** Menu com dicas e estratégias para os jogadores.

## Tecnologias Utilizadas

* **Java:** Linguagem de programação principal.
* **Swing:** Biblioteca para criação da interface gráfica.
* **Sockets:** Para comunicação em rede.

## Como Executar

1.  **Pré-requisitos:**
    * Java Development Kit (JDK) instalado.

2.  **Execução:**
    * Compile os arquivos Java do projeto.
    * Execute a classe principal (`GameUI`). O jogo será iniciado e aguardará a conexão de outro jogador.

## Como Jogar

1.  **Inicie o Jogo:** Execute o arquivo `GameUI.java`.
2.  **Conecte-se:** Aguarde até que outro jogador se conecte.
3.  **Jogue:**
    * Os jogadores se alternam para marcar uma casa no tabuleiro (X ou O).
    * O objetivo é conseguir três marcas iguais em linha, coluna ou diagonal.
    * O jogo termina quando um jogador vence ou ocorre um empate.
4.  **Novo Jogo:** Ao final da partida, os jogadores podem optar por jogar novamente.

## 📂 Estrutura do Código

O código do projeto está organizado da seguinte forma:

* `GameUI.java`: Classe principal que contém a lógica do jogo e a interface gráfica.
* `CustomButton.java`: Classe que representa os botões do tabuleiro.
* `MainWindow.java`: Classe que representa a janela principal do jogo.
* `com.ifrn.assets`: Pacote contendo os arquivos de imagem utilizados na interface.

## Informações Adicionais

* O jogo foi desenvolvido como parte da avaliação da disciplina de Sistemas Distribuídos no IFRN.
* O código inclui comentários para facilitar a compreensão.

## 🧑‍💻 Desenvolvedores

[Pedro Henrique Amorim de Azevedo](https://github.com/seu-usuario)
[José Ramos Chacon](https://github.com/JoseChacon01)
