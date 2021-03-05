package items.grocery;

import items.*;
import menu.*;
import units.Character;

public class HealingPotion extends Item {

    private final HealingPotionType healingPotionType;

    public HealingPotion(HealingPotionType healingPotionType) {
        this.healingPotionType = healingPotionType;
        addPotionMenu();
    }

    private void addPotionMenu() {
        itemMenu.addItem("Выпить", () -> {
            int heal = getHeal();
            Character.getInstance().healing(heal);
            System.out.println("Ну выпил и выпил, чё бубнить-то, захилен на " + heal + " ХП." + Character.getInstance().getHpBar());
            Character.getInstance().getInventory().removeItem(this);
        });
    }

    private int getHeal() {
        return healingPotionType.heal();
    }

    @Override
    public String getName() {
        return healingPotionType.getTitle();
    }
}
