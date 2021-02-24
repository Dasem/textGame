package units;

import equipment.Weapon;
import mechanic.battle.*;

public class Character implements Battler {

    private final String username;
    private int currentHealth = getMaxHealth();
    private int currentDamage;

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
        return currentDamage;
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

    public int setOnHitDamage() {
        Weapon weapon = new Weapon();
        int newWeaponDamage = weapon.getWeaponDamage();
        if (newWeaponDamage>getOnHitDamage()) {
            return currentDamage=newWeaponDamage;
        }
        else return getOnHitDamage();

    }

    @Override
    public String getName() {
        return username;
    }
}
