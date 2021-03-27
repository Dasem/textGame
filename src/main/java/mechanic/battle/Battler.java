package mechanic.battle;

import mechanic.dice.*;

import units.Fraction;
import utils.Utils;

import java.util.*;

public interface Battler {
    int getCurrentHealth();

    void setCurrentHealth(int currentHealth);

    int getMaxHealth();

    int getOnHitDamage();

    int getAttackModifier();

    int getArmorClass();

    int mobExp();

    Fraction getFraction();

    void setFraction(Fraction fraction);

    boolean isFriendlyTo(Battler battler);

    boolean isEnemyTo(Battler battler);

    default boolean isDead() {
        return getCurrentHealth() == 0;
    }

    BattleActionResult battleAction(List<Battler> possibleTargets);

    default int initiativeThrow() {
        int initiative = Dice.D20.roll();
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

    void died();
}
