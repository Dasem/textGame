package items;

import menu.*;

@FunctionalInterface
public interface Sellable {
    MenuItemType sell(MenuItemType ... menuItemTypes);
}
