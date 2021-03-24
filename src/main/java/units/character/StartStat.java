package units.character;


import com.google.common.collect.Lists;
import menu.Menu;
import menu.MenuItem;
import menu.MenuItemType;
import menu.MenuSetting;
import utils.Dices;


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
        //statMenu.addItem("Рандом", this::statRandom);
        statPoints=0;
        for (Stat stat : stats) {
            int count;
            int a = Dices.diceD6();
            int b = Dices.diceD6();
            int c = Dices.diceD6();
            int d = Dices.diceD6();
            count = a + b + c + d - min(a, b, c, d);
            zeroStats.put(stat, count);
            System.out.println(stat.getName() + " = " + zeroStats.get(stat));
        }

    }

    public static int min(int a, int b, int c, int d) {

        if (a < b && a < c && a < d) {
            return a;
        } else if (b < a && b < c && b < d) {
            return b;
        } else if (c < a && c < b && c < d) {
            return c;
        } else {
            return d;
        }
    }
}
