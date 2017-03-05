package skynail.service;

/**
 * Interface for dice rolling.
 * 
 * @author lmantyla
 */
public interface DiceRoller {

    /**
     * Handles a dice throw.
     * @param number maximum highest number the throw may be.
     * @return a random number.
     */
    int diceThrow(int number); 
}