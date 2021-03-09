package items;

import com.google.common.collect.*;
import menu.*;
import units.Character;

import java.util.*;
import java.util.function.*;

public abstract class Item implements Usable {
    protected int cost;

    protected Menu itemMenu = new Menu(() -> "Меню для '" + getName() + "'", MenuSetting.HIDE_CHARACTER_MENU, MenuSetting.ADD_BACK_BUTTON);
    protected Collection<UseSetting> useSettings;

    {
        itemToInventoryMenuItem();
        dropItemMenuItem();
        addBuyMenu();
        addSellMenu();
    }

    protected void dropItemMenuItem() {
        itemMenu.addItem("Выбросить", () -> {
            System.out.println("Предмет уничтожен: '" + getName() + "'");
            Character.getInstance().getInventory().removeItem(this);
        }, MenuItemType.THROW);
    }

    protected void itemToInventoryMenuItem() {
        itemMenu.addItem("Положить в инвентарь", () -> {
            System.out.println("Взят новый предмет: '" + getName() + "'");
            Character.getInstance().lootItem(this);
        }, MenuItemType.LOOT);
    }
    protected void addBuyMenu(){
        itemMenu.addItem("Купить", () -> {
            System.out.println("Вы купили : '" + getName() + "'");
            Character.getInstance().getInventory().setMoney(Character.getInstance().getInventory().getMoney()-getCost());
            Character.getInstance().getInventory().addItem(this);
            System.out.println("Осталось "+Character.getInstance().getInventory().getMoney()+" Золота");
        }, MenuItemType.BUY);
    }
    protected void addSellMenu(){
        itemMenu.addItem("Продать", () -> {
            System.out.println("Вы продали : '" + getName() + "'");
            Character.getInstance().getInventory().setMoney(Character.getInstance().getInventory().getMoney()+getCost());
            Character.getInstance().getInventory().removeItem(this);
            System.out.println("Теперь "+Character.getInstance().getInventory().getMoney()+" Золота");
        }, MenuItemType.SELL);
    }

    @Override
    public MenuItemType use(UseSetting... useSettings) {
        this.useSettings = Lists.newArrayList(useSettings);
        return itemMenu.showAndChoose(this).getMenuItemType();
    }

    public abstract String getName();

    public Predicate<MenuItem> getMenuFilters(){
        return menuItem -> {
            boolean buyAndBackWhenBuying = allowedOnlyInUseSetting(menuItem, UseSetting.BUY, MenuItemType.BUY);
            boolean sellAndBackWhenSelling = allowedOnlyInUseSetting(menuItem, UseSetting.SELL, MenuItemType.SELL);
            boolean lootWhenLooted = Character.getInstance().getInventory().getItems().contains(this) && menuItem.getMenuItemType() == MenuItemType.LOOT;
            boolean equipWhenEquipped = Character.getInstance().isEquipped(this) && menuItem.getMenuItemType() == MenuItemType.EQUIP;

            return buyAndBackWhenBuying
                    && sellAndBackWhenSelling
                    && !lootWhenLooted
                    && !equipWhenEquipped;
        };
    }

    private boolean allowedOnlyInUseSetting(MenuItem menuItem, UseSetting useSetting, MenuItemType forMenuItemType) {
        boolean showIf = !useSettings.contains(useSetting) || (menuItem.getMenuItemType() == forMenuItemType || menuItem.getMenuItemType() == MenuItemType.BACK);
        boolean hideElse = (useSettings.contains(useSetting) || menuItem.getMenuItemType() != forMenuItemType);
        return showIf && hideElse;
    }

    public int getCost() {
    return cost;
    }
}
