package mechanic.battle;

import equipment.*;

import java.util.*;

public interface Battler {
    int getCurrentHealth();

    int getMaxHealth();

    int getOnHitDamage();

    int getAttackModifier();

    int getArmorClass();

    Collection<Item> getLoot();

    /**
     * Возвращает false, если персонаж жив
     * true - если убит
     */
    boolean takeDamage(int damage);

    String getName();
}
