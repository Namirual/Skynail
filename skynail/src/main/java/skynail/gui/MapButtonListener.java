/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.gui;

/**
 *
 * @author lmantyla
 */

import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.event.ActionListener;
import skynail.game.MapController;

public class MapButtonListener implements ActionListener {

    private JTextField tekstiKentta;
    private MapController controller; 
    
    public MapButtonListener(JTextField tekstiKentta) {
        this.tekstiKentta = tekstiKentta;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int roll = controller.handleDiceRoll();
        this.tekstiKentta.setText("Your dire roll is " + roll);
    }

    public void setController(MapController controller) {
        this.controller = controller;
    }
}