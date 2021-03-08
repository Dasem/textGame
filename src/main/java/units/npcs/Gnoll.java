package units.npcs;

import items.Item;
import items.grocery.HealingPotion;
import items.grocery.HealingPotionType;
import items.grocery.UselessItem;
import utils.Dices;
import utils.random.ObjectAndProbability;
import utils.random.Randomizer;

import java.util.*;
import java.util.stream.Collectors;

public class Gnoll extends Enemy {
    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    @Override
    public int getMaxHealth() {
        return 20;
    }

    @Override
    public int getOnHitDamage() {
        return Dices.diceD8();
    }

    @Override
    public int getAttackModifier() {
        return 1;
    }

    @Override
    public int getArmorClass() {
        return 15;
    }

    @Override
    public Collection<Item> getLoot() {
        Collection<Item> colItem = new ArrayList<>();
        List<ObjectAndProbability<Item>> loot = new ArrayList<>();
        loot.add(new ObjectAndProbability<>( new HealingPotion(HealingPotionType.LESSER_HEALING_POTION),2));
        loot.add(new ObjectAndProbability<> (new HealingPotion(HealingPotionType.LESSER_HEALING_POTION),2));
        loot.add(new ObjectAndProbability<> ( new UselessItem("Ухо гнолла"),2));
        loot.add(new ObjectAndProbability<> ( new UselessItem("Ухо гнолла"),2));
        int countItem = Randomizer.randomize(
                new ObjectAndProbability<>(1, 3),
                new ObjectAndProbability<>(2, 2),
                new ObjectAndProbability<>(3, 1));
        for ( int i=0;i<countItem;i++) {
            ObjectAndProbability<Item> itemMob = Randomizer.randomizeContainer(loot);
            loot.remove(itemMob);
            colItem.add(itemMob.getObject());
        }
        return colItem.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return "Gnoll";
    }


}


