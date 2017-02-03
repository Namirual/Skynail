/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import skynail.domain.Point;
import skynail.domain.Team;
import skynail.gui.MapPainter;
import skynail.service.PathService;
import skynail.service.DiceRoller;

/**
 *
 * @author lmantyla
 */
public class MapController {

    private Team player;
    private DiceRoller diceRoller;
    private MapPainter mapPainter;
    private Map<Point, Integer> legalMoves;
    private int moves;

    public MapController(Team player, DiceRoller diceRoller, MapPainter mapPainter) {
        this.player = player;
        this.diceRoller = diceRoller;
        this.mapPainter = mapPainter;

        PathService pathService = new PathService(player);
        this.legalMoves = pathService.calculateLegalMoves(1);
    }

    // This function is called by MapListener, and supplies
    // MapPainter with information about available legal moves.
    public void handlePointInput(Point point) {
        List<Point> legalMoveList = new ArrayList<Point>();
        legalMoveList.addAll(legalMoves.keySet());

        if (legalMoves.containsKey(point)) {
            player.setLocation(point);
            moves = 0;
        }

        update();
    }

    public int handleDiceRoll() {
        moves = diceRoller.diceThrow(3);
        update();
        return moves;
    }

    public void update() {

        PathService pathService = new PathService(player);
        legalMoves = pathService.calculateLegalMoves(moves);
            
        List<Point> legalMoveList = new ArrayList<Point>();
        legalMoveList.addAll(legalMoves.keySet());

        mapPainter.setLegalMoves(legalMoveList);
        mapPainter.update();

    }
}
