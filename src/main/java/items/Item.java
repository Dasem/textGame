package items;

import menu.*;
import units.character.Character;

import java.util.function.*;

public abstract class Item implements Usable, Tradeable {
    protected int cost;

    protected Menu itemMenu = new Menu(() -> "Меню для '" + getName() + "'", MenuSetting.HIDE_CHARACTER_MENU, MenuSetting.ADD_BACK_BUTTON);
    protected TradeMenu tradeMenu = new TradeMenu(() -> "Меню торговли", MenuSetting.HIDE_CHARACTER_MENU, MenuSetting.ADD_BACK_BUTTON);

    {
        itemToInventoryMenuItem();
        dropItemMenuItem();

        addTradeMenu();
    }

    protected void dropItemMenuItem() {
        itemMenu.addItem("Выбросить", () -> {
            System.out.println("Предмет уничтожен: '" + getName() + "'");
            Character.getInstance().getInventory().removeItem(this);
        }, MenuItemType.THROW_ITEM);
    }

    protected void itemToInventoryMenuItem() {
        itemMenu.addItem("Положить в инвентарь", () -> {
            System.out.println("Взят новый предмет: '" + getName() + "'");
            Character.getInstance().lootItem(this);
        }, MenuItemType.LOOT_ITEM);
    }


    protected void addTradeMenu() {
        tradeMenu.addItem("Купить", () -> {
            if (Character.getInstance().getInventory().getMoney() > this.getCost()) {
                System.out.println("Вы купили : '" + getName() + "'");
                Character.getInstance().wasteMoney(getCost());
                Character.getInstance().getInventory().addItem(this);
                System.out.println("Осталось " + Character.getInstance().getInventory().getMoney() + " Золота");
                tradeMenu.setSuccess(true);
            } else {
                System.out.println("Недостаточно золота");
                tradeMenu.setSuccess(false);
            }
        }, MenuItemType.BUY);
        tradeMenu.addItem("Продать", () -> {
            System.out.println("Вы продали : '" + getName() + "'");
            Character.getInstance().earnMoney(getCost());
            Character.getInstance().getInventory().removeItem(this);
            System.out.println("Теперь " + Character.getInstance().getInventory().getMoney() + " Золота");
        }, MenuItemType.SELL);
    }

    @Override
    public Menu use(MenuItem fromMenuItem) {
        itemMenu.setParentMenuItem(fromMenuItem);
        Menu menu = itemMenu.showAndChoose(this);
        itemMenu.setParentMenuItem(null);
        return menu;
    }

    @Override
    public Menu trade(MenuItem fromMenuItem) {
        tradeMenu.setParentMenuItem(fromMenuItem);
        Menu menu = tradeMenu.showAndChoose(this);
        tradeMenu.setParentMenuItem(null); // Чтобы не оставалось некорректного родительского меню
        return menu;
    }

    public abstract String getName();

    public Predicate<MenuItem> getMenuFilters() {
        return menuItem -> {
            boolean lootWhenLooted = Character.getInstance().getInventory().getItems().contains(this) && menuItem.getMenuItemType() == MenuItemType.LOOT_ITEM;
            boolean equipWhenEquipped = Character.getInstance().isEquipped(this) && menuItem.getMenuItemType() == MenuItemType.EQUIP_ITEM;
            boolean buyingWhenSelling = false;
            boolean sellingWhenBuying = false;
            if (menuItem.getForMenu().getParentMenuItem() != null) {
                buyingWhenSelling = menuItem.getForMenu().getParentMenuItem().getMenuItemType() == MenuItemType.BUY && menuItem.getMenuItemType() == MenuItemType.SELL;
                sellingWhenBuying = menuItem.getForMenu().getParentMenuItem().getMenuItemType() == MenuItemType.SELL && menuItem.getMenuItemType() == MenuItemType.BUY;
            }
            return !lootWhenLooted && !equipWhenEquipped && !buyingWhenSelling && !sellingWhenBuying;
        };
    }


    public int getCost() {
        return cost;
    }

    public TradeMenu getTradeMenu() {
        return tradeMenu;
    }
}
