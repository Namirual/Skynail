package skynail.game;

import java.util.ArrayList;
import java.util.List;
import skynail.domain.Companion;
import skynail.domain.Dungeon;
import skynail.domain.Monster;
import skynail.domain.Player;
import skynail.domain.Point;
import skynail.gui.UIManager;
import skynail.service.DiceRoller;

/**
 * Handles turns and game logic for the map.
 *
 * @author lmantyla
 */
public class MapLogic {

    private int turnNumber;
    private boolean playerFight;
    private AIMover aiMover;
    private boolean moving;
    private UIManager uiManager;
    private DiceRoller diceRoller;

    private List<Point> worldMap;
    private Point goal;
    private Player player;
    private Player playerWithSkynail;

    /**
     * Creates new MapLogic class.
     * @param diceRoller Dice roller used by the game.
     * @param uiManager uiManager used by the game.
     * @param worldMap list containing all points used in the game.
     * @param player Player class used by the human player.
     */
    public MapLogic(DiceRoller diceRoller, UIManager uiManager, List<Point> worldMap, Player player) {
        this.diceRoller = diceRoller;
        this.worldMap = worldMap;
        this.player = player;
        this.uiManager = uiManager;

        this.playerWithSkynail = null;
        this.turnNumber = 1;
        this.playerFight = false;
        this.goal = worldMap.get(0);
    }

    /**
     * Handles turn events after the player's move and checks for victory.
     */
    public void processTurn() {
        turnNumber++;
        if (aiMover != null) {
            List<Point> aiPath = aiMover.moveAiPlayer();
            uiManager.displayMapMovement(aiMover.getAiPlayer(), aiPath);

            if (aiMover.getTarget() == aiMover.getAiPlayer().getLocation()) {
                aiMover.handleReachingTarget();
            }
        }
        checkPlayerFight();

        if (player.getLocation().equals(goal) && playerWithSkynail == player) {
            uiManager.showEndScreen("You retrieved the Skynail!\nYou win!");
        } else if (aiMover.getAiPlayer().getLocation().equals(goal) && playerWithSkynail == aiMover.getAiPlayer()) {
            uiManager.showEndScreen("Your rival retrieved the Skynail before you!\nYou lost!");
        }
    }

    /**
     * Checks if a fight between players is possible and starts the battle.
     *
     * @return True if battle has been initiated.
     */
    public boolean checkPlayerFight() {
        if (playerWithSkynail != null && player.getLocation() == aiMover.getAiPlayer().getLocation()
                && aiMover.getAiState() != AIState.dead) {
            playerFight = true;
            List<Monster> enemyTeam = new ArrayList<>();
            for (Companion companion : aiMover.getAiPlayer().getCompanions()) {
                enemyTeam.add(new Monster(companion.getName(), companion.getHP(), companion.getAttack()));
            }
            BattleController battleController = new BattleController(uiManager, diceRoller, player, enemyTeam);
            uiManager.startBattleScene(battleController);
            return true;
        }
        return false;
    }

    /**
     * Changes the gamestate to show who has the Skynail.
     *
     * @param player Player who found the Skynail.
     */
    public void skynailFound(Player player) {
        playerWithSkynail = player;
        uiManager.showMapMessage("The Skynail has been found by " + player.getName() + "!");
    }

    /**
     * Handles battle result based on final battle state.
     * 
     * @param battleState state at the end of the battle.
     */
    public void processBattleResult(BattleState battleState) {
        if (playerFight) {
            processPlayerBattleResult(battleState);
            return;

        }
        if (battleState == BattleState.victory) {
            if (player.getLocation().getClass().equals(Dungeon.class
            )) {
                Dungeon dungeon = (Dungeon) player.getLocation();
                player.addTrophyContents(dungeon.getTrophy());
                if (dungeon.getTrophy().isSkynail()) {
                    playerWithSkynail = player;
                }
                uiManager.showMapMessage(dungeon.getTrophy().toString());
                dungeon.setCleared(true);
            }
        }
    }

    /**
     * Processes results of fights between two player teams.
     *
     * @param battleState state at the end of the battle.
     */
    public void processPlayerBattleResult(BattleState battleState) {
        if (battleState == BattleState.victory) {
            if (playerWithSkynail != player) {
                uiManager.showMapMessage("You have gained the Skynail!");
                playerWithSkynail = player;
            }
            aiMover.setAiState(AIState.dead);
            playerFight = false;
        }
        processTurn();
    }

    public Player getPlayerWithSkynail() {
        return playerWithSkynail;
    }

    public void setPlayerWithSkynail(Player playerWithSkynail) {
        this.playerWithSkynail = playerWithSkynail;
    }

    public DiceRoller getDiceRoller() {
        return diceRoller;
    }

    public List<Point> getWorldMap() {
        return worldMap;
    }

    public void setWorldMap(List<Point> worldMap) {
        this.worldMap = worldMap;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Point getGoal() {
        return goal;
    }

    public void setGoal(Point goal) {
        this.goal = goal;
    }

    public void setAiMover(AIMover aiMover) {
        this.aiMover = aiMover;
    }

    public AIMover getAiMover() {
        return aiMover;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public boolean isPlayerFight() {
        return playerFight;
    }

    public void setPlayerFight(boolean playerFight) {
        this.playerFight = playerFight;
    }
}