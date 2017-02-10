/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.domain;

/**
 * Class for storing the player's team, presently unfinished.
 * 
 * @author lmantyla
 */
public class Team {
    String name;
    Point location;
    int gold;

    public Team(String name, Point location) {
        this.name = name;
        this.location = location;
        this.gold = 300;
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

    public boolean buyWithGold(int price) {
        if (price > gold) {
            return false;
        }
        gold -= price;
        return true;
    }
}