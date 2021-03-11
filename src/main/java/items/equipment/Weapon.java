package items.equipment;

import menu.*;
import units.Character;

public class Weapon extends Equipment {

    WeaponType weaponType;

    public Weapon(WeaponType weaponType) {
        this.weaponType = weaponType;
        addWeaponMenu();
        this.cost = weaponType.getCost();
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public int getWeaponDamage() {
        return weaponType.getDamage();
    }

    @Override
    public String getName() {
        return weaponType.getTitle();
    }

    private void addWeaponMenu() {
        equipMenuItem();
    }

    @Override
    protected void equipMenuItem() {
        itemMenu.addItem("Использовать это оружие", () -> {
            this.equipWeapon();
            System.out.println("Вы взяли '" + this.getName() + "', его максимальный урон: " + this.getWeaponDamage());
            Character.getInstance().getInventory().removeItem(this);
        }, MenuItemType.EQUIP_ITEM);
    }

    public void equipWeapon() {
        Character.getInstance().setWeapon(this);

    }

    public void removeWeapon() {
        Character.getInstance().setWeapon(null);

    }

    public String getPrettyName() {
        return "Оружие: "+getWeaponType().getTitle()+"\nМакс урон: "+getWeaponType().getDamage();

    }

    @Override
    protected String getPrettyClassName() {
        return "оружие";
    }
}
