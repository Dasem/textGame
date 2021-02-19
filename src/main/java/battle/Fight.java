package battle;

import java.util.*;

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
        return getRandom(1,20);
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
        int damage = calculateAttack(battlerFrom, battlerTo);
        if (damage == 0) {
            return new AttackResult(false, battlerFrom.getName() + " промахнулся");
        } else {
            boolean isDead = battlerTo.takeDamage(damage);
            return new AttackResult(isDead, battlerFrom.getName() + " нанёс " + damage + " урона");
        }
    }

    public int calculateAttack(Battler battlerFrom, Battler battlerTo) {
        int fullAttackModifier = getD20Result() + battlerFrom.getAttackModifier();
        int fullArmorClass = battlerTo.getArmorClass();
        if (fullAttackModifier >= fullArmorClass) {
            return battlerFrom.getOnHitDamage();
        } else {
            return 0;
        }
    }
}
