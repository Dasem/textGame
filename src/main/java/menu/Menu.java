package menu;

import com.google.common.collect.*;
import items.*;
import units.character.Character;
import utils.*;

import java.util.*;

public class Menu {
    private final String title;
    private final List<MenuItem> menuItems = new ArrayList<>();
    private final List<MenuItem> additionalMenuItems = new ArrayList<>();
    @lombok.Getter private MenuItem chosenMenuItem = null;

    Set<MenuSetting> menuSettings;

    public Menu showAndChoose() {
        return showAndChoose(this.menuItems, this.additionalMenuItems);
    }

    private Menu showAndChoose(List<MenuItem> menuItems, List<MenuItem> additionalMenuItems) {
        while (true) {
            try {
                System.out.println("\n--------------\n"); // разделение между действиями
                System.out.println(title);
                for (MenuItem menuItem : menuItems) {
                    menuItem.show(menuItems.indexOf(menuItem) + 1);
                }
                if (!additionalMenuItems.isEmpty() && !menuSettings.contains(MenuSetting.HIDE_ADDITIONAL_MENU)) {
                    System.out.println("*************"); // разделение между доп. меню и основным
                    for (MenuItem menuItem : additionalMenuItems) {
                        menuItem.show(menuItems.size() + additionalMenuItems.indexOf(menuItem) + 1);
                    }
                }

                int menuChoose = Integer.parseInt(Utils.sc.nextLine());
                MenuItem menuItem;
                if (menuChoose > menuItems.size()) {
                    menuItem = additionalMenuItems.get(menuChoose - menuItems.size() - 1);
                } else {
                    menuItem = menuItems.get(menuChoose - 1);
                }
                menuItem.doChoose();
                chosenMenuItem = menuItem;
                return this;
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                System.out.println("\nПожалуйста, выберите один из представленных вариантов");
            }
        }
    }

    private void addAdditionalMenu() {
        if (!menuSettings.contains(MenuSetting.HIDE_CHARACTER_MENU)) {
            Character.getInstance().addCharacterMenu(this);
        }
        if (menuSettings.contains(MenuSetting.ADD_BACK_BUTTON)) {
            addAdditionalItem("Назад", () -> {
                // do nothing
                // Предложение Паши:
                //parentMenuItem.getForMenu().showAndChoose();
            }, MenuItemType.BACK);
        }
    }

    public Menu(String title, MenuSetting ... menuSettings) {
        this.title = title;
        this.menuSettings = Sets.newHashSet(menuSettings);
        addAdditionalMenu();
    }

    public void addAdditionalItem(String name, Choosable choosable) {
        additionalMenuItems.add(new MenuItem(name, choosable, this));
    }

    public void addAdditionalItem(String name, Choosable choosable, MenuItemType menuItemType) {
        additionalMenuItems.add(new MenuItem(name, choosable, menuItemType, this));
    }

    public MenuItem addItem(String name, Choosable choosable) {
        MenuItem menuItem = new MenuItem(name, choosable, this);
        menuItems.add(menuItem);
        return menuItem;
    }
    public MenuItem addItem(Holder<String> calculatedName, Choosable choosable) {
        MenuItem menuItem = new MenuItem(calculatedName, choosable, this);
        menuItems.add(menuItem);
        return menuItem;
    }

    public MenuItem addItem(String name, Choosable choosable, MenuItemType menuItemType) {
        MenuItem menuItem = new MenuItem(name, choosable, menuItemType, this);
        menuItems.add(menuItem);
        return menuItem;
    }

    public MenuItem addItem(Item item) {
        MenuItem menuItem = new MenuItem(item.getName(), () -> item.generateUseMenu().showAndChoose(), this);
        menuItems.add(menuItem);
        return menuItem;
    }
}
