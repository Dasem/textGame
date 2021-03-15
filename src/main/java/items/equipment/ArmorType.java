package items.equipment;

import units.character.Character;
import units.character.Stat;

public enum ArmorType {
    LIGHT_ARMOR("Лёгкая броня") {
        @Override
        public int  getArmorClass() {
            return 14 + Character.getInstance().factStat(Stat.AGILITY);
        }

        @Override
        public int getCost() {
            return 30;
        }
    },
    MEDIUM_ARMOR("Средняя броня") {
        @Override
        public int getArmorClass() {
            return 16;
        }

        @Override
        public int getCost() {
            return 60;
        }
    },
    HEAVY_ARMOR("Тяжёлая броня") {
        @Override
        public int getArmorClass() {
            return 18;
        }

        @Override
        public int getCost() {
            return 200;
        }
    };

    private final String title;

    ArmorType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    abstract public int getArmorClass();
    abstract public int getCost();
}

