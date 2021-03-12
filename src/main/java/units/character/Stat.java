package units.character;

import com.google.common.collect.*;

import java.util.*;

import static units.character.Performance.*;

public enum Stat {
    WISDOM("Мудрость") {
        @Override
        public Set<Performance> getDependencedPerformance() {
            return Sets.newHashSet(PERCEPTION, SURVIVAL, MEDICINE, INSIGHT, ANIMAL_HANDLING);
        }
    },
    BODY("Телосложение") {
        @Override
        public Set<Performance> getDependencedPerformance() {
            return Sets.newHashSet();
        }
    },
    AGILITY("Ловкость") {
        @Override
        public Set<Performance> getDependencedPerformance() {
            return Sets.newHashSet(ACROBATICS, SLEIGHT_OF_HAND, STEALTH);
        }
    },
    INTELLIGENCE("Интеллект") {
        @Override
        public Set<Performance> getDependencedPerformance() {
            return Sets.newHashSet(INVESTIGATION, HISTORY, ARCANA, NATURE, RELIGION);
        }
    },
    CHARISMA("Харизма") {
        @Override
        public Set<Performance> getDependencedPerformance() {
            return Sets.newHashSet(PERFORMANCE, INTIMIDATION, DECEPTION, PERSUASION);
        }
    },
    STRENGTH("Сила") {
        @Override
        public Set<Performance> getDependencedPerformance() {
            return Sets.newHashSet(ATHLETICS);
        }
    };

    String name;

    public static Stat getStatFromString(String string){
        for (Stat stat: Stat.values()){
            if (stat.toString().equals(string)){
                return stat;
            }
        }
        throw new IllegalStateException("Стата не найдена");
    }

    public static Stat getStatFromName(String string){
        for (Stat stat: Stat.values()){
            if (stat.getName().equals(string)){
                return stat;
            }
        }
        throw new IllegalStateException("Стата не найдена");
    }

    Stat(String name) {
        this.name=name;
    }

    public abstract Set<Performance> getDependencedPerformance();

    public String getName() {
        return name;
    }
}