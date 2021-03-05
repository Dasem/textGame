package mechanic.location;

import com.google.common.collect.*;
import mechanic.*;
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
    private final char[][] location;
    private final Collection<LocationSetting> locationSettings;

    public Location(List<Event> eventList, String locationName, LocationSetting ... locationSettings) {
        this.eventList = eventList;
        this.locationName = locationName;
        this.location = readLocation();
        this.locationSettings = Lists.newArrayList(locationSettings);
    }

    public Location(String locationName, LocationSetting ... locationSettings) {
        this.locationName = locationName;
        this.location = readLocation();
        this.locationSettings = Lists.newArrayList(locationSettings);
    }

    public EscapeEvent enterLocation(int row, int col) {
        return enterLocation(new Position(row, col));
    }

    public EscapeEvent enterLocation(Position startPosition) {
        currentPosition = startPosition;
        while (true) {
            List<Event> forDelete = new LinkedList<>();
            for(Event event : eventList){
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

            Menu locationMenu = new Menu("Выбор пути:");
            List<String> pathOptions = startPosition.pathMenu(location);
            locationMenu.addItem(pathOptions.get(0), () -> {
                System.out.println(startPosition.goDown(location));
            });
            locationMenu.addItem(pathOptions.get(1), () -> {
                System.out.println(startPosition.goRight(location));
            });
            locationMenu.addItem(pathOptions.get(2), () -> {
                System.out.println(startPosition.goLeft(location));
            });
            locationMenu.addItem(pathOptions.get(3), () -> {
                System.out.println(startPosition.goTop(location));
            });
            locationMenu.showAndChoose();
        }
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
        for (int row = 0; row < location.length; row++) {
            for (int col = 0; col < location[row].length; col++) {
                char cell = location[row][col];
                if (gps && currentPosition.isSamePosition(row, col)) {
                    System.out.print('Ж');
                } else {
                    System.out.print(cell);
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

    public void addActions(Collection<Event> events) {
        eventList.addAll(events);
    }
}

