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
 *
 * @author lmantyla
 */
public class Dungeon extends Road implements Point {

    Trophy trophy;
    List<Monster> monsters;
    boolean cleared;

    String introText;

    /**
     * Creates new Dungeon for the text user interface.
     *
     * @param name name of the Road. ´
     */
    public Dungeon(String name) {
        super(name);
        this.monsters = new ArrayList<Monster>();
    }

    /**
     * Creates new Dungeon for the graphical user interface, MapPoint is
     * included.
     *
     * @param name name of the Road.
     * @param introText Introduction text while entering the dungeon.
     * @param monster monster contained in the dungeon.
     * @param mapPoint mapPoint containing the coordinates of the road.
     */
    public Dungeon(String name, String introText, Monster monster, MapPoint mapPoint) {
        super(name, mapPoint);
        this.introText = introText;
        this.mapPoint = mapPoint;
        this.monsters = new ArrayList<Monster>();
        monsters.add(monster);
        cleared = false;
    }

    @Override
    public int movesRequired(Player team) {
        return 2;
    }

    public String getIntroText() {
        return introText;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    /**
     * Adds monster to the list of monsters.
     *
     * @param monster monster to be added.
     */
    public void addMonster(Monster monster) {
        monsters.add(monster);
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }

    public boolean isCleared() {
        return cleared;
    }

    public void setCleared(boolean cleared) {
        this.cleared = cleared;
    }

    public void setTrophy(Trophy trophy) {
        this.trophy = trophy;
    }

    /**
     * Returns trophy.
     * @return Returns trophy or default empty trophy is none has been defined.
     */
    public Trophy getTrophy() {
        if (trophy == null) {
            return new Trophy(0, null, null);
        }
        return trophy;
    }
}
