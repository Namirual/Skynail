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
import skynail.domain.Item;
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
     * @param diceRoller diceRoller for the controller.
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

    /**
     * Handles usage of an item in combat.
     *
     * @param target GameCharacter being targeted with an item.
     * @param item Item currnetly being used.
     * @return current BattleState derived from updateGameState.
     */
    public BattleState handleItemUse(GameCharacter target, Item item) {
        if (state == BattleState.playerTurn) {
            if (item.getPotency() < 0) {
                target.healHP(Math.abs(item.getPotency()));
            } else {
                target.reduceHP(item.getPotency());
            }
        }
        
        player.reduceItemsByOne(item);
        
        nextCharacterTurn();
        return updateGameState();
    }

    /**
     * Changes whose turn it is.
     */
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
