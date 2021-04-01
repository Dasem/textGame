package items.equipment;

import menu.*;
import units.character.Character;
import units.character.Stat;

public class Weapon extends Equipment {

    WeaponType weaponType;

    public Weapon(WeaponType weaponType) {
        this.weaponType = weaponType;
        this.cost = weaponType.getCost();
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public int getWeaponDamage() {
        return weaponType.getDamage() + upgradeLevel;
    }

    public int getDicedDamage(){
        return weaponType.getDicedDamage() + upgradeLevel;
    }

    @Override
    public String getName() {
        if (upgradeLevel == 0) {
            return weaponType.getTitle();
        } else {
            return weaponType.getTitle() + " +" + upgradeLevel;
        }
    }


    @Override
    protected void addEquipMenuItem(Menu menu) {
        if (Character.getInstance().isNotEquipped(this)) {
            menu.addItem("Использовать это оружие", () -> {
                Character.getInstance().equip(this);
                System.out.println("Вы взяли '" + this.getName() + "', его максимальный урон: " + this.getWeaponDamage());
                Character.getInstance().getInventory().removeItem(this);
            }, MenuItemType.EQUIP_ITEM);
        }
    }

    public Stat weaponStat() {
        return weaponType.getWeaponStat();
    }


    public String getPrettyName() {
        return "Оружие: " + getName() + "\nМакс урон: " + getWeaponDamage();

    }

    @Override
    protected String getPrettyClassName() {
        return "оружие";
    }

    @Override
    protected int getUpgradeCost() {
        return weaponType.getUpgradeCost();
    }

}
