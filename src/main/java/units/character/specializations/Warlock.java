package units.character.specializations;

import com.google.common.collect.Lists;
import mechanic.dice.*;
import units.character.Specialization;
import units.character.Stat;

import java.util.List;


public class Warlock extends Specialization {
    List<Stat> savingThrow = Lists.newArrayList(Stat.STRENGTH,Stat.BODY);

    @Override
    public String getName() {
        return "Колдун";
    }


    @Override
    public Dice getHitDace() {
        return Dice.D8;
    }

    @Override
    public List<Stat> getSavingThrow() {
        return savingThrow;
    }

    @Override
    public Stat getBasedCharacteristicSpell() {
        return null;
    }


}
