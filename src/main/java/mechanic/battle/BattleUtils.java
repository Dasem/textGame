package mechanic.battle;

import com.google.common.collect.*;
import mechanic.dice.*;
import units.character.Character;

import java.util.*;
import java.util.stream.*;

public class BattleUtils {

    /**
     * Достаёт живых союзников для баттлера (напр.: для гоблина союзником будет другой гоблин, мб не всегда)
     * @param toBattler баттлер, для которого ищутся противники
     * @param possibleTargets участники сражения
     * @return все живые противники
     */
    public static List<Battler> extractAliveAllies(Battler toBattler, List<Battler> possibleTargets) {
        return possibleTargets.stream().filter(battler -> battler.isFriendlyTo(toBattler)
                && !battler.isDead()).collect(Collectors.toList());
    }

    /**
     * Достаёт живых противников для баттлера (напр.: для гоблинов противником будет герой, мб не всегда)
     * @param toBattler баттлер, для которого ищутся противники
     * @param possibleTargets участники сражения
     * @return все живые противники
     */
    public static List<Battler> extractAliveOpponents(Battler toBattler, List<Battler> possibleTargets) {
        return possibleTargets.stream().filter(battler -> battler.isEnemyTo(toBattler)
                && !battler.isDead()).collect(Collectors.toList());
    }

    public static BattleActionResult doDirectAttack(Battler battlerFrom, Battler battlerTo) {
        AccuracyLevel accuracyLevel = calculateAttack(battlerFrom, battlerTo);
        int damage = accuracyLevel.getTotalDamage(battlerFrom);
        boolean isDead = battlerTo.takeDamage(damage);
        List<Battler> deadBattlers = new ArrayList<>();
        if (isDead) {
            deadBattlers.add(battlerTo);
        }

        String hpBar = "";
        if (battlerTo instanceof Character) {
            hpBar = Character.getInstance().getHpBar();
        }
        BattleActionResult battleActionResult = null;
        switch (accuracyLevel) {
            case CRITICAL_HIT:
                battleActionResult = new BattleActionResult(deadBattlers, "Критический удар! \uD83D\uDD25 " + battlerFrom.getName() + " нанёс " + damage + " урона ⚔" + hpBar, battlerFrom, Lists.newArrayList(battlerTo));
                break;
            case NORMAL_HIT:
                battleActionResult = new BattleActionResult(deadBattlers, battlerFrom.getName() + " нанёс " + damage + " урона ⚔" + hpBar, battlerFrom, Lists.newArrayList(battlerTo));
                break;
            case MISS:
                battleActionResult = new BattleActionResult(deadBattlers, battlerFrom.getName() + " промахнулся", battlerFrom, Lists.newArrayList(battlerTo));
                break;
        }

        return battleActionResult;
    }

    private static AccuracyLevel calculateAttack(Battler battlerFrom, Battler battlerTo) {
        int d20Result = Dice.D20.roll();
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
