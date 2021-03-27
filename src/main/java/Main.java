import levels.*;
import menu.*;
import units.Fraction;
import units.character.*;
import units.character.Character;
import utils.*;

public class Main {
    static final String START_GAME_MENU = "1. Новая игра\n2. Выход";

    public static void main(String[] args) {
        Menu startMenu = new Menu("Добро пожаловать в игру:", MenuSetting.HIDE_CHARACTER_MENU);
        startMenu.addItem("Новая игра", () -> {
            Fraction.allInit();
            Character.createInstance();
            new Level1().startLevel();
            new Level2().startLevel();
        });
        startMenu.addItem("Выход", () -> System.out.println("Игра окончена"));
        startMenu.showAndChoose();
    }
}
