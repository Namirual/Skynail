/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail;

import java.util.Random;
import java.util.Scanner;
import skynail.domain.Road;
import skynail.domain.Point;
import skynail.domain.Player;

/**
 * Initialisation used for TextControl.
 * 
 * @author lmantyla
 */
public class InitText {

    /**
     * Initialises and creates map for the old TextControl class.
     * @param args Command line arguments.
     */
    public static void oldMain(String[] args) {
        Road a = new Road("West 1");
        Road b = new Road("West 2");
        Road c = new Road("West 3");

        Road d = new Road("East 1");
        Road e = new Road("East 2");
        Road f = new Road("East 3");

        Road g = new Road("North 1");
        Road h = new Road("North 2");
        Road i = new Road("North 3");

        a.addPoints(b, d, g);
        b.addPoints(a, c);
        c.addPoints(b);
        d.addPoints(a, e, g);
        e.addPoints(d, f);
        f.addPoints(e);
        g.addPoints(a, d, h);
        h.addPoints(g, i);
        i.addPoints(h);

        Player player = new Player("Pelaaja", a);

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        TextControl peli = new TextControl(player, scanner, random);
        peli.startTextGame();
    }
}
