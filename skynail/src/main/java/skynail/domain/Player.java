/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.domain;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import skynail.gui.MapPoint;

/**
 * Class for storing a player's characters and other statistics.
 *
 * @author lmantyla
 */
public class Player {

    String name;
    Point location;
    int gold;
    Map<Item, Integer> items;
    List<Companion> companions;
    MapPoint mapPoint;

    /**
     * Creates new player.
     *
     * @param name Name of the team.
     * @param location Position in which the team starts the game.
     */
    public Player(String name, Point location) {
        this.name = name;
        this.location = location;
        this.companions = new ArrayList<Companion>();

        this.gold = 300;
        this.items = new HashMap<Item, Integer>();
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
     * Buys item and reduces gold if player has enough gold for the purchase.
     *
     * @param item Item being bought.
     * @param price Amount of gold spent in purchase.
     * @return true if transaction is successful.
     */
    public boolean buyItemWithGold(Item item, int price) {
        if (price > gold) {
            return false;
        }
        gold -= price;
        addItem(item, 1);
        return true;
    }

    public List<Companion> getCompanions() {
        return companions;
    }

    /**
     * Adds one or more companions for the player.
     *
     * @param newCompanions One or more Companions.
     */
    public void addCompanions(Companion... newCompanions) {
        for (Companion companion : newCompanions) {
            companions.add(companion);
        }
    }
    
    /**
     * Adds to the player the objects contained in a trophy.
     * @param trophy Trophy to be added.
     */
    public void addTrophyContents(Trophy trophy) {
        gold += trophy.getGold();
        if (trophy.getItem() != null) {
            addItem(trophy.getItem(), 1);
        }
        if (trophy.getNewCompanion() != null) {
            addCompanions(trophy.getNewCompanion());
        }
    }

    public MapPoint getMapPoint() {
        return mapPoint;
    }

    public void setMapPoint(MapPoint mapPoint) {
        this.mapPoint = mapPoint;
    }

    /**
     * Adds an item to the player's item list.
     * @param item Item being added.
     * @param amount Number of items to be added.
     */
    public void addItem(Item item, int amount) {
        if (items.containsKey(item)) {
            items.put(item, items.get(item) + amount);
        } else {
            items.put(item, amount);
        }
    }

    /**
     * Removes one item from the player's list of items.
     * <p>
     * The method lowers the number of items by one and, if the number of items
     * has reached zero, removes the item from the list of items.
     * @param item item to be removed.
     */
    public void reduceItemsByOne(Item item) {
        items.replace(item, items.get(item) - 1);
        if (items.get(item) <= 0) {
            items.remove(item);
        }
    }
    
    public Map<Item, Integer> getItems() {
        return items;
    }
}