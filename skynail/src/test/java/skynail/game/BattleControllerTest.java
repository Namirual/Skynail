/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import skynail.domain.Companion;
import skynail.domain.Item;
import skynail.domain.Player;
import skynail.domain.Monster;
import skynail.domain.Road;
import skynail.gui.UIManager;
import skynail.service.DiceRoller;

/**
 *
 * @author lmantyla
 */
public class BattleControllerTest {

    BattleController battleController;
    UIManager uiManager;

    Player player;

    List<Monster> monsters;

    private class StaticRoller implements DiceRoller {

        @Override
        public int diceThrow(int luku) {
            return luku;
        }
    }

    @Before
    public void setUp() {
        player = new Player("Pelaaja", new Road("testi"));
        player.addCompanions(new Companion("Hero", 30, 6), new Companion("Sidekick", 20, 4));
        uiManager = new TestUIManager();

        monsters = new ArrayList<Monster>();
        monsters.add(new Monster(15, 4));

        battleController = new BattleController(uiManager, new StaticRoller(), player, monsters);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void battleSceneIsStarted() {
        uiManager = new TestUIManager();
        battleController = new BattleController(uiManager, new StaticRoller(), player, monsters);
        uiManager.startBattleScene(battleController);
        TestUIManager testManager = (TestUIManager) uiManager;
        assertEquals(testManager.battle, true);
    }

    @Test
    public void attackCommandIsHandled() {
        battleController.handlePlayerAttack(monsters.get(0));
        assertEquals(monsters.get(0).getHP(), 9);
    }

    @Test
    public void playerAttackMovesTurnForward() {
        battleController.handlePlayerAttack(monsters.get(0));
        assertEquals(battleController.getCharacterTurn(), 1);
    }

    @Test
    public void playerTurnReturnsToZeroAfterFullCycle() {
        battleController.handlePlayerAttack(monsters.get(0));
        battleController.handlePlayerAttack(monsters.get(0));

        assertEquals(battleController.getCharacterTurn(), 0);
    }

    @Test
    public void playerTurnStateWhenNotAllCompanionsHaveActed() {
        battleController.setCharacterTurn(1);
        assertEquals(battleController.updateGameState(), BattleState.playerTurn);
    }

    @Test
    public void enemyTurnStateWhenAllCompanionsHaveActed() {
        battleController.setCharacterTurn(2);
        assertEquals(battleController.updateGameState(), BattleState.enemyTurn);
    }

    @Test
    public void victoryStateWhenMonsterIsDead() {
        Monster monster = new Monster(-1, 4);
        monsters = new ArrayList<Monster>();
        monsters.add(monster);
        battleController = new BattleController(uiManager, new StaticRoller(), player, monsters);
        assertEquals(battleController.updateGameState(), BattleState.victory);
    }

    @Test
    public void deathStateWhenPlayerIsDead() {
        player = new Player("Pelaaja", new Road("testi"));
        player.addCompanions(new Companion("Hero", -1, 6));

        battleController = new BattleController(uiManager, new StaticRoller(), player, monsters);
        assertEquals(battleController.updateGameState(), BattleState.death);
    }

    @Test
    public void enemyAttacksDuringEnemyTurn() {
        player = new Player("Pelaaja", new Road("testi"));
        player.addCompanions(new Companion("Hero", 5, 6));

        battleController = new BattleController(uiManager, new StaticRoller(), player, monsters);
        battleController.enemyTurn();
        assertEquals(player.getCompanions().get(0).getHP(), 1);
    }

    @Test
    public void enemyDoesNotAttackCharactersWith0HP() {
        player = new Player("Pelaaja", new Road("testi"));
        player.addCompanions(new Companion("Hero", 15, 6));
        player.addCompanions(new Companion("Hero", 5, 6));
        player.getCompanions().get(1).reduceHP(5);

        battleController = new BattleController(uiManager, new StaticRoller(), player, monsters);
        battleController.enemyTurn();
        assertEquals(player.getCompanions().get(0).getHP(), 11);
        assertEquals(player.getCompanions().get(1).getHP(), 0);
    }
    
    @Test
    public void canUseItems() {
        Item potion = new Item("Potion", -15);
        player.addItem(potion, 3);

        player.getCompanions().get(1).reduceHP(5);

        battleController = new BattleController(uiManager, new StaticRoller(), player, monsters);
        battleController.handleItemUse(player.getCompanions().get(1), potion);
        assertEquals(player.getCompanions().get(1).getHP(), 20);
    }
}
