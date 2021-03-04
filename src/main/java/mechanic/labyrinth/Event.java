package mechanic.labyrinth;

import com.google.common.collect.Lists;
import equipment.Armor;
import equipment.ArmorType;
import equipment.Weapon;
import equipment.WeaponType;
import equipment.items.HealingPotion;
import equipment.items.HealingPotionType;
import mechanic.battle.Battler;
import mechanic.battle.Fight;
import units.Character;
import units.npcs.*;
import utils.Utils;
import utils.random.ObjectAndProbability;
import utils.random.Randomizer;

public class Event {

    public void findFight(char[][] labyrinth, Position position,int row,int column) {
        if (labyrinth[position.currentRow][position.currentColumn] == labyrinth[row][column]) {
            System.out.println("Бродя по лабиринту, ты находишь враждебное существо...");
            Battler battler = Randomizer.randomize(
                    new ObjectAndProbability<>(new Wolf(),5),
                    new ObjectAndProbability<>(new Goblin(),500),
                    new ObjectAndProbability<>(new Sanya(),1),
                    new ObjectAndProbability<>(new Spider(),3),
                    new ObjectAndProbability<>(new Skeleton(),3),
                    new ObjectAndProbability<>(new Gnoll(),1)
            );
            Fight fight = new Fight(Character.getInstance(), battler);
            fight.battle();
            if (Character.getInstance().getCurrentHealth() <= 0) {
                System.out.println("В глубинах лабиринта ты погиб. Причиной твоей смерти стал '" + battler.getName() + "'");
                Utils.endGame();
            } else {
                System.out.println("Бой дался тебе нелегко, но ты чувствуешь в себе силы двигаться дальше");
                clearCurrentCell(labyrinth, position);
            }
        }
    }
    public void findArmor(char[][] labyrinth, Position position,int row,int column) {
        if (labyrinth[position.currentRow][position.currentColumn] == labyrinth[row][column]) {
            Armor armor = Randomizer.randomize(
                    new ObjectAndProbability<>(new Armor(ArmorType.LIGHT_ARMOR), 3),
                    new ObjectAndProbability<>(new Armor(ArmorType.HEAVY_ARMOR), 1),
                    new ObjectAndProbability<>(new Armor(ArmorType.MEDIUM_ARMOR), 2),
                    new ObjectAndProbability<>(null, 2)
            );
            if (armor == null) {
                System.out.println("Тут должна была быть броня, но её украл Саня");
            } else {
                Character.getInstance().loot(Lists.newArrayList(armor));
            }
            clearCurrentCell(labyrinth, position);
        }
    }
    public void findPotion(char[][] labyrinth, Position position,int row,int column) {
        if (labyrinth[position.currentRow][position.currentColumn] == labyrinth[row][column]) {
            HealingPotion healingPotion = Randomizer.randomize(
                    new ObjectAndProbability<>(new HealingPotion(HealingPotionType.LESSER_HEALING_POTION), 3),
                    new ObjectAndProbability<>(new HealingPotion(HealingPotionType.NORMAL_HEALING_POTION), 1),
                    new ObjectAndProbability<>(null, 1)
            );
            Character.getInstance().loot(Lists.newArrayList(healingPotion));
            clearCurrentCell(labyrinth, position);
        }
    }
    public void findWeapon(char[][] labyrinth, Position position,int row,int column) {
        if (labyrinth[position.currentRow][position.currentColumn] == labyrinth[row][column]) {
            Weapon weapon = Randomizer.randomize(
                    new ObjectAndProbability<>(new Weapon(WeaponType.DAGGER), 3),
                    new ObjectAndProbability<>(new Weapon(WeaponType.TWO_HANDED_SWORD), 1),
                    new ObjectAndProbability<>(new Weapon(WeaponType.SWORD), 2));
            Character.getInstance().loot(Lists.newArrayList(weapon));
            clearCurrentCell(labyrinth, position);
        }
    }
    public void clearCurrentCell(char[][] labyrinth, Position position) {
        labyrinth[position.currentRow][position.currentColumn] = ' ';
    }

}
