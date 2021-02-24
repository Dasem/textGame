package menu;

public class MenuItem {
    private final int itemNumber;
    private final String name;
    private final Executable executable;

    public void show() {
        System.out.println(itemNumber + ". " + name);
    }

    public MenuItem(int itemNumber, String name, Executable executable) {
        this.itemNumber = itemNumber;
        this.name = name;
        this.executable = executable;
    }

    public String getName() {
        return name;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void execute() {
        executable.execute();
    }
}
