package units.character;


import mechanic.dice.Dice;

public abstract class Specialization {

    abstract public Stat getMainStat();

    abstract public Dice getHitDace();

    public int getBasedHP(){
       return getHitDace().getNumber() + Character.getInstance().factStat(Stat.BODY);
    }

    abstract public int getIncreaseHP();

    abstract public Stat getBasedCharacteristicSpell();


}
