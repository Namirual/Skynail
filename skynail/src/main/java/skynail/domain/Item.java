/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynail.domain;

/**
 * Consumable items that can be used in combat.
 * @author lmantyla
 */
public class Item {
    String name;
    int potency;

    /**
     * Creates new item.
     * @param name Name of the item.
     * @param effect How much effect (in HP) the item has.
     */
    public Item(String name, int effect) {
        this.name = name;
        this.potency = effect;
    }
    
    @Override
    public boolean equals(Object obj) {
        Item item = (Item) obj;
        if (item.name.equals(this.name)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
    
    public String getName() {
        return name;
    }

    public int getPotency() {
        return potency;
    }
}
