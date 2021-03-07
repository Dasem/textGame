package units.npcs;

import items.*;
import items.grocery.HealingPotion;
import items.grocery.HealingPotionType;
import items.grocery.UselessItem;
import utils.Dices;
import utils.random.ObjectAndProbability;
import utils.random.Randomizer;

import java.util.*;
import java.util.stream.Collectors;

public class Wolf extends Enemy {
    protected int currentHealth = getMaxHealth();

    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    @Override
    public int getMaxHealth() {
        return 10;
    }

    @Override
    public int getOnHitDamage() {
        return Dices.diceD4();
    }

    @Override
    public int getAttackModifier() {
        return 1;
    }

    @Override
    public int getArmorClass() {
        return 13;
    }

    @Override
    public Collection<Item> getLoot() { //todo Пофиксить 3 уха и тд.
                Collection<Item> colItem = new ArrayList<>();
        int countItem = Randomizer.randomize(
                new ObjectAndProbability<>(1, 3),
                new ObjectAndProbability<>(2, 2),
                new ObjectAndProbability<>(3, 1));
        for ( int i=0;i<countItem;i++) {
            Item itemMob = Randomizer.randomize(
                    new ObjectAndProbability<>(new HealingPotion(HealingPotionType.LESSER_HEALING_POTION), 3),
                    new ObjectAndProbability<>(new UselessItem("Ухо волка"), 1),
                    new ObjectAndProbability<>(null, 1)
            );
            colItem.add(itemMob);
        }
        return colItem.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public boolean takeDamage(int damage) {
        currentHealth -= damage;
        return currentHealth <= 0;
    }

    @Override
    public String getName() {
        return "Волк";
    }
}