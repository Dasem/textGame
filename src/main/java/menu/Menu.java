package menu;

import equipment.*;
import units.Character;
import utils.*;

import java.util.*;

public class Menu {
    String title;
    List<MenuItem> menuItems = new ArrayList<>();
    List<MenuItem> additionalMenuItems = new ArrayList<>();
    boolean showCharacterMenu = true;

    private void showAndChooseForNotRepeatAdditionalMenu() {
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
                if (!additionalMenuItems.isEmpty()) {
                    System.out.println("/\\/\\/\\/\\/\\/\\/\\");
                    for (MenuItem menuItem : additionalMenuItems) {
                        menuItem.show();
                    }
                }

                int menuChoose = Integer.parseInt(Utils.sc.nextLine());
                MenuItem menuItem;
                if (menuChoose > menuItems.size()) {
                    menuItem = additionalMenuItems.get(menuChoose - menuItems.size() - 1);
                } else {
                    menuItem = menuItems.get(menuChoose - 1);
                }
                menuItem.execute();
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                System.out.println("Выберите подходящий вариант меню, ПОЖОЖДА");
                chooseDone = false;
            }
        }
    }

    public void showAndChoose() {
        addAdditionalMenu();
        showAndChooseForNotRepeatAdditionalMenu();
    }

    private void addAdditionalMenu() {
        if (showCharacterMenu) {
            addCharacterMenu();
        }
    }

    public Menu(String title) {
        this.title = title;
    }

    public Menu(String title, boolean showCharacterMenu) {
        this.title = title;
        this.showCharacterMenu = showCharacterMenu;
    }

    private void addCharacterMenu() {
        addAdditionalItem("Открыть инвентарь", () -> {
            if (Character.getInstance().getInventory().getItems().isEmpty()) {
                System.out.println("Твой инвентарь пуст");
            } else {
                Menu inventoryMenu = new Menu("Инвентарь:", false);
                for (Item item : Character.getInstance().getInventory().getItems()) {
                    inventoryMenu.addItem(item);
                }
                inventoryMenu.showAndChoose();
            }
            this.showAndChooseForNotRepeatAdditionalMenu();
        });



    }

    public void addAdditionalItem(String name, Executable executable) {
        additionalMenuItems.add(new MenuItem(additionalMenuItems.size() + menuItems.size() + 1, name, executable));
    }

    public void addItem(String name, Executable executable) {
        menuItems.add(new MenuItem(menuItems.size() + 1, name, executable));
    }

    public void addItem(Item item) {
        menuItems.add(new MenuItem(menuItems.size() + 1, item.getName(), item));
    }
}
