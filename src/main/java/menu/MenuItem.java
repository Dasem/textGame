package menu;

import lombok.Getter;

public class MenuItem {
    @Getter
    private final Menu forMenu;
    @Getter
    private String name;
    private Holder<String> calculatedName;
    private final Choosable choosable;
    @Getter
    private MenuItemType menuItemType = MenuItemType.CUSTOM;

    public void show(int itemNumber) {
        if (name == null) {
            System.out.println(itemNumber + ". " + calculatedName.get());
        } else {
            System.out.println(itemNumber + ". " + name);
        }
    }

    public MenuItem(String name, Choosable choosable, Menu forMenu) {
        this.name = name;
        this.choosable = choosable;
        this.forMenu = forMenu;
    }

    public MenuItem(Holder<String> calculatedName, Choosable choosable, Menu forMenu) {
        this.calculatedName = calculatedName;
        this.choosable = choosable;
        this.forMenu = forMenu;
    }

    public MenuItem(String name, Choosable choosable, MenuItemType menuItemType, Menu forMenu) {
        this.name = name;
        this.choosable = choosable;
        this.menuItemType = menuItemType;
        this.forMenu = forMenu;
    }

    public boolean typeIs(MenuItemType menuItemType) {
        return this.menuItemType == menuItemType;
    }

    public boolean typeIsBack() {
        return typeIs(MenuItemType.BACK);
    }

    public void doChoose() {
        choosable.doChoose();
    }
}
