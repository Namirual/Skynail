/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.game;

import java.util.Arrays;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import skynail.domain.City;
import skynail.domain.Companion;
import skynail.domain.Dungeon;
import skynail.domain.Monster;
import skynail.domain.Player;
import skynail.domain.Point;
import skynail.domain.Road;
import skynail.gui.UIManager;
import skynail.service.DiceRoller;

/**
 *
 * @author lmantyla
 */
public class AIMoverTest {

    Road a;
    Road b;
    Road c;
    Road d;
    Dungeon e;
    Road f;
    Road g;
    City h;

    Player player;
    MapController mapController;
    UIManager uiManager;
    AIMover aiMover;
    StaticRoller roller;

    private class StaticRoller implements DiceRoller {

        int staticNumber;

        public StaticRoller(int staticNumber) {
            this.staticNumber = staticNumber;
        }

        public void setStaticNumber(int staticNumber) {
            this.staticNumber = staticNumber;
        }

        @Override
        public int diceThrow(int number) {
            return staticNumber;
        }
    }

    @Before
    public void setUp() {
        a = new Road("Test 1");
        b = new Road("Test 2");
        c = new Road("Test 3");
        d = new Road("Test 4");
        e = new Dungeon("Test Dungeon");

        f = new Road("Test 2");
        g = new Road("Test 3");
        h = new City("Test City");

        a.addPointsBothWays(b);
        b.addPointsBothWays(c);
        c.addPointsBothWays(d);
        d.addPointsBothWays(e);

        a.addPointsBothWays(f);
        f.addPointsBothWays(g);
        g.addPointsBothWays(h);

        player = new Player("Pelaaja", a);

        uiManager = new TestUIManager();

        roller = new StaticRoller(2);
        mapController = new MapController(player, Arrays.asList(a, b, c, d), roller, uiManager);

        aiMover = new AIMover(new Player("Test AI", a), mapController.getMapLogic(), roller);
        mapController.getMapLogic().setAiMover(aiMover);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void aiMovesTowardsDungeon() {
        List<Point> points = aiMover.moveAiPlayer();
        assertEquals(aiMover.getAiPlayer().getLocation(), c);
    }

    @Test
    public void aiStopsAtDungeon() {
        roller.setStaticNumber(3);
        aiMover.moveAiPlayer();
        aiMover.moveAiPlayer();
        assertEquals(mapController.getMapLogic().getAiMover().getAiPlayer().getLocation(), e);
    }

    @Test
    public void aiMovesTowardsCity() {
        aiMover.setAiState(AIState.goToCity);
        List<Point> points = aiMover.moveAiPlayer();
        assertEquals(aiMover.getAiPlayer().getLocation(), g);
    }

    @Test
    public void aiStopsAtCity() {
        aiMover.setAiState(AIState.goToCity);
        aiMover.moveAiPlayer();
        aiMover.moveAiPlayer();
        assertEquals(mapController.getMapLogic().getAiMover().getAiPlayer().getLocation(), h);
    }

    @Test
    public void visitingDungeonWithMonstersChangesStateToCityVisit() {
        e.addMonster(new Monster(10, 10));
        aiMover.getAiPlayer().addCompanions(new Companion("Character", 10, 10));
        aiMover.setTarget(e);
        mapController.getMapLogic().getAiMover().getAiPlayer().setLocation(e);
        aiMover.handleReachingTarget();
        assertEquals(aiMover.getAiState(), AIState.goToCity);
    }

    @Test
    public void visitingDungeonRemovesMonsters() {
        e.addMonster(new Monster(10, 10));
        e.addMonster(new Monster(10, 10));
        e.addMonster(new Monster(10, 10));
        aiMover.getAiPlayer().addCompanions(new Companion("Character", 10, 10));
        aiMover.setTarget(e);
        mapController.getMapLogic().getAiMover().getAiPlayer().setLocation(e);
        aiMover.handleReachingTarget();
        assertEquals(e.getMonsters().size(), 2);
    }

    @Test
    public void visitingCityIncreasesAIHealthAndChangesState() {
        aiMover.getAiPlayer().addCompanions(new Companion("Character", 10, 10));
        aiMover.setTarget(h);
        aiMover.setHealth(0);
        mapController.getMapLogic().getAiMover().getAiPlayer().setLocation(h);
        aiMover.handleReachingTarget();
        assertEquals(aiMover.getHealth(), 1);
        assertEquals(aiMover.getAiState(), AIState.goToDungeon);
    }

    @Test
    public void aiChasesIfPlayerHasFoundSkynail() {
        aiMover.getAiPlayer().addCompanions(new Companion("Character", 10, 10));
        aiMover.setTarget(h);
        player.setLocation(b);
        mapController.getMapLogic().setPlayerWithSkynail(player);
        mapController.getMapLogic().getAiMover().getAiPlayer().setLocation(h);
        aiMover.moveAiPlayer();
        assertEquals(aiMover.getTarget(), b);
    }
    
    @Test
    public void aiMovesAtGoalIfAIHasFoundSkynail() {
        aiMover.getAiPlayer().addCompanions(new Companion("Character", 10, 10));
        aiMover.setTarget(h);
        player.setLocation(b);
        mapController.getMapLogic().setGoal(a);
        mapController.getMapLogic().setPlayerWithSkynail(aiMover.getAiPlayer());
        mapController.getMapLogic().getAiMover().getAiPlayer().setLocation(h);
        aiMover.moveAiPlayer();
        assertEquals(aiMover.getTarget(), a);
    }
}
