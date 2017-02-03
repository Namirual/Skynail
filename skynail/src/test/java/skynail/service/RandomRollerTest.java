/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.service;

import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Test;
import skynail.domain.Road;

/**
 *
 * @author lmantyla
 */
public class RandomRollerTest {

    RandomRoller randomRoller;

    @Before
    public void setUp() {
        randomRoller = new RandomRoller();
    }

    @Test
    public void diceThrowNotZeroOrTooLarge() {
        int number = 0;
        number = randomRoller.diceThrow(6);
        for (int luku = 0; luku < 100; luku++) {
            number = randomRoller.diceThrow(6);
            assertNotEquals(number, 0);
            assertNotEquals(number, 7);
        }
    }
}
