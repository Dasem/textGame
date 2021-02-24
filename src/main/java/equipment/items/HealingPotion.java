package equipment.items;

import equipment.*;
import menu.*;
import units.Character;
import utils.*;

public class HealingPotion implements Item {

    Menu menu = new Menu("Меню для зелья", false);

    public HealingPotion() {
        menu.addItem("Выпить", () -> {
            System.out.println("Выпил");
            Character.getInstance().healing(getHeal());
            Character.getInstance().getInventory().removeItem(this);
        });
        menu.addItem("Не пить", () -> System.out.println("Не выпил"));
    }

    private int getHeal() {
        return Dices.diceD6();
    }

    @Override
    public String getName() {
        return "Зелье лечения";
    }

    @Override
    public void execute() {
        menu.showAndChoose();
    }
}
