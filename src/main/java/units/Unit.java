package units;

import mechanic.battle.Battler;

public abstract class Unit implements Battler {

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
}
