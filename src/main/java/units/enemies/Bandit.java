package units.enemies;

import items.Item;
import items.grocery.HealingPotion;
import items.grocery.HealingPotionType;
import items.grocery.UselessItem;

import mechanic.dice.*;
import utils.random.ObjectAndProbability;
import utils.random.Randomizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Bandit extends Enemy {
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
        return Dice.D4.roll();
    }

    @Override
    public int getAttackModifier() {
        return 2;
    }

    @Override
    public int getArmorClass() {
        return 11;
    }

    public int  mobExp() {return 75;}

    @Override
    public Collection<Item> getLoot() {
        Collection<Item> colItem = new ArrayList<>();
        List<ObjectAndProbability<Item>> loot = new ArrayList<>();
        loot.add(new ObjectAndProbability<>( new HealingPotion(HealingPotionType.LESSER_HEALING_POTION),1));
        loot.add(new ObjectAndProbability<> (new HealingPotion(HealingPotionType.LESSER_HEALING_POTION),1));
        loot.add(new ObjectAndProbability<> ( new UselessItem("Бондана бандита"),1));
        loot.add(new ObjectAndProbability<> ( new UselessItem("Дырявый сапог"),1));
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
        return "Бандит";
    }


}


