package equipment;

import java.util.*;

public class Inventory {
    List<Item> items = new ArrayList<>();

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    public String getNumeratedList() {
        StringBuilder stringBuilder = new StringBuilder();
        int index = 1;
        for (Item el : items) {
            stringBuilder.append(index++).append(". ").append(el.getName()).append("\n"); // 1. Предмет
        }
        return stringBuilder.toString();
    }
}