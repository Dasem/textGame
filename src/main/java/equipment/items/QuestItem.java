package equipment.items;

import equipment.*;

public class QuestItem extends Item {
    int questId;
    String itemName;

    public QuestItem(String itemName) {
        this.itemName = itemName;
        postInitialize();
    }

    @Override
    public String getName() {
        return itemName;
    }

    @Override
    public void execute() {
        // Чот с ним сделать
    }
}
