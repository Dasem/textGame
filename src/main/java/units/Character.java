package units;

import equipment.*;
import levels.*;
import mechanic.battle.*;
import menu.*;
import utils.*;

import java.util.*;

public class Character implements Battler {
    private final static int DEFAULT_ARMOR_CLASS = 10;
    private final String username;
    private int currentHealth = getMaxHealth();
    private Armor armor;
    private Weapon weapon;
    private final Inventory inventory = new Inventory();

    private static Character character;

    public static void createInstance(String username) {
        Character.character = new Character(username);
    }

    public static Character getInstance() {
        return character;
    }

    private Character(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void loot(Collection<Item> itemsFromMob) {
        if (itemsFromMob == null) {
            return;
        }
        while (!itemsFromMob.isEmpty()) {
            Menu lootMenu = new Menu("Вы нашли предметы:", MenuSetting.HIDE_CHARACTER_MENU);
            for (Item item : itemsFromMob) {
                lootMenu.addItem(item.getName(), ()-> {
                    item.execute();
                    itemsFromMob.remove(item);
                });
            }
            lootMenu.addAdditionalItem("Забрать всё", () -> {
                this.getInventory().addItems(itemsFromMob);
                itemsFromMob.clear();
            });
            lootMenu.addAdditionalItem("Закончить лутаться", () -> {
                itemsFromMob.clear();
            });
            lootMenu.showAndChoose();
        }
    }

    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    @Override
    public int getMaxHealth() {
        return 10;
    }

    @Override
    public int getOnHitDamage() {
        if (weapon == null) {
            return Dices.diceD4();
        } else {
            return weapon.getWeaponType().getDicedDamage();
        }
    }

    @Override
    public int getAttackModifier() {
        return 20;
    }

    @Override
    public int getArmorClass() {
        if (armor == null) {
            return DEFAULT_ARMOR_CLASS;
        } else {
            return getArmor().getArmorType().getArmorClass();
        }
    }



    @Override
    public boolean takeDamage(int damage) {
        currentHealth -= damage;
        return currentHealth <= 0;
    }

    public void healing(int heal) {
        setCurrentHealth(getCurrentHealth() + heal);
    }

    public void setCurrentHealth(int currentHealth) {
        if (currentHealth < 0) {
            this.currentHealth = 0;
        } else if (currentHealth > getMaxHealth()) {
            this.currentHealth = getMaxHealth();
        } else {
            this.currentHealth = currentHealth;
        }
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    @Override
    public String getName() {
        return username;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void printItemsList() {
        System.out.print(inventory.getNumeratedList());
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean isDead(){
       return currentHealth==0;
    }
}
