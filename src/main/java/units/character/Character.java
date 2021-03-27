package units.character;

import com.google.common.collect.*;
import items.*;
import items.equipment.*;
import items.grocery.*;
import mechanic.Actionable;
import mechanic.battle.*;
import mechanic.dice.*;
import mechanic.quest.*;
import mechanic.quest.task.DialogTask;
import mechanic.quest.task.Task;
import menu.*;
import units.Fraction;
import units.Unit;
import utils.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.*;

public class Character extends Unit {

    private Specialization specialization;


    private final static int DEFAULT_ARMOR_CLASS = 10;
    private String username;
    private Armor armor;
    private Weapon weapon;
    private final Inventory inventory = new Inventory();
    private final Collection<Quest> activeQuests = new ArrayList<>();
    private final Map<Stat, Integer> stats = new HashMap<>();
    private int level = 1;
    int[] levelThreshold = {0, 300, 600, 1800, 3800, 7500, 9000, 11000, 14000, 16000, 21000, 15000, 20000, 20000, 25000, 30000, 30000, 40000, 40000, 50000, 999999};
    private int currentExp;
    private int expToLvlUp = levelThreshold[level];

    private static Character character;

    public static void createInstance() {
        System.out.print("Введите имя персонажа: ");
        String username = Utils.sc.nextLine();
        character = new Character(username);
        System.out.print("Задайте характеристики: ");
        new StartStat().statEnter();
        System.out.print("Выберите класс персонажа: ");
        chooseSpecialization();
        character.setFullRest(); // Отхилить после создания на фулл
    }

    public static Character getInstance() {
        return character;
    }

    private Character(String username) {
        //todo красивее
        super(Fraction.getByName("ГГ"));
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

    public void getExp(int exp) {
        character.currentExp += exp;
        System.out.println("Вы получили " + exp + " опыта.");
        while (currentExp / expToLvlUp > 0) {
            currentExp -= expToLvlUp;
            level += 1;
            expToLvlUp = levelThreshold[level];
            System.out.println("Вы достигли " + level + " уровня!");
        }
    }

    @Override
    public int getMaxHealth() {
        return Character.getInstance().getSpecialization().getBasedHP(); //TODO правильно ли
    }

    @Override
    public int getOnHitDamage() {
        if (weapon == null) {
            return Dice.D4.roll();
        } else {
            return weapon.getDicedDamage();
        }
    }

    @Override
    public int getAttackModifier() {
        //TODO: если бьёшь рукой, оно падает (weapon == null)
        return factStat(weapon.weaponStat());
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
    public int mobExp() {
        return 0;
    }

    @Override
    public boolean isFriendlyTo(Battler battler) {
        return true;
    }

    @Override
    public BattleActionResult battleAction(List<Battler> possibleTargets) {
        //todo: можно заюзать заклинание
        // (можно добавить какой-нибудь рандомный фаербол который на всех енеми работает)
        AtomicReference<BattleActionResult> result = new AtomicReference<>();
        List<Battler> aliveTargets = BattleUtils.extractAliveOpponents(possibleTargets);

        Menu battleMenu = new Menu("Выберите действие: ", MenuSetting.HIDE_CHARACTER_MENU);

        Menu attackMenu = new Menu("Выберите цель для атаки: ",
                MenuSetting.ADD_BACK_BUTTON, MenuSetting.HIDE_CHARACTER_MENU);
        Menu inventoryMenu = new Menu("Выберите предмет для использования: ",
                MenuSetting.ADD_BACK_BUTTON, MenuSetting.HIDE_CHARACTER_MENU);
//      Menu spellMenu = new Menu("Выберите цель для лечения: ",
//              MenuSetting.ADD_BACK_BUTTON, MenuSetting.HIDE_CHARACTER_MENU);

        battleMenu.addItem("Выбрать цель для атаки", attackMenu::showAndChoose);
        battleMenu.addItem("Использовать предмет", inventoryMenu::showAndChoose);

        for (Battler target : aliveTargets) {
            if (!target.isFriendlyTo(character)) {
                attackMenu.addItem(target.getName(),
                        () -> result.set(BattleUtils.doDirectAttack(character, target)));
            }
        }

        for (Item item : getInventory().getItems()) {
            // Эта циклическая зависимость отвратительна
            MenuItem inventoryMenuItem = inventoryMenu.addItem(item.getName(), null);
            inventoryMenuItem.setChoosable(() -> {
                if (item.use(inventoryMenuItem).getChosenMenuItem().typeIsBack()) {
                    inventoryMenu.showAndChoose();
                } else
                    result.set(new BattleActionResult(Lists.newArrayList(),
                            String.format("Использован предмет %s", item.getName()),
                            this, Lists.newArrayList(this)));
            });
        }

        //battleMenu.addAdditionalItem("Автобой", () -> {});

        battleMenu.addAdditionalItem("Сбежать из боя", () -> {
            boolean isLucky = Dice.D20.roll() > 6;
            result.set(new BattleActionResult(Lists.newArrayList(),
                    isLucky ? "Вы сбежали из боя" : "Вам не удалось избежать боя",
                    this, Lists.newArrayList(), isLucky));
            // Не стал делать списки null`ами, вдруг это что-нибудь сломает в месте их обработки. Пусть будут просто пустыми.
        });

        do {
            battleMenu.showAndChoose();
        } while (attackMenu.getChosenMenuItem() != null && attackMenu.getChosenMenuItem().typeIsBack() ||
                inventoryMenu.getChosenMenuItem() != null && inventoryMenu.getChosenMenuItem().typeIsBack());
        return result.get();
    }

    @Override
    public int initiativeThrow() {
        int initiative = Dice.D20.roll() + Character.getInstance().factStat(Stat.AGILITY);
        Utils.suspense(250);
        System.out.println(this.getName() + " Бросил на инициативу " + initiative);
        return initiative;
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
        System.out.println("Вы сдохли как тварь дрожащая!");
        Utils.endGame();
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
            if (quest.getQuestIdentifier().equals(questIdentifier)) {
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
                System.out.println(c.currentExp + "/" + c.expToLvlUp + " опыта.");
                if (c.getArmor() != null) {
                    System.out.println(c.getArmor().getPrettyName());
                } else System.out.println("Нет брони");
                if (c.getWeapon() != null) {
                    System.out.println(c.getWeapon().getPrettyName());
                } else System.out.println("Нет оружия");
                for (Stat stat : Stat.values()) {
                    System.out.println(stat.getName() + " " + getStat(stat));
                }
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
        if (!activeQuests.isEmpty()) {
            menu.addAdditionalItem("Текущие задания", () -> {
                Menu questMenu = new Menu("Задания:", MenuSetting.HIDE_CHARACTER_MENU, MenuSetting.ADD_BACK_BUTTON);
                for (Quest quest : activeQuests) {
                    if (!quest.isDone()) {
                        questMenu.addItem(quest.getDescription(), () -> {
                            Menu innerMenu = new Menu("Задание: ", MenuSetting.HIDE_CHARACTER_MENU, MenuSetting.ADD_BACK_BUTTON);
                            innerMenu.addItem("Просмотреть задачи", () -> {
                                for (Task task : quest.getTasks()) {
                                    task.print();
                                }
                            });
                            innerMenu.addItem("Отклонить", () -> {
                                this.denyQuest(quest);
                                System.out.println("Ты отколняешь задание");
                            });
                            innerMenu.showAndChoose();
                        });
                    }
                }
                questMenu.showAndChoose();
            }, MenuItemType.QUEST_LIST);
        }
    }

    public Map<Stat, Integer> getStats() {
        return stats;
    }

    public int getStat(Stat stat) {
        return stats.get(stat);
    }

    public int factStat(Stat stat) {
        int statValue = getStat(stat);
        if (statValue - 10 >= 0) {
            return (statValue - 10) / 2;
        } else {
            return (statValue - 11) / 2;
        }
    }

    public boolean tryDialog(String dialogIdentifier, Actionable success, Actionable fail) {
        for (Quest quest : Character.getInstance().getActiveQuests()) {
            for (Task task : quest.getTasks()) {
                if (task instanceof DialogTask) {
                    DialogTask dialogTask = (DialogTask) task;
                    if (dialogTask.talkWithId(dialogIdentifier)) {
                        if (success != null) {
                            success.doAction();
                        }
                        return true;
                    }
                }
            }
        }
        if (fail != null) {
            fail.doAction();
        }
        return false;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public static void chooseSpecialization(){
        Menu menu = new Menu("Меню выбора Специализации");
        Specializations [] specializations = Specializations.values();
        for(Specializations special : specializations){
            MenuItem menuItem = menu.addItem(special.getName(), ()-> Character.getInstance().setSpecialization(special.getSpecialization()));

        }
        menu.showAndChoose();

    }

}

