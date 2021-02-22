import utils.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Labyrinth {

    public void enterLabyrinth() { // start: 3 col, 6 row
        char[][] labyrinth = readLabyrinth();
        Position position = new Position(6, 3, 6, 6);
        while (!position.escaped(labyrinth)) {
            System.out.println(position.pathMenu(labyrinth));
            switch (Utils.sc.nextInt()) {
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

    private char[][] readLabyrinth() {
        ClassLoader classLoader = Main.class.getClassLoader();
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
                //if ( currentColumn < column) {
                    labyrinth[currentRow][currentColumn] = currentSymbol;
                    currentColumn++;
                //}
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return labyrinth;
    }
}