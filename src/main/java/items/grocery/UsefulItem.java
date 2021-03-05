package items.grocery;

import items.*;
import menu.*;

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
    public MenuItemType use() {
        return itemMenu.showAndChoose().getMenuItemType();
    }
}
