package equipment.items;

import equipment.*;

public class UselessItem extends Item {
    String itemName;

    public UselessItem(String itemName) {
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
        // Посмотреть какой красивый (нет)
    }
}
