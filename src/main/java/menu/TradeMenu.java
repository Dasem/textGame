package menu;

import items.*;
import lombok.Getter;
import lombok.Setter;

public class TradeMenu extends Menu{
    public TradeMenu(String title, MenuSetting... menuSettings) {
        super(title, menuSettings);
    }

    @Getter @Setter
    private boolean success;
    @Getter @Setter
    private Item tradedItem;

}
