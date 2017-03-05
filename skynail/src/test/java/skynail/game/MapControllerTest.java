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
import skynail.domain.Companion;
import skynail.domain.Dungeon;
import skynail.domain.Item;
import skynail.domain.Monster;
import skynail.domain.Point;

import skynail.domain.Road;
import skynail.domain.Player;
import skynail.domain.Trophy;
import skynail.gui.MapListener;
import skynail.gui.MapPainter;
import skynail.gui.MapPoint;
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
    Dungeon e;

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
        e = new Dungeon("Test Dungeon");

        a.addPointsBothWays(b);
        b.addPointsBothWays(c);
        c.addPointsBothWays(d);
        d.addPointsBothWays(e);

        player = new Player("Pelaaja", a);

        uiManager = new TestUIManager();

        DiceRoller diceRoller = new StaticRoller();
        mapController = new MapController(player, Arrays.asList(a, b, c, d), diceRoller, uiManager);

        AIMover aiMover = new AIMover(new Player("Test AI", a), mapController.getMapLogic(), diceRoller);
        mapController.getMapLogic().setAiMover(aiMover);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void gettersRequiredByUIWork() {
        assertEquals(mapController.getPlayer(), player);
        assertEquals(mapController.getWorldMap().size(), 4);
        assertEquals(mapController.getPathPoints().size(), 0);
    }

    @Test
    public void settersWork() {
        Player player2 = new Player("Toinen pelaaja", b);
        mapController.setPlayer(player2);
        assertEquals(mapController.getPlayer().getName(), "Toinen pelaaja");
        mapController.setWorldMap(Arrays.asList(a, b));
        assertEquals(mapController.getWorldMap().size(), 2);
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
        //TestUIManager manager = (TestUIManager) uiManager;
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
        int number = 0;
        number = mapController.handleDiceRoll();
        assertNotEquals(number, 0);
    }

    @Test
    public void rollingDiceWhenAlreadyRolledMovesTurn() {
        int number = mapController.getMapLogic().getTurnNumber();
        mapController.handleDiceRoll();
        assertEquals(number, mapController.getMapLogic().getTurnNumber());
        mapController.handleDiceRoll();
        assertEquals(number + 1, mapController.getMapLogic().getTurnNumber());
    }

    @Test
    public void aiMovesWithNextTurn() {
        mapController.getMapLogic().processTurn();
        assertNotSame(mapController.getMapLogic().getAiMover().getAiPlayer().getLocation(), a);
    }

    @Test
    public void playerVictoryWorks() {
        mapController.getMapLogic().setGoal(a);
        mapController.getMapLogic().setPlayerWithSkynail(player);
        mapController.getPlayer().setLocation(a);

        mapController.getMapLogic().processTurn();
        TestUIManager manager = (TestUIManager) uiManager;
        assertTrue(manager.victory);
    }

    @Test
    public void aiVictoryWorks() {
        mapController.getMapLogic().setGoal(a);
        mapController.getMapLogic().setPlayerWithSkynail(mapController.getMapLogic().getAiMover().getAiPlayer());
        mapController.getMapLogic().getAiMover().getAiPlayer().setLocation(a);
        mapController.getMapLogic().getAiMover().setAiState(AIState.goToGoal);
        mapController.getMapLogic().processTurn();
        TestUIManager manager = (TestUIManager) uiManager;
        assertTrue(manager.victory);
    }

    @Test
    public void victoryInBattleResultIncreasesGold() {
        player.setGold(100);
        Dungeon d = new Dungeon("Test 3", "Grotto", new Monster(35, 6), new MapPoint(70, 160));
        d.setTrophy(new Trophy(100, null, null));
        player.setLocation(d);
        mapController.getMapLogic().processBattleResult(BattleState.victory);
        assertEquals(player.getGold(), 200);
    }

    @Test
    public void victoryInBattleGivesAllTrophies() {
        player.setGold(100);
        Dungeon d = new Dungeon("Test 3", "Grotto", new Monster(35, 6), new MapPoint(70, 160));
        Item item = new Item("Test Item", -20);
        Companion companion = new Companion("Test Companion", 20, 10);
        d.setTrophy(new Trophy(100, companion, item));
        d.getTrophy().setSkynail(true);
        player.setLocation(d);
        mapController.getMapLogic().processBattleResult(BattleState.victory);
        assertEquals(player.getGold(), 200);
        assertTrue(player.getCompanions().contains(companion));
        assertTrue(player.getItems().containsKey(item));
        assertTrue(mapController.getMapLogic().getPlayerWithSkynail() == player);
    }

    @Test
    public void victoryAgainstOtherPlayerGivesSkynail() {
        mapController.getMapLogic().setPlayerFight(true);
        mapController.getMapLogic().processBattleResult(BattleState.victory);
        assertEquals(mapController.getMapLogic().getPlayerWithSkynail(), player);
    }

    @Test
    public void victoryAgainstOtherPlayerKillsAI() {
        mapController.getMapLogic().setPlayerFight(true);
        mapController.getMapLogic().processBattleResult(BattleState.victory);
        assertEquals(mapController.getMapLogic().getAiMover().getAiState(), AIState.dead);
        assertEquals(mapController.getMapLogic().isPlayerFight(), false);
    }

    @Test
    public void enteringCityStartsCityScene() {
        City city = new City("Area");
        mapController.getPlayer().setLocation(city);
        mapController.handleEnteringArea();
    }

    @Test
    public void enteringDungeonStartsBattleScene() {
        Dungeon dungeon = new Dungeon("Dungeon");
        mapController.getPlayer().setLocation(dungeon);

        mapController.handleEnteringArea();
    }
}
