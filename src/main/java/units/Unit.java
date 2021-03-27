package units;

import mechanic.battle.Battler;

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
}
