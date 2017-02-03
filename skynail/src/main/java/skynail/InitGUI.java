/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail;

/**
 *
 * @author lmantyla
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.*;
import skynail.domain.Road;
import skynail.domain.Team;
import skynail.gui.MapPoint;
import skynail.gui.MapSwingPainter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import skynail.domain.Road;
import skynail.domain.Point;
import skynail.domain.Team;

import skynail.game.*;
import skynail.service.DiceRoller;
import skynail.service.RandomRoller;

public class InitGUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Skynail");
        frame.setPreferredSize(new Dimension(450, 450));
        frame.setSize(450, 450);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Road a = new Road("Test 1", new MapPoint(20, 50));
        Road b = new Road("Test 2", new MapPoint(60, 110));
        Road c = new Road("Test 3", new MapPoint(70, 160));
        Road d = new Road("Test 4", new MapPoint(120, 110));
        Road e = new Road("Test 5", new MapPoint(150, 130));
        Road f = new Road("Test 6", new MapPoint(190, 110));
        Road g = new Road("Test 7", new MapPoint(180, 150));

        a.addPointsBothWays(b);
        b.addPointsBothWays(c, d);
        c.addPointsBothWays(d);
        d.addPointsBothWays(e);
        e.addPointsBothWays(f, g);

        List<Point> worldPoints = new ArrayList<>();

        worldPoints.addAll(Arrays.asList(a, b, c, d, e, f, g));
        
        Team player = new Team("Pelaaja", a);
        MapSwingPainter mapPainter = new MapSwingPainter(worldPoints, player);

        DiceRoller diceRoller = new RandomRoller();
        
        MapController controller = new MapController(player, diceRoller, mapPainter);
        controller.handlePointInput(a);
        mapPainter.setController(controller);
        
        frame.getContentPane().add(mapPainter);
        frame.setVisible(true);
    }
}
