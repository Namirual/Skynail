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

/**
 *
 * @author lmantyla
 */
public class GameCharacterTest {

    GameCharacter a;

    @Before
    public void setUp() {
        a = new GameCharacter(20, 5);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void createNewCharacterWorks() {
        GameCharacter b = new GameCharacter(5, 4);
        assertEquals(b.getHP(), 5);
        assertEquals(b.getAttack(), 4);
    }

    @Test
    public void reduceHPByCorrectValue() {
        a.reduceHP(5);
        assertEquals(a.getHP(), 15);
    }

    @Test
    public void healHPByCorrectValue() {
        a.reduceHP(10);
        a.healHP(5);
        assertEquals(a.getHP(), 15);
    }

    @Test
    public void healingDoesNotExceedMaxHP() {
        a.reduceHP(3);
        a.healHP(5);
        assertEquals(a.getHP(), 20);
    }
}
