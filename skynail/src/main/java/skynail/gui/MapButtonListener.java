/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.gui;

/**
 * ButtonListener, probably superfluous and should be put in MapPainter.
 * 
 * @author lmantyla
 */

import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.event.ActionListener;
import skynail.game.MapController;

public class MapButtonListener implements ActionListener {

    private JTextField tekstiKentta;
    private UIManager manager; 
    
    public MapButtonListener(JTextField tekstiKentta, UIManager manager) {
        this.tekstiKentta = tekstiKentta;
        this.manager = manager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int roll = manager.getMapController().handleDiceRoll();
        this.tekstiKentta.setText("Your dire roll is " + roll);
    }
}