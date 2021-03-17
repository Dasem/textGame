package units.npcs;

import items.*;
import items.equipment.Equipment;
import items.equipment.Weapon;
import menu.*;
import units.character.Character;
import com.google.common.collect.*;

import java.util.*;

public class Blacksmith extends NPC {

    private final ArrayList<Item> tradeItems;

    public Blacksmith(Item... tradeItems) {
        this.tradeItems = Lists.newArrayList(tradeItems);
    }

    public void upgrade() {
        MenuItem resultItem;
        do {
            Menu tradeMenu = new Menu("Меню кузницы", MenuSetting.ADD_BACK_BUTTON, MenuSetting.HIDE_CHARACTER_MENU);
            tradeMenu.addItem("Заточка", () -> {
                Menu upgradeMenu = new Menu("Заточить", MenuSetting.ADD_BACK_BUTTON, MenuSetting.HIDE_CHARACTER_MENU);
                for (Item item : Character.getInstance().getInventory().getItems()) {
                    if (item instanceof Equipment) {
                        Equipment equipment = (Equipment) item;
                        MenuItem menuItem = upgradeMenu.addItem(equipment.getName(), null, MenuItemType.UPGRADE, equipment);
                        menuItem.setChoosable(() -> equipment.upgrade(menuItem));
                    }
                }
                upgradeMenu.showAndChoose();
            });
            resultItem = tradeMenu.showAndChoose().getChosenMenuItem();
        } while (resultItem.getMenuItemType() != MenuItemType.BACK);

    }
}

