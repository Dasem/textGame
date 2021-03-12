package units.character;
public enum Performance {

    ATHLETICS("Атлетика", Stat.STRENGTH),
    ACROBATICS("Акробатика", Stat.AGILITY),
    SLEIGHT_OF_HAND("Ловкость рук", Stat.AGILITY),
    STEALTH("Скрытность", Stat.AGILITY),
    ARCANA("Магия", Stat.INTELLIGENCE),
    HISTORY("История", Stat.INTELLIGENCE),
    INVESTIGATION("Анализ", Stat.INTELLIGENCE),
    NATURE("Природа", Stat.INTELLIGENCE),
    RELIGION("Религия", Stat.INTELLIGENCE),
    ANIMAL_HANDLING("Уход за животными", Stat.WISDOM),
    INSIGHT("Проницательность", Stat.WISDOM),
    MEDICINE("Медицина", Stat.WISDOM),
    PERCEPTION("Внимательность", Stat.WISDOM),
    SURVIVAL("Выживание", Stat.WISDOM),
    DECEPTION("Обман", Stat.CHARISMA),
    INTIMIDATION("Запугивание", Stat.CHARISMA),
    PERFORMANCE("Выступление", Stat.CHARISMA),
    PERSUASION("Убеждение", Stat.CHARISMA);

    private final String name;
    private final Stat stat;

    public static Performance getPerformanceFromString(String string){
        for (Performance performance: Performance.values()){
            if (performance.getName().equals(string)){
                return performance;
            }
        }
        throw new IllegalStateException("Архетип не найден");
    }

    Performance(String name, Stat stat) {
        this.name = name;
        this.stat = stat;
    }

    public String getName() {
        return name;
    }

    public Stat getStat() {
        return stat;
    }
}