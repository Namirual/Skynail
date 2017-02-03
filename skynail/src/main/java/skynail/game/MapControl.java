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
public class MapControl {

    Team player;
    DiceRoller diceRoller;
    MapPainter mapPainter;
    Map<Point, Integer> legalMoves;

    public MapControl(Team player, DiceRoller diceRoller, MapPainter mapPainter) {
        this.player = player;
        this.diceRoller = diceRoller;
        this.mapPainter = mapPainter;
        
        PathService pathService = new PathService(player);
        this.legalMoves = pathService.calculateLegalMoves(1);
    }

    // This function is called by MapListener, and supplies
    // MapPainter with information about available legal moves.
    public boolean handlePointInput(Point point) {
        List<Point> legalMoveList = new ArrayList<Point>();
        legalMoveList.addAll(legalMoves.keySet());

        if (legalMoveList.contains(point))  {

            player.setLocation(point);
            PathService pathService = new PathService(player);
            int moves = diceRoller.diceThrow(2);

            legalMoves = pathService.calculateLegalMoves(moves);
            legalMoveList = new ArrayList<Point>();
            legalMoveList.addAll(legalMoves.keySet());
        }

        mapPainter.setLegalMoves(legalMoveList);
        mapPainter.update();

        return true;
    }
}
