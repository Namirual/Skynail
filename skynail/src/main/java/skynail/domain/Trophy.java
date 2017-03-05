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
    private boolean skynail;

    /**
     * Creates new trophy.
     * @param gold gold received from trophy.
     * @param newCompanion Companion received from trophy.
     * @param item Item received from trophy.
     */
    public Trophy(int gold, Companion newCompanion, Item item) {
        this.gold = gold;
        this.newCompanion = newCompanion;
        this.item = item;
        this.skynail = false;
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

    public boolean isSkynail() {
        return skynail;
    }

    public void setSkynail(boolean skynail) {
        this.skynail = skynail;
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
            reward += "You meet " + newCompanion.getName() + ", who joins your quest!\n";
        }
        if (skynail == true) {
            reward += "You found the Skynail!";
        }

        if (reward.isEmpty()) {
            return "You find nothing!";
        } else {
            return reward;
        }
    }
}
