package com.ifrn.jogo;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author pedro
 */
public class GameUI extends JFrame implements ActionListener {

    private CustomButton tiles[] = new CustomButton[9];
    private CustomButton currentTile;
    private boolean iWonLast = false;

    private char icon;
    private char opponentIcon;

    private final int PORT = 5000;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    /**
     * Creates new form NewJFrame
     */
    public GameUI() {
        this.initComponents();
        this.initTiles();
        this.initIO();
    }

    private void initTiles() {
        CustomButton btn;
        for (int i = 0; i < 9; i++) {
            btn = new CustomButton();
            btn.setBackground(Color.WHITE);
            btn.setBorder(null);
            btn.addActionListener(this);
            this.tiles[i] = btn;
            this.tilesPanel.add(btn);
        }
    }

    private void initIO() {
        try {
            // Setup networking
            System.out.println("Starting client IO");
            this.socket = new Socket("localhost", PORT);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Client IO sucessfully started");
        } catch (IOException ex) {
            Logger.getLogger(GameUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CustomButton btn = this.tiles[0];

        int position;
        for (position = 0; position < 9; position++) {
            if (e.getSource() == this.tiles[position]) {
                btn = this.tiles[position];
                break;
            }
        }

        this.currentTile = btn;
        // Previne que o botão possa ser afetado pelo efeito hover
        btn.lock();
        
        this.out.println("MOVE " + position);
    }

    public void initHoverEffect() {
        for (CustomButton btn : this.tiles) {
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (!btn.isLocked()) {
                        btn.setFadedCover(icon);
                    }
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (!btn.isLocked()) {
                        btn.setIcon(null);
                    }
                }
            });
        }
    }

    public void startNetListening() {
        String response;
        try {
            response = this.in.readLine();
            if (response.startsWith("WELCOME")) {
                char mark = response.charAt(8);
                this.icon = mark;
                this.opponentIcon = (mark == 'X') ? 'O' : 'X';
                this.initHoverEffect();
            }
            while (true) {
                response = this.in.readLine();
                
                if (response.startsWith("VALID_MOVE")) {
                    messageLabel.setText("Você já jogou, por favor espere");
                    this.currentTile.setType(icon);
                    this.currentTile.repaint();
                } 
                else if (response.startsWith("OPPONENT_MOVED")) {
                    int location = Integer.parseInt(response.substring(15));
                    this.tiles[location].setType(opponentIcon);
                    this.tiles[location].lock();
                    this.tiles[location].repaint();
                    this.messageLabel.setText("Seu oponente jogou, sua vez");
                } 
                else if (response.startsWith("VICTORY")) {
                    System.out.println(response);
                    this.messageLabel.setText("Você ganhou");
                    this.iWonLast = true;
                    this.wantsToPlayAgain();
                } 
                else if (response.startsWith("DEFEAT")) {
                    System.out.println(response);
                    this.messageLabel.setText("Você perdeu");
                    this.iWonLast = false;
                    this.wantsToPlayAgain();
                } 
                else if (response.startsWith("TIE")) {
                    System.out.println(response);
                    this.messageLabel.setText("Empate");
                    this.wantsToPlayAgain();
                } 
                else if (response.startsWith("MESSAGE")) {
                    this.messageLabel.setText(response.substring(8));
                }
                else if (response.startsWith("SCOREBOARD")) {
                    this.updateScoreBoard(response);
                }
            }
            // se eu mandar sair eu perco meus dados por reseta a conexao
            //this.out.println("QUIT");
        } catch (IOException ex) {
            Logger.getLogger(GameUI.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.socket.close();
            } catch (IOException ex) {
                Logger.getLogger(GameUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void wantsToPlayAgain() {
        int response = JOptionPane.showConfirmDialog(this,
                "Quer jogar denovo?",
                "Jogo da velha é diversão",
                JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            this.resetGame();
            this.out.println("SCOREBOARD");
        } else {
            this.out.println("QUIT");
            this.dispose();
        }
    }

    private void resetGame() {
        for (CustomButton btn : this.tiles) {
            btn.setIcon(null);
            btn.unlock();
        }
        this.messageLabel.setText(this.iWonLast ? "Sua vez" : "");
    }
    
    private void updateScoreBoard(String response) {
        String[] info = response.split(":");
        this.crossScore.setText("ganhou " + info[1]);
        this.circleScore.setText("ganhou " + info[2]);
        this.tiesCount.setText("empatou " + info[3]);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgPanel = new javax.swing.JPanel();
        tilesPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        circleScore = new javax.swing.JLabel();
        crossScore = new javax.swing.JLabel();
        tiesCount = new javax.swing.JLabel();
        messageLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Jogo da Velha");
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(java.awt.Color.white);
        setName("gameFrame"); // NOI18N
        setResizable(false);

        bgPanel.setBackground(new java.awt.Color(255, 255, 255));

        tilesPanel.setBackground(new java.awt.Color(218, 218, 218));
        tilesPanel.setLayout(new java.awt.GridLayout(3, 3, 3, 3));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridLayout(2, 3));

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ifrn/assets/circle_small.png"))); // NOI18N
        jPanel1.add(jLabel10);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ifrn/assets/cross_small.png"))); // NOI18N
        jPanel1.add(jLabel11);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ifrn/assets/scale_small.png"))); // NOI18N
        jPanel1.add(jLabel12);

        circleScore.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 16)); // NOI18N
        circleScore.setForeground(new java.awt.Color(59, 188, 212));
        circleScore.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        circleScore.setText("ganhou 0");
        circleScore.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel1.add(circleScore);

        crossScore.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 16)); // NOI18N
        crossScore.setForeground(new java.awt.Color(57, 137, 212));
        crossScore.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        crossScore.setText("ganhou 0");
        crossScore.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel1.add(crossScore);

        tiesCount.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 16)); // NOI18N
        tiesCount.setForeground(new java.awt.Color(218, 218, 218));
        tiesCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tiesCount.setText("empatou 0");
        tiesCount.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel1.add(tiesCount);

        messageLabel.setText(" ");

        javax.swing.GroupLayout bgPanelLayout = new javax.swing.GroupLayout(bgPanel);
        bgPanel.setLayout(bgPanelLayout);
        bgPanelLayout.setHorizontalGroup(
            bgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgPanelLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(bgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tilesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(messageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(70, 70, 70))
        );
        bgPanelLayout.setVerticalGroup(
            bgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tilesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(messageLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenuBar1.setBorder(null);
        jMenuBar1.setBorderPainted(false);

        jMenu1.setText("Menu");

        jMenuItem1.setText("Procurar Jogador");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenu3.setText("Ajuda");

        jMenu4.setText("Regras do Jogo");

        jMenuItem8.setText("O tabuleiro  é uma matriz  de 3x3;");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem8);

        jMenuItem9.setText("São permitidos 2 jogadores em rede;");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem9);

        jMenuItem10.setText("Os jogadores jogam alternadamente;");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem10);

        jMenuItem11.setText("Uma marcação por vez, numa lacuna que esteja vazia;");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem11);

        jMenuItem12.setText("O objectivo é conseguir três círculos ou três xis em linha;");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem12);

        jMenuItem13.setText("Caso ninguém alcance o objetivo, será empate.");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem13);

        jMenu3.add(jMenu4);

        jMenu5.setText("Dicas de Jogo");

        jMenuItem14.setText("Casa do Meio: é a casa que mais dá chances de vitória;");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem14);

        jMenuItem15.setText("Se não usa-lá, a melhor alternativa é usar um dos quatro cantos;");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem15);

        jMenuItem16.setText("Use a estratégia de \"direita, esquerda, acima, abaixo\";");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem16);

        jMenuItem17.setText(" Use a estratégia dos três cantos;");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem17);

        jMenuItem18.setText("Aperfeiçoe as suas habilidades;");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem18);

        jMenuItem19.setText("Treine suas jogadas.");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem19);

        jMenu3.add(jMenu5);

        jMenu1.add(jMenu3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bgPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bgPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }*/
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        while (true) {
            GameUI game = new GameUI();
            game.setVisible(true);
            game.startNetListening();
        }

        /* Create and display the form 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
            }
        });*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bgPanel;
    private javax.swing.JLabel circleScore;
    private javax.swing.JLabel crossScore;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JLabel tiesCount;
    private javax.swing.JPanel tilesPanel;
    // End of variables declaration//GEN-END:variables
}
