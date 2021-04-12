package units.character.specializations;

import com.google.common.collect.Lists;
import mechanic.dice.*;
import units.character.Specialization;
import units.character.Stat;

import java.util.ArrayList;
import java.util.List;


public class Barbarian extends Specialization {

    List<Stat> savingThrow = Lists.newArrayList(Stat.STRENGTH,Stat.BODY);


    @Override
    public Dice getHitDace() {
        return Dice.D12;
    }

    @Override
    public List<Stat> getSavingThrow() {
        return savingThrow;
    }

    @Override
    public Stat getBasedCharacteristicSpell() {
        return null;
    }

    @Override
    public String getName() {
        return "Варвар";
    }
}
