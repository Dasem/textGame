package mechanic.labyrinth;

import menu.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Location {
    List<Event> eventList = new ArrayList<>();
    String locationName;

    public Location(List<Event> eventList, String locationName) {
        this.eventList = eventList;
        this.locationName = locationName;
    }

    public Location(String locationName) {
        this.locationName = locationName;
    }

    public EscapeEvent enterLocation() {
        LocationAndPosition locationAndPosition = readLocation();
        Position position = locationAndPosition.getPosition();
        char[][] location = locationAndPosition.getLocation();
        while (true) {
            List<Event> forDelete = new LinkedList<>();
            for(Event event : eventList){
                if (event instanceof EscapeEvent && event.checkPosition(position)) {
                    return (EscapeEvent) event;
                }

                boolean runned = event.checkPositionAndRunEvent(position);
                if (event.isSingleTime() && runned) {
                    forDelete.add(event);
                }
            }
            eventList.removeAll(forDelete);

            Menu locationMenu = new Menu("Выбор пути:");
            List<String> pathOptions = position.pathMenu(location);
            locationMenu.addItem(pathOptions.get(0), () -> {
                System.out.println(position.goDown(location));
            });
            locationMenu.addItem(pathOptions.get(1), () -> {
                System.out.println(position.goRight(location));
            });
            locationMenu.addItem(pathOptions.get(2), () -> {
                System.out.println(position.goLeft(location));
            });
            locationMenu.addItem(pathOptions.get(3), () -> {
                System.out.println(position.goTop(location));
            });
            locationMenu.showAndChoose();
        }
    }


    private LocationAndPosition readLocation() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource(locationName).getFile());
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
        return new LocationAndPosition(labyrinth, position);

    }

    public void addAction(int row, int column, Executable event) {
        eventList.add(new Event(row, column, event));
    }

    public void addActions(Collection<Event> events) {
        eventList.addAll(events);
    }

    class LocationAndPosition {
        private final char[][] location;
        private final Position position;

        public LocationAndPosition(char[][] location, Position position) {
            this.location = location;
            this.position = position;
        }

        public char[][] getLocation() {
            return location;
        }

        public Position getPosition() {
            return position;
        }
    }
}

