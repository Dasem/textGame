package equipment;

import equipment.items.HealingPotionType;
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
    private Menu weaponMenu(){
        Menu weaponMenu = new Menu("Меню для оружия");
        weaponMenu.addItem("Использовать это оружие", () -> {
            this.equipWeapon();
            System.out.println("Ну теперь точно всем вломлю");
            Character.getInstance().getInventory().removeItem(this);
        });
        weaponMenu.addItem("Оставить как есть", () -> System.out.println("Моя пушка получше будет"));
        return weaponMenu;
    }

    public void equipWeapon(){
        Character.getInstance().setWeapon(this);

    }



}
