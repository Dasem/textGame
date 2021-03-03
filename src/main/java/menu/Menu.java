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

    private void showAndChooseForBack() {
        Utils.suspense(500);
        boolean chooseDone = false;
        while (!chooseDone) {
            try {
                chooseDone = true;
                System.out.println();
                System.out.println("--------------");
                System.out.println();
                System.out.println(title);
                for (MenuItem menuItem : menuItems) {
                    menuItem.show();
                }
                if (!additionalMenuItems.isEmpty()) {
                    System.out.println("*************");
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
                System.out.println("\nВыберите подходящий вариант меню, ПОЖОЖДА");
                chooseDone = false;
            }
        }
    }

    public void showAndChoose() {
        addAdditionalMenu();
        showAndChooseForBack();
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
            this.showAndChooseForBack();
        });
        addAdditionalItem("Просмотр персонажа", () -> {
            Menu characterMenu = new Menu("Персонаж:", false);
            characterMenu.addItem("Информация о персонаже", () -> {
                Character c = Character.getInstance();
                System.out.println("Меня зовут " + c.getName());
                System.out.println(c.getCurrentHealth() + "/" + c.getMaxHealth() + " HP");
                System.out.println(c.getArmorClass() + " Защиты");
                if (c.getArmor() != null) {
                    System.out.println(c.getArmor());
                } else System.out.println("Нет брони");
                if (c.getWeapon() != null) {
                    System.out.println(c.getWeapon().getPrettyName());
                } else System.out.println("Нет оружия");

                this.showAndChooseForBack();
            });
            characterMenu.addItem("Снаряжение", () -> {
                Menu equippedMenu = new Menu("Экипированное снаряжение:",false);
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
            characterMenu.addItem("Назад", () -> {
                this.showAndChooseForBack();
            });
            characterMenu.showAndChoose();
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
