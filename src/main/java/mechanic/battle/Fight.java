package mechanic.battle;

import equipment.*;
import menu.Lootable;
import units.Character;
import utils.*;

public class Fight {
    private final Battler battler1;
    private final Battler battler2;

    public Fight(Battler battler1, Battler battler2) {
        this.battler1 = battler1;
        this.battler2 = battler2;
    }

    public void battle() {
        boolean firstHit = firstHitBattler1();
        boolean battleEnd = false;
        Battler deadButtler = null;
        while (!battleEnd) {
            AttackResult attackResult = hitOnBattler(firstHit);
            System.out.println(attackResult.getAttackText());
            Utils.suspense();
            if (!attackResult.isKill()) {
                AttackResult counterAttack = hitOnBattler(!firstHit);
                System.out.println(counterAttack.getAttackText());
                Utils.suspense(700);
                if (counterAttack.isKill()) {
                    battleEnd = true;
                    deadButtler = attackResult.getOffencer();
                }
            } else {
                battleEnd = true;
                deadButtler = battler2;
            }
        }
            System.out.println(deadButtler.getName()+" мёртв ☠");
        if (Character.getInstance().isDead()){
            Utils.endGame();
        }
        if(deadButtler instanceof Lootable){
            Character.getInstance().loot(((Lootable) deadButtler).getLoot());
        }
    }

    private AttackResult hitOnBattler(boolean firstHit) {
        if (firstHit) {
            return getAttackResult(battler1, battler2);
        } else return getAttackResult(battler2, battler1);
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
                return new AttackResult(isDead, "Критический удар! \uD83D\uDD25 " + battlerFrom.getName() + " нанёс " + damage + " урона ⚔",battlerFrom,battlerTo);
            case NORMAL_HIT:
                isDead = battlerTo.takeDamage(damage);
                return new AttackResult(isDead, battlerFrom.getName() + " нанёс " + damage + " урона ⚔",battlerFrom,battlerTo);
            case MISS:
                return new AttackResult(false, battlerFrom.getName() + " промахнулся",battlerFrom,battlerTo);

        }

        return null;
    }

    private boolean firstHitBattler1() {
        int initiativBattler1 = battler1.initiativThrow();
        int initiativBattler2 = battler2.initiativThrow();
        while (initiativBattler1 == initiativBattler2) {
            initiativBattler1 = battler1.initiativThrow();
            initiativBattler2 = battler2.initiativThrow();
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

