package units;

import utils.dices;

public class Wolf extends Enemy {
    protected int currentHealth = getMaxHealth();

    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    @Override
    public int getMaxHealth() {
        return 11;
    }

    @Override
    public int getOnHitDamage() {
        dices dice = new dices();
        return dice.diceD4();
    }

    @Override
    public int getAttackModifier() {
        return 1;
    }

    @Override
    public int getArmorClass() {
        return 13;
    }

    @Override
    public boolean takeDamage(int damage) {
        currentHealth -= damage;
        return currentHealth <= 0;
    }

    @Override
    public String getName() {
        return "Wolf";
    }
}