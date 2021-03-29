package mechanic.location;

import com.google.common.collect.*;
import mechanic.Actionable;
import menu.*;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Location {
    private List<Event> eventList = new ArrayList<>();
    private final String locationName;
    private Position currentPosition;
    private final List<Position> positionsHistory = new ArrayList<>();
    private final char[][] location;
    private final Collection<LocationSetting> locationSettings;
    private static final int VISION_DEPTH = 3;
    private int locationWidth;
    private int locationHeight;

    public Location(List<Event> eventList, String locationName, LocationSetting... locationSettings) {
        this.eventList = eventList;
        this.locationName = locationName;
        this.location = readLocation();
        this.locationSettings = Lists.newArrayList(locationSettings);
    }

    public Location(String locationName, LocationSetting... locationSettings) {
        this.locationName = locationName;
        this.location = readLocation();
        this.locationWidth = location[0].length;
        this.locationHeight = location.length;
        this.locationSettings = Lists.newArrayList(locationSettings);
    }

    public EscapeEvent enterLocation(int row, int col) {
        return enterLocation(new Position(row, col));
    }

    public EscapeEvent enterLocation(Position startPosition) {
        currentPosition = startPosition;
        while (true) {
            List<Event> forDelete = new LinkedList<>();
            for (Event event : eventList) {
                if (event instanceof EscapeEvent && event.checkPosition(currentPosition)) {
                    return (EscapeEvent) event;
                }

                boolean runned = event.checkPositionAndRunEvent(currentPosition);
                if (event.isSingleTime() && runned) {
                    forDelete.add(event);
                }
            }
            eventList.removeAll(forDelete);

            if (locationSettings.contains(LocationSetting.ENABLE_GPS)) {
                printMap(true);
            }

            if (locationSettings.contains(LocationSetting.ENABLE_VISION)) {
                printVision();
            }

            Menu locationMenu = new Menu("Выбор пути:");
            for (PathDirection direction : PathDirection.values()) {
                locationMenu.addItem(direction.getIcon(), () -> {
                    positionsHistory.add(currentPosition.clone());
                    currentPosition.goInDirection(direction, location);
                });
            }
            locationMenu.showAndChoose();
        }
    }

    public void goBack() {
        System.out.println("Вы отступили!");
        currentPosition = positionsHistory.get(positionsHistory.size() - 1);
    }

    private char[][] readLocation() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource(locationName).getFile());
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
        char[][] location = new char[row][column];
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
                if (currentColumn < column) {
                    location[currentRow][currentColumn] = currentSymbol;
                    currentColumn++;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return location;
    }

    public void printMap(boolean gps) {
        for (int row = -1; row <= locationHeight; row++) {
            for (int col = -1; col <= locationWidth; col++) {
                if (row == -1 || row == locationHeight) {
                    System.out.print('~');
                } else if (col == -1 || col == locationWidth) {
                    System.out.print('|');
                } else {
                    char cell = location[row][col];
                    if (gps && currentPosition.isSamePosition(row, col)) {
                        System.out.print('Ж');
                    } else {
                        System.out.print(cell);
                    }
                }
            }
            System.out.println();
        }
    }


    // Отображает местность вокруг. Если параметр attention == true, то производим проверку на ловушки поблизости.
    public void printVision() {
        int startRow = Math.max(currentPosition.currentRow - VISION_DEPTH, 0) - 1;
        int startCol = Math.max(currentPosition.currentColumn - VISION_DEPTH, 0) - 1;
        int endRow = Math.min(currentPosition.currentRow + VISION_DEPTH, locationHeight);
        int endCol = Math.min(currentPosition.currentColumn + VISION_DEPTH, locationWidth);
        for (int row = startRow; row <= endRow; row++) {
            for (int col = startCol; col <= endCol; col++) {
                if (row == startRow || row == endRow || col == startCol || col == endCol) {
                    System.out.print('~');
                } else {
                    char cell = location[row][col];
                    if (currentPosition.isSamePosition(row, col)) {
                        System.out.print('Ж');
                    } else {
                        System.out.print(cell);
                    }
                }
            }
            System.out.println();
        }
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void addAction(int row, int column, Actionable event) {
        eventList.add(new Event(row, column, event));
    }

    public void addActions(Event... events) {
        addActions(Lists.newArrayList(events));
    }

    public void addActions(Collection<Event> events) {
        eventList.addAll(events);
    }
}

