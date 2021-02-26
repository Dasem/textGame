package mechanic.battle;

import equipment.AccuracyLevel;

import java.util.*;

import static equipment.AccuracyLevel.MISS;
import static utils.Utils.*;

public class Fight {
    private final Battler battler1;
    private final Battler battler2;

    public Fight(Battler battler1, Battler battler2) {
        this.battler1 = battler1;
        this.battler2 = battler2;
    }

    public void battle() {
        while (true) {
            suspense(1000);
            AttackResult attackResult1 = hitOnBattler2();
            if (attackResult1.isKill) {
                System.out.println(attackResult1.attackText);
                System.out.println(battler2.getName() + " умирает");
                return;
            } else {
                System.out.println(attackResult1.attackText);
            }
            suspense(700);
            AttackResult attackResult2 = hitOnBattler1();
            if (attackResult2.isKill) {
                System.out.println(attackResult2.attackText);
                System.out.println(battler1.getName() + " умирает");
                return;
            } else {
                System.out.println(attackResult2.attackText);
            }
        }
    }

    private int getD20Result() {
        return getRandom(1, 20);
    }

    public int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }

    public AttackResult hitOnBattler2() {
        return getAttackResult(battler1, battler2);
    }

    public AttackResult hitOnBattler1() {
        return getAttackResult(battler2, battler1);
    }

    private AttackResult getAttackResult(Battler battlerFrom, Battler battlerTo) {
        AccuracyLevel accuracyLevel = calculateAttack(battlerFrom, battlerTo);
        int damage = accuracyLevel.getTotalDamage(battlerFrom);
        switch (accuracyLevel) {
            case CRITICAL_HIT:
                boolean isDead = battlerTo.takeDamage(damage);
                return new AttackResult(isDead, "Критический удар! " + battlerFrom.getName() + " нанёс " + damage + " урона");
            case NORMAL_HIT:
                isDead = battlerTo.takeDamage(damage);
                return new AttackResult(isDead, battlerFrom.getName() + " нанёс " + damage + " урона");
            case MISS:
                return new AttackResult(false, battlerFrom.getName() + " промахнулся");

        }

        return null;
    }

    public AccuracyLevel calculateAttack(Battler battlerFrom, Battler battlerTo) {
        int d20Result = getD20Result();
        int fullAttackModifier = d20Result + battlerFrom.getAttackModifier();
        int fullArmorClass = battlerTo.getArmorClass();
        if (d20Result == 20) {
            return AccuracyLevel.CRITICAL_HIT;
        } else if (fullAttackModifier >= fullArmorClass) {
            return AccuracyLevel.NORMAL_HIT;
        } else {
            return AccuracyLevel.MISS;
        }

    }
}

