package menu;

public class MenuItem {
    private final String name;
    private final Executable executable;

    public void show(int itemNumber) {
        System.out.println(itemNumber + ". " + name);
    }

    public MenuItem(String name, Executable executable) {
        this.name = name;
        this.executable = executable;
    }

    public String getName() {
        return name;
    }

    public void execute() {
        executable.execute();
    }
}
