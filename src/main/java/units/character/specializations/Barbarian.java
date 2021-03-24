package units.character.specializations;

import mechanic.dice.*;
import units.character.Character;
import units.character.Specialization;
import units.character.Stat;


public class Barbarian extends Specialization {
    @Override
    public Stat getMainStat() {
        return null;
    }

    @Override
    public Dice getHitDace() {
        return Dice.D12;
    }

    @Override
    public Stat getBasedCharacteristicSpell() {
        return null;
    }


}
