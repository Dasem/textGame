package menu;

import equipment.Item;

import java.util.Collection;

public interface Lootable {
    Collection<Item> getLoot();
}
