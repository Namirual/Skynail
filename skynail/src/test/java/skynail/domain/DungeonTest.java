/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.domain;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import skynail.gui.MapPoint;

/**
 *
 * @author lmantyla
 */
public class DungeonTest {

    Dungeon a;
    Dungeon b;
    Dungeon c;

    @Before
    public void setUp() {
        a = new Dungeon("Test 1");
        b = new Dungeon("Test 2");
        c = new Dungeon("Test 3");

    }

    @After
    public void tearDown() {
    }

    @Test
    public void createNewDungeonWorks() {
        Dungeon test = new Dungeon("Test");
        assertEquals(test.getName(), "Test");
    }

    @Test
    public void creationWithIntroTextAndMonsterWorks() {
        Dungeon test = (new Dungeon("Test", "intro", new Monster(20, 4), new MapPoint(1, 1)));
        assertEquals(test.getIntroText(), "intro");
        assertEquals(test.getMonster().getHP(), 20);
    }

    @Test
    public void dungeonRequiresTwoMoves() {
        assertEquals(b.movesRequired(new Player("testi", a)), 2);
    }
}