package menu;

import equipment.*;
import units.Character;
import utils.*;

import java.util.*;

public class Menu {
    String title;
    List<MenuItem> menuItems = new ArrayList<>();

    public void showAndChoose() {
        Utils.suspense(500);
        boolean chooseDone = false;
        while (!chooseDone) {
            try {
                chooseDone = true;
                System.out.println();
                System.out.println("---------------");
                System.out.println();
                System.out.println(title);
                for (MenuItem menuItem : menuItems) {
                    menuItem.show();

                }
                int menuChoose = Integer.parseInt(Utils.sc.nextLine());
                MenuItem menuItem = menuItems.get(menuChoose - 1);
                menuItem.execute();
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                System.out.println("Выберите подходящий вариант меню, ПОЖОЖДА");
                chooseDone = false;
            }
        }
    }

    public Menu(String title) {
        this.title = title;
        addCharacterMenu();
    }

    public Menu(String title, boolean showCharacterMenu) {
        this.title = title;

        //TODO: Вынести в персонажа, тут это тупо дикий костыль
        if (showCharacterMenu) {
            addCharacterMenu();
        }
    }

    private void addCharacterMenu() {
        addItem("Открыть инвентарь", () -> {
            if (Character.getInstance().getInventory().getItems().isEmpty()) {
                System.out.println("Твой инвентарь пуст");
            } else {
                Menu inventoryMenu = new Menu("Инвентарь:", false);
                for (Item item : Character.getInstance().getInventory().getItems()) {
                    inventoryMenu.addItem(item);
                }
                inventoryMenu.showAndChoose();
            }
            this.showAndChoose();
        });
    }

    public void addItem(String name, Executable executable) {
        menuItems.add(new MenuItem(menuItems.size() + 1, name, executable));
    }

    public void addItem(Item item) {
        menuItems.add(new MenuItem(menuItems.size() + 1, item.getName(), item));
    }
}
