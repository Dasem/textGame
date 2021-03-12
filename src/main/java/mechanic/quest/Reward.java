package mechanic.quest;


import items.Item;
import units.character.Character;

import java.util.ArrayList;
import java.util.Collection;

public class Reward {
    private int money = 0;
    private final Collection<Item> items;

    public Reward(int money, Collection<Item> items) {
        this.money = money;
        if (items == null) {
            this.items = new ArrayList<>();
        } else {
            this.items = items;
        }
    }

    public void receiveReward() {
        Character.getInstance().earnMoney(money);
        Character.getInstance().getInventory().addItems(items);
    }
}
