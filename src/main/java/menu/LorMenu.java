package menu;

import com.google.common.collect.Lists;

public class LorMenu extends Menu{

    public LorMenu(String title, MenuSetting... menuSettings) {
        super(title, menuSettings);
        this.menuSettings.addAll(Lists.newArrayList(MenuSetting.HIDE_CHARACTER_MENU, MenuSetting.HIDE_ADDITIONAL_MENU));
    }
}
