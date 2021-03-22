package units.character.specializations;

import units.character.Character;
import units.character.Specialization;
import units.character.Stat;
import utils.Dices;

public class Paladin extends Specialization {
    @Override
    public Stat getMainStat() {
        return null;
    }

    @Override
    public int getHitDace() {
        return Dices.diceD10();
    }

    @Override
    public int getBasedHP() {
        return 10 + Character.getInstance().factStat(Stat.BODY);
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
