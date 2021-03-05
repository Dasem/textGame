package items;

import menu.*;
import units.Character;

import java.util.function.*;

public abstract class Item implements Usable {

    protected Menu itemMenu = new Menu(() -> "Меню для '" + getName() + "'", MenuSetting.HIDE_CHARACTER_MENU, MenuSetting.ADD_BACK_BUTTON);

    {
        itemToInventoryMenuItem();
        dropItemMenuItem();
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
            Character.getInstance().getInventory().addItem(this);
        }, MenuItemType.LOOT);
    }

    @Override
    public MenuItemType use() {
        return itemMenu.showAndChoose(this).getMenuItemType();
    }

    public abstract String getName();

    public Predicate<MenuItem> getMenuFilters(){
        return menuItem -> {
            boolean result;
            result = !(Character.getInstance().getInventory().getItems().contains(this) && menuItem.getMenuItemType() == MenuItemType.LOOT);
            result &= !(Character.getInstance().isEquipped(this) && menuItem.getMenuItemType() == MenuItemType.EQUIP);
            return result;
        };
    }
}
