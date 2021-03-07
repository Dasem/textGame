package items.equipment;

import items.*;
import menu.*;
import units.Character;

public abstract class Equipment extends Item {
    @Override
    protected void itemToInventoryMenuItem() {
        itemMenu.addItem("Положить в инвентарь (" + getPrettyClassName() + ")", () -> {
            System.out.println("Взят новый предмет (" + getPrettyClassName() + "): '" + getName() + "'");
            Character.getInstance().lootItem(this);
            Character.getInstance().removeIfEquipped(this);
        }, MenuItemType.LOOT);
    }

    @Override
    protected void dropItemMenuItem() {
        itemMenu.addItem("Выбросить снаряжение (" + getPrettyClassName() + ")", () -> {
            System.out.println("Снаряжение уничтожено " + getPrettyClassName() + ": '" + getName() + "'");
            Character.getInstance().getInventory().removeItem(this);
            Character.getInstance().removeIfEquipped(this);
        }, MenuItemType.THROW);
    }

    protected abstract void equipMenuItem();

    protected abstract String getPrettyClassName();
}
