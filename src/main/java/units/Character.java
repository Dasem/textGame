package units;

import equipment.Weapon;
import mechanic.battle.*;
import utils.*;

public class Character implements Battler {

    private final String username;
    private int currentHealth = getMaxHealth();
    private Weapon weapon;

    public Character(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    @Override
    public int getMaxHealth() {
        return 10;
    }

    @Override
    public int getOnHitDamage() {
        if (weapon == null) {
            return Dices.diceD4();
        } else {
            return weapon.getWeaponType().getDicedDamage();
        }
    }

    @Override
    public int getAttackModifier() {
        return 2;
    }

    @Override
    public int getArmorClass() {
        return 14;
    }

    @Override
    public boolean takeDamage(int damage) {
        currentHealth -= damage;
        return currentHealth <= 0;
    }

    public void healing(int heal) {
        setCurrentHealth(getCurrentHealth() + heal);
    }

    public void setCurrentHealth(int currentHealth) {
        if (currentHealth < 0) {
            this.currentHealth = 0;
        } else if (currentHealth > getMaxHealth()) {
            this.currentHealth = getMaxHealth();
        } else {
            this.currentHealth = currentHealth;
        }
    }

    @Override
    public String getName() {
        return username;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}
