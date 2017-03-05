/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;

import java.awt.event.MouseEvent;

import java.util.List;
import skynail.domain.Point;

import skynail.domain.Player;
import skynail.game.MapController;

/**
 * MouseListener that receives mouse input on the map.
 * 
 * @author lmantyla
 */
public class MapListener implements MouseListener {

    private JLabel tieto;
    GUIManager manager;

    public MapListener(JLabel tieto, GUIManager manager) {
        this.tieto = tieto;
        this.manager = manager;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        //tieto.setText(e.getX() + " " + e.getY());

        for (Point point : manager.getMapController().getWorldMap()) {
            if (point.getMapPoint().checkIfInside(e.getX(), e.getY())) {
                //tieto.setText("Point Clicked! " + e.getX() + " " + e.getY());
                manager.getMapController().handlePointInput(point);
                manager.getMapPainter().update();
                return;
                
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //tieto.setText(e.getX() + " " + e.getY());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
