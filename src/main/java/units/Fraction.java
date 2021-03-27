package units;

import mechanic.battle.Battler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Fraction {

    private final String name;
    private final List<Fraction> allies;
    private final List<Fraction> enemies;

    private final static List<Fraction> all = new ArrayList<>();

    public static void allInit()
    {
        Fraction gg = new Fraction("ГГ");
        Fraction mobs = new Fraction("Враги");
        gg.addEnemies(mobs);
    }

    public Fraction(String name)
    {
        this(name, new ArrayList<>(), new ArrayList<>());
    }

    public Fraction(String name, List<Fraction> allies, List<Fraction> enemies) {
        this.name = name;
        this.allies = allies;
        this.enemies = enemies;
        all.add(this);
    }

    public String getName() { return name; }

    public List<Fraction> getAllies() {
        return allies;
    }

    public List<Fraction> getEnemies() {
        return enemies;
    }

    private void addAlliesOrEnemies(boolean allies, Fraction ... fractions) {
        (allies ? this.allies : enemies).addAll(Arrays.stream(fractions).collect(Collectors.toList()));
        for (Fraction fraction : fractions) {
            (allies ? fraction.allies : fraction.enemies).add(this);
        }
    }

    public void addAllies(Fraction ... fractions) {
        addAlliesOrEnemies(true, fractions);
    }

    public void addEnemies(Fraction ... fractions) {
        addAlliesOrEnemies(false, fractions);
    }

    public boolean isAlly(Fraction fraction) {
        return this.equals(fraction) || allies.contains(fraction);
    }

    public boolean isAlly(String fractionName) {
        return allies.contains(getByName(fractionName));
    }

    public boolean isAlly(Battler battler) {
        return isAlly(battler.getFraction());
    }

    public static Fraction getByName(String name) {
        return all.stream().filter((fraction) -> fraction.name.equals(name)).findFirst().orElseGet(() -> {
            System.out.println("Фракция " + name + " не найдена.");
            return null;
        });
    }
}
