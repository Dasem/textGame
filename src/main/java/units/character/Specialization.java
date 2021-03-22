package units.character;


import utils.Dices;
import utils.Utils;

public abstract class Specialization {

    abstract public Stat getMainStat();

    abstract public int getHitDace();

    abstract  public int getBasedHP();

    abstract public int getIncreaseHP();

    abstract public Stat getBasedCharacteristicSpell();


}
