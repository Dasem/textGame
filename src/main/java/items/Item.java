package items;

import menu.*;
import units.Character;

public abstract class Item implements Usable {

    protected Menu itemMenu;

    protected void postInitialize() {
        itemMenu = new Menu("Меню для '" + getName() + "'", MenuSetting.HIDE_CHARACTER_MENU, MenuSetting.ADD_BACK_BUTTON);
        if (!Character.getInstance().getInventory().getItems().contains(this)) {
            itemMenu.addItem("Положить в инвентарь", () -> {
                System.out.println("Взят новый предмет: '" + getName() + "'");
                Character.getInstance().getInventory().addItem(this);
            }, MenuItemType.LOOT);
        }
        itemMenu.addItem("Выбросить", () -> {
            System.out.println("Предмет уничтожен: '" + getName() + "'");
            Character.getInstance().getInventory().removeItem(this);
        }, MenuItemType.THROW);
    }

    public abstract String getName();

}
