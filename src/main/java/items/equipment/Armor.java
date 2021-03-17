package items.equipment;

import menu.*;
import units.character.Character;

public class Armor extends Equipment {

    private final ArmorType armorType;




    public ArmorType getArmorType() {
        return armorType;
    }

    public Armor(ArmorType armorType) {
        this.armorType = armorType;
        addArmorMenu();
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
            return armorType.getTitle() + " " + upgradeLevel;
        }
    }

    private void addArmorMenu() {
        equipMenuItem();
    }

    public void equipArmor() {
        Character.getInstance().setArmor(this);
    }

    public void removeArmor() {
        Character.getInstance().setArmor(null);
    }

    public String getPrettyName() {
        return "Ваша броня: " + getName() + "\nКласс Доспеха: " + getArmorClass();
    }

    @Override
    protected void equipMenuItem() {
        itemMenu.addItem("Надеть эту броню", () -> {
            this.equipArmor();
            System.out.println("Вы надеваете доспех. Ваш класс доспеха теперь равен: " + this.getArmorClass());
            Character.getInstance().getInventory().removeItem(this);
        }, MenuItemType.EQUIP_ITEM);
    }

    @Override
    protected String getPrettyClassName() {
        return "броня";
    }

    @Override
    protected int getUpgradeCost() {
        return (int) (armorType.getUpgradeCost()*Math.pow(3,upgradeLevel));
    }
}


