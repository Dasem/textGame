import levels.*;
import menu.*;
import units.character.*;
import units.character.Character;
import utils.*;

public class Main {
    static final String START_GAME_MENU = "1. Новая игра\n2. Выход";

    public static void main(String[] args) {
        Menu startMenu = new Menu("Добро пожаловать в игру:", MenuSetting.HIDE_CHARACTER_MENU);
        startMenu.addItem("Новая игра", () -> {
            createCharacter();
            new Level1().startLevel();
            new Level2().startLevel();
        });
        startMenu.addItem("Выход", () -> {
            System.out.println("Игра окончена");
        });
        startMenu.showAndChoose();
    }

    private static void createCharacter() {
        System.out.print("Введите имя персонажа: ");
        String username = Utils.sc.nextLine();
        Character.createInstance(username);
        System.out.print("Задайте характеристики: ");
        //TODO: сделать распределение статов
        Character.getInstance().getStats().put(Stat.BODY, 12);
        Character.getInstance().getStats().put(Stat.CHARISMA, 12);
        Character.getInstance().getStats().put(Stat.INTELLIGENCE, 12);
        Character.getInstance().getStats().put(Stat.AGILITY, 12);
        Character.getInstance().getStats().put(Stat.STRENGTH, 14);
        Character.getInstance().getStats().put(Stat.WISDOM, 12);
    }
}
