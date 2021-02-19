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

    public String pathMenu(char[][] labyrinth) {
        // col 0, row +1
        String pathMenu="Выберите путь: \n";
        if (checkValid(currentRow + 1, currentColumn, labyrinth)) {
            pathMenu += "1. Вниз\n";
        } else {
            pathMenu += "1. Низя\n";
        }
        // col +1, row 0
        if (checkValid(currentRow, currentColumn + 1, labyrinth)) {
            pathMenu += "2. Вправо\n";
        } else {
            pathMenu += "2. Низя\n";
        }
        // col -1, row 0
        if (checkValid(currentRow, currentColumn -1, labyrinth)) {
            pathMenu += "3. Влево\n";
        } else {
            pathMenu += "3. Низя\n";
        }
        // col 0, row -1
        if (checkValid(currentRow - 1, currentColumn, labyrinth)) {
            pathMenu += "4. Вверх\n";
        } else {
            pathMenu += "4. Низя\n";
        }
        return pathMenu;
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
        return row <= maxRow && col <= maxColumn && row >= 0 && col >= 0
                && labyrinth[row][col] != 'x';
    }
}
