package units;

import com.google.common.collect.*;
import items.*;
import items.equipment.*;
import items.grocery.*;
import mechanic.Actionable;
import mechanic.battle.*;
import mechanic.quest.*;
import mechanic.quest.task.DialogTask;
import mechanic.quest.task.Task;
import menu.*;
import utils.*;

import java.util.*;
import java.util.stream.*;

public class Character implements Battler {
    private final static int DEFAULT_ARMOR_CLASS = 100;
    private String username;
    private int currentHealth = getMaxHealth();
    private Armor armor;
    private Weapon weapon;
    private final Inventory inventory = new Inventory();
    private Collection<Quest> activeQuests = new ArrayList<>();

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
        List<Item> existedItems = lootableItems.stream().filter(Objects::nonNull).collect(Collectors.toList());

        while (!existedItems.isEmpty()) {
            Menu lootMenu = new Menu("Вы нашли предметы:", MenuSetting.HIDE_CHARACTER_MENU);
            for (Item item : existedItems) {
                MenuItem menuItem = lootMenu.addItem(item.getName(), null);
                menuItem.setChoosable(() -> {
                    Menu use = item.use(menuItem);
                    if (use.getChosenMenuItem().getMenuItemType() != MenuItemType.BACK) {
                        existedItems.remove(item);
                    }
                });
            }
            lootMenu.addAdditionalItem("Забрать всё", () -> {
                this.lootItems(existedItems);
                existedItems.clear();
            });
            lootMenu.addAdditionalItem("Закончить лутаться", existedItems::clear);
            lootMenu.showAndChoose();
        }
    }

    public void earnMoney(int money) {
        getInventory().setMoney(getInventory().getMoney() + money);
    }

    public void wasteMoney(int money) {
        getInventory().setMoney(getInventory().getMoney() - money);
    }

    public void lootItem(Item item) {
        this.inventory.addItem(item);
    }

    public void lootItems(Collection<Item> item) {
        this.inventory.addItems(item);
    }

    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    @Override
    public void setCurrentHealth(int currentHealth) {
        if (currentHealth < 0) {
            this.currentHealth = 0;
        } else {
            this.currentHealth = Math.min(currentHealth, getMaxHealth());
        }
    }

    @Override
    public int getMaxHealth() {
        return 10;
    }

    @Override
    public int getOnHitDamage() {
        return 100;
    }

    @Override
    public int getAttackModifier() {
        return 100;
    }

    @Override
    public int getArmorClass() {
        if (armor == null) {
            return DEFAULT_ARMOR_CLASS;
        } else {
            return getArmor().getArmorType().getArmorClass();
        }
    }

    public void acceptQuest(Quest quest) {
        activeQuests.add(quest);
    }

    public void denyQuest(Quest quest) {
        activeQuests.remove(quest);
    }

    @Override
    public boolean takeDamage(int damage) {
        setCurrentHealth(getCurrentHealth() - damage);
        return getCurrentHealth() == 0;
    }

    public void healing(int heal) {
        setCurrentHealth(getCurrentHealth() + heal);
    }

    public void setFullRest() {
        this.currentHealth = this.getMaxHealth();
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

    @Override
    public void died() {

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

    public void removeIfEquipped(Equipment equipment) {
        if (equipment == weapon) {
            weapon = null;
        }
        if (equipment == armor) {
            armor = null;
        }
    }

    public Collection<Quest> getActiveQuests() {
        return activeQuests;
    }

    public Quest getQuestById(String questIdentifier) {
        for (Quest quest : activeQuests) {
            if (quest.getQuestIdentifier().equals(questIdentifier)){
                return quest;
            }
        }
        return null;
    }

    public String getHpBar() {
        return " (" + getCurrentHealth() + "/" + getMaxHealth() + ")";
    }

    public QuestItem findQuestItemByInventory(int questId) {
        return (QuestItem) getInventory().getItems().stream().filter(item -> {
            if (item instanceof QuestItem) {
                return ((QuestItem) item).getQuestId() == questId;
            }
            return false;
        }).findAny().orElse(null);
    }

    public boolean isEquipped(Item item) {
        return weapon == item || armor == item;
    }

    public void addCharacterMenu(Menu menu) {
        menu.addAdditionalItem("Открыть инвентарь", () -> {
            if (getInventory().getItems().isEmpty()) {
                System.out.println("Твой инвентарь пуст");
            } else {
                Menu inventoryMenu = new Menu("Инвентарь:", MenuSetting.HIDE_CHARACTER_MENU, MenuSetting.ADD_BACK_BUTTON);
                for (Item item : getInventory().getItems()) {
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
                System.out.println((c.getInventory().getMoney() + " Золота"));
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
                if (getWeapon() == null && getArmor() == null) {
                    System.out.println("Нет надетого снаряжения");
                } else {
                    if (getWeapon() != null) {
                        equippedMenu.addItem(getWeapon());
                    }
                    if (getArmor() != null) {
                        equippedMenu.addItem(getArmor());
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


    public boolean tryDialog(String dialogIdentifier, Actionable success, Actionable fail) {
        for (Quest quest : Character.getInstance().getActiveQuests()) {
            for (Task task : quest.getTasks()) {
                if (task instanceof DialogTask) {
                    DialogTask dialogTask = (DialogTask) task;
                    if (dialogTask.talkWithId(dialogIdentifier)) {
                        if(success!=null) {
                            success.doAction();
                        }
                        return true;
                    }
                }
            }
        }
        if(fail!=null) {
            fail.doAction();
        }
        return false;
    }
}
