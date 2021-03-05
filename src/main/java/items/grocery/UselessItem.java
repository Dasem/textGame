package items.grocery;

import items.*;
import menu.*;

public class UselessItem extends Item {
    String itemName;

    public UselessItem(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String getName() {
        return itemName;
    }
}
