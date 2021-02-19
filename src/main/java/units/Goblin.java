package units;

public class Goblin extends Enemy {
    protected int currentHealth = getMaxHealth();

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
        return 3;
    }

    @Override
    public int getAttackModifier() {
        return 1;
    }

    @Override
    public int getArmorClass() {
        return 11;
    }

    @Override
    public boolean takeDamage(int damage) {
        currentHealth -= damage;
        return currentHealth <= 0;
    }

    @Override
    public String getName() {
        return "Goblin";
    }
}
