package equipment.items;

import equipment.*;
import menu.*;
import units.Character;

public class HealingPotion implements Item {

    private final HealingPotionType healingPotionType;

    Menu menu = new Menu("Меню для зелья", false);

    public HealingPotion(HealingPotionType healingPotionType) {
        this.healingPotionType = healingPotionType;
        menu.addItem("Выпить", () -> {
            int heal = this.use();
            System.out.println("Ну выпил и выпил, чё бубнить-то, захилен на " + heal + " ХП.");
            Character.getInstance().getInventory().removeItem(this);
        });
        menu.addItem("Не пить", () -> System.out.println("Не выпил"));
    }

    public int use() {
        int heal = getHeal();
        Character.getInstance().healing(heal);
        return heal;
    }

    private int getHeal() {
        return healingPotionType.heal();
    }

    @Override
    public String getName() {
        return healingPotionType.getTitle();
    }

    @Override
    public void execute() {
        menu.showAndChoose();
    }
}
