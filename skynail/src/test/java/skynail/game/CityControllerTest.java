/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.game;

import java.util.Arrays;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import skynail.domain.Player;
import skynail.domain.Road;
import skynail.domain.City;
import skynail.domain.Item;
import skynail.gui.MapPoint;

import skynail.gui.UIManager;
import skynail.service.DiceRoller;

/**
 *
 * @author lmantyla
 */
public class CityControllerTest {

    Player player;
    MapController mapController;
    UIManager uiManager;
    CityController controller;

    City city;
    Item item;

    private class StaticRoller implements DiceRoller {

        public StaticRoller() {
        }

        @Override
        public int diceThrow(int luku) {
            return 2;
        }
    }

    @Before
    public void setUp() {
        city = new City("Test", "Welcome to Test", new MapPoint(170, 200));

        player = new Player("Pelaaja", city);
        uiManager = new TestUIManager();

        controller = new CityController(uiManager, player, city);

        item = new Item("Potion", -15);
        city.addItem(item, 50);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void canBuy() {
        player.setGold(200);
        assertTrue(controller.handleItemBuy(item));
        assertEquals(player.getGold(), 150);
    }

    @Test
    public void canBuyThingsWithExactGold() {
        player.setGold(50);
        assertTrue(controller.handleItemBuy(item));
        assertEquals(player.getGold(), 0);
    }

    @Test
    public void cannotBuyWhenNotEnoughGold() {
        player.setGold(49);
        assertFalse(controller.handleItemBuy(item));
        assertEquals(player.getGold(), 49);
    }

    @Test
    public void cannotBuyWhenItemIsNotSoldInCity() {
        player.setGold(200);
        Item otherItem = new Item("Super Potion", -30);
        assertFalse(controller.handleItemBuy(otherItem));
        assertEquals(player.getGold(), 200);
    }

}
