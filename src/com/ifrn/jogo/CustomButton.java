/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifrn.jogo;

/**
 *
 * @author 20201198060009
 */
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CustomButton extends JButton {
    
    private char type;
    private boolean locked = false;
    
    public CustomButton() {
        super();
    }
    
    public void setType(char type) {
        this.type = type;
        if(type == 'O') {
            this.setIcon(new ImageIcon(getClass().getResource("/com/ifrn/assets/circle.png")));
        } else if(type == 'X') {
            this.setIcon(new ImageIcon(getClass().getResource("/com/ifrn/assets/cross.png"))); // NOI18N
        } 
    }
    
    public void setFadedCover(char type) {
        if(type == 'O') {
            this.setIcon(new ImageIcon(getClass().getResource("/com/ifrn/assets/circle_faded.png"))); // NOI18N
        } else if(type == 'X') {
            this.setIcon(new ImageIcon(getClass().getResource("/com/ifrn/assets/cross_faded.png"))); // NOI18N
        }
    }
    
    public char getType() {
        return this.type;
    }
    
    public void lock() {
        this.locked = true;
    }
    
    public void unlock() {
        this.locked = false;
    }
    
    public boolean isLocked() {
        return this.locked;
    }
    
}
