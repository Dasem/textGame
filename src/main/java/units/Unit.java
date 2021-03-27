package units;

import mechanic.battle.BattleActionResult;
import mechanic.battle.BattleUtils;
import mechanic.battle.Battler;
import utils.Utils;

import java.util.List;

public abstract class Unit implements Battler {

    protected int currentHealth;
    private Fraction fraction;

    protected Unit(Fraction fraction) {
        this.fraction = fraction;
    }

    @Override
    public void setFraction(Fraction fraction) {
        this.fraction = fraction;
    }

    @Override
    public Fraction getFraction() {
        return fraction;
    }

    @Override
    public boolean isFriendlyTo(Battler battler) {
        return fraction.isAlly(battler);
    }


    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    @Override
    public void setCurrentHealth(int currentHealth) {
        if (currentHealth < 0) {
            this.currentHealth = 0;
        } else {
            this.currentHealth = Math.min(currentHealth, getMaxHealth());
        }
    }

    @Override
    public BattleActionResult battleAction(List<Battler> possibleTargets) {
        Utils.suspense();
        List<Battler> opponents = BattleUtils.extractAliveOpponents(this, possibleTargets);
        return BattleUtils.doDirectAttack(this, opponents.get(0));
    }
}
