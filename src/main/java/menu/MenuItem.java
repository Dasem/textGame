package menu;

public class MenuItem {
    private final Menu forMenu;
    private String name;
    private Holder<String> calculatedName;
    private Choosable choosable;
    private MenuItemType menuItemType = MenuItemType.CUSTOM;
    private Object callbackObject;

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
