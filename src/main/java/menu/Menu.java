package menu;

import com.google.common.collect.*;
import items.*;
import units.Character;
import utils.*;

import java.util.*;
import java.util.stream.*;

public class Menu {
    private String title;
    private Holder<String> titleHolder;
    private final List<MenuItem> menuItems = new ArrayList<>();
    private final List<MenuItem> additionalMenuItems = new ArrayList<>();
    private MenuItem parentMenuItem = null;
    private MenuItem chosenMenuItem = null;

    Set<MenuSetting> menuSettings;

    public Menu showAndChoose() {
        return showAndChoose(this.menuItems, this.additionalMenuItems);
    }

    public Menu showAndChoose(Item item) {
        List<MenuItem> filteredMenuItems = this.menuItems.stream().filter(item.getMenuFilters()).collect(Collectors.toList());
        List<MenuItem> filteredAdditionalMenuItems = this.additionalMenuItems.stream().filter(item.getMenuFilters()).collect(Collectors.toList());
        return showAndChoose(filteredMenuItems, filteredAdditionalMenuItems);
    }

    private Menu showAndChoose(List<MenuItem> menuItems, List<MenuItem> additionalMenuItems) {
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
        additionalMenuItems.add(new MenuItem(name, choosable, this));
    }

    public void addAdditionalItem(String name, Choosable choosable, MenuItemType menuItemType) {
        additionalMenuItems.add(new MenuItem(name, choosable, menuItemType, null, this));
    }

    public void addAdditionalItem(String name, Choosable choosable, MenuItemType menuItemType, Object callbackObject) {
        additionalMenuItems.add(new MenuItem(name, choosable, menuItemType, callbackObject, this));
    }

    public MenuItem addItem(String name, Choosable choosable) {
        MenuItem menuItem = new MenuItem(name, choosable, this);
        menuItems.add(menuItem);
        return menuItem;
    }

    public MenuItem addItem(String name, Choosable choosable, MenuItemType menuItemType) {
        MenuItem menuItem = new MenuItem(name, choosable, menuItemType, null, this);
        menuItems.add(menuItem);
        return menuItem;
    }

    public MenuItem addItem(String name, Choosable choosable, MenuItemType menuItemType, Object callbackObject) {
        MenuItem menuItem = new MenuItem(name, choosable, menuItemType, callbackObject, this);
        menuItems.add(menuItem);
        return menuItem;
    }

    public MenuItem addItem(Item item) {
        MenuItem menuItem = new MenuItem(item.getName(), null, this);
        menuItem.setChoosable(() -> item.use(menuItem));
        menuItems.add(menuItem);
        return menuItem;
    }
    public Set<MenuSetting> getMenuSettings() {
        return menuSettings;
    }

    public MenuItem getChosenMenuItem() {
        return chosenMenuItem;
    }

    public MenuItem getParentMenuItem() {
        return parentMenuItem;
    }

    public void setParentMenuItem(MenuItem parentMenuItem) {
        this.parentMenuItem = parentMenuItem;
    }
}
