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
 * Class for storing a player's characters and other statistics, presently unfinished.
 *
 * @author lmantyla
 */
public class Player {

    String name;
    Point location;
    int gold;
    int potions;
    List<Companion> companions;
    MapPoint mapPoint;

    /**
     *
     * @param name
     * @param location
     */
    public Player(String name, Point location) {
        this.name = name;
        this.location = location;
        this.companions = new ArrayList<Companion>();

        this.gold = 300;
        this.potions = 2;
        if (location.getMapPoint() != null) {
            this.mapPoint = new MapPoint(location.getMapPoint());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    /**
     * Reduces gold if player has enough gold for the purchase.
     * @param price
     * @return true if transaction is successful.
     */
    public boolean buyWithGold(int price) {
        if (price > gold) {
            return false;
        }
        gold -= price;
        return true;
    }

    public List<Companion> getCompanions() {
        return companions;
    }

    /**
     * Adds one or more companions for the player.
     * @param newCompanions
     */
    public void addCompanions(Companion... newCompanions) {
        for (Companion companion : newCompanions) {
            companions.add(companion);
        }
    }

    public MapPoint getMapPoint() {
        return mapPoint;
    }
}
