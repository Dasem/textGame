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

    public void enterLabyrinth() {
        LabyrinthAndPosition labyrinthAndPosition = readLabyrinth();
        Position position = labyrinthAndPosition.getPosition();
        char[][] labyrinth = labyrinthAndPosition.getLabyrinth();
        Event event = new Event();
        while (!position.escaped(labyrinth)) {
            event.findPotion(labyrinth, position,4,2);
            event.findArmor(labyrinth, position,4,5);
            event.findWeapon(labyrinth, position,5,4);
            event.findFight(labyrinth, position,4,3);

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
        char[][] labyrinth = new char[row][column];
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
                        position = new Position(currentRow, currentColumn);
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
        return new LabyrinthAndPosition(labyrinth, position);

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

