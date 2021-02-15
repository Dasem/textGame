import java.io.*;
import java.util.*;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String menu = "1. Новая игра\n2. Выход";
        System.out.println(menu);
        int menuChoose = sc.nextInt();
        switch (menuChoose) {
            case 1:
                Character mainCharacter = createCharacter();
                startGame(mainCharacter);
                break;
            case 2:
                System.out.println("Игра окончена");
                break;
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
            System.out.println("Поздравляю! Ты закончил игру. Вот тебе плюшки.");
        } else if (menuChoose2 == 2) {
            System.out.println("Вы умерли, слава герою " + character.username);
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
        System.out.println("Ура! Лабиринт пройден! Перед тобой открылись просторы древнего мира!");
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
