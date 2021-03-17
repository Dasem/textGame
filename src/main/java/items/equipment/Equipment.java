package items.equipment;

import items.*;
import menu.*;
import units.character.Character;

public abstract class Equipment extends Item implements Upgradeable {
    protected int upgradeLevel = 0;


    protected Menu upgradeMenu = new Menu(() -> "Меню заточки", MenuSetting.HIDE_CHARACTER_MENU, MenuSetting.ADD_BACK_BUTTON);

    {
        addUpgradeMenu();
    }



    @Override
    public Menu upgrade(MenuItem fromMenuItem) {
        upgradeMenu.setParentMenuItem(fromMenuItem);
        Menu menu = upgradeMenu.showAndChoose(this);
        upgradeMenu.setParentMenuItem(null); // Чтобы не оставалось некорректного родительского меню
        return menu;
    }




    protected void upgrade() {
        upgradeLevel++;
    }

    protected void addUpgradeMenu() {
        upgradeMenu.addItem("Заточить", () -> {
            if (Character.getInstance().getInventory().getMoney() >= this.getUpgradeCost()) {
                System.out.println("Вы заточили : '" + getName() + "'");
                this.upgrade();
                Character.getInstance().wasteMoney(this.getUpgradeCost());
                System.out.println("Осталось " + Character.getInstance().getInventory().getMoney() + " Золота");
            }
        }, MenuItemType.UPGRADE);
    }


    @Override
    protected void itemToInventoryMenuItem() {
        itemMenu.addItem("Положить в инвентарь (" + getPrettyClassName() + ")", () -> {
            System.out.println("Взят новый предмет (" + getPrettyClassName() + "): '" + getName() + "'");
            Character.getInstance().lootItem(this);
            Character.getInstance().removeIfEquipped(this);
        }, MenuItemType.LOOT_ITEM);
    }

    @Override
    protected void dropItemMenuItem() {
        itemMenu.addItem("Выбросить снаряжение (" + getPrettyClassName() + ")", () -> {
            System.out.println("Снаряжение уничтожено " + getPrettyClassName() + ": '" + getName() + "'");
            Character.getInstance().getInventory().removeItem(this);
            Character.getInstance().removeIfEquipped(this);
        }, MenuItemType.THROW_ITEM);
    }

    protected abstract int getUpgradeCost();

    protected abstract void equipMenuItem();

    protected abstract String getPrettyClassName();
}
