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
import skynail.domain.Player;
import skynail.gui.MapPoint;
import skynail.gui.MapPainter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import skynail.domain.Road;
import skynail.domain.City;
import skynail.domain.Companion;
import skynail.domain.Dungeon;

import skynail.domain.Point;
import skynail.domain.Player;
import skynail.domain.Monster;

import skynail.game.*;
import skynail.gui.GUIManager;
import skynail.service.DiceRoller;
import skynail.service.RandomRoller;

/**
 * Initialisation for the graphical user interface.
 * 
 * @author lmantyla
 */

public class InitGUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Skynail");
        frame.setPreferredSize(new Dimension(480, 480));
        frame.setSize(480, 480);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Road a = new Road("Test 1", new MapPoint(20, 50));
        Road b = new Road("Test 2", new MapPoint(60, 110));
        Dungeon c = new Dungeon("Test 3", "Grotto", new Monster(35, 6), new MapPoint(70, 160));
        Road d = new Road("Test 4", new MapPoint(120, 110));
        Road e = new Road("Test 5", new MapPoint(150, 130));
        Road f = new Road("Test 6", new MapPoint(190, 110));
        Road g = new Road("Test 7", new MapPoint(180, 150));
        City h = new City("Test 8", "Welcome to Corneria!", new MapPoint(170, 200));
        Dungeon i = new Dungeon("Test 9", "Dungeon!", new Monster(20, 5), new MapPoint(250, 220));

        c.addMonster(new Monster(15, 3));
        c.addMonster(new Monster(20, 4));

        a.addPointsBothWays(b);
        b.addPointsBothWays(c, d);
        c.addPointsBothWays(d);
        d.addPointsBothWays(e);
        e.addPointsBothWays(f, g);
        g.addPointsBothWays(h, i);

        
        List<Point> worldPoints = new ArrayList<>();

        worldPoints.addAll(Arrays.asList(a, b, c, d, e, f, g, h, i));

        Player player = new Player("Pelaaja", a);
        player.addCompanions(new Companion(30, 6, "Hero"), new Companion(20, 4, "Sidekick"));
        
        GUIManager manager = new GUIManager();                
        DiceRoller diceRoller = new RandomRoller();
        
        MapController controller = new MapController(player, worldPoints, diceRoller, manager);
        manager.setMapController(controller);

        controller.handlePointInput(a);
        
        frame.getContentPane().add(manager);
        frame.setVisible(true);
    }
}
