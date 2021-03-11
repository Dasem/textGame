package items;

import menu.*;

@FunctionalInterface
public interface Usable {
    Menu use(MenuItem fromMenuItem);
}
