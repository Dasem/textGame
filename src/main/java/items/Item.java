package items;

import menu.*;
import units.character.Character;

public abstract class Item {

    @lombok.Getter protected int cost;

    protected void addDropItemMenuItem(Menu menu) {
        menu.addItem("Выбросить", () -> {
            System.out.println("Предмет уничтожен: '" + getName() + "'");
            Character.getInstance().removeIfEquipped(this);
            Character.getInstance().getInventory().removeItem(this);
        }, MenuItemType.THROW_ITEM);
    }

    protected void addItemToInventoryMenuItem(Menu menu) {
        if (!Character.getInstance().getInventory().getItems().contains(this)) {
            Character.getInstance().removeIfEquipped(this);
            menu.addItem("Положить в инвентарь", () -> {
                System.out.println("Взят новый предмет: '" + getName() + "'");
                Character.getInstance().lootItem(this);
            }, MenuItemType.LOOT_ITEM);
        }
    }

    public Menu generateUseMenu() {
        Menu itemMenu = new Menu("Меню для '" + getName() + "'", MenuSetting.HIDE_CHARACTER_MENU, MenuSetting.ADD_BACK_BUTTON);
        addItemToInventoryMenuItem(itemMenu);
        addDropItemMenuItem(itemMenu);
        return itemMenu;
    }

    public Menu generateBuyMenu() {
        TradeMenu tradeMenu = new TradeMenu("Меню покупки", MenuSetting.HIDE_CHARACTER_MENU, MenuSetting.ADD_BACK_BUTTON);
        tradeMenu.addItem("Купить", () -> {
            if (Character.getInstance().getInventory().getMoney() > this.getCost()) {
                System.out.println("Вы купили : '" + getName() + "'");
                Character.getInstance().wasteMoney(getCost());
                Character.getInstance().getInventory().addItem(this);
                System.out.println("Осталось " + Character.getInstance().getInventory().getMoney() + " Золота");
                tradeMenu.setTradedItem(this);
                tradeMenu.setSuccess(true);
            } else {
                System.out.println("Недостаточно золота");
                tradeMenu.setSuccess(false);
            }
        }, MenuItemType.BUY);
        return tradeMenu;
    }

    public Menu generateSellMenu() {
        TradeMenu tradeMenu = new TradeMenu("Меню продажи", MenuSetting.HIDE_CHARACTER_MENU, MenuSetting.ADD_BACK_BUTTON);
        tradeMenu.addItem("Продать", () -> {
            System.out.println("Вы продали : '" + getName() + "'");
            Character.getInstance().earnMoney(getCost());
            Character.getInstance().getInventory().removeItem(this);
            System.out.println("Теперь " + Character.getInstance().getInventory().getMoney() + " Золота");
            tradeMenu.setTradedItem(this);
            tradeMenu.setSuccess(true);
        }, MenuItemType.SELL);
        return tradeMenu;
    }


    public abstract String getName();
}
