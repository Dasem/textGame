package levels;

import com.google.common.collect.Lists;
import items.*;
import items.equipment.*;
import items.grocery.*;
import mechanic.Traps.Trap;
import mechanic.Traps.TrapType;
import mechanic.battle.*;
import mechanic.location.*;
import mechanic.quest.Reward;
import mechanic.quest.Quest;
import mechanic.quest.task.DialogTask;
import mechanic.quest.task.MobTask;
import menu.*;
import units.character.Character;
import units.character.Stat;
import units.enemies.*;
import units.npcs.*;
import utils.*;
import utils.random.ObjectAndProbability;
import utils.random.Randomizer;

import java.lang.*;

import static utils.Utils.lor;

public class Level1 implements Levelable {

    @Override
    public void startLevel() {
        System.out.println("Вы видите перед собой карту и поднимаете её\n");

        Location startLabyrinth = new Location("startLabyrinth");

        Item map = new UsefulItem("Карта подземелья") {
            final Location location = startLabyrinth;

            {
                itemMenu.addItem("Посмотреть карту", () -> {
                    location.printMap(false);
                });
            }
        };

        Character.getInstance().lootItem(map);
        startLabyrinth.addActions(
                new Event(5, 2, this::findTrap),
                new Event(4, 1, this::findStartLabyrinthPotion),
                new Event(4, 4, this::findStartLabyrinthArmor),
                new Event(5, 3, this::findStartLabyrinthWeapon),
                new Event(4, 2, this::findStartLabyrinthFight),
                new Event(2, 3, this::findStartLabyrinthFight),
                new EscapeEvent(0, 3, this::crossroad),
                new EscapeEvent(3, 2, this::enchantedForest)
        );

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
            Character.getInstance().lootItem(new UselessItem("Голова гоблина"));
        }
        Location rivergard = new Location("rivergard", LocationSetting.ENABLE_GPS);
        Trader trader = new Trader(
                new Weapon(WeaponType.STAFF),
                new Armor(ArmorType.MEDIUM_ARMOR),
                new HealingPotion(HealingPotionType.LESSER_HEALING_POTION)
        );
        QuestNPC bartender = new QuestNPC("KillGangBanger", "Bartender",
                () -> System.out.println("Вас встречает статный мужчина средних лет с длинными рыжими волосами."),
                () -> System.out.println("Вижу ты не из робких. Тут в городе назначена награда за голову одного засранца, сходи к доске объявлений, если тебе интересно."),
                () -> System.out.println("С Амброзом, как я вижу ты ещё не разобрался. Так чего ты ждёшь? Благословения?"),
                () -> System.out.println("Слухи о том как ты надрал зад Амброзу уже разнеслись по всему городу. Жаль, что мне не довелось это увидеть самому.\n" +
                        "Доказательств требовать не буду, награда твоя."),
                () -> System.out.println("Ооо, новоиспечённый герой вернулся! Тебе чего?")
        );
        //Таверна (Пока что только можно сдать квест на Генг Бенгера)
        rivergard.addActions(Lists.newArrayList(
                new Event(6, 8, () -> { // фонтан
                    System.out.println("Подойдя к фонтану ты решаешь немного отдохнуть... ");
                    Character.getInstance().setFullRest();
                    System.out.println("Твоё хп: " + Character.getInstance().getCurrentHealth());
                }, false),
                new Event(5, 12, () -> { // кузня

                }),
                new Event(3, 12, () -> { // квест
                    lor("Ты видишь странную фигуру в плаще, которая подзывает тебя к себе\n" +
                            "Ты решаешь подойти к нему...\n" +
                            "- Найди одинокий дом по пути к Думгарду и передай то, что там найдёшь Волчице..." +
                            "*Он кидает тебе звонкий мешочек*" +
                            "и давай без лишних вопросов...");
                    Character.getInstance().earnMoney(100);
                    System.out.println("Ты получаешь 100 голды");
                }, true),
                new Event(3, 11, () -> { // генг-бенг
                    System.out.println("Из стайки бандитов выходит один из них, кажется он хочет выделится...");
                    Fight gangbang = new Fight(Character.getInstance(), new Bandit() {
                        @Override
                        public String getName() {
                            return "Амброз Джакис";
                        }
                    });
                    gangbang.battle();
                    System.out.println("Шайка разбежалась... ");

                }),
                new Event(6, 12, () -> { //Квест в городе на убийство генг бенгера
                    System.out.println("Подойдя к доске объявлений, ты видишь листовку на которой написано:\n " +
                            "\t\t\"АМБРОЗ ДЖАКИС\"\n" +
                            "\"ЖИВЫМ ИЛИ МЁРТВЫМ (ЛУЧШЕ МЁРТВЫМ)\"\n" +
                            "\t\"НАГРАДА 100 ЗОЛОТЫХ\"");
                    Menu menu = new Menu("Взяться за задание?:");
                    menu.addItem("Да", () -> {
                        Quest quest = new Quest("KillGangBanger",
                                "Убить Амброза Джакиса",
                                new Reward(100, null),
                                () -> {
                                }
                        );
                        quest.addTask(new MobTask("Амброз Джакис", 1, "Убить Амброза Джакиса на восточной стороне Ривергарда.", () -> {
                            quest.addTask(new DialogTask("Bartender", "Поговорите с хозяином таверны, чтобы получить награду.")).print();
                        })).print();
                        Character.getInstance().acceptQuest(quest);
                    });
                    menu.addItem("Нет", () -> {
                        System.out.println("На тебя смотрит пьянчуга и говорит: \"Ха, обоссался?! Дед сам с ним разделается!\", - и срывает объявление.");
                    });
                    menu.showAndChoose();
                }),
                new Event(1, 4, bartender::doDialog, false),
                new Event(2, 9, trader::trade, false),
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
        lor("Уверенно шагая по лесной тропинке ты чувствуешь на себе чей-то взгляд.\n" +
                "По спине пробежал холодок.\n" +
                "Ты решаешь перейти на бег, но коварные корни деревьев цепляются тебе за ноги и ты кубарем катишься вниз, в глубь леса.\n" +
                "Встав и отряхнувшись ты видишь перед собой развилку...");
        Menu menu = new Menu("Куда ты отравишься?");
        menu.addItem("Влево", () -> {
//            Location labyrinth = new Location(null,null);
//            labyrinth.enterLabyrinth();
        });
        menu.addItem("Вправо", () -> {
            lor("Перед тобой появляется волк с явно недружелюбными намерениями\n");
            fightWithWolf();
        });
        menu.showAndChoose();
    }

    private void fightWithWolf() {
        Fight fight = new Fight(Character.getInstance(), new Wolf());
        fight.battle();
        if (Character.getInstance().getCurrentHealth() <= 0) {
            lor("Ты убит волком. пресс F");
            Utils.endGame();
        } else {
            lor("Бой дался тебе нелегко, но ты чувствуешь в себе силы двигаться дальше");
        }
    }

    public void findStartLabyrinthFight() {

        lor("Бродя по лабиринту, ты находишь враждебное существо...");
        Battler battler = Randomizer.randomize(
                new ObjectAndProbability<>(new Wolf(), 5),
                new ObjectAndProbability<>(new Goblin(), 10),
                new ObjectAndProbability<>(new Sanya(), 1),
                new ObjectAndProbability<>(new Spider(), 3),
                new ObjectAndProbability<>(new Skeleton(), 3),
                new ObjectAndProbability<>(new Gnoll(), 1)
        );
        AdvancedFight fight = new AdvancedFight(battler);
        fight.battle();
        if (Character.getInstance().getCurrentHealth() <= 0) {
            lor("В глубинах лабиринта ты погиб. Причиной твоей смерти стал '" + battler.getName() + "'");
            Utils.endGame();
        } else {
            lor("Бой дался тебе нелегко, но ты чувствуешь в себе силы двигаться дальше");

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
            lor("Тут должна была быть броня, но её украл Саня");
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

    public void findTrap() {
        int rollResult = Dices.diceD20();
        Trap trap = Randomizer.randomize(
                new ObjectAndProbability<>(new Trap(TrapType.AGILITY_EASY_TRAP),3),
                new ObjectAndProbability<>(new Trap(TrapType.AGILITY_MEDIUM_TRAP),2),
                new ObjectAndProbability<>(new Trap(TrapType.AGILITY_HARD_TRAP),1),
                new ObjectAndProbability<>(new Trap(TrapType.STRENGTH_EASY_TRAP),3),
                new ObjectAndProbability<>(new Trap(TrapType.STRENGTH_EASY_TRAP),2),
                new ObjectAndProbability<>(new Trap(TrapType.STRENGTH_EASY_TRAP),1));
        Stat necessaryStat = trap.trapStat();
        if (rollResult + Character.getInstance().factStat(Stat.WISDOM) >= trap.getTrapPerceptionThreshold()) {
            System.out.println("Вы удачно прошли ловушку");
        } else {
            System.out.println("Вы не прошли ловушку =(");
            int damageCount = Dices.diceD4();
            Character.getInstance().takeDamage(damageCount);
            System.out.println("Вы получили " + damageCount + " урона.");
        }
    }
}

/*if ((rollResult + Character.getInstance().getStat().necessaryStat) > )*/