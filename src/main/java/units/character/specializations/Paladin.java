package units.character.specializations;

import mechanic.dice.*;
import units.character.Character;
import units.character.Specialization;
import units.character.Stat;


public class Paladin extends Specialization {
    @Override
    public String getName() {
        return "Паладин";
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
