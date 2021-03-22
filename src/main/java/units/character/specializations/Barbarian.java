package units.character.specializations;

import units.character.Character;
import units.character.Specialization;
import units.character.Stat;
import utils.Dices;

public class Barbarian extends Specialization {
    @Override
    public Stat getMainStat() {
        return null;
    }

    @Override
    public int getHitDace() {
        return Dices.diceD12();
    }

    @Override
    public int getBasedHP() {
        return 12 + Character.getInstance().factStat(Stat.BODY);
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
