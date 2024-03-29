package units.character;

import com.google.common.collect.*;
import dnl.utils.text.table.*;
import items.*;
import items.equipment.*;
import items.grocery.*;
import lombok.*;
import mechanic.*;
import mechanic.battle.*;
import mechanic.dice.*;
import mechanic.location.*;
import mechanic.quest.*;
import mechanic.quest.task.*;
import menu.*;
import org.reflections.*;
import service.*;
import units.*;
import utils.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

public class Character extends Unit {
    private final static int DEFAULT_ARMOR_CLASS = 10;

    // Основная информация
    private String username;
    @Getter
    @Setter
    private Armor armor;
    @Getter
    @Setter
    private Weapon weapon;
    @Getter
    private final Inventory inventory = new Inventory();

    // Квесты
    @Getter
    private final Collection<Quest> activeQuests = new ArrayList<>();

    // Состояние персонажа
    @Getter
    private final Map<Stat, Integer> stats = new HashMap<>();
    @Getter
    @Setter
    private Specialization specialization;
    @Getter
    private int level = 1;
    @Getter
    private int currentExp;
    private int currentMasteryLvl; //todo: ненужное поле, можно ретурнить в геттере значение

    // Перемещение
    @Getter
    @Setter
    private Position currentPosition;
    @Getter
    private final List<Position> positionsHistory = new ArrayList<>();

    // Бой
    private boolean autoBattle = false;

    {
        setFraction(Fraction.HERO);
    }

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
        this.username = username;
    }

    // TODO: только для тестов, по хорошему потом нормально поменять
    public static void createTestInstance() {
        character = new Character("test");
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
                lootMenu.addItem(item.getName(), () -> {
                    Menu use = item.generateUseMenu().showAndChoose();
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

    public void equip(Equipment equipment) {
        if (equipment instanceof Weapon) {
            Character.getInstance().setWeapon((Weapon) equipment);
        }
        if (equipment instanceof Armor) {
            Character.getInstance().setArmor((Armor) equipment);
        }
    }

    public void gainExp(int exp) {
        character.currentExp += exp;
        System.out.println("Вы получили " + exp + " опыта.");
        setupActualLevel();
        System.out.println("Вы достигли " + level + " уровня!");
    }

    private int expForLevelUp() {
        return LevelService.CHARACTER_LEVEL_THRESHOLD[level];
    }

    public void setupActualLevel() {
        for (int i = 0; i < LevelService.CHARACTER_LEVEL_THRESHOLD.length; i++) {
            if (currentExp < LevelService.CHARACTER_LEVEL_THRESHOLD[i]) {
                level = i;
                return;
            }
        }
    }

    @Override
    public int getMaxHealth() {
        return Character.getInstance().getSpecialization().getBasedHP();
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
        return weapon != null ? factStat(weapon.weaponStat()) : 0;

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
    public BattleActionResult battleAction(List<Battler> possibleTargets) {
        //todo: можно заюзать заклинание
        // (можно добавить какой-нибудь рандомный фаербол который на всех енеми работает)
        if (autoBattle) {
            BattleActionResult result = super.battleAction(possibleTargets);
            if (BattleUtils.extractAliveOpponents(this, possibleTargets).isEmpty()) {
                autoBattle = false;
            }
            return result;
        } else {
            AtomicReference<BattleActionResult> result = new AtomicReference<>();
            List<Battler> aliveTargets = BattleUtils.extractAliveOpponents(this, possibleTargets);

            Menu battleMenu = new Menu("Выберите действие: ", MenuSetting.HIDE_CHARACTER_MENU);

            Menu attackMenu = new Menu("Выберите цель для атаки: ",
                    MenuSetting.ADD_BACK_BUTTON, MenuSetting.HIDE_CHARACTER_MENU);
            Menu inventoryMenu = new Menu("Выберите предмет для использования: ",
                    MenuSetting.ADD_BACK_BUTTON, MenuSetting.HIDE_CHARACTER_MENU);
//      Menu spellMenu = new Menu("Выберите цель для лечения: ",
//              MenuSetting.ADD_BACK_BUTTON, MenuSetting.HIDE_CHARACTER_MENU);
            //todo: Не совсем согласен с целью для лечения. Скорее так:
            // -> Использование заклинания
            // -> <выбор заклинания, возможно оно будет лечить>
            // -> <выбор цели для заклинания, если требуется (в общем случае просто дальше работает сам скилл, хз что ему там нужно будет)>

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
                inventoryMenu.addItem(item.getName(), () -> {
                    if (item.generateUseMenu().showAndChoose().getChosenMenuItem().typeIsBack()) {
                        inventoryMenu.showAndChoose();
                    } else
                        result.set(new BattleActionResult(Lists.newArrayList(),
                                String.format("Использован предмет %s", item.getName()),
                                this, Lists.newArrayList(this)));
                });
            }

            battleMenu.addAdditionalItem("Автобой", () -> {
                autoBattle = true;
                result.set(super.battleAction(possibleTargets));
                if (BattleUtils.extractAliveOpponents(this, possibleTargets).isEmpty()) {
                    autoBattle = false;
                }
            });

            battleMenu.addAdditionalItem("Сбежать из боя", () -> {
                if (Dice.D20.roll() > 6) {
                    result.set(new BattleActionResult(Lists.newArrayList(), "Вы сбежали из боя",
                            this, Lists.newArrayList(), true));
                    goBack();
                } else {
                    result.set(new BattleActionResult(Lists.newArrayList(), "Вам не удалось избежать боя",
                            this, Lists.newArrayList()));
                }
            });

            do {
                battleMenu.showAndChoose();
            } while (attackMenu.getChosenMenuItem() != null && attackMenu.getChosenMenuItem().typeIsBack() ||
                    inventoryMenu.getChosenMenuItem() != null && inventoryMenu.getChosenMenuItem().typeIsBack());
            return result.get();
        }
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

    @Override
    public String getName() {
        return username;
    }

    @Override
    public void died() {
        System.out.println("Вы сдохли как тварь дрожащая!");
        Utils.endGame();
    }

    public void printItemsList() {
        System.out.print(inventory.getNumeratedList());
    }

    public boolean isDead() {
        return currentHealth == 0;
    }

    public void removeIfEquipped(Item item) {
        if (item == weapon) {
            weapon = null;
        }
        if (item == armor) {
            armor = null;
        }
    }

    public Quest getQuestById(String questIdentifier) {
        for (Quest quest : activeQuests) {
            if (quest.getQuestIdentifier().equals(questIdentifier)) {
                return quest;
            }
        }
        return null;
    }

    public String getHPView() {
        return Character.getInstance().getCurrentHealth() + "/" + Character.getInstance().getMaxHealth() + " HP";
    }

    public String getArmorView() {
        if (getArmor() == null){
        return "Нет брони / " + getArmorClass() + " Защиты";}
        else {
            return getArmor().getName() + " / "+ getArmor().getArmorClass() + " Защиты";
        }
    }

    public String getWeaponView() {
        if (getWeapon() == null) {
        return "Безоружный / " + Dice.D4 + " Урона";}
        else { return getWeapon().getName()+ " / "+getWeapon().getWeaponDamage() + " Атаки"; }
    }

    public String getLvlWithNeedExp() {
        return Character.getInstance().getLevel() + "(" + getCurrentExp() + "/" + expForLevelUp();
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

    public boolean isNotEquipped(Item item) {
        return weapon != item && armor != item;
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


                String[] columnNames1 = {
                        "Имя", "Класс", "Уровень", "Здоровье", "Золото"
                };
                List<Object[]> list1 = new ArrayList<>();
                list1.add(new Object[]{c.getName(), c.specialization.getName(), c.getLvlWithNeedExp(),c.getHPView(),c.getInventory().getMoney()});
                TextTable tt1 = new TextTable(columnNames1, list1.toArray(new Object[0][0]));
                tt1.printTable();

                String[] columnNames3 = {
                        "Броня", "Оружие"
                };
                List<Object[]> list2 = new ArrayList<>();
                list2.add(new Object[]{getArmorView(),getWeaponView()});
                TextTable tt3 = new TextTable(columnNames3, list2.toArray(new Object[0][0]));
                tt3.printTable();



                String[] columnNames2 = {
                        "Характеристика", "Значение", "Бонус Характеристики",
                };
                List<Object[]> list = new ArrayList<>();
                for (Stat stat : Stat.values()) {
                    list.add(new Object[]{stat.getName(), String.valueOf(getStat(stat)), String.valueOf(factStat(stat))});
                }
                TextTable tt = new TextTable(columnNames2, list.toArray(new Object[0][0]));
                tt.printTable();
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

    public static void chooseSpecialization() {
        Menu menu = new Menu("Меню выбора Специализации", MenuSetting.HIDE_CHARACTER_MENU);

        List<Specialization> specs = new Reflections("units.character.specializations")
                .getSubTypesOf(Specialization.class).stream()
                .map(clazz -> {
                    try {
                        return clazz.getConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        throw new RuntimeException("There is no constructor for Specialization.class");
                    }
                }).collect(Collectors.toList());

        for (Specialization spec : specs) {
            menu.addItem(spec.getName(), () -> Character.getInstance().setSpecialization(spec));
        }
        menu.showAndChoose();

    }

    public void goBack() {
        System.out.println("Вы отступили!");
        Character.getInstance().setCurrentPosition(positionsHistory.get(positionsHistory.size() - 1));
    }

    public boolean checkSavingThrow(Stat stat, int difficulty) {
        int sumOfStat = getInstance().factStat(stat) + Dice.D20.roll();
        if (specialization.getSavingThrow().contains(stat)) {
            sumOfStat += getCurrentMasteryLvl();
        }
        return sumOfStat >= difficulty;
    }

    public int getCurrentMasteryLvl() {
        // return (level - 1) / 4 + 2; todo: ну прост
        if (level < 5) {
            currentMasteryLvl = 2;
        } else {
            if (level < 9) {
                currentMasteryLvl = 3;
            } else {
                if (level < 13) {
                    currentMasteryLvl = 4;
                } else {
                    if (level < 17) {
                        currentMasteryLvl = 5;
                    } else {
                        if (level < 21) {
                            currentMasteryLvl = 6;
                        }
                    }
                }
            }
        }
        return currentMasteryLvl;
    }
}

