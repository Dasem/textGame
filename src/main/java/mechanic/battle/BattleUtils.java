package mechanic.battle;

import com.google.common.collect.*;
import units.character.*;
import units.character.Character;
import utils.*;

import java.util.*;
import java.util.stream.*;

public class BattleUtils {

    public static List<Battler> extractAliveAllies(List<Battler> possibleTargets) {
        return possibleTargets.stream().filter(battler -> battler.isFriendly() && !battler.isDead()).collect(Collectors.toList());
    }

    public static List<Battler> extractAliveOpponents(List<Battler> possibleTargets) {
        return possibleTargets.stream().filter(battler -> !battler.isFriendly() && !battler.isDead()).collect(Collectors.toList());
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
        if (battlerTo instanceof units.character.Character) {
            hpBar = units.character.Character.getInstance().getHpBar();
        }
        BattleActionResult BattleActionResult = null;
        switch (accuracyLevel) {
            case CRITICAL_HIT:
                BattleActionResult = new BattleActionResult(deadBattlers, "Критический удар! \uD83D\uDD25 " + battlerFrom.getName() + " нанёс " + damage + " урона ⚔" + hpBar, battlerFrom, Lists.newArrayList(battlerTo));
                break;
            case NORMAL_HIT:
                BattleActionResult = new BattleActionResult(deadBattlers, battlerFrom.getName() + " нанёс " + damage + " урона ⚔" + hpBar, battlerFrom, Lists.newArrayList(battlerTo));
                break;
            case MISS:
                BattleActionResult = new BattleActionResult(deadBattlers, battlerFrom.getName() + " промахнулся", battlerFrom, Lists.newArrayList(battlerTo));
                break;
        }

        return BattleActionResult;
    }

    private static AccuracyLevel calculateAttack(Battler battlerFrom, Battler battlerTo) {
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
