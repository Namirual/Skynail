/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import skynail.gui.MapPoint;

/**
 * Map point for cities, presently unfinished.
 *
 * @author lmantyla
 */
public class City extends Road implements Point {

    String introText;
    HashMap<Item, Integer> items;

    /**
     * Creates new City.
     *
     * @param name name of the city.
     */
    public City(String name) {
        super(name);
    }

    /**
     * Creates new City with introduction text.
     *
     * @param name Name of the city.
     * @param introText Introduction text while entering the city.
     */
    public City(String name, String introText) {
        super(name);
        this.introText = introText;
        this.items = new HashMap<Item, Integer>();
    }

    /**
     * Creates new City for the graphical user interface, MapPoint is included.
     *
     * @param name Name of the city.
     * @param introText Introduction text while entering the dungeon.
     * @param mapPoint mapPoint containing the coordinates of the city.
     */
    public City(String name, String introText, MapPoint mapPoint) {
        super(name, mapPoint);
        this.introText = introText;
        this.items = new HashMap<Item, Integer>();
    }

    /**
     * Adds item to the list of items sold in the city.
     * @param item Item being added.
     * @param price Selling price of the item.
     */
    public void addItem(Item item, int price) {
        items.put(item, price);
    }

    public HashMap<Item, Integer> getItems() {
        return items;
    }

    public String getIntroText() {
        return introText;
    }
}
