/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.domain;

/**
 *
 * @author lmantyla
 */
public class Monster extends GameCharacter {

    public Monster(int hp, int attack) {
        super(hp, attack);
        this.name = "Monster";
    }
}
