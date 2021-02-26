package mechanic.labyrinth;

import equipment.Armor;
import equipment.ArmorType;
import equipment.*;
import mechanic.battle.Fight;
import menu.*;
import units.Character;
import units.Wolf;
import utils.Dices;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Labyrinth {

    public void enterLabyrinth() { // start: 3 col, 6 row
        char[][] labyrinth = readLabyrinth();
        Position position = new Position(6, 3, 6, 6);
        while (!position.escaped(labyrinth)) {
            if (labyrinth[position.currentRow][position.currentColumn] == '+') {
                int heal = Dices.diceD4();
                Character.getInstance().healing(heal);
                System.out.println("Ты нашел зелье лечения, твоё текущее здоровье: " + Character.getInstance().getCurrentHealth());
            }
            if (labyrinth[position.currentRow][position.currentColumn] == 'A') {
                Character.getInstance().setArmor(new Armor(ArmorType.LIGHT_ARMOR));
                System.out.println("Ты подобрал броню, твой текущий класс защиты: " + Character.getInstance().getArmorClass());
            }
            if (labyrinth[position.currentRow][position.currentColumn] == '>') {
                Menu weapPickMenu = new Menu("Ты нашел короткий меч");
                Weapon weapon = new Weapon(WeaponType.SWORD);
                System.out.println("Его урон: " + weapon.getWeaponDamage());
        weapPickMenu.addItem("Взять короткий меч в руки" ,() ->
                Character.getInstance().getInventory().addItem(new Weapon(WeaponType.SWORD)));
                Character.getInstance().setWeapon(weapon);
                clearCurrentCell(labyrinth, position);
                weapPickMenu.addItem("Сложить в рюкзак",()->
                        Character.getInstance().getInventory().addItem(new Weapon(WeaponType.SWORD)));
                clearCurrentCell(labyrinth, position);
                weapPickMenu.addItem("Да зачем он нужен?(сломать об колено)",() ->
                        clearCurrentCell(labyrinth, position));
                weapPickMenu.showAndChoose();
            }
            if (labyrinth[position.currentRow][position.currentColumn] == '@') {
                System.out.println("Кажется, начинается битва:");
                Fight fight = new Fight(Character.getInstance(), new Wolf());
                fight.battle();
                if (Character.getInstance().getCurrentHealth() <= 0) {
                    System.out.println("Ты убит волком. пресс F");
                    System.exit(0);
                } else {
                    System.out.println("Бой дался тебе нелегко, но ты чувствуешь в себе силы двигаться дальше");
                    clearCurrentCell(labyrinth, position);
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

    private void clearCurrentCell(char[][] labyrinth, Position position) {
        labyrinth[position.currentRow][position.currentColumn] = ' ';
    }

    private char[][] readLabyrinth() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource("startLabyrinth").getFile());

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
                    System.out.print("\n");
                    continue;
                }
                if (currentColumn < column) {
                    labyrinth[currentRow][currentColumn] = currentSymbol;
                    if (currentSymbol != 'x' && currentSymbol != 'O' && currentSymbol != 'Z') {
                        System.out.print(" ");
                    } else {
                        System.out.print(currentSymbol);
                    }
                    currentColumn++;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return labyrinth;
    }
}