package equipment.items;

import menu.Menu;
import equipment.*;
import units.Character;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Map implements Item {
    String itemName;

    public Map(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    private Menu mapMenu() {
        Menu mapMenu = new Menu("Карта:", false);
        mapMenu.addItem("Посмотреть карту", () -> {
            System.out.println("Вы смотрите карту^ \n");
            readLabyrinth();
        });
        mapMenu.addItem("Назад", () ->{
            System.out.println("Вы решили что и так хорошо помните карту");
        });
        return mapMenu;
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

    @Override
    public String getName() {
        return itemName;
    }

    @Override
    public void execute() {
        mapMenu().showAndChoose();
    }
}


