/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ifrn.jogo;

/**
 *
 * @author pedro
 */
public class NewClass {
    public static void main(String[] args) {
        String teste = "3:2:1";
        System.out.println();
        String[] teste2 = teste.split(":");
        for(String s : teste2) {
            System.out.println(s);
        }
    }
}
