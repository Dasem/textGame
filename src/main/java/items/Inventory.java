package items;

import java.util.*;

public class Inventory {

    @lombok.Getter List<Item> items = new ArrayList<>();

    @lombok.Getter private int money = 100;

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void addItems(Collection<Item> items) {
        this.items.addAll(items);
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

    public void setMoney(int money){
        this.money = Math.max(money, 0);
    }
}