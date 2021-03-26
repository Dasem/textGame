package units.character.specializations;

import mechanic.dice.*;
import units.character.Character;
import units.character.Specialization;
import units.character.Stat;


public class Cleric extends Specialization {

    @Override
    public String getName() {
        return "Жрец";
    }



    @Override
    public Dice getHitDace() {
        return Dice.D8;
    }

    @Override
    public Stat getBasedCharacteristicSpell() {
        return null;
    }


}
