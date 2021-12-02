/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinerpg;

import javax.swing.ImageIcon;

/**
 *
 * @author vadimkaz58
 */
public class Player {
    public String name = null;
    public ImageIcon img = null;
    public int score = 0;
    
    Player(String name, ImageIcon img, int score) {
        this.name = name;
        this.img = img;
        this.score = score;
    }
    
    public Player(String name) {
        this.name = name;
    }
}
