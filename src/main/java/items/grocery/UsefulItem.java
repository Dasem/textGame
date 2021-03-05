package items.grocery;

import items.*;
import menu.*;

public class UsefulItem extends Item {
    String itemName;

    public UsefulItem(String itemName) {
        this.itemName = itemName;

    }

    public String getItemName() {
        return itemName;
    }

    @Override
    public String getName() {
        return itemName;
    }
}
