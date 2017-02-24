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
public class ItemTest {
    Item item;

    @Before
    public void setUp() {
        item = new Item("Potion", -15);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void gettersWork() {
        assertEquals(item.getName(), "Potion");
        assertEquals(item.getPotency(), -15);
    }

}
