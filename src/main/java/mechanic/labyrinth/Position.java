package mechanic.labyrinth;

import java.util.*;

public class Position {
    public int currentRow;
    public int currentColumn;
    private final int maxRow;
    private final int maxColumn;

    public Position(int currentRow, int currentColumn, int maxRow, int maxColumn) {
        this.currentRow = currentRow;
        this.currentColumn = currentColumn;
        this.maxRow = maxRow;
        this.maxColumn = maxColumn;
    }

    public List<String> pathMenu(char[][] labyrinth) {
        List<String> result = new ArrayList<>();
        String NONE = "Пути нет";
        String pathMenu="Выберите путь: \n";

        // col 0, row +1
        if (checkValid(currentRow + 1, currentColumn, labyrinth)) {
            result.add("\u2193");
        } else {
            result.add(NONE);
        }

        // col +1, row 0
        if (checkValid(currentRow, currentColumn + 1, labyrinth)) {
            result.add("\u2192");
        } else {
            result.add(NONE);
        }
        // col -1, row 0
        if (checkValid(currentRow, currentColumn -1, labyrinth)) {
            result.add("\u2190");
        } else {
            result.add(NONE);
        }
        // col 0, row -1
        if (checkValid(currentRow - 1, currentColumn, labyrinth)) {
            result.add("\u2191");
        } else {
            result.add(NONE);
        }
        return result;
    }

    public String goTop(char[][] labyrinth){
        if (checkValid(currentRow - 1, currentColumn, labyrinth)) {
            currentRow --;
            return "Ты прошёл вверх";
        } else {
            return "Говорил же низя. Стоишь на месте";
        }
    }

    public String goDown(char[][] labyrinth){
        if (checkValid(currentRow + 1, currentColumn, labyrinth)) {
            currentRow ++;
            return "Ты прошёл вниз";
        } else {
            return "Говорил же низя. Стоишь на месте";
        }
    }

    public String goLeft(char[][] labyrinth){
        if (checkValid(currentRow, currentColumn - 1, labyrinth)) {
            currentColumn --;
            return "Ты прошёл влево";
        } else {
            return "Говорил же низя. Стоишь на месте";
        }
    }

    public String goRight(char[][] labyrinth){
        if (checkValid(currentRow, currentColumn + 1, labyrinth)) {
            currentColumn++;
            return "Ты прошёл вправо";
        } else {
            return "Говорил же низя. Стоишь на месте";
        }
    }

    public boolean escaped(char[][] labyrinth) {
        return labyrinth[currentRow][currentColumn] == 'Z';
    }

    private boolean checkValid(int row, int col, char[][] labyrinth) {
        boolean checkNotOutOfBounds = row <= maxRow && col <= maxColumn && row >= 0 && col >= 0;

        return checkNotOutOfBounds &&
                (labyrinth[row][col] != 'x');
    }
}
