/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import skynail.domain.City;
import skynail.domain.Dungeon;

import skynail.domain.Point;
import skynail.domain.Player;
import skynail.gui.UIManager;
import skynail.service.PathService;
import skynail.service.DiceRoller;

/**
 * Controller with methods that receives and handles player input from the UI.
 *
 * @author lmantyla
 */
public class MapController {

    private Player player;
    private DiceRoller diceRoller;

    private UIManager uiManager;

    PathService pathService;

    List<Point> worldMap;
    List<Point> pathPoints;

    private Map<Point, Integer> legalMoves;
    private int moves;
    boolean moving;

    /**
     * Initializes the Map Controller.
     *
     * @param player current player
     * @param worldMap all the points in the current game
     * @param uiManager user interface currently in use
     * @param diceRoller currently used dice roller
     */
    public MapController(Player player, List<Point> worldMap, DiceRoller diceRoller, UIManager uiManager) {
        this.player = player;
        this.diceRoller = diceRoller;
        this.uiManager = uiManager;
        this.worldMap = worldMap;

        this.pathService = new PathService(player);
        this.legalMoves = pathService.calculateLegalMoves(1);

        this.pathPoints = new ArrayList<Point>();
        this.moving = false;
    }

    /**
     * The method attempts to move the player to point selected in the UI.
     * <p>
     * The method is called by the user interface. The method subsequently calls
     * update() to initiate redrawing the map view.
     *
     * @param point Map point selected by player in the interface.
     */
    public void handlePointInput(Point point) {

        if (legalMoves.containsKey(point)) {

            moving = true;
            pathPoints = pathService.getMovementPath(point);
            uiManager.displayMapMovement(pathPoints);

            player.setLocation(point);
            moving = false;

            moves = 0;
            legalMoves.clear();
        }
    }

    public void handeEnteringArea() {
        Point point = player.getLocation();
        if (point.getClass().equals(City.class)) {
            uiManager.startCityScene((City) point);
        }

        if (point.getClass().equals(Dungeon.class)) {
            Dungeon dungeon = (Dungeon) point;
            BattleController battleController = new BattleController(uiManager, diceRoller, player, dungeon.getMonsters());
            BattleState battleState = battleController.startBattle();
        }
    }

    /**
     * Processes rewards or other changes at the end of the battle, unfinished.
     *
     * @param battleState state at the end of the battle.
     */
    public void processBattleResult(BattleState battleState) {
        if (battleState == BattleState.victory) {
            player.setGold(player.getGold() + 150);
        }
    }

    /**
     * Calls a remote dice roller.
     *
     * @return random number from dice roller
     */
    public int handleDiceRoll() {
        moves = diceRoller.diceThrow(3);
        legalMoves = pathService.calculateLegalMoves(moves);

        return moves;
    }

    /**
     * Used to make purchases in cities, currently unfinished.
     *
     * @return true if transaction is successful
     */
    public boolean handleCityBuy() {
        return player.buyWithGold(100);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Point> getWorldMap() {
        return worldMap;
    }

    public Map<Point, Integer> getLegalMoves() {
        return legalMoves;
    }

    public List<Point> getPathPoints() {
        return pathPoints;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setWorldMap(List<Point> worldMap) {
        this.worldMap = worldMap;
    }
}
