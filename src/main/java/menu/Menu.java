package menu;

import com.google.common.collect.*;
import items.*;
import units.Character;
import utils.*;

import java.util.*;
import java.util.stream.*;

public class Menu {
    String title;
    Holder<String> titleHolder;
    List<MenuItem> menuItems = new ArrayList<>();
    List<MenuItem> additionalMenuItems = new ArrayList<>();


    Set<MenuSetting> menuSettings;

    public MenuItem showAndChoose() {
        return showAndChoose(this.menuItems, this.additionalMenuItems);
    }

    public MenuItem showAndChoose(Item item) {
        List<MenuItem> filteredMenuItems = this.menuItems.stream().filter(item.getMenuFilters()).collect(Collectors.toList());
        List<MenuItem> filteredAdditionalMenuItems = this.additionalMenuItems.stream().filter(item.getMenuFilters()).collect(Collectors.toList());
        return showAndChoose(filteredMenuItems, filteredAdditionalMenuItems);
    }

    private MenuItem showAndChoose(List<MenuItem> menuItems, List<MenuItem> additionalMenuItems) {
        if (title == null) {
            title = titleHolder.get();
        }
        while (true) {
            try {
                System.out.println("\n--------------\n"); // разделение между действиями
                System.out.println(title);
                for (MenuItem menuItem : menuItems) {
                    menuItem.show(menuItems.indexOf(menuItem) + 1);
                }
                if (!additionalMenuItems.isEmpty()) {
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
                return menuItem;
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
            }, MenuItemType.BACK);
        }
    }

    public Menu(String title, MenuSetting ... menuSettings) {
        this.title = title;
        this.menuSettings = Sets.newHashSet(menuSettings);
        addAdditionalMenu();
    }

    public Menu(Holder<String> titleHolder, MenuSetting ... menuSettings) {
        this.titleHolder = titleHolder;
        this.menuSettings = Sets.newHashSet(menuSettings);
        addAdditionalMenu();
    }

    public void addAdditionalItem(String name, Choosable choosable) {
        additionalMenuItems.add(new MenuItem(name, choosable));
    }

    public void addAdditionalItem(String name, Choosable choosable, MenuItemType menuItemType) {
        additionalMenuItems.add(new MenuItem(name, choosable, menuItemType));
    }

    public void removeItemsByType(MenuItemType menuItemType) {
        menuItems.removeIf(menuItem -> menuItem.getMenuItemType() == menuItemType);
        additionalMenuItems.removeIf(menuItem -> menuItem.getMenuItemType() == menuItemType);
    }

    public void addItem(String name, Choosable choosable) {
        menuItems.add(new MenuItem(name, choosable));
    }

    public void addItem(String name, Choosable choosable, MenuItemType menuItemType) {
        menuItems.add(new MenuItem(name, choosable, menuItemType));
    }

    public void addItem(Item item) {
        menuItems.add(new MenuItem(item.getName(), () -> item.use()));
    }
    public Set<MenuSetting> getMenuSettings() {
        return menuSettings;
    }
}
