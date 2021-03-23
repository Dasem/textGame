package units.character.specializations;

import mechanic.dice.*;
import units.character.Character;
import units.character.Specialization;
import units.character.Stat;


public class Warlock extends Specialization {
    @Override
    public Stat getMainStat() {
        return null;
    }

    @Override
    public Dice getHitDace() {
        return Dice.D8;
    }

    @Override
    public int getIncreaseHP() {
        return 0;
    }

    @Override
    public Stat getBasedCharacteristicSpell() {
        return null;
    }


}
