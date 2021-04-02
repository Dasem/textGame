package mechanic.location;

import java.util.*;

@lombok.AllArgsConstructor
public class Position {
    // TODO: обернуть в геттеры и сеттеры
    public int currentRow;
    public int currentColumn;
    public Location location;

    public void goInDirection(PathDirection direction, char[][] location){
        if (checkValidDirection(direction, location)) {
            currentRow += direction.getRowDifference();
            currentColumn += direction.getColumnDifference();
            System.out.println(direction.getMessage());
        } else {
            System.out.println("Говорил же низя. Стоишь на месте");
        }
    }

    public boolean escaped(char[][] location) {
        return location[currentRow][currentColumn] == 'Z';
    }

    public boolean checkValidDirection(PathDirection direction, char[][] location) {
        try {
            return location[currentRow+direction.getRowDifference()][currentColumn+direction.getColumnDifference()] != 'x';
        } catch (ArrayIndexOutOfBoundsException ex) {
            return false;
        }
    }

    public boolean isSamePosition(int row, int col) {
        return currentRow == row && currentColumn == col;
    }

    @Override
    protected Position clone() {
        return new Position(currentRow, currentColumn, location);
    }
}
