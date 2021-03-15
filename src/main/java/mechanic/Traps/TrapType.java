package mechanic.Traps;

import com.google.common.collect.Sets;
import units.character.Stat;

import java.util.Set;

public enum TrapType {

    STRENGTH_EASY_TRAP("Легкая ловушка на силу") {
        public int trapDifficulty() {
            return 10;
        }

        public int trapPerceptionThreshold() {
            return 10;
        }

        public Stat trapStat() {
            return Stat.STRENGTH;
        }
    },

    STRENGTH_MEDIUM_TRAP("Средняя ловушка на силу") {
        public int trapDifficulty() {
            return 10;
        }

        public int trapPerceptionThreshold() {
            return 15;
        }

        public Stat trapStat() {
            return Stat.STRENGTH;
        }
    },

    STRENGTH_HARD_TRAP("Сложная ловушка на силу") {
        public int trapDifficulty() {
            return 15;
        }

        public int trapPerceptionThreshold() {
            return 15;
        }

        public Stat trapStat() {
            return Stat.STRENGTH;
        }
    },

    AGILITY_EASY_TRAP("Легкая ловушка на ловкость") {
        public int trapDifficulty() {
            return 10;
        }

        public int trapPerceptionThreshold() {
            return 10;
        }

        public Stat trapStat() {
            return Stat.AGILITY;
        }
    },

    AGILITY_MEDIUM_TRAP("Средняя ловушка на ловкость") {
        public int trapDifficulty() {
            return 10;
        }

        public int trapPerceptionThreshold() {
            return 15;
        }

        public Stat trapStat() {
            return Stat.AGILITY;
        }
    },

    AGILITY_HARD_TRAP("Сложная ловушка на ловкость") {
        public int trapDifficulty() {
            return 15;
        }

        public int trapPerceptionThreshold() {
            return 15;
        }

        public Stat trapStat() {
            return Stat.AGILITY;
        }
    };

    private final String title;

    TrapType(String title) {
        this.title = title;
    }

    abstract public int trapDifficulty();
    abstract public int trapPerceptionThreshold();
    public abstract Stat trapStat();

}
