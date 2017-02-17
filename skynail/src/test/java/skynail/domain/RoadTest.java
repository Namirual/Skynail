/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.domain;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import skynail.service.PathService;
import static org.junit.Assert.*;
import skynail.gui.MapPoint;

/**
 *
 * @author lmantyla
 */
public class RoadTest {

    Road a;
    Road b;
    Road c;

    @Before
    public void setUp() {
        a = new Road("Test 1");
        b = new Road("Test 2");
        c = new Road("Test 3");

    }

    @After
    public void tearDown() {
    }

    @Test
    public void createNewPathWorks() {
        Road test = new Road("Test");
        assertEquals(test.getName(), "Test");
    }

    @Test
    public void creationWithMapPointWorks() {
        Road test = (new Road("Test", new MapPoint(10, 10)));
        assertEquals(test.getMapPoint().getX(), 10);
    }

    @Test
    public void addingLinkedPointsWorks() {
        a.addPoints(b);
        assertEquals(a.getLinkedPoints().size(), 1);
    }

    @Test
    public void addingMoreThanOneWorks() {
        a.addPoints(b, c);
        assertEquals(a.getLinkedPoints().size(), 2);
    }

    @Test
    public void addingBothWaysWorks() {
        a.addPointsBothWays(b);
        assertEquals(b.getLinkedPoints().size(), 1);
    }

    @Test
    public void roadRequiresOneMove() {
        assertEquals(b.movesRequired(new Player("testi", a)), 1);
    }

}
