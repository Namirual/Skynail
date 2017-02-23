/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.game;

import java.util.List;
import skynail.domain.Companion;
import skynail.domain.Player;
import skynail.domain.GameCharacter;
import skynail.domain.Monster;
import skynail.gui.UIManager;
import skynail.service.DiceRoller;

/**
 * Controller for battles against monsters.
 *
 * @author lmantyla
 */
public class BattleController {

    private UIManager uiManager;
    private DiceRoller diceRoller;
    private List<Monster> monsters;
    private Player player;

    private BattleState state;

    private int characterTurn;

    /**
     * Creates new BattleController.
     *
     * @param uiManager UI manager in use.
     * @param player Current player.
     * @param monsters Monster.
     */
    public BattleController(UIManager uiManager, DiceRoller diceRoller, Player player, List<Monster> monsters) {
        this.uiManager = uiManager;
        this.diceRoller = diceRoller;

        this.player = player;

        this.monsters = monsters;

        this.state = BattleState.playerTurn;
        this.characterTurn = 0;
    }

    /**
     * Starts the battle by telling the UI Manager to create the battle user
     * interface.
     *
     * @return battle state at the end of the battle.
     */
    public BattleState startBattle() {
        uiManager.startBattleScene(this);
        return state;
    }

    /**
     * Handles UI command to attack a monster.
     *
     * @param monster The monster that the player attacks.
     * @return state of the battle.
     */
    public BattleState handlePlayerAttack(Monster monster) {
        if (state == BattleState.playerTurn) {
            monster.reduceHP(player.getCompanions().get(characterTurn).getAttack());
        }
        nextCharacterTurn();
        return updateGameState();
    }
    
    public BattleState handlePlayerHealing(Companion companion) {
        if (state == BattleState.playerTurn) {
            companion.healHP(20);
        }
        nextCharacterTurn();
        return updateGameState();
    }

    public void nextCharacterTurn() {
        while (true) {
            characterTurn++;
            if (characterTurn >= player.getCompanions().size()) {
                return;
            }
            if (player.getCompanions().get(characterTurn).getHP() > 0) {
                return;
            }
        }
    }

    /**
     * Checks for deaths and other events and updates the battle state.
     *
     * @return state of the battle.
     */
    public BattleState updateGameState() {
        for (int num = 0; num < monsters.size(); num++) {
            if (monsters.get(num).getHP() <= 0) {
                monsters.remove(monsters.get(num));
            }
        }

        if (monsters.size() <= 0) {
            return BattleState.victory;
        }

        if (characterTurn >= player.getCompanions().size()) {
            state = BattleState.enemyTurn;
            enemyTurn();
            characterTurn = 0;
        }

        if (player.getCompanions().get(0).getHP() < 0) {
            return BattleState.death;
        }

        if (state == BattleState.enemyTurn) {
            state = BattleState.playerTurn;
            return BattleState.enemyTurn;

        }
        state = BattleState.playerTurn;
        return state;
    }

    /**
     * Handles enemy attacks on player, currently very primitive.
     */
    public void enemyTurn() {
        for (Monster monster : monsters) {
            int attacked = diceRoller.diceThrow(player.getCompanions().size()) - 1;

            player.getCompanions().get(attacked).reduceHP(monster.getAttack());
        }
    }

    public int getCharacterTurn() {
        return characterTurn;
    }

    public void setCharacterTurn(int characterTurn) {
        this.characterTurn = characterTurn;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }
}
