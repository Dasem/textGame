package units.character;


import com.google.common.collect.Lists;
import mechanic.dice.*;
import menu.Menu;
import menu.MenuItem;
import menu.MenuItemType;
import menu.MenuSetting;


import java.util.*;


public class StartStat {
    int statPoints = 27;
    int cost = 0;
    Map<Stat, Integer> zeroStats = new HashMap<>();
    List<Stat> stats = Lists.newArrayList(Stat.values());
    MenuItem resultItem;

    public void statEnter() {
        for (Stat autoStat : stats) {
            zeroStats.put(autoStat, 8);
        }
        Menu statMenu = new Menu("Распределение характеристик", MenuSetting.HIDE_CHARACTER_MENU);
        for (Stat stat : stats) {
            statMenu.addItem(() ->stat.getName() + " = " + zeroStats.get(stat), () -> {
                do {
                    Menu statChangeMenu = new Menu("Изменение " + stat.getName(), MenuSetting.HIDE_CHARACTER_MENU, MenuSetting.ADD_BACK_BUTTON);
                    statChangeMenu.addItem("Повысить стату " + stat.getName(), () -> {
                        if (zeroStats.get(stat) >= 15) {
                            System.out.println("Сейчас " + stat.getName() + " = " + zeroStats.get(stat));
                            System.out.println("Больше нельзя");
                        } else {
                            if (zeroStats.get(stat) >= 13) {
                                zeroStats.put(stat, zeroStats.get(stat) + 1);
                                cost += 2;
                                System.out.println("Сейчас " + stat.getName() + " = " + zeroStats.get(stat));
                            } else {
                                zeroStats.put(stat, zeroStats.get(stat) + 1);
                                cost++;
                                System.out.println("Сейчас " + stat.getName() + " = " + zeroStats.get(stat));
                            }
                        }
                        statPoints -= cost;
                    });
                    statChangeMenu.addItem("Понизить стату " + stat.getName(), () -> {
                        if (zeroStats.get(stat) <= 8) {
                            System.out.println("Сейчас " + stat.getName() + " = " + zeroStats.get(stat));
                            System.out.println("Меньше нельзя");
                        } else {
                            if (zeroStats.get(stat) > 13) {
                                zeroStats.put(stat, zeroStats.get(stat) - 1);
                                cost += 2;
                                System.out.println("Сейчас " + stat.getName() + " = " + zeroStats.get(stat));
                            } else {
                                zeroStats.put(stat, zeroStats.get(stat) - 1);
                                cost++;
                                System.out.println("Сейчас " + stat.getName() + " = " + zeroStats.get(stat));
                            }
                        }
                        statPoints += cost;
                    });
                    System.out.println("Осталось " + statPoints + " очков");
                    resultItem = statChangeMenu.showAndChoose().getChosenMenuItem();
                    cost = 0;
                } while (resultItem.getMenuItemType() != MenuItemType.BACK);
            });
        }
        statMenu.addItem("Рандом", this::statRandom);
        while (statPoints != 0) {
            statMenu.showAndChoose();
        }
        Character.getInstance().getStats().putAll(zeroStats);

    }

    public void statRandom() {
        statPoints=0;
        Dice d6 = Dice.D6;
        for (Stat stat : stats) {
            int count;
            int a = d6.roll();
            int b = d6.roll();
            int c = d6.roll();
            int d = d6.roll();
            count = a + b + c + d - min(a, b, c, d);
            zeroStats.put(stat, count);
            System.out.println(stat.getName() + " = " + zeroStats.get(stat));
        }

    }

    public static int min(int ... digits) {
        int min = 0;
        for (int num : digits) {
            if (num < min)
                min = num;
        }
        return min;
    }
}
