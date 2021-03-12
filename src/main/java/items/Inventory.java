package items;

import java.util.*;

public class Inventory {
    List<Item> items = new ArrayList<>();

    private int money = 100;

    public List<Item> getItems() {
        return items;
    }

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
    public int getMoney() {
        return money;
    }
    public void setMoney(int money){
        this.money=money;
        if (this.money < 0 ){
            this.money = 0;
        }
    }
}