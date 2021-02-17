import battle.*;
import utils.*;

import java.io.*;
import java.util.*;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    static final String START_GAME_MENU = "1. Новая игра\n2. Выход";

    public static void main(String[] args) {
        boolean chooseDone = false;
        while (!chooseDone) {
            try {
                chooseDone = true;
                System.out.println(START_GAME_MENU);
                int menuChoose = Integer.parseInt(sc.nextLine());
                switch (menuChoose) {
                    case 1:
                        Character mainCharacter = createCharacter();
                        startGame(mainCharacter);
                        break;
                    case 2:
                        System.out.println("Игра окончена");
                        break;
                    default:
                        chooseDone = false;
                        throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                System.out.println("Выберите подходящий вариант меню, ПОЖОЖДА");
            }
        }
    }

    private static Character createCharacter() {
        System.out.print("Введите имя персонажа: ");
        String username = sc.next();
        return new Character(username);
    }

    private static void startGame(Character character) {
        String menu1 = "1. Выжить\n2. Умереть";
        System.out.println(menu1);
        int menuChoose2 = sc.nextInt();
        if (menuChoose2 == 1) {
            System.out.println("Вы видите перед собой карту и поднимаете её");
            System.out.println("Необходимо преодолеть лабиринт:");
            labyrinth();
            System.out.println("Ура! Лабиринт пройден! Перед тобой открылись просторы древнего мира!");
            System.out.println("Перед тобой развилка с путевым знаком, на нём видны вариаинты, выбери дальнейший путь:\n" +
                    "1. Ривергард\n" +
                    "2. Литориан\n" +
                    "3. Зачарованный лес");
            switch (sc.nextInt()) {
                case 1:
                    rivergard(character);
                    System.out.println("Перед твоим взором расстилаются огромные ворота города Ривергард");
                    break;
                case 2:
                    litorian();
                    break;
                case 3:
                    enchantedForest();
                    break;
            }
            System.out.println("Поздравляю! Ты закончил игру. Вот тебе плюшки.");
        } else if (menuChoose2 == 2) {
            System.out.println("Вы умерли, слава герою " + character.getUsername());
        }
    }

    /*
    x   x Z     x
    x     x x   x
    x x         x
    x x   x   x x
    x   x x   x x
    x         x x
    x x x O x x x
     */
    private static void labyrinth() { // start: 3 col, 6 row
        char[][] labyrinth = readLabyrinth();
        Position position = new Position(6, 3, 6, 6);
        while (!position.escaped(labyrinth)) {
            System.out.println(position.pathMenu(labyrinth));
            switch (sc.nextInt()) {
                case 1:
                    System.out.println(position.goDown(labyrinth));
                    break;
                case 2:
                    System.out.println(position.goRight(labyrinth));
                    break;
                case 3:
                    System.out.println(position.goLeft(labyrinth));
                    break;
                case 4:
                    System.out.println(position.goTop(labyrinth));
                    break;
            }
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
        } else {
            System.out.println("Бой дался тебе не легко, но ты чувствуешь в себе силы двигаться дальше");
        }
    }

    private static void litorian() {
        System.out.println("Ты в литориане, но тут пусто, купи DLC, всего за 49,99$");
    }

    private static void enchantedForest() {
        System.out.println("Ты в лесу, но тут пока ничего нет.");
    }

    private static char[][] readLabyrinth() {
        char[][] labyrinth = new char[7][7]; // 7x7
        ClassLoader classLoader = Main.class.getClassLoader();
        File file = new File(classLoader.getResource("map").getFile());
        try (FileReader reader = new FileReader(file)) {
            // читаем посимвольно
            int intSymbol;
            int currentRow = 0;
            int currentColumn = 0;
            while ((intSymbol = reader.read()) != -1) {
                char currentSymbol = (char) intSymbol;
                if (currentSymbol == '\n') {
                    currentRow++;
                    currentColumn = 0;
                    continue;
                }

                labyrinth[currentRow][currentColumn] = currentSymbol;
                currentColumn++;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return labyrinth;
    }
}
