/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.domain;

/**
 * The trophy stores the rewards received from exploring a dungeon.
 *
 * @author lmantyla
 */
public class Trophy {

    private int gold;
    private Companion newCompanion;
    private Item item;

    public Trophy(int gold, Companion newCompanion, Item item) {
        this.gold = gold;
        this.newCompanion = newCompanion;
        this.item = item;
    }
    
    public int getGold() {
        return gold;
    }

    public Item getItem() {
        return item;
    }

    public Companion getNewCompanion() {
        return newCompanion;
    }

    @Override
    public String toString() {
        String reward = "";
        if (gold > 0) {
            reward += "You find " + gold + " gold!\n";
        }
        if (item != null) {
            reward += "You find a " + item.getName() + "!\n";
        }
        if (newCompanion != null) {
            reward += "You meet " + newCompanion.getName() + ", who joins your quest!";
        }
        if (reward.isEmpty()) {
            return "You find nothing!";
        } else {
            return reward;
        }
    }
}
