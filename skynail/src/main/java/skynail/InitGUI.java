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
import skynail.domain.Item;

import skynail.domain.Point;
import skynail.domain.Player;
import skynail.domain.Monster;
import skynail.domain.Trophy;

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

    /**
     * Initialises and creates map for the old TextControl class.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {

        JFrame frame = new JFrame("Skynail");
        frame.setPreferredSize(new Dimension(480, 480));
        frame.setSize(480, 480);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Road a = new Road("Road 1", new MapPoint(20, 50));
        Road b = new Road("Road 2", new MapPoint(60, 110));
        Dungeon c = new Dungeon("Dungeon 1", "Grotto", new Monster(35, 6), new MapPoint(70, 160));

        c.addMonster(new Monster(15, 3));
        c.addMonster(new Monster(20, 4));

        Road d = new Road("Road 3", new MapPoint(120, 110));
        Road e = new Road("Road 4", new MapPoint(150, 130));
        Road f = new Road("Road 5", new MapPoint(190, 110));
        Road g = new Road("Road 6", new MapPoint(180, 150));
        City h = new City("City 1", "Welcome to Corneria!", new MapPoint(170, 200));
        Dungeon i = new Dungeon("Dungeon 2", "Dungeon!", new Monster(20, 5), new MapPoint(250, 220));

        Road j = new Road("Road 7", new MapPoint(60, 200));
        Road k = new Road("Road 8", new MapPoint(80, 230));
        Road l = new Road("Road 9", new MapPoint(50, 260));
        Dungeon m = new Dungeon("Dungeon 3", "Cave!", new Monster(40, 5), new MapPoint(80, 310));
        Road n = new Road("Road 10", new MapPoint(130, 280));
        Road o = new Road("Road 11", new MapPoint(150, 240));

        Road p = new Road("Road 12", new MapPoint(240, 90));
        Road q = new Road("Road 13", new MapPoint(270, 100));
        Dungeon r = new Dungeon("Dungeon 4", "Ruin!", new Monster(30, 5), new MapPoint(320, 110));
        Road s = new Road("Road 14", new MapPoint(360, 140));
        Road t = new Road("Road 15", new MapPoint(400, 200));
        City u = new City("City 2", "You came pretty far!", new MapPoint(380, 240));

        Road v = new Road("Road 16", new MapPoint(320, 165));
        Road x = new Road("Road 17", new MapPoint(300, 200));

        Road y = new Road("Road 18", new MapPoint(350, 300));
        Dungeon z = new Dungeon("Dungeon 5", "Tombs!", new Monster(30, 5), new MapPoint(350, 390));
        z.addMonster(new Monster(25, 5));

        Item potion = new Item("Potion", -15);
        Companion heroine = new Companion("Heroine", 25, 6);
        Companion guide = new Companion("Guide", 15, 4);

        h.addItem(new Item("Potion", -15), 75);
        u.addItem(new Item("Potion", -15), 50);
        u.addItem(new Item("Super Potion", -30), 150);

        i.setTrophy(new Trophy(100, heroine, potion));
        m.setTrophy(new Trophy(100, null, null));
        r.setTrophy(new Trophy(0, guide, null));
        z.setTrophy(new Trophy(100, null, potion));
        z.getTrophy().setSkynail(true);

        
        a.addPointsBothWays(b);
        b.addPointsBothWays(c, d);
        c.addPointsBothWays(d, j);
        d.addPointsBothWays(e);
        e.addPointsBothWays(f, g);
        g.addPointsBothWays(h, i);

        j.addPointsBothWays(k);
        k.addPointsBothWays(l);
        l.addPointsBothWays(m);
        m.addPointsBothWays(n);
        n.addPointsBothWays(o);
        o.addPointsBothWays(h);

        f.addPointsBothWays(p);
        p.addPointsBothWays(q);
        q.addPointsBothWays(r);
        r.addPointsBothWays(s);
        s.addPointsBothWays(t, v);
        t.addPointsBothWays(u);
        
        v.addPointsBothWays(x);
        x.addPointsBothWays(i);
        
        u.addPointsBothWays(y);
        y.addPointsBothWays(z);

        List<Point> worldPoints = new ArrayList<>();

        worldPoints.addAll(Arrays.asList(a, b, c, d, e, f, g, h, i, j, k, l, m,
                n, o, p, q, r, s, t, u, v, x, y, z));

        Player player = new Player("Player", a);
        player.addCompanions(new Companion("Hero", 30, 6));
        player.addItem(new Item("Potion", -15), 3);

        GUIManager manager = new GUIManager();
        DiceRoller diceRoller = new RandomRoller();

        Player aiPlayer = new Player("the Rival", f);
        aiPlayer.addCompanions(new Companion("Villain", 30, 6));

        MapController controller = new MapController(player, worldPoints, diceRoller, manager);
        AIMover aiMover = new AIMover(aiPlayer, controller.getMapLogic(), diceRoller);
        controller.getMapLogic().setAiMover(aiMover);

        manager.setMapController(controller);

        controller.handlePointInput(a);

        frame.getContentPane().add(manager);
        frame.setVisible(true);
        manager.showMapMessage("Go forth and find the Skynail"
                + "\nand bring it to your starting point!");

    }
}
