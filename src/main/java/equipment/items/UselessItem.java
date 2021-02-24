package equipment.items;

import equipment.*;

public class UselessItem implements Item {
    String itemName;

    public UselessItem(String itemName) {
        this.itemName = itemName;
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
