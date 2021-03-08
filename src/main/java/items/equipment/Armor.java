package items.equipment;

import menu.*;
import units.Character;

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
        return armorType.getArmorClass();
    }

    @Override
    public String getName() {
        return armorType.getTitle();
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
        return "Ваша броня: " + getArmorType().getTitle() + "\nКласс Доспеха: " + armorType.getArmorClass();
    }

    @Override
    protected void equipMenuItem() {
        itemMenu.addItem("Надеть эту броню", () -> {
            this.equipArmor();
            System.out.println("Вы надеваете доспех. Ваш класс доспеха теперь равен: " + this.getArmorClass());
            Character.getInstance().getInventory().removeItem(this);
        }, MenuItemType.EQUIP);
    }

    @Override
    protected String getPrettyClassName() {
        return "броня";
    }
}
