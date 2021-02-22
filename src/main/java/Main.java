import battle.*;
import menu.*;
import units.*;
import units.Character;
import utils.*;

public class Main {
    static final String START_GAME_MENU = "1. Новая игра\n2. Выход";

    public static void main(String[] args) {
        boolean chooseDone = false;
        while (!chooseDone) {
            try {
                chooseDone = true;
                System.out.println(START_GAME_MENU);
                int menuChoose = Integer.parseInt(Utils.sc.nextLine());
                switch (menuChoose) {
                    case 1:
                        Character mainCharacter = createCharacter();
                        startGame(mainCharacter);
                        break;
                    case 2:
                        System.out.println("Игра окончена");
                        break;
                    default:
                        throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                System.out.println("Выберите подходящий вариант меню, ПОЖОЖДА");
                chooseDone = false;
            }
        }
    }

    private static Character createCharacter() {
        System.out.print("Введите имя персонажа: ");
        String username = Utils.sc.next();
        return new Character(username);
    }

    private static void startGame(Character character) {
        String menu1 = "1. Выжить\n2. Умереть";
        System.out.println(menu1);
        int menuChoose2 = Utils.sc.nextInt();
        if (menuChoose2 == 1) {
            System.out.println("Вы видите перед собой карту и поднимаете её");
            System.out.println("Необходимо преодолеть лабиринт:");
            Labyrinth startLabyrinth = new Labyrinth();
            startLabyrinth.enterLabyrinth(character);
            System.out.println("Ура! Лабиринт пройден! Перед тобой открылись просторы древнего мира!");
            Menu menu = new Menu("Перед тобой развилка с путевым знаком, на нём видны варианты, выбери дальнейший путь:");
            menu.addItem("Ривергард", () -> {
                rivergard(character);
                System.out.println("Перед твоим взором расстилаются огромные ворота города Ривергард");
            });
            menu.addItem("Литориан", () -> {
                litorian();
            });
            menu.addItem("Зачарованный лес", () -> {
                enchantedForest(character);
            });
            menu.showAndChoose();

            System.out.println("Поздравляю! Ты закончил игру. Вот тебе плюшки.");
        } else if (menuChoose2 == 2) {
            System.out.println("Вы умерли, слава герою " + character.getUsername());
        }
    }

    private static void rivergard(Character character) {
        System.out.println("На своём пути к Ривергарду, ты видишь одинокого гоблина...");
        Utils.suspense(1500);
        System.out.println("Кажется, начинается битва:");
        Fight fight = new Fight(character, new Goblin());
        fight.battle();
        if (character.getCurrentHealth() <=0) {
            System.out.println("Ты убит гоблином. пресс F");
            System.exit(0);
        } else {
            System.out.println("Бой дался тебе нелегко, но ты чувствуешь в себе силы двигаться дальше");
        }
    }

    private static void litorian() {
        System.out.println("Ты в литориане, но тут пусто, купи DLC, всего за 49,99$");
    }

    private static void enchantedForest(Character character) {
        System.out.println("Уверенно шагая по лесной тропинке ты чувствуешь на себе чей-то взгляд.\nПо спине пробежал холодок.\nТы решаешь перейти на бег, но коварные корни деревьев цепляются тебе за ноги и ты кубарем катишься вниз, в глубь леса.\nВстав и отряхнувшись ты видишь перед собой развилку...   ");
        Menu menu = new Menu ("Куда ты отравишься?");
        menu.addItem("Влево",() -> {
            Labyrinth labyrinth = new Labyrinth();
            labyrinth.enterLabyrinth(character);
        });

        menu.addItem("Вправо",() -> {
            System.out.println("Перед тобой появляется волк с явно недружелюбными намерениями\n");
            fightWithWolf(character);
        });
        menu.showAndChoose();
    }

    private static void fightWithWolf(Character character) {
        Fight fight = new Fight(character, new Wolf());
        fight.battle();
        if (character.getCurrentHealth() <=0) {
            System.out.println("Ты убит волком. пресс F");
            System.exit(0);
        } else {
            System.out.println("Бой дался тебе нелегко, но ты чувствуешь в себе силы двигаться дальше");
        }
    }


}
