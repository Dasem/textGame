package units.character;

import units.character.specializations.*;

import java.util.Set;

public enum Specializations {
    BARBARIAN("Варвар") {
        @Override
        public Barbarian getSpecialization() {
            return new Barbarian();
        }
    },

    BARD("Бард") {
        @Override
        public Bard getSpecialization() {
            return new Bard();
        }
    },

    FIGHTER("Воин") {
        @Override
        public Bard getSpecialization() {
            return new Bard();
        }
    },

    WIZARD("Волшебник") {
        @Override
        public Wizard getSpecialization() {
            return new Wizard();
        }
    },

    DRUID("Друид") {
        @Override
        public Druid getSpecialization() {
            return new Druid();
        }
    },

    CLERIC("Жрец") {
        @Override
        public Cleric getSpecialization() {
            return new Cleric();
        }
    },

    WARLOCK("Колдун") {
        @Override
        public Warlock getSpecialization() {
            return new Warlock();
        }
    },

    MONK("Монах") {
        @Override
        public Monk getSpecialization() {
            return new Monk();
        }
    },

    PALADIN("Паладин") {
        @Override
        public Paladin getSpecialization() {
            return new Paladin();
        }
    },

    ROGUE("Плут") {
        @Override
        public Rogue getSpecialization() {
            return new Rogue();
        }
    },

    RANGER("Следопыт") {
        @Override
        public Ranger getSpecialization() {
            return new Ranger();
        }
    },

    SORCERER("Чародей") {
        @Override
        public Sorcerer getSpecialization() {
            return new Sorcerer();
        }
    };


    private final String name;

    Specializations(String name) {
        this.name = name;
    }

    public abstract Specialization getSpecialization();

    public String getName() {
        return name;
    }




}
