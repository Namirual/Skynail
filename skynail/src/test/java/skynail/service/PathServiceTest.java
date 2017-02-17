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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import skynail.domain.Road;
import skynail.domain.Point;
import skynail.domain.Player;
import skynail.service.PathService;

/**
 *
 * @author lmantyla
 */
public class PathServiceTest {

    Road a;
    Road b;
    Road c;
    Road d;
    Road e;

    Player player;
    Map<Point, Integer> legalMoves;
    PathService pathService;

    public PathServiceTest() {
    }

    @Before
    public void setUp() {
        a = new Road("Center");
        b = new Road("Test 1");
        c = new Road("Test 2");
        d = new Road("Test 3");
        e = new Road("Test 4");

        player = new Player("Pelaaja", a);

        legalMoves = new HashMap<Point, Integer>();

        pathService = new PathService(player);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void returnsReachablePoints() {
        b.addPoints(a, c);

        List<Point> list = new ArrayList<>();
        list.addAll(pathService.getNewReachablePoints(b, 1, 0));

        assertEquals(list.size(), 2);
    }

    @Test
    public void findsPointsOneMoveAway() {
        a.addPoints(b, c);

        legalMoves = pathService.calculateLegalMoves(1);

        assertEquals(legalMoves.size(), 2);
    }

    @Test
    public void doesNotFindExtraMoves() {
        a.addPoints(b);
        b.addPoints(c);

        legalMoves = pathService.calculateLegalMoves(1);
        assertEquals(legalMoves.size(), 1);
    }

    @Test
    public void findsPointsThreePointsAway() {
        a.addPoints(b);
        b.addPoints(c);
        c.addPoints(d);

        legalMoves = pathService.calculateLegalMoves(3);
        assertEquals(legalMoves.size(), 3);
        assertTrue(legalMoves.containsKey(d));
    }

    @Test
    public void cannotMoveToStartingPoint() {
        a.addPoints(b);
        b.addPoints(a);

        legalMoves = pathService.calculateLegalMoves(2);
        assertEquals(legalMoves.size(), 1);
        assertFalse(legalMoves.containsKey(a));
    }

    @Test
    public void handleReachedPointDelaysAnalysisOfMovesUntilDistanceMatches() {
        a.addPoints(b, c);

        pathService.getLegalMoves().put(b, 2);
        List<Point> newReachablePoints = new ArrayList<Point>();
        newReachablePoints.addAll(pathService.handleReachedPoint(b, 1));
        assertEquals(newReachablePoints.size(), 1);
        assertTrue(newReachablePoints.contains(b));
    }

    @Test
    public void handleReachedPointReturnsEmptyWhenBetterRouteExists() {
        a.addPoints(b);
        b.addPoints(c);
        c.addPoints(d);
        c.addPoints(e);

        pathService.getLegalMoves().put(c, 2);
        List<Point> newReachablePoints = new ArrayList<Point>();
        newReachablePoints.addAll(pathService.handleReachedPoint(c, 2));
        assertEquals(newReachablePoints.size(), 2);
        assertTrue(newReachablePoints.contains(d));
        assertTrue(newReachablePoints.contains(e));

    }

    @Test
    public void handleReachedPointReturnsPointsWhenDistanceIsCorrect() {
        a.addPoints(b);
        b.addPoints(c);

        pathService.getLegalMoves().put(c, 1);
        List<Point> newReachablePoints = new ArrayList<Point>();
        newReachablePoints.addAll(pathService.handleReachedPoint(c, 2));
        assertEquals(newReachablePoints.size(), 0);
        assertFalse(newReachablePoints.contains(c));
    }

    @Test
    public void getNewReachablePointsReturnsEmptyWhenBetterLegalMoveExists() {
        a.addPoints(b);
        b.addPoints(c);

        pathService.getLegalMoves().put(c, 1);
        List<Point> newReachablePoints = new ArrayList<Point>();
        newReachablePoints.addAll(pathService.getNewReachablePoints(c, 2, 2));
        assertEquals(newReachablePoints.size(), 0);
        assertFalse(newReachablePoints.contains(b));
    }

    @Test
    public void getNewReachablePointsContinuesWhenDistanceMatchesLegalMoveDistance() {
        a.addPoints(b);
        b.addPoints(c);
        c.addPoints(d);

        pathService.getLegalMoves().put(b, 1);
        List<Point> newReachablePoints = new ArrayList<Point>();
        newReachablePoints.addAll(pathService.getNewReachablePoints(c, 4, 1));
        assertEquals(newReachablePoints.size(), 1);
        assertTrue(newReachablePoints.contains(d));
    }

    @Test
    public void routeIsReturned() {
        a.addPoints(b, e);
        b.addPoints(a, c);
        c.addPoints(b, d);
        d.addPoints(c);

        legalMoves = pathService.calculateLegalMoves(3);
        List<Point> route = pathService.getMovementPath(d);

        assertEquals(route.size(), 4);
        assertTrue(route.get(3) == a);
    }

    @Test
    public void straightestRouteIsFound() {
        a.addPoints(b, e);
        b.addPoints(a, c);
        c.addPoints(b, d);
        d.addPoints(c, e);
        e.addPoints(a, d);

        legalMoves = pathService.calculateLegalMoves(3);

        List<Point> route = pathService.getMovementPath(d);

        assertEquals(route.size(), 3);
        assertTrue(route.get(1) == e);
    }

    @Test
    public void returnsEmptyListWhenTargetIsNotALegalMove() {
        a.addPointsBothWays(b);
        b.addPointsBothWays(c);
        c.addPointsBothWays(d);

        legalMoves = pathService.calculateLegalMoves(2);

        assertEquals(pathService.getMovementPath(d).size(), 0);
    }
}
