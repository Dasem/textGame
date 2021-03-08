package menu;

public class MenuItem {
    private final String name;
    private final Choosable choosable;
    private MenuItemType menuItemType = MenuItemType.CUSTOM;
    private Object callbackObject;

    public void show(int itemNumber) {
        System.out.println(itemNumber + ". " + name);
    }

    public MenuItem(String name, Choosable choosable) {
        this.name = name;
        this.choosable = choosable;
    }

    public MenuItem(String name, Choosable choosable, MenuItemType menuItemType, Object callbackObject) {
        this.name = name;
        this.choosable = choosable;
        this.menuItemType = menuItemType;
        this.callbackObject = callbackObject;
    }

    public Object getCallbackObject() {
        return callbackObject;
    }

    public MenuItemType getMenuItemType() {
        return menuItemType;
    }

    public String getName() {
        return name;
    }

    public void doChoose() {
        choosable.doChoose();
    }
}
