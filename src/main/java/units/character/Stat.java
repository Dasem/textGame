package units.character;

import com.google.common.collect.*;

import java.util.*;

import static units.character.Performance.*;

public enum Stat {
    STRENGTH("Сила") {
        @Override
        public Set<Performance> getDependencePerformance() {
            return Sets.newHashSet(ATHLETICS);
        }
    },
    AGILITY("Ловкость") {
        @Override
        public Set<Performance> getDependencePerformance() {
            return Sets.newHashSet(ACROBATICS, SLEIGHT_OF_HAND, STEALTH);
        }
    },
    BODY("Телосложение") {
        @Override
        public Set<Performance> getDependencePerformance() {
            return Sets.newHashSet();
        }
    },
    INTELLIGENCE("Интеллект") {
        @Override
        public Set<Performance> getDependencePerformance() {
            return Sets.newHashSet(INVESTIGATION, HISTORY, ARCANA, NATURE, RELIGION);
        }
    },
    WISDOM("Мудрость") {
        @Override
        public Set<Performance> getDependencePerformance() {
            return Sets.newHashSet(PERCEPTION, SURVIVAL, MEDICINE, INSIGHT, ANIMAL_HANDLING);
        }
    },
    CHARISMA("Харизма") {
        @Override
        public Set<Performance> getDependencePerformance() {
            return Sets.newHashSet(PERFORMANCE, INTIMIDATION, DECEPTION, PERSUASION);
        }
    };

    private final String name;

    public static Stat getStatFromString(String string) {
        for (Stat stat : Stat.values()) {
            if (stat.toString().equals(string)) {
                return stat;
            }
        }
        throw new IllegalStateException("Стата не найдена");
    }

    public static Stat getStatFromName(String string) {
        for (Stat stat : Stat.values()) {
            if (stat.getName().equals(string)) {
                return stat;
            }
        }
        throw new IllegalStateException("Стата не найдена");
    }

    Stat(String name) {
        this.name = name;
    }

    public abstract Set<Performance> getDependencePerformance();

    public String getName() {
        return name;
    }
}