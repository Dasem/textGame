package units.character;


import com.google.common.collect.Lists;
import items.Item;
import menu.Menu;
import utils.Utils;

import java.util.List;
import java.util.Scanner;

public class StartStat {
    int statPoints = 27;
    int cost = 0;

    public void statEnter() {
        List<Stat> stats  = Lists.newArrayList(Stat.values());
        while (statPoints != 0) {
            Menu statMenu = new Menu("Распределение характеристик");
            for (Stat stat : stats) {
                statMenu.addItem(stat.getName(), () -> {
                    int count = Utils.sc.nextInt();
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
}
