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
        postInitialize();
        addArmorMenu();
    }

    public int getArmorClass() {
        return armorType.getArmorClass();
    }

    @Override
    public String getName() {
        return armorType.getTitle();
    }

    @Override
    public MenuItemType use() {
        return itemMenu.showAndChoose().getMenuItemType();
    }

    private void addArmorMenu() {
        if (this == Character.getInstance().getArmor()) {
            itemMenu.addItem("Снять броню", () -> {
                this.removeArmor();
                System.out.println("Сняв броню, вы чувствует облегчение");
                Character.getInstance().getInventory().addItem(this);
            });
        } else {
            itemMenu.addItem("Надеть эту броню", () -> {
                this.equipArmor();
                System.out.println("Вы надеваете доспех. Ваш класс доспеха теперь равен: " + this.getArmorClass());
                Character.getInstance().getInventory().removeItem(this);
            });
        }
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

}
