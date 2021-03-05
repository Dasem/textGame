package levels;

import com.google.common.collect.Lists;
import items.*;
import items.equipment.*;
import items.grocery.*;
import mechanic.battle.*;
import mechanic.location.*;
import menu.*;
import units.Character;
import units.npcs.*;
import utils.*;
import utils.random.ObjectAndProbability;
import utils.random.Randomizer;

import java.lang.*;

public class Level1 implements Levelable {

    @Override
    public void startLevel() {
        System.out.println("Вы видите перед собой карту и поднимаете её\n");

        Location startLabyrinth = new Location("startLabyrinth");

        Item map = new UsefulItem("Карта подземелья") {
            final Location location = startLabyrinth;

            @Override
            public MenuItemType use() {
                itemMenu.addItem("Посмотреть карту", () -> {
                    location.printMap(false);
                });
                return super.use();
            }
        };

        Character.getInstance().getInventory().addItem(map);
        startLabyrinth.addActions(Lists.newArrayList(
                new Event(4, 1, this::findStartLabyrinthPotion),
                new Event(4, 4, this::findStartLabyrinthArmor),
                new Event(5, 3, this::findStartLabyrinthWeapon),
                new Event(4, 2, this::findStartLabyrinthFight),
                new Event(2, 3, this::findStartLabyrinthFight),
                new EscapeEvent(0, 3, this::crossroad),
                new EscapeEvent(3, 2, this::enchantedForest)
        ));

        startLabyrinth.enterLocation(6, 3).escapeAction();
    }

    private void crossroad() {
        System.out.println("Ура! Лабиринт пройден! Перед тобой открылись просторы древнего мира!");
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
        Location rivergard = new Location("rivergard", LocationSetting.ENABLE_GPS);
        rivergard.addActions(Lists.newArrayList(
                new Event(6, 8, () -> { // фонтан
                    //TODO: Сане сделать фонтан
                }),
                new Event(5, 12, () -> { // кузня

                }),
                new Event(3, 12, () -> { // квест

                }),
                new Event(3, 11, () -> { // генг-бенг

                }),
                new Event(2, 9, () -> { // Магазин

                }),
                new EscapeEvent(4, 0, () -> { // западный выход

                }),
                new EscapeEvent(0, 6, () -> { // северный выход

                })
        ));
        rivergard.enterLocation(9, 8).escapeAction();
    }

    private void litorian() {
        System.out.println("Ты в литориане, но тут пусто, купи DLC, всего за 49,99$");
    }

    private void enchantedForest() {
        Utils.lor(
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
            Utils.lor("Перед тобой появляется волк с явно недружелюбными намерениями\n");
            fightWithWolf();
        });
        menu.showAndChoose();
    }

    private void fightWithWolf() {
        Fight fight = new Fight(Character.getInstance(), new Wolf());
        fight.battle();
        if (Character.getInstance().getCurrentHealth() <= 0) {
            Utils.lor("Ты убит волком. пресс F");
            Utils.endGame();
        } else {
            Utils.lor("Бой дался тебе нелегко, но ты чувствуешь в себе силы двигаться дальше");
        }
    }
    public void findStartLabyrinthFight() {

            Utils.lor("Бродя по лабиринту, ты находишь враждебное существо...");
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
                Utils.lor("В глубинах лабиринта ты погиб. Причиной твоей смерти стал '" + battler.getName() + "'");
                Utils.endGame();
            } else {
                Utils.lor("Бой дался тебе нелегко, но ты чувствуешь в себе силы двигаться дальше");

            }

    }
    public void findStartLabyrinthArmor() {

            Armor armor = Randomizer.randomize(
                    new ObjectAndProbability<>(new Armor(ArmorType.LIGHT_ARMOR), 3),
                    new ObjectAndProbability<>(new Armor(ArmorType.HEAVY_ARMOR), 1),
                    new ObjectAndProbability<>(new Armor(ArmorType.MEDIUM_ARMOR), 2),
                    new ObjectAndProbability<>(null, 2)
            );
            if (armor == null) {
                Utils.lor("Тут должна была быть броня, но её украл Саня");
            } else {
                Character.getInstance().loot(armor);
            }

    }
    public void findStartLabyrinthPotion() {
            HealingPotion healingPotion = Randomizer.randomize(
                    new ObjectAndProbability<>(new HealingPotion(HealingPotionType.LESSER_HEALING_POTION), 3),
                    new ObjectAndProbability<>(new HealingPotion(HealingPotionType.NORMAL_HEALING_POTION), 1),
                    new ObjectAndProbability<>(null, 1)
            );
            Character.getInstance().loot(healingPotion);

    }
    public void findStartLabyrinthWeapon() {
            Weapon weapon = Randomizer.randomize(
                    new ObjectAndProbability<>(new Weapon(WeaponType.DAGGER), 3),
                    new ObjectAndProbability<>(new Weapon(WeaponType.TWO_HANDED_SWORD), 1),
                    new ObjectAndProbability<>(new Weapon(WeaponType.SWORD), 2));
            Character.getInstance().loot(weapon);

    }
}