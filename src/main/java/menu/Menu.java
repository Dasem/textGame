package menu;

import units.Character;
import utils.*;

import java.util.*;

public class Menu {
    String title;
    List<MenuItem> menuItems = new ArrayList<>();

    public void showAndChoose() {
        boolean chooseDone = false;
        while (!chooseDone) {
            try {
                chooseDone = true;
                System.out.println(title);
                for (MenuItem menuItem : menuItems) {
                    menuItem.show();
                }
                int menuChoose = Integer.parseInt(Utils.sc.nextLine());
                MenuItem menuItem = menuItems.get(menuChoose - 1);
                menuItem.execute();
            } catch (NumberFormatException ex) {
                System.out.println("Выберите подходящий вариант меню, ПОЖОЖДА");
                chooseDone = false;
            }
        }
    }

    public Menu(String title) {
        this.title = title;
    }

    public void addItem(String name, Executor executor) {
        menuItems.add(new MenuItem(menuItems.size() + 1, name, executor));
    }
}
