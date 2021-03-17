package items;


import menu.Menu;
import menu.MenuItem;

@FunctionalInterface
public interface Upgradeable {
    Menu upgrade(MenuItem fromMenuItem);
}
