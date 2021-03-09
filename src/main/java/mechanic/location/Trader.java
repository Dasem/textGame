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
                    buyMenu.addItem(item.getName(), () -> item.use(MenuItemType.BUY, MenuItemType.BACK), MenuItemType.BUY, item);
                }
                Object maybeItem = buyMenu.showAndChoose().getCallbackObject();
                if (maybeItem instanceof Item) {
                    Item item = (Item) maybeItem;
                    tradeItems.remove(item);
                }
            });
            tradeMenu.addItem("Продажа", () -> {
                Menu sellMenu = new Menu("Продажа", MenuSetting.ADD_BACK_BUTTON, MenuSetting.HIDE_CHARACTER_MENU);
                for (Item item : Character.getInstance().getInventory().getItems()) {
                    sellMenu.addItem(item.getName(), () -> item.use(MenuItemType.SELL, MenuItemType.BACK), MenuItemType.SELL, item);
                }
                Object maybeItem = sellMenu.showAndChoose().getCallbackObject();
                if (maybeItem instanceof Item) {
                    Item item = (Item) maybeItem;
                    tradeItems.add(item);
                }
            });
            resultItem = tradeMenu.showAndChoose();
        } while (resultItem.getMenuItemType() != MenuItemType.BACK);
    }
}
