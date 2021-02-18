package battle;

public interface Battler {
     int getCurrentHealth();

     int getMaxHealth();

     int getOnHitDamage();

     int getAttackModifier();

     int getArmorClass();

    /**
     * Возвращает false, если персонаж жив
     * true - если убит
     */
    public abstract boolean takeDamage(int damage);

    public abstract String getName();
}
