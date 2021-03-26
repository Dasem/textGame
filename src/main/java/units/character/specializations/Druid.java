package units.character.specializations;

import mechanic.dice.*;
import units.character.Character;
import units.character.Specialization;
import units.character.Stat;


public class Druid extends Specialization {
    @Override
    public String getName() {
        return "Друид";
    }



    @Override
    public Dice getHitDace() {return Dice.D8;}

    @Override
    public Stat getBasedCharacteristicSpell() {
        return null;
    }


}
