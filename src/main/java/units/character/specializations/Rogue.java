package units.character.specializations;

import units.character.Character;
import units.character.Specialization;
import units.character.Stat;
import utils.Dices;

public class Rogue extends Specialization {
    @Override
    public Stat getMainStat() {
        return null;
    }

    @Override
    public int getHitDace() {
        return Dices.diceD8();
    }

    @Override
    public int getBasedHP() {
        return 8 + Character.getInstance().factStat(Stat.BODY);
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
