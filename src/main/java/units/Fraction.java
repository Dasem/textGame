package units;

import mechanic.battle.Battler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static units.Relation.*;

public enum Fraction {
    // ATTENTION! не менять попусту порядок в перечислении, влияет на логику взаимоотношений фракций
    // при необходимости возможно добавить ordinal'ы и перенести со встроенного порядка на кастомный
    HERO("Герой"),
    ENEMIES("Враги");

    /**
     * Описаны отношения между фракциями в том же порядке, что и перечисление выше.
     *
     * Например:
     * {ALLY, OPPONENT},
     * {OPPONENT, ALLY}
     * означает, что:
     *      * HERO (индекс в перечислении 0) союзник сам себе (индекс 0)
     *      * ENEMIES (индекс в перечислении 1) противник герою (индекс 0)
     * Также это работает и в обратную сторону.
     * При необходимости есть возможность сделать чтобы "Союзность" была односторонней.
     */
    Relation[][] fractionsRelations = new Relation[][]{
            {ALLY, OPPONENT},
            {OPPONENT, ALLY}
    };

    private final String name;

    Fraction(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public Relation getRelationTo(Battler battler) {
        return fractionsRelations[ordinal()][battler.getFraction().ordinal()];
    }
}
