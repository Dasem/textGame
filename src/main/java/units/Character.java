package units;

import com.google.common.collect.*;
import items.*;
import items.equipment.*;
import items.grocery.*;
import mechanic.battle.*;
import menu.*;
import utils.*;

import java.util.*;
import java.util.stream.*;

public class Character implements Battler {
    private final static int DEFAULT_ARMOR_CLASS = 10;
    private String username;
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

    public void loot(Item... lootableItems) {
        loot(Lists.newArrayList(lootableItems));
    }

    public void loot(Collection<Item> lootableItems) {
        if (lootableItems == null) {
            return;
        }
        List<Item> notNullItems = lootableItems.stream().filter(Objects::nonNull).collect(Collectors.toList());

        while (!notNullItems.isEmpty()) {
            Menu lootMenu = new Menu("Вы нашли предметы:", MenuSetting.HIDE_CHARACTER_MENU);
            for (Item item : notNullItems) {
                lootMenu.addItem(item.getName(), () -> {
                    MenuItemType menuItemType = item.use();
                    if (menuItemType != MenuItemType.BACK) {
                        notNullItems.remove(item);
                    }
                });
            }
            lootMenu.addAdditionalItem("Забрать всё", () -> {
                this.getInventory().addItems(notNullItems);
                notNullItems.clear();
            });
            lootMenu.addAdditionalItem("Закончить лутаться", () -> {
                notNullItems.clear();
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
        if (Character.getInstance().isDead()) {
            Utils.endGame();
        }
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

    public boolean isDead() {
        return currentHealth == 0;
    }

    public QuestItem findQuestItemByInventory(int questId) {
        return (QuestItem) getInventory().getItems().stream().filter(item -> {
            if (item instanceof QuestItem) {
                return ((QuestItem) item).getQuestId() == questId;
            }
            return false;
        }).findAny().orElse(null);
    }

    public void addCharacterMenu(Menu menu) {
        menu.addAdditionalItem("Открыть инвентарь", () -> {
            if (Character.getInstance().getInventory().getItems().isEmpty()) {
                System.out.println("Твой инвентарь пуст");
            } else {
                Menu inventoryMenu = new Menu("Инвентарь:", MenuSetting.HIDE_CHARACTER_MENU, MenuSetting.ADD_BACK_BUTTON);
                for (Item item : Character.getInstance().getInventory().getItems()) {
                    inventoryMenu.addItem(item);
                }
                inventoryMenu.showAndChoose();
            }
            menu.showAndChoose();
        });
        menu.addAdditionalItem("Просмотр персонажа", () -> {
            Menu characterMenu = new Menu("Персонаж:", MenuSetting.HIDE_CHARACTER_MENU, MenuSetting.ADD_BACK_BUTTON);
            characterMenu.addItem("Информация о персонаже", () -> {
                Character c = Character.getInstance();
                System.out.println("Меня зовут " + c.getName());
                System.out.println(c.getCurrentHealth() + "/" + c.getMaxHealth() + " HP");
                System.out.println(c.getArmorClass() + " Защиты");
                if (c.getArmor() != null) {
                    System.out.println(c.getArmor().getPrettyName());
                } else System.out.println("Нет брони");
                if (c.getWeapon() != null) {
                    System.out.println(c.getWeapon().getPrettyName());
                } else System.out.println("Нет оружия");
            });
            characterMenu.addItem("Снаряжение", () -> {
                Menu equippedMenu = new Menu("Экипированное снаряжение:", MenuSetting.HIDE_CHARACTER_MENU, MenuSetting.ADD_BACK_BUTTON);
                if (Character.getInstance().getWeapon() == null && Character.getInstance().getArmor() == null ) {
                    System.out.println("Нет надетого снаряжения");
                } else {
                    if (Character.getInstance().getWeapon() != null) {
                        equippedMenu.addItem(Character.getInstance().getWeapon());
                    }
                    if (Character.getInstance().getArmor() != null) {
                        equippedMenu.addItem(Character.getInstance().getArmor());
                    }
                    equippedMenu.showAndChoose();
                }
            });
            characterMenu.addItem("Переименовать персонажа", () -> {
                System.out.print("Введите новое имя персонажа: ");
                username = Utils.sc.nextLine();
                System.out.print("Теперь вас зовут: " + username);
            });
            characterMenu.showAndChoose();
            menu.showAndChoose();
        });
    }
}
