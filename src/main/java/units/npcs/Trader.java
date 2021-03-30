package units.npcs;

import com.google.common.collect.*;
import items.*;
import menu.*;
import units.character.Character;

import java.util.*;
import java.util.concurrent.atomic.*;

public class Trader extends NPC {

    private final ArrayList<Item> tradeItems;

    public Trader(Item... tradeItems) {
        this.tradeItems = Lists.newArrayList(tradeItems);
    }

    /**
     * Меню торговли:
     * 1. Покупка
     * 2. Продажа
     * ... ->
     * Покупка:
     * 1. Предмет 1
     * 2. Предмет 2
     * ... ->
     * Меню покупки:
     * 1. Купить
     * 2. Назад
     * ...
     * Аналогично с продажей
     */
    public void trade() {
        MenuItem resultItem;
        do {
            Menu traderMenu = new Menu("Меню торговли", MenuSetting.ADD_BACK_BUTTON, MenuSetting.HIDE_CHARACTER_MENU);
            traderMenu.addItem("Покупка", () -> {
                Menu buyMenu = new Menu("Покупка", MenuSetting.ADD_BACK_BUTTON, MenuSetting.HIDE_CHARACTER_MENU);
                AtomicReference<TradeMenu> itemBuyMenu = new AtomicReference<>();
                for (Item item : tradeItems) {
                    buyMenu.addItem(item.getName(), () -> itemBuyMenu.set((TradeMenu) item.generateBuyMenu().showAndChoose()), MenuItemType.BUY);
                }
                if (itemBuyMenu.get().isSuccess()) {
                    tradeItems.remove(itemBuyMenu.get().getTradedItem());
                }
            });
            traderMenu.addItem("Продажа", () -> {
                Menu sellMenu = new Menu("Продажа", MenuSetting.ADD_BACK_BUTTON, MenuSetting.HIDE_CHARACTER_MENU);
                AtomicReference<TradeMenu> itemSellMenu = new AtomicReference<>();
                for (Item item : Character.getInstance().getInventory().getItems()) {
                    sellMenu.addItem(item.getName(), () -> itemSellMenu.set((TradeMenu) item.generateSellMenu().showAndChoose()), MenuItemType.SELL);
                }
                if (itemSellMenu.get().isSuccess()) {
                    tradeItems.add(itemSellMenu.get().getTradedItem());
                }
            });
            resultItem = traderMenu.showAndChoose().getChosenMenuItem();
        } while (resultItem.getMenuItemType() != MenuItemType.BACK);
    }
}
