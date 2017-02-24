/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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