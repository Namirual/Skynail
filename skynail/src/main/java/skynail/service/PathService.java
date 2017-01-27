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
import skynail.domain.Team;
import skynail.domain.Point;

/**
 *
 * @author lmantyla
 */
public class PathService {

    private Map<Point, Integer> legalMoves;
    final private Team team;

    public PathService(Team team) {
        this.team = team;
        legalMoves = new HashMap<>();
    }

    public Map<Point, Integer> getLegalMoves() {
        return legalMoves;
    }

    public void setLegalMoves(Map<Point, Integer> legalMoves) {
        this.legalMoves = legalMoves;
    }

    public Map<Point, Integer> calculateLegalMoves(int moves) {
        legalMoves = new HashMap<>();
        List<Point> reachablePoints = new ArrayList<>();
        List<Point> newReachablePoints = new ArrayList<>();

        reachablePoints.addAll(team.getLocation().getLinkedPoints());

        for (int distance = 0; distance < moves; distance++) {
            for (Point searchablePoint : reachablePoints) {
                newReachablePoints.addAll(getNewReachablePoints(searchablePoint, moves, distance));
            }

            reachablePoints.clear();
            reachablePoints.addAll(newReachablePoints);
            if (reachablePoints.contains(team.getLocation())) {
                reachablePoints.remove(team.getLocation());
            }
            newReachablePoints.clear();
        }

        return legalMoves;
    }

    public List<Point> getNewReachablePoints(Point reachablePoint, int moves, int distance) {
        // If a reachable point has a higher distance than is currently examined, examining it
        // is delayed until the loop's distance catches up. Therefore the original point is returned.

        if (legalMoves.containsKey(reachablePoint)) {
            if (legalMoves.get(reachablePoint) > distance) {
                return Arrays.asList(reachablePoint);
            } else {
                return new ArrayList<Point>();
            }
        }

        int movesRequired = reachablePoint.movesRequired(team);

        if (movesRequired > 0 && movesRequired <= moves - distance) {
            legalMoves.put(reachablePoint, distance + movesRequired);
            return reachablePoint.getLinkedPoints();
        } else {
            return new ArrayList<Point>();
        }
    }

    // Produces a route to a point found in legalMoves.
    // The method works backwards towards the current team position.
    public List<Point> getMovementRoute(Point reachablePoint) {
        List<Point> routePoints = new ArrayList<>();

        if (!legalMoves.containsKey(reachablePoint)) {
            return routePoints;
        }

        int currentLowestDistance = legalMoves.get(reachablePoint);
        Point currentPoint = reachablePoint;

        routePoints.add(currentPoint);

        while (!currentPoint.equals(team.getLocation())) {
            for (Point point : currentPoint.getLinkedPoints()) {
                if (point.equals(team.getLocation())) {
                    currentPoint = team.getLocation();
                } else if (legalMoves.containsKey(point) && legalMoves.get(point) < currentLowestDistance) {
                    currentLowestDistance = legalMoves.get(point);
                    currentPoint = point;
                }
            }
            routePoints.add(currentPoint);
        }

        return routePoints;
    }
}
