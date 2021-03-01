package mechanic.labyrinth;

import equipment.Armor;
import equipment.ArmorType;
import equipment.*;
import equipment.items.HealingPotion;
import equipment.items.HealingPotionType;
import mechanic.battle.*;
import menu.*;
import units.Character;
import units.npcs.Goblin;
import units.npcs.Sanya;
import units.npcs.Wolf;
import utils.*;
import utils.random.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Labyrinth {

    public void enterLabyrinth() { // start: 3 col, 6 row
        char[][] labyrinth = readLabyrinth();
        Position position = new Position(6, 3);
        while (!position.escaped(labyrinth)) {
            findPotion(labyrinth, position);
            findArmor(labyrinth, position);
            findSword(labyrinth, position);
            findFight(labyrinth, position);

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

    private void findFight(char[][] labyrinth, Position position) {
        if (labyrinth[position.currentRow][position.currentColumn] == '@') {
            System.out.println("Бродя по лабиринту, ты замечаешь...");
            //TODO: Саня, полечи
            Battler battler = Randomizer.randomize(
                    new ObjectWithWeight<>(new Wolf(),5),
                    new ObjectWithWeight<>(new Goblin(),10),
                    new ObjectWithWeight<>(new Sanya(),1)
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

    private void findArmor(char[][] labyrinth, Position position) {
        if (labyrinth[position.currentRow][position.currentColumn] == 'A') {
            Armor armor = Randomizer.randomize(
                    new ObjectWithWeight<>(new Armor(ArmorType.LIGHT_ARMOR), 2),
                    new ObjectWithWeight<>(new Armor(ArmorType.HEAVY_ARMOR), 1),
                    new ObjectWithWeight<>(new Armor(ArmorType.MEDIUM_ARMOR), 4),
                    new ObjectWithWeight<>(null, 7)
            );
            if (armor == null) {
                System.out.println("Тут должна была быть броня, но её украл Саня");
            } else {
                Character.getInstance().setArmor(armor);
                System.out.println("Ты подобрал броню, твой текущий класс защиты: " + Character.getInstance().getArmorClass());
            }
        }
    }

    private void findPotion(char[][] labyrinth, Position position) {
        if (labyrinth[position.currentRow][position.currentColumn] == '+') {
            HealingPotion healingPotion = new HealingPotion(HealingPotionType.LESSER_HEALING_POTION);
            Menu HealPotionMenu = new Menu("Ты нашел '" + healingPotion.getName() + "'");
            HealPotionMenu.addItem("Положить в рюкзак", () -> {
                Character.getInstance().getInventory().addItem(healingPotion);
            });
            HealPotionMenu.addItem("Использовать", () -> {
                int heal = healingPotion.use();
                System.out.println("Ты нашел '" + healingPotion.getName() + "' и восстановил " + heal + " ХП. Твоё текущее здоровье: " + Character.getInstance().getCurrentHealth());
            });
            HealPotionMenu.showAndChoose();
        }
    }

    private void findSword(char[][] labyrinth, Position position) {
        if (labyrinth[position.currentRow][position.currentColumn] == '>') {
            Weapon weapon = new Weapon(WeaponType.SWORD);
            Menu weaponPickMenu = new Menu("Ты нашел '" + weapon.getName() + "', его максимальный урон: " + weapon.getWeaponDamage());
            weaponPickMenu.addItem("Взять в руки", () ->{
                    Character.getInstance().setWeapon(weapon);
                    clearCurrentCell(labyrinth, position);});
            weaponPickMenu.addItem("Положить в рюкзак", () ->{
                    Character.getInstance().getInventory().addItem(new Weapon(WeaponType.SWORD));
                    clearCurrentCell(labyrinth, position);});
            weaponPickMenu.addItem("Зачем он нужен(Сломать об колено)", () ->{
                    clearCurrentCell(labyrinth, position);});
            weaponPickMenu.showAndChoose();
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
        System.out.println();
        return labyrinth;
    }
}