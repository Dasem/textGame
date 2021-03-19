package units.character;


import com.google.common.collect.Lists;
import items.Item;
import menu.Menu;
import menu.MenuItemType;
import menu.MenuSetting;
import utils.Utils;

import java.util.List;
import java.util.Scanner;

public class StartStat {
    int statPoints = 27;
    int cost = 0;
    List<Stat> stats  = Lists.newArrayList(Stat.values());
    public void statEnter() {
        while (statPoints != 0) {
            Menu statMenu = new Menu("Распределение характеристик", MenuSetting.HIDE_CHARACTER_MENU);
            for (Stat stat : stats) {
                statMenu.addItem(stat.getName(), () -> {
                    System.out.println(stat.getName()+":");
                    int count = Integer.parseInt(Utils.sc.nextLine());
                    for (int i = 8; i != count; i++) {
                        if (i >= 13) {
                            cost += 2;
                        } else {
                            cost++;
                        }
                    }
                    Character.getInstance().getStats().put(stat, count);
                    stats.remove(stat);
                    statPoints = statPoints - cost;
                    System.out.println("Остадось " + statPoints + " очков");
                    cost = 0;
                });
            }
            statMenu.showAndChoose();
        }

    }
    public void statRandom() {
        Menu randoStatMenu = new Menu("Рандомное распределение", MenuSetting.HIDE_CHARACTER_MENU);
        for (Stat stat : stats) {


            randoStatMenu.showAndChoose();
        }
    }
}
