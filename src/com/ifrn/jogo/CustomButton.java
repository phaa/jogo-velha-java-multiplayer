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
    
    private String type = "";
    
    public CustomButton() {
        super();
    }
    
    public void setType(String type) {
        this.type = type;
        if("o".equals(type)) {
            this.setIcon(new ImageIcon(getClass().getResource("/com/ifrn/assets/circle.png")));
        } else if("x".equals(type)) {
            this.setIcon(new ImageIcon(getClass().getResource("/com/ifrn/assets/cross.png"))); // NOI18N
        }
    }
    
    public String getType() {
        return this.type;
    }
    
}
