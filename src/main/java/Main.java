import levels.*;
import menu.*;
import units.Character;
import utils.*;

public class Main {
    static final String START_GAME_MENU = "1. Новая игра\n2. Выход";

    public static void main(String[] args) {
        Menu startMenu = new Menu("Добро пожаловать в игру:");
        startMenu.addItem("Новая игра", () -> {
            Character mainCharacter = createCharacter();
            new Level1().startLevel(mainCharacter);
            new Level2().startLevel(mainCharacter);
        });
        startMenu.addItem("Выход", () -> {
            System.out.println("Игра окончена");
        });
        startMenu.showAndChoose();
    }

    private static Character createCharacter() {
        System.out.print("Введите имя персонажа: ");
        String username = Utils.sc.nextLine();
        return new Character(username);
    }
}
