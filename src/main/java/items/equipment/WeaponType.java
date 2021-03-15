package items.equipment;

import units.character.*;
import units.character.Character;
import utils.*;

public enum WeaponType {
    TWO_HANDED_SWORD("Двуручный меч") {
        @Override
        public int getDamage() {
            return 12;
        }
        @Override
        public int getDicedDamage() {
            return Dices.diceD6() + Dices.diceD6() + Character.getInstance().factStat(Stat.STRENGTH);
        }

        @Override
        public int getCost() {
            return 80;
        }
    },
    DAGGER("Кинжал") {
        @Override
        public int getDamage() {
            return 4 ;
        }

        @Override
        public int getDicedDamage() {
            return Dices.diceD4() + Character.getInstance().factStat(Stat.AGILITY);
        }

        @Override
        public int getCost() {
            return 15;
        }
    },
    STAFF("Посох") {
        @Override
        public int getDamage() {
            return 10;
        }

        @Override
        public int getDicedDamage() {
            return Dices.diceD10() + Character.getInstance().factStat(Stat.STRENGTH);
        }

        @Override
        public int getCost() {
            return 50;
        }
    },
    SWORD("Меч") {
        @Override
        public int getDamage() {
            return 6;
        }

        @Override
        public int getDicedDamage() {
            return Dices.diceD6() + Character.getInstance().factStat(Stat.STRENGTH);
        }

        @Override
        public int getCost() {
            return 40;
        }
    };

    private final String title;

    WeaponType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    abstract public int getDamage();

    abstract public int getDicedDamage();

    abstract public int getCost();
}
