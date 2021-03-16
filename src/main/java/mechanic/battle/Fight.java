package mechanic.battle;

import units.Lootable;
import units.character.Character;
import units.character.Stat;
import units.enemies.*;
import utils.*;

public class Fight {
    private final Battler battler1;
    private final Battler battler2;

    public Fight(Battler battler1, Battler battler2) {
        this.battler1 = battler1;
        this.battler2 = battler2;
    }

    public void battle() {
        boolean hitOnBattler2 = hitBattler1();
        Battler deadBattler;
        while (true) {
            AttackResult attack = hitOnBattler(hitOnBattler2);
            System.out.println(attack.getAttackText());
            Utils.suspense();
            if (attack.isKill()) {
                deadBattler = attack.getDefender();
                break;
            }
            AttackResult counterAttack = hitOnBattler(!hitOnBattler2);
            System.out.println(counterAttack.getAttackText());
            Utils.suspense(700);
            if (counterAttack.isKill()) {
                deadBattler = counterAttack.getDefender();
                break;
            }
        }
        System.out.println(deadBattler.getName() + " мёртв ☠");
        deadBattler.died();

        if (deadBattler instanceof Lootable) {
            Character.getInstance().loot(((Lootable) deadBattler).getLoot());
        }
    }

    private AttackResult hitOnBattler(boolean hitOnBattler2) {
        if (hitOnBattler2) {
            return getAttackResult(battler1, battler2);
        } else {
            return getAttackResult(battler2, battler1);
        }
    }

    private AttackResult getAttackResult(Battler battlerFrom, Battler battlerTo) {
        AccuracyLevel accuracyLevel = calculateAttack(battlerFrom, battlerTo);
        int damage = accuracyLevel.getTotalDamage(battlerFrom);
        boolean isDead = battlerTo.takeDamage(damage);

        String hpBar = "";
        if (battlerTo instanceof Character) {
            hpBar = Character.getInstance().getHpBar();
        }
        AttackResult attackResult = null;
        switch (accuracyLevel) {
            case CRITICAL_HIT:
                attackResult = new AttackResult(isDead, "Критический удар! \uD83D\uDD25 " + battlerFrom.getName() + " нанёс " + damage + " урона ⚔" + hpBar, battlerFrom, battlerTo);
                break;
            case NORMAL_HIT:
                attackResult = new AttackResult(isDead, battlerFrom.getName() + " нанёс " + damage + " урона ⚔" + hpBar, battlerFrom, battlerTo);
                break;
            case MISS:
                attackResult =  new AttackResult(false, battlerFrom.getName() + " промахнулся", battlerFrom, battlerTo);
                break;
        }

        return attackResult;
    }

    private boolean hitBattler1() {
        //TODO: W3st125 Сделать через instanceof
        /*if (battler1 instanceof Character) {

        }*/
        int initiativBattler1 = battler1.initiativeThrow();
        int initiativBattler2 = battler2.initiativeThrow();
        while (initiativBattler1 == initiativBattler2) {
            System.out.println("Реролл...");
            initiativBattler1 = battler1.initiativeThrow() + Character.getInstance().factStat(Stat.AGILITY);
            initiativBattler2 = battler2.initiativeThrow();
        }
        return initiativBattler1 > initiativBattler2;
    }

    public AccuracyLevel calculateAttack(Battler battlerFrom, Battler battlerTo) {
        int d20Result = Dices.diceD20();
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

