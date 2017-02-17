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
public class Companion extends GameCharacter {

    public Companion(int hp, int attack, String name) {
        super(hp, attack);
        this.name = name;
    }
}
