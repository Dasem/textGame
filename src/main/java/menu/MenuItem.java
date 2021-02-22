package menu;

public class MenuItem implements Executor {
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

    public int getItemNumber() {
        return itemNumber;
    }

    @Override
    public void execute() {
        executor.execute();
    }
}
