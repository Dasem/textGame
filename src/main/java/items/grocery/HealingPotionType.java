package items.grocery;

import utils.Dices;

public enum HealingPotionType {

    LESSER_HEALING_POTION("Малое зелье лечения") {
        @Override
        public int heal() {
            return Dices.diceD4() + 2;
        }

        @Override
        public int getCost() {
            return 10;
        }
    },
    NORMAL_HEALING_POTION("Среднее зелье лечения") {
        @Override
        public int heal() {
            return (4 * Dices.diceD4()) + 4;
        }

        @Override
        public int getCost() {
            return 20;
        }
    },
    GREATER_HEALING_POTION("Большое зелье лечения") {
        @Override
        public int heal() {
            return (8 * Dices.diceD4()) + 8;
        }

        @Override
        public int getCost() {
            return 40;
        }
    },
    EXCELLENT_HEALING_POTION("Превосходное зелье лечения") {
        @Override
        public int heal() {
            return (10 * Dices.diceD4()) + 20;
        }

        @Override
        public int getCost() {
            return 80;
        }
    };

    private final String title;

    HealingPotionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    abstract public int heal();
    abstract public int getCost();

}
