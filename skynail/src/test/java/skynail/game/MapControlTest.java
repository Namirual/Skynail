/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import skynail.service.PathService;
import static org.junit.Assert.*;
import skynail.domain.Point;

import skynail.domain.Road;
import skynail.domain.Team;
import skynail.gui.MapListener;
import skynail.gui.MapPainter;
import skynail.service.DiceRoller;

/**
 *
 * @author lmantyla
 */
public class MapControlTest {

    Road a;
    Road b;
    Road c;

    Team player;

    MapController mapControl;

    private class StaticRoller implements DiceRoller {

        public StaticRoller() {
        }

        @Override
        public int diceThrow(int luku) {
            return luku - 1;
        }
    }

    static class BlankPainter implements MapPainter {

        List<Point> worldMap;
        List<Point> legalMoves;

        public void setLegalMoves(List<Point> legalMoveList) {
            this.legalMoves = legalMoves;
        }

        @Override
        public void setController(MapController controller) {
        }

        public void update() {

        }
    }

    @Before
    public void setUp() {
        a = new Road("Test 1");
        b = new Road("Test 2");
        c = new Road("Test 3");

        a.addPointsBothWays(b);
        b.addPointsBothWays(c);

        player = new Team("Pelaaja", a);

        DiceRoller diceRoller = new StaticRoller();
        MapPainter painter = new BlankPainter();
        mapControl = new MapController(player, diceRoller, painter);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void mapControlMovesPlayerTeam() {
        mapControl.handlePointInput(b);
        assertEquals(player.getLocation(), b);
    }

    @Test
    public void diceRollMoreThanZero() {
        int luku = 0;
        luku = mapControl.handleDiceRoll();
        assertNotEquals(luku, 0);
    }
}
