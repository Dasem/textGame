package units.npcs;

import equipment.*;
import utils.Dices;

import java.util.*;

public class Wolf extends Enemy {
    protected int currentHealth = getMaxHealth();

    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    @Override
    public int getMaxHealth() {
        return 13;
    }

    @Override
    public int getOnHitDamage() {
        return Dices.diceD4();
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
    public Collection<Item> getLoot() {
        return null;
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