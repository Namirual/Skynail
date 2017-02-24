/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.domain;

/**
 * Companions are specialised Game Characters that are part of player parties.
 * @author lmantyla
 */
public class Companion extends GameCharacter {
    
    /**
     * Creates a companion.
     * @param hp The amount of health a character has.
     * @param attack The amount of damage a character does.
     * @param name Name of the companion character.
     */
    public Companion(int hp, int attack, String name) {
        super(hp, attack);
        this.name = name;
    }
}
