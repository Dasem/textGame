package menu;

import items.*;

public class TradeMenu extends Menu{
    public TradeMenu(String title, MenuSetting... menuSettings) {
        super(title, menuSettings);
    }

    @lombok.Getter @lombok.Setter private boolean success;
    @lombok.Getter @lombok.Setter private Item tradedItem;

}
