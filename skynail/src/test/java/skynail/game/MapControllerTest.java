/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import skynail.service.PathService;
import static org.junit.Assert.*;
import skynail.domain.City;
import skynail.domain.Point;

import skynail.domain.Road;
import skynail.domain.Player;
import skynail.gui.MapListener;
import skynail.gui.MapPainter;
import skynail.gui.UIManager;
import skynail.service.DiceRoller;

/**
 *
 * @author lmantyla
 */
public class MapControllerTest {

    Road a;
    Road b;
    Road c;
    Road d;

    Player player;

    MapController mapController;

    UIManager uiManager;

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
        a = new Road("Test 1");
        b = new Road("Test 2");
        c = new Road("Test 3");
        d = new Road("Test 4");

        a.addPointsBothWays(b);
        b.addPointsBothWays(c);
        c.addPointsBothWays(d);

        player = new Player("Pelaaja", a);

        uiManager = new TestUIManager();

        DiceRoller diceRoller = new StaticRoller();
        mapController = new MapController(player, Arrays.asList(a, b, c, d), diceRoller, uiManager);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void gettersRequiredByUIWork() {
        assertEquals(mapController.getPlayer(), player);
        assertEquals(mapController.getWorldMap().size(), 4);
        assertEquals(mapController.getPathPoints().size(), 0);
        assertEquals(mapController.isMoving(), false);
    }

    @Test
    public void mapControlMovesPlayerTeam() {
        mapController.handlePointInput(b);
        assertEquals(player.getLocation(), b);
    }

    @Test
    public void mapControlProducesListOfLegalMoves() {
        mapController.handleDiceRoll();
        TestUIManager manager = (TestUIManager) uiManager;
        assertEquals(mapController.getLegalMoves().size(), 2);
    }

    @Test
    public void afterLegalMovementZeroLegalMoves() {
        mapController.handlePointInput(b);
        TestUIManager manager = (TestUIManager) uiManager;
        assertEquals(mapController.getLegalMoves().size(), 0);
    }

    @Test
    public void CanMoveMoreThanOneStep() {
        mapController.handleDiceRoll();
        mapController.handlePointInput(c);
        assertEquals(player.getLocation(), c);
    }

    @Test
    public void CannotMoveMoreThanDiceRoll() {
        mapController.handleDiceRoll();
        mapController.handlePointInput(d);
        assertNotEquals(player.getLocation(), d);
    }

    @Test
    public void diceRollMoreThanZero() {
        int luku = 0;
        luku = mapController.handleDiceRoll();
        assertNotEquals(luku, 0);
    }

    @Test
    public void canBuy() {
        player.setGold(200);
        assertTrue(mapController.handleCityBuy());
        assertEquals(player.getGold(), 100);
    }

    @Test
    public void canBuyThingsWithExactGold() {
        player.setGold(100);
        assertTrue(mapController.handleCityBuy());
        assertEquals(player.getGold(), 0);
    }

    @Test
    public void cannotBuyWhenNotEnoughGold() {
        player.setGold(99);
        assertFalse(mapController.handleCityBuy());
        assertEquals(player.getGold(), 99);
    }
}
