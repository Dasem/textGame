package levels;

import com.google.common.collect.Lists;
import equipment.Armor;
import equipment.ArmorType;
import equipment.Weapon;
import equipment.WeaponType;
import equipment.items.*;
import equipment.items.Map;
import mechanic.battle.*;
import mechanic.labyrinth.*;
import menu.*;
import units.Character;
import units.npcs.*;
import utils.*;
import utils.random.ObjectAndProbability;
import utils.random.Randomizer;

import java.lang.*;
import java.util.ArrayList;
import java.util.List;

public class Level1 implements Levelable {

    @Override
    public void startLevel() {
        System.out.println("Вы видите перед собой карту и поднимаете её\n");

        Character.getInstance().getInventory().addItem(new Map("Карта подземелья"));
        List<Event> labyrinthEventList = new ArrayList<>();
        labyrinthEventList.add(new Event(4,2, this::findPotion));
        labyrinthEventList.add(new Event(4,5, this::findArmor));
        labyrinthEventList.add(new Event(5,4, this::findWeapon));
        labyrinthEventList.add(new Event(4,3, this::findFight));
        labyrinthEventList.add(new Event(2,3, this::findFight));
        Location startLabyrinth = new Location(labyrinthEventList,"startLabyrinth");
        startLabyrinth.enterLabyrinth(); System.out.println("Ура! Лабиринт пройден! Перед тобой открылись просторы древнего мира!");
        Menu menu = new Menu("Перед тобой развилка с путевым знаком, на нём видны варианты, выбери дальнейший путь:");
        menu.addItem("Ривергард", () -> {
            rivergard();
            System.out.println("Перед твоим взором расстилаются огромные ворота города Ривергард");
        });
        menu.addItem("Литориан", () -> {
            litorian();
        });
        menu.addItem("Зачарованный лес", () -> {
            enchantedForest();
        });
        menu.showAndChoose();

        System.out.println("Поздравляю! Ты закончил первый уровень. Вот тебе плюшки.");
    }



    private void rivergard() {
        System.out.println("На своём пути к Ривергарду, ты видишь одинокого гоблина...");
        Utils.suspense(1500);
        System.out.println("Кажется, начинается битва:");
        Fight fight = new Fight(Character.getInstance(), new Goblin());
        fight.battle();
        if (Character.getInstance().getCurrentHealth() <= 0) {
            System.out.println("Ты убит гоблином. пресс F");
            Utils.endGame();
        } else {
            System.out.println("Бой дался тебе нелегко, но ты чувствуешь в себе силы двигаться дальше");
            Utils.suspense();
            System.out.println("После недолгих раздумий ты берёшь с собой голову гоблина");
            Character.getInstance().getInventory().addItem(new UselessItem("Голова гоблина"));
        }
    }

    private void litorian() {
        System.out.println("Ты в литориане, но тут пусто, купи DLC, всего за 49,99$");
    }

    private void enchantedForest() {
        System.out.println(
                "Уверенно шагая по лесной тропинке ты чувствуешь на себе чей-то взгляд.\n" +
                        "По спине пробежал холодок.\n" +
                        "Ты решаешь перейти на бег, но коварные корни деревьев цепляются тебе за ноги и ты кубарем катишься вниз, в глубь леса.\n" +
                        "Встав и отряхнувшись ты видишь перед собой развилку...");
        Menu menu = new Menu("Куда ты отравишься?");
        menu.addItem("Влево", () -> {
//            Location labyrinth = new Location(null,null);
//            labyrinth.enterLabyrinth();
        });
        menu.addItem("Вправо", () -> {
            System.out.println("Перед тобой появляется волк с явно недружелюбными намерениями\n");
            fightWithWolf();
        });
        menu.showAndChoose();
    }

    private void fightWithWolf() {
        Fight fight = new Fight(Character.getInstance(), new Wolf());
        fight.battle();
        if (Character.getInstance().getCurrentHealth() <= 0) {
            System.out.println("Ты убит волком. пресс F");
            Utils.endGame();
        } else {
            System.out.println("Бой дался тебе нелегко, но ты чувствуешь в себе силы двигаться дальше");
        }
    }
    public void findFight() {

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

            }

    }
    public void findArmor() {

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

    }
    public void findPotion() {
            HealingPotion healingPotion = Randomizer.randomize(
                    new ObjectAndProbability<>(new HealingPotion(HealingPotionType.LESSER_HEALING_POTION), 3),
                    new ObjectAndProbability<>(new HealingPotion(HealingPotionType.NORMAL_HEALING_POTION), 1),
                    new ObjectAndProbability<>(null, 1)
            );
            Character.getInstance().loot(Lists.newArrayList(healingPotion));

    }
    public void findWeapon() {
            Weapon weapon = Randomizer.randomize(
                    new ObjectAndProbability<>(new Weapon(WeaponType.DAGGER), 3),
                    new ObjectAndProbability<>(new Weapon(WeaponType.TWO_HANDED_SWORD), 1),
                    new ObjectAndProbability<>(new Weapon(WeaponType.SWORD), 2));
            Character.getInstance().loot(Lists.newArrayList(weapon));

    }
}