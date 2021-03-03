package equipment;

import menu.*;
import units.Character;

public class Weapon extends Equipment {

    WeaponType weaponType;

    public Weapon(WeaponType weaponType) {
        this.weaponType = weaponType;
        postInitialize();
        addWeaponMenu();
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

    @Override
    public void execute() {
        itemMenu.showAndChoose();
    }

    private void addWeaponMenu() {
        if (this == Character.getInstance().getWeapon()) {
            itemMenu.addItem("Убрать оружие", () -> {
                this.removeWeapon();
                System.out.println("Вы решили сражаться без оружия. Ваш максимальный урон: 4");
                Character.getInstance().getInventory().addItem(this);
            });
        } else {
            itemMenu.addItem("Использовать это оружие", () -> {
                this.equipWeapon();
                System.out.println("Вы взяли '" + this.getName() + "', его максимальный урон: " + this.getWeaponDamage());
                Character.getInstance().getInventory().removeItem(this);
            });
        }
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

}
