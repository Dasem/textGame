package menu;

public class MenuItem {
    private final int itemNumber;
    private final String name;
    private final Executor executor;

    public void show() {
        System.out.println(itemNumber + ". " + name);
    }

    public MenuItem(int itemNumber, String name, Executor executor) {
        this.itemNumber = itemNumber;
        this.name = name;
        this.executor = executor;
    }

    public String getName() {
        return name;
    }

    public void execute() {
        executor.execute();
    }

    public int getItemNumber() {
        return itemNumber;
    }
}
