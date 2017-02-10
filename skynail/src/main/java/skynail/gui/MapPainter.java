/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;

import skynail.domain.Road;
import skynail.domain.Point;
import skynail.domain.Team;
import skynail.game.MapController;

/**
 * Extended JPanel used to initialise and draw the map screen.
 *
 * @author lmantyla
 */
public class MapPainter extends JPanel {

    List<Point> worldMap;
    List<Point> legalMoves;

    MapListener listener;
    MapButtonListener buttonListener;

    UIManager manager;

    public MapPainter(List<Point> newWorldMap, UIManager manager) {
        worldMap = newWorldMap;
        legalMoves = new ArrayList<>();

        this.manager = manager;

        JPanel panel = new JPanel();
        this.add(panel);
        setSize(450, 450);

        JTextField field = new JTextField("Pixel coordinates of clicks.");
        panel.add(field);

        listener = new MapListener(field, worldMap, manager);
        this.addMouseListener(listener);

        buttonListener = new MapButtonListener(field, manager);
        JButton button = new JButton("Roll dice.");
        //button.setBounds(20, 20, 100, 30);
        button.addActionListener(buttonListener);
        panel.add(button);
    }

    public void paint(Graphics g) {
        super.paint(g);

        for (Point point : worldMap) {
            Graphics2D g2 = (Graphics2D) g;
            Graphics2D g3 = (Graphics2D) g;

            for (Point targetPoint : point.getLinkedPoints()) {
                Line2D line = new Line2D.Float(point.getMapPoint().getX(), point.getMapPoint().getY(),
                        targetPoint.getMapPoint().getX(), targetPoint.getMapPoint().getY());
                g3.setColor(Color.black);
                g3.draw(line);
            }
            if (legalMoves.contains(point)) {
                g2.setColor(Color.BLUE);
            } else {
                g2.setColor(Color.gray);
            }
            g2.fillOval((point.getMapPoint().getX() - 10), point.getMapPoint().getY() - 10, 20, 20);
        }
        Graphics2D g4 = (Graphics2D) g;
        Point point = manager.getMapController().getPlayer().getLocation();
        g4.setColor(Color.red);
        g4.fillRect((point.getMapPoint().getX() - 10), point.getMapPoint().getY() - 10, 20, 20);
    }

    public void update() {
        repaint();
    }

    public MapListener getMapListener() {
        return listener;
    }

    public void setLegalMoves(List<Point> legalMoves) {
        this.legalMoves = legalMoves;
    }
}
