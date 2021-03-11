package mechanic.location;

import com.google.common.collect.*;
import items.*;
import menu.*;
import units.Character;

import java.util.*;

public class Trader {

    private final ArrayList<Item> tradeItems;

    public Trader(Item ... tradeItems) {
        this.tradeItems = Lists.newArrayList(tradeItems);
    }

    public void trade() {
        MenuItem resultItem;
        do {
            Menu tradeMenu = new Menu("Меню торговли", MenuSetting.ADD_BACK_BUTTON, MenuSetting.HIDE_CHARACTER_MENU);
            tradeMenu.addItem("Покупка", () -> {
                Menu buyMenu = new Menu("Покупка", MenuSetting.ADD_BACK_BUTTON, MenuSetting.HIDE_CHARACTER_MENU);
                for (Item item : tradeItems) {
                    MenuItem menuItem = buyMenu.addItem(item.getName(), null, MenuItemType.BUY, item);
                    menuItem.setChoosable(() -> item.trade(menuItem));
                }
                Object maybeItem = buyMenu.showAndChoose().getChosenMenuItem().getCallbackObject();
                if (maybeItem instanceof Item) {
                    Item item = (Item) maybeItem;
                    tradeItems.remove(item);
                }
            });
            tradeMenu.addItem("Продажа", () -> {
                Menu sellMenu = new Menu("Продажа", MenuSetting.ADD_BACK_BUTTON, MenuSetting.HIDE_CHARACTER_MENU);
                for (Item item : Character.getInstance().getInventory().getItems()) {
                    MenuItem menuItem = sellMenu.addItem(item.getName(), null, MenuItemType.SELL, item);
                    menuItem.setChoosable(() -> item.trade(menuItem));
                }
                Object maybeItem = sellMenu.showAndChoose().getChosenMenuItem().getCallbackObject();
                if (maybeItem instanceof Item) {
                    Item item = (Item) maybeItem;
                    tradeItems.add(item);
                }
            });
            resultItem = tradeMenu.showAndChoose().getChosenMenuItem();
        } while (resultItem.getMenuItemType() != MenuItemType.BACK);
    }
}
