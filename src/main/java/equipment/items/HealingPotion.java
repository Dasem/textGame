package equipment.items;

import equipment.*;
import menu.*;
import units.Character;

public class HealingPotion extends Item {

    private final HealingPotionType healingPotionType;


    public HealingPotion(HealingPotionType healingPotionType) {
        this.healingPotionType = healingPotionType;
        postInitialize();
        addPotionMenu();
    }

    private void addPotionMenu() {
        itemMenu.addItem("Выпить", () -> {
            int heal = this.use();
            System.out.println("Ну выпил и выпил, чё бубнить-то, захилен на " + heal + " ХП.");
            Character.getInstance().getInventory().removeItem(this);
        });
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
        itemMenu.showAndChoose();
    }
}
