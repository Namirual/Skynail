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

import skynail.domain.Point;
import skynail.domain.Team;
import skynail.gui.UIManager;
import skynail.service.PathService;
import skynail.service.DiceRoller;

/**
 * Controller with methods that receives and handles player input from the UI. 
 * 
 * @author lmantyla
 */
public class MapController {

    private Team player;
    private DiceRoller diceRoller;

    private UIManager uiManager;

    private Map<Point, Integer> legalMoves;
    private int moves;

     /**
     * Initializes the Map Controller.
     * @param player
     * @param uiManager
     * @param diceRoller
     */
    public MapController(Team player, DiceRoller diceRoller, UIManager uiManager) {
        this.player = player;
        this.diceRoller = diceRoller;
        this.uiManager = uiManager;

        PathService pathService = new PathService(player);
        this.legalMoves = pathService.calculateLegalMoves(1);
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
        List<Point> legalMoveList = new ArrayList<Point>();
        legalMoveList.addAll(legalMoves.keySet());

        if (legalMoves.containsKey(point)) {
            player.setLocation(point);
            moves = 0;
            if (point.getClass().equals(City.class)) {
                uiManager.startCityScene((City) point);
            }
        }

        update();
    }

    /**
     * Calls a remote dice roller.
     * @return random number from dice roller
     */
    public int handleDiceRoll() {
        moves = diceRoller.diceThrow(3);
        update();
        return moves;
    }

    /**
     *
     */
    public boolean handleCityBuy() {
        return player.buyWithGold(100);
    }

    /**
     * Tells UI manager to update the map view.
     */
    public void update() {

        PathService pathService = new PathService(player);
        legalMoves = pathService.calculateLegalMoves(moves);

        List<Point> legalMoveList = new ArrayList<Point>();
        legalMoveList.addAll(legalMoves.keySet());

        uiManager.updateMap(legalMoveList);
    }

    public Team getPlayer() {
        return player;
    }

    public void setPlayer(Team player) {
        this.player = player;
    }
}
