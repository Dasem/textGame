package mechanic.labyrinth;

import com.google.common.collect.*;
import equipment.Armor;
import equipment.ArmorType;
import equipment.*;
import equipment.items.HealingPotion;
import equipment.items.HealingPotionType;
import mechanic.battle.*;
import menu.*;
import units.Character;
import units.npcs.*;
import utils.*;
import utils.random.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Labyrinth {

    public void enterLabyrinth() { // start: 3 col, 6 row
        LabyrinthAndPosition labyrinthAndPosition = readLabyrinth();
        Position position = labyrinthAndPosition.getPosition();
        char[][] labyrinth = labyrinthAndPosition.getLabyrinth();
        while (!position.escaped(labyrinth)) {
            findPotion(labyrinth, position);
            findArmor(labyrinth, position);
            findWeapon(labyrinth, position);
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

    private void findArmor(char[][] labyrinth, Position position) {
        if (labyrinth[position.currentRow][position.currentColumn] == 'A') {
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

    private void findPotion(char[][] labyrinth, Position position) {
        if (labyrinth[position.currentRow][position.currentColumn] == '+') {
            HealingPotion healingPotion = Randomizer.randomize(
                    new ObjectAndProbability<>(new HealingPotion(HealingPotionType.LESSER_HEALING_POTION), 3),
                    new ObjectAndProbability<>(new HealingPotion(HealingPotionType.NORMAL_HEALING_POTION), 1),
                    new ObjectAndProbability<>(null, 1)
            );
            Character.getInstance().loot(Lists.newArrayList(healingPotion));
        }
    }

    private void findWeapon(char[][] labyrinth, Position position) {
        if (labyrinth[position.currentRow][position.currentColumn] == '>') {
            Weapon weapon = Randomizer.randomize(
                    new ObjectAndProbability<>(new Weapon(WeaponType.DAGGER), 3),
                    new ObjectAndProbability<>(new Weapon(WeaponType.TWO_HANDED_SWORD), 1),
                    new ObjectAndProbability<>(new Weapon(WeaponType.SWORD), 2));
            Character.getInstance().loot(Lists.newArrayList(weapon));
            clearCurrentCell(labyrinth, position);
        }
    }
    private void clearCurrentCell(char[][] labyrinth, Position position) {
        labyrinth[position.currentRow][position.currentColumn] = ' ';
    }

    private LabyrinthAndPosition readLabyrinth() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource("startLabyrinth").getFile());
        Position position = null;
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
                    if (currentSymbol == 'O') {
                        int xx = currentRow;
                        int xy = currentColumn;
                        position = new Position(xx, xy);
                    }
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
        return new LabyrinthAndPosition(labyrinth,position);

    }

    class LabyrinthAndPosition {
        private final char[][] labyrinth;
        private final Position position;

        public LabyrinthAndPosition(char[][] labyrinth, Position position) {
            this.labyrinth = labyrinth;
            this.position = position;
        }

        public char[][] getLabyrinth() {
            return labyrinth;
        }

        public Position getPosition() {
            return position;
        }
    }
}

