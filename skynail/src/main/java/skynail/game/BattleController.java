/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.game;

import skynail.domain.Companion;
import skynail.domain.Player;
import skynail.domain.GameCharacter;
import skynail.domain.Monster;
import skynail.gui.UIManager;

/**
 * Controller for battles against monsters.
 *
 * @author lmantyla
 */
public class BattleController {

    private UIManager uiManager;
    private Monster monster;
    private Player player;

    private BattleState state;

    private int characterTurn;

    /**
     * Creates new BattleController.
     *
     * @param uiManager UI manager in use.
     * @param player Current player.
     * @param monster Monster.
     */
    public BattleController(UIManager uiManager, Player player, Monster monster) {
        this.uiManager = uiManager;
        this.player = player;

        this.monster = monster;

        this.state = BattleState.playerTurn;
        this.characterTurn = 0;
    }

    /**
     * Starts the battle by telling the UI Manager to create the battle user interface.
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
        characterTurn++;
        return updateGameState();
    }

    /**
     * Checks for deaths and other events and updates the battle state.
     *
     * @return state of the battle.
     */
    public BattleState updateGameState() {
        if (monster.getHP() <= 0) {
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
        player.getCompanions().get(0).reduceHP(monster.getAttack());
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

    public Monster getMonster() {
        return monster;
    }
}
