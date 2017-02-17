/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import skynail.domain.Player;
import skynail.domain.Point;

/**
 * Service for finding available legal moves and generating paths. 
 * 
 * @author lmantyla
 */
public class PathService {

    private Map<Point, Integer> legalMoves;
    final private Player player;

    /**
     *
     * @param player The player from whose perspective the path is being searched.
     */
    public PathService(Player player) {
        this.player = player;
        legalMoves = new HashMap<>();
    }

    public Map<Point, Integer> getLegalMoves() {
        return legalMoves;
    }

    public void setLegalMoves(Map<Point, Integer> legalMoves) {
        this.legalMoves = legalMoves;
    }

    /**
     * Makes a list of legal moves a team can make on the map.
     * @param moves Number of moves available this turn.
     * @return Map of legal moves and the amount of moves needed to reach them.
     */
    public Map<Point, Integer> calculateLegalMoves(int moves) {
        legalMoves = new HashMap<>();
        List<Point> reachablePoints = new ArrayList<>();

        reachablePoints.addAll(player.getLocation().getLinkedPoints());

        int distance = 0;

        while (reachablePoints.size() > 0) {
            List<Point> newReachablePoints = new ArrayList<>();
            for (Point reachablePoint : reachablePoints) {
                if (legalMoves.containsKey(reachablePoint)) {
                    newReachablePoints.addAll(handleReachedPoint(reachablePoint, distance));
                } else {
                    newReachablePoints.addAll(getNewReachablePoints(reachablePoint, moves, distance));
                }
            }

            reachablePoints.clear();
            reachablePoints.addAll(newReachablePoints);
            distance++;
        }

        return legalMoves;
    }

    /**
     * Determines what to do with points that have already been reached once.
     * <p>
     * If a reachable point has a higher distance than is currently examined,
     * examining it is delayed until the loop's distance catches up. Therefore
     * the original point is returned to be added to the buffer of points to
     * be examined.
     * 
     * @param reachablePoint point that has been reached
     * @param distance distance from the player's position that the calculation has reached.
     * @return list of points linked to the point, only when distance is correct. 
     */
    public List<Point> handleReachedPoint(Point reachablePoint, int distance) {

        if (legalMoves.get(reachablePoint) > distance) {
            return Arrays.asList(reachablePoint);
        } else if (legalMoves.get(reachablePoint) < distance) {
            return new ArrayList<Point>();
        }

        return reachablePoint.getLinkedPoints();
    }

    /**
     * Checks if a point reached by the search is a legal move.
     * 
     * @param reachablePoint point being examined
     * @param moves number of moves this turn
     * @param distance distance of the point from current position
     * @return either empty list or the points linked to reachablePoint
     */
    public List<Point> getNewReachablePoints(Point reachablePoint, int moves, int distance) {

        int movesRequired = reachablePoint.movesRequired(player);

        if (reachablePoint.equals(player.getLocation())) {
            return new ArrayList<>();
        }

        /*if (movesRequired > 1 && movesRequired <= moves - distance) {
            legalMoves.put(reachablePoint, distance + movesRequired);
            return new ArrayList<Point>();
        }*/
        if (movesRequired > 0 && movesRequired <= moves - distance) {
            legalMoves.put(reachablePoint, distance + movesRequired);
            return reachablePoint.getLinkedPoints();
        } else {
            return new ArrayList<Point>();
        }
    }

    /**
     * Produces a path between the player and a selected legal move; the method works 
     * backwards towards the current position of the team.
     * 
     * @param reachablePoint Point the player wants to reach.
     * 
     * @return list of points leading to the current point.
     */
    public List<Point> getMovementPath(Point reachablePoint) {
        List<Point> pathPoints = new ArrayList<>();

        if (!legalMoves.containsKey(reachablePoint)) {
            return pathPoints;
        }

        int currentLowestDistance = legalMoves.get(reachablePoint);
        Point currentPoint = reachablePoint;

        pathPoints.add(currentPoint);

        while (!currentPoint.equals(player.getLocation())) {
            for (Point point : currentPoint.getLinkedPoints()) {
                if (point.equals(player.getLocation())) {
                    currentPoint = player.getLocation();
                } else if (legalMoves.containsKey(point) && legalMoves.get(point) < currentLowestDistance) {
                    currentLowestDistance = legalMoves.get(point);
                    currentPoint = point;
                }
            }
            pathPoints.add(currentPoint);
        }

        return pathPoints;
    }
}
