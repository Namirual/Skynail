/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.domain;

/**
 * Basic class for characters containing game logic for battle scenes.
 * @author lmantyla
 */
public class GameCharacter {
    protected String name;
    protected int hp;
    protected int maxHP;
    protected int attack;

    /**
     * Creates a game character.
     * @param hp The amount of health a character has.
     * @param attack The amount of damage a character does.
     */
    public GameCharacter(int hp, int attack) {
        this.name = "Anon";
        this.hp = hp;
        this.maxHP = hp;
        this.attack = attack;
    }

    public int getHP() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }
    
    public String getName() {
        return name;
    }

    /**
     * Reduces health by the amount of damage indicated.
     * @param damage
     */
    public void reduceHP(int damage) {
        hp -= damage;
    }

    /**
     * Increases health by the amount indicated up to maximum health.
     * @param healing
     */
    public void healHP(int healing) {
        hp += healing;
        if (hp > maxHP) {
            hp = maxHP;
        }
    }
}
