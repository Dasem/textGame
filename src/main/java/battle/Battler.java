package battle;

public interface Battler {
    public abstract int getCurrentHealth();

    public abstract int getMaxHealth();

    public abstract int getOnHitDamage();

    public abstract int getAttackModifier();

    public abstract int getArmorClass();

    /**
     * Возвращает false, если персонаж жив
     * true - если убит
     */
    public abstract boolean takeDamage(int damage);

    public abstract String getName();
}
