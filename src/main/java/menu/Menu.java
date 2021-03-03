package menu;

import com.google.common.collect.*;
import equipment.*;
import units.Character;
import utils.*;

import java.util.*;

public class Menu {
    String title;
    List<MenuItem> menuItems = new ArrayList<>();
    List<MenuItem> additionalMenuItems = new ArrayList<>();
    Set<MenuSetting> menuSettings;

    private void showAndChooseForBack() {
        boolean chooseDone = false;
        while (!chooseDone) {
            try {
                chooseDone = true;
                System.out.println();
                System.out.println("--------------"); // разделение между менюшками
                System.out.println();
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
                menuItem.execute();
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                System.out.println("\nВыберите подходящий вариант меню, ПОЖОЖДА");
                chooseDone = false;
            }
        }
    }

    public void showAndChoose() {
        showAndChooseForBack();
    }

    private void addAdditionalMenu() {
        if (!menuSettings.contains(MenuSetting.HIDE_CHARACTER_MENU)) {
            addCharacterMenu();
        }
        if (menuSettings.contains(MenuSetting.ADD_BACK_BUTTON)) {
            addAdditionalItem("Назад", () -> {
                // do nothing (в конце метода возвращается)
            });
        }
    }

    public Menu(String title, MenuSetting ... menuSettings) {
        this.title = title;
        this.menuSettings = Sets.newHashSet(menuSettings);
        addAdditionalMenu();
    }

    private void addCharacterMenu() {
        addAdditionalItem("Открыть инвентарь", () -> {
            if (Character.getInstance().getInventory().getItems().isEmpty()) {
                System.out.println("Твой инвентарь пуст");
            } else {
                Menu inventoryMenu = new Menu("Инвентарь:", MenuSetting.HIDE_CHARACTER_MENU, MenuSetting.ADD_BACK_BUTTON);
                for (Item item : Character.getInstance().getInventory().getItems()) {
                    inventoryMenu.addItem(item);
                }
                inventoryMenu.showAndChoose();
            }
            this.showAndChooseForBack();
        });
        addAdditionalItem("Просмотр персонажа", () -> {
            Menu characterMenu = new Menu("Персонаж:", MenuSetting.HIDE_CHARACTER_MENU, MenuSetting.ADD_BACK_BUTTON);
            characterMenu.addItem("Информация о персонаже", () -> {
                Character c = Character.getInstance();
                System.out.println("Меня зовут " + c.getName());
                System.out.println(c.getCurrentHealth() + "/" + c.getMaxHealth() + " HP");
                System.out.println(c.getArmorClass() + " Защиты");
                if (c.getArmor() != null) {
                    System.out.println(c.getArmor().getPrettyName());
                } else System.out.println("Нет брони");
                if (c.getWeapon() != null) {
                    System.out.println(c.getWeapon().getPrettyName());
                } else System.out.println("Нет оружия");

                characterMenu.showAndChooseForBack(); // после закрытия инвентаря, возвращаемся а меню персонажа
            });
            characterMenu.addItem("Снаряжение", () -> {
                Menu equippedMenu = new Menu("Экипированное снаряжение:", MenuSetting.HIDE_CHARACTER_MENU, MenuSetting.ADD_BACK_BUTTON);
                if (Character.getInstance().getWeapon() == null && Character.getInstance().getArmor() == null ) {
                    System.out.println("Нет надетого снаряжения");
                } else {
                    if (Character.getInstance().getWeapon() != null) {
                        equippedMenu.addItem(Character.getInstance().getWeapon());
                    }
                    if (Character.getInstance().getArmor() != null) {
                        equippedMenu.addItem(Character.getInstance().getArmor());
                    }
                    equippedMenu.showAndChoose();
                }
                characterMenu.showAndChooseForBack();
            });
            characterMenu.showAndChoose();
            this.showAndChooseForBack();
        });
    }

    public void addAdditionalItem(String name, Executable executable) {
        additionalMenuItems.add(new MenuItem(name, executable));
    }

    public void addItem(String name, Executable executable) {
        menuItems.add(new MenuItem(name, executable));
    }

    public void addItem(Item item) {
        menuItems.add(new MenuItem(item.getName(), item));
    }
}
