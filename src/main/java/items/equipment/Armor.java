package items.equipment;

import lombok.Getter;
import menu.*;
import units.character.Character;

public class Armor extends Equipment {

    @Getter
    private final ArmorType armorType;

    public Armor(ArmorType armorType) {
        this.armorType = armorType;
        this.cost = armorType.getCost();
    }

    public int getArmorClass() {
        return armorType.getArmorClass() + upgradeLevel;
    }

    @Override
    public String getName() {
        if (upgradeLevel == 0) {
            return armorType.getTitle();
        } else {
            return armorType.getTitle() + " +" + upgradeLevel;
        }
    }

    public String getPrettyName() {
        return "Ваша броня: " + getName() + "\nКласс Доспеха: " + getArmorClass();
    }

    @Override
    protected void addEquipMenuItem(Menu menu) {
        if (Character.getInstance().isNotEquipped(this)) {
            menu.addItem("Надеть эту броню", () -> {
                Character.getInstance().equip(this);
                System.out.println("Вы надеваете доспех. Ваш класс доспеха теперь равен: " + this.getArmorClass());
                Character.getInstance().getInventory().removeItem(this);
            }, MenuItemType.EQUIP_ITEM);
        }
    }

    @Override
    protected String getPrettyClassName() {
        return "броня";
    }

    @Override
    protected int getUpgradeCost() {
        return (int) (armorType.getUpgradeCost() * Math.pow(3, upgradeLevel));
    }
}


