package mechanic.battle;

import utils.Dices;
import utils.Utils;

public interface Battler {
    int getCurrentHealth();

    int getMaxHealth();

    int getOnHitDamage();

    int getAttackModifier();

    int getArmorClass();

  default  int initiativThrow() {
      int initiativ = Dices.diceD20();
      Utils.suspense(250);
      System.out.println(this.getName()+" Бросил на инициативу "+initiativ);
    return initiativ;
    }



    /**
     * Возвращает false, если персонаж жив
     * true - если убит
     */
    boolean takeDamage(int damage);

    String getName();
}
