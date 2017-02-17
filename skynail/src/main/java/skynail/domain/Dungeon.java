/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.domain;

import java.util.ArrayList;
import java.util.List;
import skynail.gui.MapPoint;

/**
 * Map point containing a monster and treasure, currently partially unfinished.
 * @author lmantyla
 */
public class Dungeon extends Road implements Point {

    int treasure;
    Monster monster;

    String introText;

    public Dungeon(String name) {
        super(name);
    }

    public Dungeon(String name, String introText, Monster monster, MapPoint mapPoint) {
        super(name, mapPoint);
        this.introText = introText;
        this.mapPoint = mapPoint;
        this.monster = monster;
    }

    @Override
    public int movesRequired(Player team) {
        return 2;
    }

    public String getIntroText() {
        return introText;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }
}
