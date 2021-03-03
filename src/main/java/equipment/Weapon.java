package equipment;

import menu.Menu;
import units.Character;

public class Weapon extends Equipment {

    WeaponType weaponType;

    public Weapon(WeaponType weaponType) {
        this.weaponType = weaponType;
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
        weaponMenu().showAndChoose();
    }

    private Menu weaponMenu() {
        Menu weaponMenu = new Menu("Меню для оружия", false);
        if (this == Character.getInstance().getWeapon()) {
            weaponMenu.addItem("Убрать оружие", () -> {
                this.removeWeapon();
                System.out.println("Вы решили сражаться без оружия. Ваш максимальный урон: 4");
                Character.getInstance().getInventory().addItem(this);
            });
        } else {
            weaponMenu.addItem("Использовать это оружие", () -> {
                this.equipWeapon();
                System.out.println("Вы взяли '" + this.getName() + "', его максимальный урон: " + this.getWeaponDamage());
                Character.getInstance().getInventory().removeItem(this);
            });
        }
        weaponMenu.addItem("Оставить как есть", () -> System.out.println("Моя пушка получше будет"));
        return weaponMenu;
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
