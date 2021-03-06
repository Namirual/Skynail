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

/**
 *
 * @author lmantyla
 */
public class CityTest {

    City a;
    City b;
    City c;

    @Before
    public void setUp() {
        a = new City("Test 1");
        b = new City("Test 2");
        c = new City("Test 3");

    }

    @After
    public void tearDown() {
    }

    @Test
    public void createNewPathWorks() {
        City test = new City("Test");
        assertEquals(test.getName(), "Test");
    }

    @Test
    public void CreationWithIntroTextWorks() {
        City test = (new City("Test", "intro"));
        assertEquals(test.getIntroText(), "intro");
    }

    @Test
    public void cityRequiresOneMove() {
        assertEquals(b.movesRequired(new Player("testi", a)), 1);
    }
}
