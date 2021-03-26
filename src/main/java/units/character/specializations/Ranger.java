package units.character.specializations;

import mechanic.dice.*;
import units.character.Character;
import units.character.Specialization;
import units.character.Stat;


public class Ranger extends Specialization {


    @Override
    public String getName() {
        return "Следопыт";
    }


    @Override
    public Dice getHitDace() {
        return Dice.D10;
    }

    @Override
    public Stat getBasedCharacteristicSpell() {
        return null;
    }


}
