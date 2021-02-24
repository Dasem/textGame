package mechanic.labyrinth;

import equipment.Weapon;
import mechanic.battle.Fight;
import menu.*;
import units.Character;
import units.Goblin;
import units.Wolf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Labyrinth {

    public void enterLabyrinth(Character character) { // start: 3 col, 6 row
        char[][] labyrinth = readLabyrinth();
        Position position = new Position(6, 3, 6, 6);
        while (!position.escaped(labyrinth)) {
            System.out.println();
            if (labyrinth[position.currentRow][position.currentColumn] == '+') {
                int heal = 3;
                character.healing(heal);
                System.out.println("Ты нашел зелье лечения, твоё текущее здоровье: " + character.getCurrentHealth());
            }
            if (labyrinth[position.currentRow][position.currentColumn] == '>') {
                character.setOnHitDamage();
                System.out.println("Ты нашел короткий меч");
                System.out.println("Его урон "+ character.getOnHitDamage());
            }
            if (labyrinth[position.currentRow][position.currentColumn] == '@'){
                System.out.println("Кажется, начинается битва:");
                Fight fight = new Fight(character, new Wolf());
                fight.battle();
                if (character.getCurrentHealth() <=0) {
                    System.out.println("Ты убит волком. пресс F");
                    System.exit(0);
                } else {
                    System.out.println("Бой дался тебе нелегко, но ты чувствуешь в себе силы двигаться дальше");
                    labyrinth[position.currentRow][position.currentColumn] = ' ';
                }
            }

            Menu labyrinthMenu = new Menu("Необходимо преодолеть лабиринт:");
            List<String> pathOptions = position.pathMenu(labyrinth);
            labyrinthMenu.addItem(pathOptions.get(0), () -> {
                System.out.println(position.goDown(labyrinth));
            });
            labyrinthMenu.addItem(pathOptions.get(1), () -> {
                System.out.println(position.goRight(labyrinth));
            });
            labyrinthMenu.addItem(pathOptions.get(2), () -> {
                System.out.println(position.goLeft(labyrinth));
            });
            labyrinthMenu.addItem(pathOptions.get(3), () -> {
                System.out.println(position.goTop(labyrinth));
            });
            labyrinthMenu.showAndChoose();
        }
    }

    private char[][] readLabyrinth() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource("map").getFile());

        int row = 0, column = 0;
        try (FileReader fr = new FileReader(file)) {
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            column = line.length();
            while (line != null) {
                row++;
                line = reader.readLine();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(row + " " + column);
        char[][] labyrinth = new char[row][column]; // 7x7
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
                if (currentColumn < column) {
                    labyrinth[currentRow][currentColumn] = currentSymbol;
                    currentColumn++;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return labyrinth;
    }
}