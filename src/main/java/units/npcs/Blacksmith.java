package units.npcs;

import items.equipment.Equipment;
import menu.*;
import units.character.Character;

import java.util.*;
import java.util.stream.Collectors;

public class Blacksmith extends NPC {


    public void upgrade() {
        MenuItem resultItem;
        do {
            Menu tradeMenu = new Menu("Меню кузницы", MenuSetting.ADD_BACK_BUTTON, MenuSetting.HIDE_CHARACTER_MENU);
            tradeMenu.addItem("Заточка", () -> {
                Menu upgradeMenu = new Menu("Заточка", MenuSetting.ADD_BACK_BUTTON, MenuSetting.HIDE_CHARACTER_MENU);
                List<Equipment> equipments = Character.getInstance().getInventory().getItems().stream()
                        .filter(item -> item instanceof Equipment)
                        .map(item -> (Equipment) item)
                        .collect(Collectors.toList());
                for (Equipment equipment : equipments) {
                    upgradeMenu.addItem(equipment.getName(), () -> equipment.generateUpgradeMenu().showAndChoose(), MenuItemType.UPGRADE);
                }
                if (equipments.isEmpty()) {
                    System.out.println("Точить нечего");
                }
                upgradeMenu.showAndChoose();
            });
            resultItem = tradeMenu.showAndChoose().getChosenMenuItem();
        } while (resultItem.getMenuItemType() != MenuItemType.BACK);

    }
}

