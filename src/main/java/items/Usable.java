package items;

import menu.*;

@FunctionalInterface
public interface Usable {
    MenuItemType use(UseSettings... useSettings);
}
