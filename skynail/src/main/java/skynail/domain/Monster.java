package skynail.domain;

/**
 * Monsters are specialised GameCharacters present in dungeons.
 *
 * @author lmantyla
 */
public class Monster extends GameCharacter {

    /**
     * Creates a monster.
     *
     * @param hp The amount of health a character has.
     * @param attack The amount of damage a character does.
     */
    public Monster(int hp, int attack) {
        super(hp, attack);
        this.name = "Monster";
    }
    
    /**
     * Creates a monster with a name.
     *
     * @param hp The amount of health a character has.
     * @param attack The amount of damage a character does.
     * @param name Name of the character.
     */
    public Monster(String name, int hp, int attack) {
        super(hp, attack);
        this.name = name;
    }
}
