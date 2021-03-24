package items.equipment;

import mechanic.dice.*;
import units.character.*;
import units.character.Character;
import utils.*;

//TODO: Secondary Добавить стат к оружию, которым оно бьёт
public enum WeaponType {
    TWO_HANDED_SWORD("Двуручный меч") {
        @Override
        public int getDamage() {
            return 12;
        }

        @Override
        public int getDicedDamage() {
            return Dice.D6.roll() + Dice.D6.roll() + Character.getInstance().factStat(Stat.STRENGTH);
        }

        @Override
        public int getCost() {
            return 80;
        }

        public Stat getWeaponStat() {return Stat.STRENGTH;}

        @Override
        public int getUpgradeCost() {
            return getCost()*2;
        }
    },
    DAGGER("Кинжал") {
        @Override
        public int getDamage() {
            return 4 ;
        }

        @Override
        public int getDicedDamage() {
            return Dice.D4.roll() + Character.getInstance().factStat(Stat.AGILITY);
        }

        @Override
        public int getCost() {
            return 15;
        }

        public Stat getWeaponStat() {return Stat.AGILITY;}

        @Override
        public int getUpgradeCost() {
            return getCost()*2;
        }
    },
    STAFF("Посох") {
        @Override
        public int getDamage() {
            return 10;
        }

        @Override
        public int getDicedDamage() {
            return Dice.D10.roll() + Character.getInstance().factStat(Stat.STRENGTH);
        }

        @Override
        public int getCost() {
            return 50;
        }

        public Stat getWeaponStat() {return Stat.STRENGTH;}

        @Override
        public int getUpgradeCost() {
            return getCost()*2;
        }
    },
    SWORD("Меч") {
        @Override
        public int getDamage() {
            return 6;
        }

        @Override
        public int getDicedDamage() {
            return Dice.D6.roll() + Character.getInstance().factStat(Stat.STRENGTH);
        }

        @Override
        public int getCost() {
            return 40;
        }

        public Stat getWeaponStat() {return Stat.STRENGTH;}

        @Override
        public int getUpgradeCost() {
            return getCost()*2;
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

    abstract public Stat getWeaponStat();

    abstract public int getUpgradeCost();
}
