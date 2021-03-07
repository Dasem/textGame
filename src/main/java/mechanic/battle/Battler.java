package mechanic.battle;

import utils.Dices;
import utils.Utils;

public interface Battler {
    int getCurrentHealth();

    void setCurrentHealth(int currentHealth);

    int getMaxHealth();

    int getOnHitDamage();

    int getAttackModifier();

    int getArmorClass();

    default int initiativeThrow() {
        int initiative = Dices.diceD20();
        Utils.suspense(250);
        System.out.println(this.getName() + " Бросил на инициативу " + initiative);
        return initiative;
    }

    /**
     * Возвращает false, если персонаж жив
     * true - если убит
     */
    default boolean takeDamage(int damage) {
        setCurrentHealth(getCurrentHealth() - damage);
        return getCurrentHealth() == 0;
    }

    String getName();
}
