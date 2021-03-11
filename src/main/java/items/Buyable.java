package items;

import menu.*;

@FunctionalInterface
public interface Buyable {
    MenuItemType buy(MenuItemType ... menuItemTypes);
}
