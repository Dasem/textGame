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
     boolean takeDamage(int damage);

     String getName();
}
