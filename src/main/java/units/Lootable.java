package units;

import items.Item;

import java.util.Collection;

public interface Lootable {
    Collection<Item> getLoot();
}
