/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.game;

import java.util.Arrays;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import skynail.domain.Companion;
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

    Monster monster;

    @Before
    public void setUp() {
        player = new Player("Pelaaja", new Road("testi"));
        player.addCompanions(new Companion(30, 6, "Hero"), new Companion(20, 4, "Sidekick"));
        uiManager = new TestUIManager();
        monster = new Monster(15, 4);

        battleController = new BattleController(uiManager, player, monster);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void battleSceneIsStarted() {
        uiManager = new TestUIManager();
        battleController = new BattleController(uiManager, player, monster);
        battleController.startBattle();
        TestUIManager testManager = (TestUIManager) uiManager;
        assertEquals(testManager.battle, true);
    }

    @Test
    public void attackCommandIsHandled() {
        battleController.handlePlayerAttack(monster);
        assertEquals(monster.getHP(), 9);
    }

    @Test
    public void playerAttackMovesTurnForward() {
        battleController.handlePlayerAttack(monster);
        assertEquals(battleController.getCharacterTurn(), 1);
    }

    @Test
    public void playerTurnReturnsToZeroAfterFullCycle() {
        battleController.handlePlayerAttack(monster);
        battleController.handlePlayerAttack(monster);

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
        monster = new Monster(-1, 4);
        battleController = new BattleController(uiManager, player, monster);
        assertEquals(battleController.updateGameState(), BattleState.victory);
    }

    @Test
    public void deathStateWhenPlayerIsDead() {
        player = new Player("Pelaaja", new Road("testi"));
        player.addCompanions(new Companion(-1, 6, "Hero"));

        battleController = new BattleController(uiManager, player, monster);
        assertEquals(battleController.updateGameState(), BattleState.death);
    }

    public void enemyAttacksDuringEnemyTurn() {
        player = new Player("Pelaaja", new Road("testi"));
        player.addCompanions(new Companion(5, 6, "Hero"));

        battleController = new BattleController(uiManager, player, monster);
        battleController.enemyTurn();
        assertEquals(player.getCompanions().get(0).getHP(), 1);
    }
}
