package items;

import menu.*;

@FunctionalInterface
public interface Tradeable {
    Menu trade(MenuItem fromMenuItem);
}
