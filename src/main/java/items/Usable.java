package items;

import menu.*;

@FunctionalInterface
public interface Usable {
    MenuItemType use(UseSetting... useSettings);
}
