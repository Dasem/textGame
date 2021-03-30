package items.equipment;

import items.*;
import menu.*;
import units.character.Character;

public abstract class Equipment extends Item {
    protected int upgradeLevel = 0;

    @Override
    public Menu generateUseMenu() {
        Menu menu = super.generateUseMenu();
        addEquipMenuItem(menu);
        return menu;
    }

    public Menu generateUpgradeMenu() {
        Menu upgradeMenu = new Menu("Меню заточки", MenuSetting.HIDE_CHARACTER_MENU, MenuSetting.ADD_BACK_BUTTON);
        upgradeMenu.addItem("Заточить", () -> {
            if (Character.getInstance().getInventory().getMoney() >= this.getUpgradeCost()) {
                System.out.println("Вы заточили : '" + getName() + "'");
                this.upgrade();
                Character.getInstance().wasteMoney(this.getUpgradeCost());
                System.out.println("Осталось " + Character.getInstance().getInventory().getMoney() + " Золота");
            }
            else System.out.println("У вас не хватает денег для заточки...");
        }, MenuItemType.UPGRADE);
        return upgradeMenu;
    }

    protected void upgrade() {
        upgradeLevel++;
    }

    protected abstract int getUpgradeCost();

    protected abstract void addEquipMenuItem(Menu menu);

    protected abstract String getPrettyClassName();
}
