package equipment.items;

import equipment.*;

public class UsefulItem extends Item {
    String itemName;

    public UsefulItem(String itemName) {
        this.itemName = itemName;
        postInitialize();
    }

    public String getItemName() {
        return itemName;
    }

    @Override
    public String getName() {
        return itemName;
    }

    @Override
    public void execute() {
        itemMenu.showAndChoose();
    }
}
