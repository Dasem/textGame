package menu;

public class MenuItem {
    private final Menu forMenu;
    private final String name;
    private Choosable choosable;
    private MenuItemType menuItemType = MenuItemType.CUSTOM;
    private Object callbackObject;

    public void show(int itemNumber) {
        System.out.println(itemNumber + ". " + name);
    }

    public MenuItem(String name, Choosable choosable, Menu forMenu) {
        this.name = name;
        this.choosable = choosable;
        this.forMenu = forMenu;
    }

    public MenuItem(String name, Choosable choosable, MenuItemType menuItemType, Object callbackObject, Menu forMenu) {
        this.name = name;
        this.choosable = choosable;
        this.menuItemType = menuItemType;
        this.callbackObject = callbackObject;
        this.forMenu = forMenu;
    }

    public Object getCallbackObject() {
        return callbackObject;
    }

    public MenuItemType getMenuItemType() {
        return menuItemType;
    }

    public boolean typeIsBack() {
        return menuItemType == MenuItemType.BACK;
    }

    public String getName() {
        return name;
    }

    public void doChoose() {
        choosable.doChoose();
    }

    public Menu getForMenu() {
        return forMenu;
    }

    public void setChoosable(Choosable choosable) {
        this.choosable = choosable;
    }
}
