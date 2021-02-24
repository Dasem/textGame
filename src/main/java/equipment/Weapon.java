package equipment;

import mechanic.labyrinth.Labyrinth;

public class Weapon extends Equipment {

    WeaponType weaponType;
    public int getWeaponDamage(){
        return weaponType.SWORD.getDamage();
    }
}
