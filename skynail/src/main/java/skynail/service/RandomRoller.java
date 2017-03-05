package skynail.service;

import java.util.Random;

/**
 * Dice rolling, separated as a class to make testing easier.
 * 
 * @author lmantyla
 */
public class RandomRoller implements DiceRoller {

    Random random;

    /**
     * Creates new RandomRoller.
     */
    public RandomRoller() {
        this.random = new Random();
    }

    @Override
    public int diceThrow(int number) {
        return random.nextInt(number) + 1;
    }

}
