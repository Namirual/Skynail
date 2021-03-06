/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import skynail.domain.Dungeon;
import skynail.domain.City;

import skynail.domain.Road;
import skynail.domain.Point;
import skynail.domain.Player;
import skynail.game.AIState;
import skynail.game.MapController;

/**
 * Extended JPanel used to initialise and draw the map screen.
 *
 * @author lmantyla
 */
public class MapPainter extends JPanel {

    MapListener listener;
    GUIManager manager;

    JPanel buttonPanel;
    JButton rollDice;
    JButton explore;
    JLabel turnLabel;

    final int sizex = 480;
    final int sizey = 480;

    public MapPainter(GUIManager manager) {
        this.manager = manager;

        setPreferredSize(new Dimension(sizex, sizey));
        setSize(sizex, sizey);

        buttonPanel = new JPanel();
        this.add(buttonPanel);

        JLabel field = new JLabel("Roll the dice!");
        turnLabel = new JLabel("Turn 1  ");

        buttonPanel.add(turnLabel);
        buttonPanel.add(field);

        listener = new MapListener(field, manager);
        this.addMouseListener(listener);

        rollDice = new JButton("Roll dice");
        explore = new JButton("Enter area");

        rollDice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int roll = manager.getMapController().handleDiceRoll();
                field.setText("Your dice roll is " + roll + ".");
                update();
            }
        });

        explore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.getMapController().handleEnteringArea();
            }
        });

        buttonPanel.add(rollDice);
        buttonPanel.add(explore);
        explore.setVisible(false);

    }

    public void movePath(Player player, List<Point> characterPath) {
        if (characterPath.size() <= 1) {
            return;
        }

        buttonPanel.setVisible(false);

        player.setMapPoint(new MapPoint(characterPath.get(characterPath.size() - 1).getMapPoint()));

        for (int luku = characterPath.size() - 1; luku > 0; luku--) {
            moveInALine(characterPath.get(luku), characterPath.get(luku - 1),
                    player.getMapPoint());
        }
        updateGUI(manager.getMapController().getPlayer().getLocation());

        buttonPanel.setVisible(true);
    }

    public void moveInALine(Point origin, Point target, MapPoint characterPosition) {
        for (int luku = 0; luku < 20; luku++) {
            characterPosition.setXf(characterPosition.getXf()
                    + (target.getMapPoint().getX() - origin.getMapPoint().getX()) / 20f);
            characterPosition.setYf(characterPosition.getYf()
                    + (target.getMapPoint().getY() - origin.getMapPoint().getY()) / 20f);
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            this.paintImmediately(0, 0, sizex, sizey);
        }

        characterPosition = new MapPoint(target.getMapPoint().getX(), target.getMapPoint().getY());
    }

    public void updateGUI(Point currentPoint) {
        if (currentPoint.getClass().equals(Dungeon.class)) {
            Dungeon dungeon = (Dungeon) currentPoint;
            if (!dungeon.isCleared()) {
                explore.setText("Enter dungeon");
                explore.setVisible(true);
            }
        } else if (currentPoint.getClass().equals(City.class)) {
            explore.setText("Enter city");
            explore.setVisible(true);
        } else {
            explore.setVisible(false);
        }
        turnLabel.setText("Turn " + manager.getMapController().getMapLogic().getTurnNumber() + "  ");
        
    }

    public void paint(Graphics g) {
        super.paint(g);

        List<Point> legalMoves = new ArrayList<Point>();
        legalMoves.addAll(manager.getMapController().getLegalMoves().keySet());

        for (Point point : manager.getMapController().getWorldMap()) {
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
            if (point.getClass().equals(Road.class)) {
                g2.fillOval((point.getMapPoint().getX() - 10), point.getMapPoint().getY() - 10, 20, 20);
            } else {
                g2.fillOval((point.getMapPoint().getX() - 15), point.getMapPoint().getY() - 15, 30, 30);
                g2.setColor(Color.WHITE);
                if (point.getClass().equals(Dungeon.class)) {
                    Dungeon dungeon = (Dungeon) point;
                    if (dungeon.isCleared()) {
                        g2.setColor(Color.BLACK);
                    }
                }
                g2.fillOval((point.getMapPoint().getX() - 10), point.getMapPoint().getY() - 10, 20, 20);
            }
        }
        Graphics2D g4 = (Graphics2D) g;

        MapPoint characterPosition = manager.getMapController().getPlayer().getMapPoint();

        if (characterPosition == null) {
            Point point = manager.getMapController().getPlayer().getLocation();
            g4.setColor(Color.red);
            g4.fillRect((point.getMapPoint().getX() - 10), point.getMapPoint().getY() - 10, 20, 20);
        } else {
            g4.setColor(Color.red);
            g4.fillRect((characterPosition.getX() - 10), characterPosition.getY() - 10, 20, 20);
        }

        MapPoint enemyPosition = manager.getMapController().getMapLogic().getAiMover().getAiPlayer().getMapPoint();
        if (manager.getMapController().getMapLogic().getAiMover().getAiState() != AIState.dead) {
            if (enemyPosition == null) {
                Point point = manager.getMapController().getMapLogic().getAiMover().getAiPlayer().getLocation();
                g4.setColor(Color.black);
                g4.fillRect((point.getMapPoint().getX() - 10), point.getMapPoint().getY() - 10, 20, 20);
            } else {
                g4.setColor(Color.black);
                g4.fillRect((enemyPosition.getX() - 10), enemyPosition.getY() - 10, 20, 20);
            }
        }
    }

    public void showMessageWindow(String text, boolean endWindow) {

        JDialog message = new JDialog();

        message.setPreferredSize(new Dimension(320, 240));
        message.setSize(320, 240);
        message.setLocationRelativeTo(this);
        message.setModal(true);
        message.setUndecorated(true);

        JButton closeButton = new JButton("OK!");
        //JTextArea messageText = new JTextArea();

        JPanel textPanel = new JPanel(new GridLayout(0, 1));
        String[] textLines = text.split("\n");
        for (String line : textLines) {
            JLabel label = new JLabel(line);
            textPanel.add(label);
        }

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message.setVisible(false);
                if (endWindow) {
                    System.exit(0);
                }
                message.dispose();
            }
        });

        JPanel windowPanel = new JPanel();
        windowPanel.add(textPanel);
        windowPanel.add(closeButton);
        message.getContentPane().add(windowPanel);

        message.setVisible(true);
    }

    public void showMessageWindow(String text) {
        showMessageWindow(text, false);
    }

    public void update() {
        repaint();
    }

    public MapListener getMapListener() {
        return listener;
    }
}
