/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.domain;

/**
 * Monsters are specialised GameCharacters present in dungeons.
 * @author lmantyla
 */
public class Monster extends GameCharacter {

    /**
     * Creates a monster.
     * @param hp The amount of health a character has.
     * @param attack The amount of damage a character does.
     */
    public Monster(int hp, int attack) {
        super(hp, attack);
        this.name = "Monster";
    }
}
