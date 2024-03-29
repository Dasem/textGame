package units.character;


import mechanic.dice.Dice;

import java.util.List;

public abstract class Specialization {

    abstract public String getName();

    abstract public Dice getHitDace();

    public int getBasedHP(){
       return getHitDace().getNumber() + Character.getInstance().factStat(Stat.BODY);
    }

    public int getIncreaseHP(){
        return (getHitDace().getNumber())/2 + 1 + Character.getInstance().factStat(Stat.BODY);
    }

    abstract public List<Stat> getSavingThrow() ;

    abstract public Stat getBasedCharacteristicSpell();
}
