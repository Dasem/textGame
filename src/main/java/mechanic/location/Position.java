package mechanic.location;

import java.util.*;

public class Position {
    public int currentRow;
    public int currentColumn;

    public Position(int currentRow, int currentColumn) {
        this.currentRow = currentRow;
        this.currentColumn = currentColumn;
    }

    public List<String> pathMenu(char[][] labyrinth) {
        List<String> result = new ArrayList<>();
        String NONE = "Пути нет";
        String pathMenu="Выберите путь: \n";

        // col 0, row -1
        if (checkValid(currentRow - 1, currentColumn, labyrinth)) {
            result.add("\u2191" );
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
        // col 0, row +1
        if (checkValid(currentRow + 1, currentColumn, labyrinth)) {
            result.add("\u2193");
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
        try {
            return labyrinth[row][col] != 'x';
        } catch (ArrayIndexOutOfBoundsException ex) {
            return false;
        }
    }

    public boolean isSamePosition(int row, int col) {
        return currentRow == row && currentColumn == col;
    }

    @Override
    protected Position clone() {
        return new Position(currentRow, currentColumn);
    }
}
