package menu;

import items.*;

public class TradeMenu extends Menu{
    public TradeMenu(String title, MenuSetting... menuSettings) {
        super(title, menuSettings);
    }

    private boolean success;
    private Item tradedItem;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Item getTradedItem() {
        return tradedItem;
    }

    public void setTradedItem(Item tradedItem) {
        this.tradedItem = tradedItem;
    }
}
